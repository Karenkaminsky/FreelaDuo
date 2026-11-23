
package com.example.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class FeedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        // Ajusta a cor da logo "Duo" para SlateBlue
        TextView txtLogoDuo = findViewById(R.id.txtLogoDuo);

        if (txtLogoDuo != null) {
            txtLogoDuo.setTextColor(Color.parseColor("#6A5ACD"));
        }

        BottomNavigationView bottomNav =
                findViewById(R.id.bottomNavigation);

        if (bottomNav != null) {

            // Marca o ícone Início ao abrir a tela
            bottomNav.setSelectedItemId(R.id.nav_home);

            bottomNav.setOnItemSelectedListener(item -> {

                int itemId = item.getItemId();

                // Início
                if (itemId == R.id.nav_home) {
                    return true;

                    // Comunidade
                } else if (itemId == R.id.nav_network) {

                    Intent intent = new Intent(
                            FeedActivity.this,
                            Comunidade.class
                    );

                    startActivity(intent);
                    return true;

                    // Publicar
                } else if (itemId == R.id.nav_post) {

                    Intent intent = new Intent(
                            FeedActivity.this,
                            Publicar.class
                    );

                    startActivity(intent);
                    return true;

                    // Vagas
                } else if (itemId == R.id.nav_jobs) {

                    Intent intent = new Intent(
                            FeedActivity.this,
                            Vagas.class
                    );

                    startActivity(intent);
                    return true;

                    // Perfil
                } else if (itemId == R.id.nav_profile) {

                    Intent intent = new Intent(
                            FeedActivity.this,
                            ProfileActivity.class
                    );

                    startActivity(intent);
                    return true;
                }

                return false;
            });
        }
    }
}
