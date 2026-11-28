package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.view.MenuItem;

import androidx.annotation.NonNull;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BottomNavigationHelper {

    // 1. Configura os cliques (chamado no onCreate de cada tela)
    public static void configurar(Activity activity, int itemIdSelecionado) {
        BottomNavigationView bottomNav = activity.findViewById(R.id.bottomNavigation);
        if (bottomNav == null) return;

        bottomNav.setOnItemSelectedListener(null);
        bottomNav.setSelectedItemId(itemIdSelecionado);

        bottomNav.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == itemIdSelecionado) {
                    return false; // Já está na tela
                }

                Intent intent = null;
                if (itemId == R.id.nav_home) intent = new Intent(activity, FeedActivity.class);
                else if (itemId == R.id.nav_network) intent = new Intent(activity, Comunidade.class);
                else if (itemId == R.id.nav_post) intent = new Intent(activity, Publicar.class);
                else if (itemId == R.id.nav_jobs) intent = new Intent(activity, Vagas.class);
                else if (itemId == R.id.nav_profile) intent = new Intent(activity, ProfileActivity.class);

                if (intent != null) {
                    // Traz a tela para frente sem recriar (evita o "surgir")
                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    activity.startActivity(intent);
                    activity.overridePendingTransition(0, 0); // Sem animação
                }
                return true;
            }
        });
    }

    // 2. FORÇA o menu a marcar a aba correta quando a tela volta a ficar visível
    // (Chamado no onResume de cada tela)
    public static void atualizarAbaSelecionada(Activity activity, int itemIdSelecionado) {
        BottomNavigationView bottomNav = activity.findViewById(R.id.bottomNavigation);
        if (bottomNav != null) {
            // Remove temporariamente o listener para não disparar navegação à toa
            bottomNav.setOnItemSelectedListener(null);
            bottomNav.setSelectedItemId(itemIdSelecionado);
            // Reconfigura o listener (para manter os cliques funcionando)
            configurar(activity, itemIdSelecionado);
        }
    }
}