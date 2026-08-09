package com.example.myapplication;

import com.example.myapplication.register.RegisterActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Mapeia os componentes do XML pelos IDs
        LinearLayout btnGoogle = findViewById(R.id.btnGoogle);
        LinearLayout btnGithub = findViewById(R.id.btnGithub);
        LinearLayout btnLinkedin = findViewById(R.id.btnLinkedin);
        TextView txtRegister = findViewById(R.id.txtRegister);

        // Clique no Botão Google
        btnGoogle.setOnClickListener(v -> {
            Toast.makeText(this, "Redirecionando para login com Google...", Toast.LENGTH_SHORT).show();
            // Aqui entra a integração do SDK do Google (OAuth)
        });

        // Clique no Botão GitHub
        btnGithub.setOnClickListener(v -> {
            Toast.makeText(this, "Redirecionando para login com GitHub...", Toast.LENGTH_SHORT).show();
            // Aqui entra a integração OAuth do GitHub
        });

        // Clique no Botão LinkedIn
        btnLinkedin.setOnClickListener(v -> {
            Toast.makeText(this, "Redirecionando para login com LinkedIn...", Toast.LENGTH_SHORT).show();
            // Aqui entra a integração OAuth do LinkedIn
        });

        // Clique no Link de Cadastro
        txtRegister.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }
}