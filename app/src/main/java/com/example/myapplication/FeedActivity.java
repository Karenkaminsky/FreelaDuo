package com.example.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class FeedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        // Ajusta a cor da logo "Duo" para SlateBlue
        TextView txtLogoDuo = findViewById(R.id.txtLogoDuo);
        txtLogoDuo.setTextColor(Color.parseColor("#6A5ACD"));

        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigation);

        bottomNav.setSelectedItemId(R.id.nav_home);

        bottomNav.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.nav_home) {
                Intent intent = new Intent(FeedActivity.this, FeedActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                return true;
            } else if (itemId == R.id.nav_network) {
                Toast.makeText(this, "Minha rede em breve", Toast.LENGTH_SHORT).show();
                return true;
            } else if (itemId == R.id.nav_post) {
                Toast.makeText(this, "Publicar em breve", Toast.LENGTH_SHORT).show();
                return true;
            } else if (itemId == R.id.nav_jobs) {
                Toast.makeText(this, "Vagas em breve", Toast.LENGTH_SHORT).show();
                return true;
            } else if (itemId == R.id.nav_profile) {
                // Redireciona para a tela de Perfil
                Intent intent = new Intent(FeedActivity.this, ProfileActivity.class);
                startActivity(intent);
                return true;
            }
            return false;
        });
    }
}