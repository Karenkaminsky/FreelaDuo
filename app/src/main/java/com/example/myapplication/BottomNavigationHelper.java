package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BottomNavigationHelper {

    public static void configurar(Activity activity, int itemSelecionado) {

        BottomNavigationView menu =
                activity.findViewById(R.id.bottomNavigation);

        if (menu == null) {
            return;
        }

        menu.setSelectedItemId(itemSelecionado);

        menu.setOnItemSelectedListener(item -> {

            Class<?> destino;

            int id = item.getItemId();

            if (id == R.id.nav_home) {
                destino = FeedActivity.class;

            } else if (id == R.id.nav_network) {
                destino = Comunidade.class;

            } else if (id == R.id.nav_post) {
                destino = Publicar.class;

            } else if (id == R.id.nav_jobs) {
                destino = Vagas.class;

            } else if (id == R.id.nav_profile) {
                destino = ProfileActivity.class;

            } else {
                return false;
            }

            if (!activity.getClass().equals(destino)) {
                Intent intent = new Intent(activity, destino);
                activity.startActivity(intent);
                activity.finish();
            }

            return true;
        });
    }
}