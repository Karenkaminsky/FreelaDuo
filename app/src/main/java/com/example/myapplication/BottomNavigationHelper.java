
package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.view.MenuItem;

import androidx.annotation.NonNull;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BottomNavigationHelper {

    public static void configurar(Activity activity, int itemIdSelecionado) {

        BottomNavigationView bottomNav =
                activity.findViewById(R.id.bottomNavigation);

        if (bottomNav == null) return;

        bottomNav.setOnItemSelectedListener(null);
        bottomNav.setSelectedItemId(itemIdSelecionado);

        bottomNav.setOnItemSelectedListener(
                new BottomNavigationView.OnItemSelectedListener() {

                    @Override
                    public boolean onNavigationItemSelected(
                            @NonNull MenuItem item) {

                        int itemId = item.getItemId();

                        if (itemId == itemIdSelecionado) {
                            return true;
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

                            intent.addFlags(
                                    Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                            Intent.FLAG_ACTIVITY_SINGLE_TOP
                            );

                            activity.startActivity(intent);
                            activity.overridePendingTransition(0, 0);
                        }

                        return true;
                    }
                }
        );
    }
}
