package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.register.RegisterFreelancerActivity;

public class RegisterSelectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        RelativeLayout cardFreelancer = findViewById(R.id.cardFreelancer);
        RelativeLayout cardEmpresa = findViewById(R.id.cardEmpresa);

        Button btnCadastrarFreelancer = findViewById(R.id.btnCadastrarFreelancer);
        Button btnCadastrarCompany = findViewById(R.id.btnCadastrarCompany);

        TextView txtBackToLogin = findViewById(R.id.txtBackToLogin);

        /*============ AÇÕES FREELANCER ============*/
        Runnable irParaCadastroFreelancer = () -> {
            Intent intent = new Intent(RegisterSelectionActivity.this, RegisterFreelancerActivity.class);
            startActivity(intent);
        };

        if (cardFreelancer != null) {
            cardFreelancer.setOnClickListener(v -> irParaCadastroFreelancer.run());
        }
        if (btnCadastrarFreelancer != null) {
            btnCadastrarFreelancer.setOnClickListener(v -> irParaCadastroFreelancer.run());
        }

        /*============ AÇÕES EMPRESA ============*/
        Runnable irParaCadastroEmpresa = () -> {
            Intent intent = new Intent(RegisterSelectionActivity.this, RegisterCompanyActivity.class);
            startActivity(intent);
        };

        if (cardEmpresa != null) {
            cardEmpresa.setOnClickListener(v -> irParaCadastroEmpresa.run());
        }
        if (btnCadastrarCompany != null) {
            btnCadastrarCompany.setOnClickListener(v -> irParaCadastroEmpresa.run());
        }

        /*============ AÇÃO VOLTAR AO LOGIN ============*/
        if (txtBackToLogin != null) {
            txtBackToLogin.setOnClickListener(v -> finish());
        }
    }
}