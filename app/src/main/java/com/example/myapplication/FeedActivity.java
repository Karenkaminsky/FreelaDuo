package com.example.myapplication;

import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
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

        TextView txtLogoDuo = findViewById(R.id.txtLogoDuo);
        Shader textShader = new LinearGradient(
                0f, 0f, txtLogoDuo.getPaint().measureText("Duo"), 0f,
                new int[]{
                        Color.parseColor("#7B2CBF"), // Roxo
                        Color.parseColor("#FF007A"), // Rosa
                        Color.parseColor("#0072FF")  // Azul
                },
                null, Shader.TileMode.CLAMP
        );
        txtLogoDuo.getPaint().setShader(textShader);

        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigation);
        bottomNav.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_home) {
                Toast.makeText(this, "Início", Toast.LENGTH_SHORT).show();
                return true;
            } else if (itemId == R.id.nav_network) {
                Toast.makeText(this, "Minha rede", Toast.LENGTH_SHORT).show();
                return true;
            } else if (itemId == R.id.nav_post) {
                Toast.makeText(this, "Publicar", Toast.LENGTH_SHORT).show();
                return true;
            } else if (itemId == R.id.nav_jobs) {
                Toast.makeText(this, "Vagas", Toast.LENGTH_SHORT).show();
                return true;
            } else if (itemId == R.id.nav_profile) {
                Toast.makeText(this, "Perfil", Toast.LENGTH_SHORT).show();
                return true;
            }
            return false;
        });
    }
}