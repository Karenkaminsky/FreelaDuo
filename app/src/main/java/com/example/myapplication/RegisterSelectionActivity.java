package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterSelectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Garanta que este ID corresponda ao arquivo XML criado no Passo 3
        setContentView(R.layout.activity_register);

        // Mapeia os elementos do seu XML pelos IDs reais
        LinearLayout cardFreelancer = findViewById(R.id.cardFreelancer);
        LinearLayout cardEmpresa = findViewById(R.id.cardEmpresa);
        TextView txtBackToLogin = findViewById(R.id.txtBackToLogin);

        // Clique para ir para o cadastro de Freelancer (Borda Roxa)
        if (cardFreelancer != null) {
            cardFreelancer.setOnClickListener(v -> {
                // Insira aqui a Intent da sua activity de Freelancer, ex:
                // Intent intent = new Intent(RegisterSelectionActivity.this, RegisterFreelancerActivity.class);
                // startActivity(intent);
            });
        }

        // Clique para ir para o cadastro de Empresa (Borda Azul-Petróleo)
        if (cardEmpresa != null) {
            cardEmpresa.setOnClickListener(v -> {
                Intent intent = new Intent(RegisterSelectionActivity.this, RegisterCompanyActivity.class);
                startActivity(intent);
            });
        }

        // Clique para voltar ao Login
        if (txtBackToLogin != null) {
            txtBackToLogin.setOnClickListener(v -> finish());
        }
    }
}