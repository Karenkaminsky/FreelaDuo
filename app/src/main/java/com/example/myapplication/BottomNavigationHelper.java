package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.view.MenuItem;

import androidx.annotation.NonNull;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BottomNavigationHelper {

    public static void configurar(Activity activity, int itemIdSelecionado) {
        BottomNavigationView bottomNav = activity.findViewById(R.id.bottomNavigation);
        if (bottomNav == null) return;

        // 1. Remove qualquer listener antigo para evitar conflitos
        bottomNav.setOnItemSelectedListener(null);

        // 2. Marca visualmente a aba correta
        bottomNav.setSelectedItemId(itemIdSelecionado);

        // 3. Adiciona o novo listener
        bottomNav.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                // Se clicar na aba que já está, não faz nada (evita bug)
                if (itemId == itemIdSelecionado) {
                    return false;
                }

                Intent intent = null;

                if (itemId == R.id.nav_home) {
                    intent = new Intent(activity, FeedActivity.class);
                } else if (itemId == R.id.nav_network) {
                    intent = new Intent(activity, Comunidade.class);
                } else if (itemId == R.id.nav_post) {
                    intent = new Intent(activity, Publicar.class);
                } else if (itemId == R.id.nav_jobs) {
                    intent = new Intent(activity, Vagas.class);
                } else if (itemId == R.id.nav_profile) {
                    intent = new Intent(activity, ProfileActivity.class);
                }

                if (intent != null) {
                    // Garante que a nova tela seja única na pilha
                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    activity.startActivity(intent);
                    activity.overridePendingTransition(0, 0); // Sem animação
                }
                return true;
            }
        });
    }
}