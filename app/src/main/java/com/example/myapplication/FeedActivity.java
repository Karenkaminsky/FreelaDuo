package com.example.myapplication;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class FeedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        // Configura o menu inferior (isso fica no onCreate)
        BottomNavigationHelper.configurar(this, R.id.nav_home);

        // Ajusta a cor da logo "Duo" para SlateBlue
        TextView txtLogoDuo = findViewById(R.id.txtLogoDuo);
        if (txtLogoDuo != null) {
            txtLogoDuo.setTextColor(Color.parseColor("#6A5ACD"));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Atualiza a aba do menu sempre que a tela volta a ficar visível
        BottomNavigationHelper.atualizarAbaSelecionada(this, R.id.nav_home);
    }
}