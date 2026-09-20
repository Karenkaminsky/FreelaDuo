package com.example.myapplication.register;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.FeedActivity;
import com.example.myapplication.R;
import com.example.myapplication.RegisterSelectionActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 1. Verificação de sessão (Saber se o usuário já fez login anteriormente)
        SharedPreferences preferences = getSharedPreferences("UserSession", MODE_PRIVATE);
        boolean isLoggedIn = preferences.getBoolean("isLoggedIn", false);

        if (isLoggedIn) {
            // Se já estiver logado, redireciona direto para o FeedActivity
            Intent intent = new Intent(MainActivity.this, FeedActivity.class);
            startActivity(intent);
            finish(); // Fecha a MainActivity para não voltar a ela ao pressionar "Voltar"
            return;
        }

        setContentView(R.layout.activity_main);

        // Mapeamento dos componentes da interface
        MaterialButton btnLogin = findViewById(R.id.btnLogin);
        MaterialCardView btnGoogle = findViewById(R.id.btnGoogle);
        MaterialCardView btnGithub = findViewById(R.id.btnGithub);
        MaterialCardView btnLinkedin = findViewById(R.id.btnLinkedin);
        TextView txtRegister = findViewById(R.id.txtRegister);

        // 2. Ação do botão Entrar (Login)
        btnLogin.setOnClickListener(v -> {
            // Salva o estado de login na sessão do aplicativo
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("isLoggedIn", true);
            editor.apply();

            // Navega para o FeedActivity
            Intent intent = new Intent(MainActivity.this, FeedActivity.class);
            startActivity(intent);
            finish(); // Fecha a tela de login
        });

        // Ações dos botões de login social
        btnGoogle.setOnClickListener(v ->
                Toast.makeText(this, "Redirecionando para login com Google...", Toast.LENGTH_SHORT).show()
        );

        btnGithub.setOnClickListener(v ->
                Toast.makeText(this, "Redirecionando para login com GitHub...", Toast.LENGTH_SHORT).show()
        );

        btnLinkedin.setOnClickListener(v ->
                Toast.makeText(this, "Redirecionando para login com LinkedIn...", Toast.LENGTH_SHORT).show()
        );

        // Redirecionamento para a tela de seleção de cadastro
        txtRegister.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, RegisterSelectionActivity.class);
            startActivity(intent);
        });
    }
}