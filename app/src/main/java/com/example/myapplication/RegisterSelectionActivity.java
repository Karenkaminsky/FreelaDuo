package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.register.RegisterActivity;
import com.example.myapplication.register.RegisterFreelancerActivity;

public class RegisterSelectionActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 1. Vincula este arquivo Java ao arquivo de layout XML 'activity_register.xml'
        setContentView(R.layout.activity_register);
        RelativeLayout cardFreelancer = findViewById(R.id.cardFreelancer);
        RelativeLayout cardEmpresa = findViewById(R.id.cardEmpresa);

        // Mapeamento dos botões 'Cadastrar' que estão dentro de cada card
        Button btnCadastrarFreelancer = findViewById(R.id.btnCadastrarFreelancer);
        Button btnCadastrarCompany = findViewById(R.id.btnCadastrarCompany);

        // Mapeamento do texto "Faça Login" no rodapé
        TextView txtBackToLogin = findViewById(R.id.txtBackToLogin);

        /*============ AÇÕES DO CARD E BOTÃO FREELANCER ============ */

        // Método auxiliar para centralizar a navegação do Freelancer em um só lugar
        Runnable irParaCadastroFreelancer = () -> {
             Intent intent = new Intent(RegisterSelectionActivity.this, RegisterFreelancerActivity.class);
            startActivity(intent);
        };

        // Permite clicar tanto no card inteiro quanto diretamente no botão do Freelancer
        if (cardFreelancer != null) {
            cardFreelancer.setOnClickListener(v -> irParaCadastroFreelancer.run());
        }
        if (btnCadastrarFreelancer != null) {
            btnCadastrarFreelancer.setOnClickListener(v -> irParaCadastroFreelancer.run());
        }

        /*============ AÇÕES DO CARD E BOTÃO EMPRESA ============*/

        // Método auxiliar para centralizar a navegação da Empresa em um só lugar
        Runnable irParaCadastroEmpresa = () -> {
            // Abre a Activity de cadastro da Empresa que já foi criada
            Intent intent = new Intent(RegisterSelectionActivity.this, RegisterCompanyActivity.class);
            startActivity(intent);
        };

        // Permite clicar tanto no card inteiro quanto diretamente no botão de Empresa
        if (cardEmpresa != null) {
            cardEmpresa.setOnClickListener(v -> irParaCadastroEmpresa.run());
        }
        if (btnCadastrarCompany != null) {
            btnCadastrarCompany.setOnClickListener(v -> irParaCadastroEmpresa.run());
        }

        /*============ AÇÃO DO RODAPÉ (VOLTAR AO LOGIN) ============*/

        if (txtBackToLogin != null) {
            // O comando finish() fecha a tela atual na pilha e retorna para a tela de Login
            txtBackToLogin.setOnClickListener(v -> finish());
        }
    }
}