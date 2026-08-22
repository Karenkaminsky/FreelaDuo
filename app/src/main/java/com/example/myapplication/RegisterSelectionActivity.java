package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterSelectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 1. Vincula este arquivo Java ao arquivo de layout XML 'activity_register.xml'
        setContentView(R.layout.activity_register);

        /*
         * CORREÇÃO DO CRASH:
         * No XML, os IDs 'cardFreelancer' e 'cardEmpresa' são componentes <RelativeLayout>.
         * Declarar como 'LinearLayout' antes causava o encerramento do app por incompatibilidade de tipo.
         */
        RelativeLayout cardFreelancer = findViewById(R.id.cardFreelancer);
        RelativeLayout cardEmpresa = findViewById(R.id.cardEmpresa);

        // Mapeamento dos botões 'Cadastrar' que estão dentro de cada card
        Button btnCadastrarFreelancer = findViewById(R.id.btnCadastrarFreelancer);
        Button btnCadastrarCompany = findViewById(R.id.btnCadastrarCompany);

        // Mapeamento do texto "Faça Login" no rodapé
        TextView txtBackToLogin = findViewById(R.id.txtBackToLogin);

        /*
         * ==========================================
         *  AÇÕES DO CARD E BOTÃO FREELANCER
         * ==========================================
         */
        // Método auxiliar para centralizar a navegação do Freelancer em um só lugar
        Runnable irParaCadastroFreelancer = () -> {
            // Quando tiver a Activity do Freelancer pronta, descomente a linha abaixo:
            // Intent intent = new Intent(RegisterSelectionActivity.this, RegisterFreelancerActivity.class);
            // startActivity(intent);
        };

        // Permite clicar tanto no card inteiro quanto diretamente no botão do Freelancer
        if (cardFreelancer != null) {
            cardFreelancer.setOnClickListener(v -> irParaCadastroFreelancer.run());
        }
        if (btnCadastrarFreelancer != null) {
            btnCadastrarFreelancer.setOnClickListener(v -> irParaCadastroFreelancer.run());
        }

        /*
         * ==========================================
         *  AÇÕES DO CARD E BOTÃO EMPRESA
         * ==========================================
         */
        // Método auxiliar para centralizar a navegação da Empresa em um só lugar
        Runnable irParaCadastroEmpresa = () -> {
            // Abre a Activity de cadastro da Empresa que você já possui criada
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

        /*
         * ==========================================
         *  AÇÃO DO RODAPÉ (VOLTAR AO LOGIN)
         * ==========================================
         */
        if (txtBackToLogin != null) {
            // O comando finish() fecha a tela atual na pilha e retorna para a tela anterior (Login)
            txtBackToLogin.setOnClickListener(v -> finish());
        }
    }
}