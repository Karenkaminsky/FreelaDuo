package com.example.myapplication.register;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.RegisterCompanyActivity;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        LinearLayout cardFreelancer = findViewById(R.id.cardFreelancer);
        LinearLayout cardEmpresa = findViewById(R.id.cardEmpresa);
        TextView txtBackToLogin = findViewById(R.id.txtBackToLogin);

        // Clique no card Freelancer
        cardFreelancer.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterActivity.this, RegisterFreelancerActivity.class);
            startActivity(intent);
        });

        // Clique no card Empresa
        cardEmpresa.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterActivity.this, RegisterCompanyActivity.class);
            startActivity(intent);
        });

        // Voltar para Login
        txtBackToLogin.setOnClickListener(v -> finish());
    }
}