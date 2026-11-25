package com.example.myapplication;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class PlanosActivity extends AppCompatActivity {

    private ImageButton btnBackPlanos;
    private MaterialButton btnPlanoGratuito;
    private MaterialButton btnEscolherPremium;

    private SharedPreferences preferencias;
    private String planoAtual;

    private static final String PREFS_PLANOS = "FreelaDuoPlanos";
    private static final String CHAVE_PLANO = "plano_atual";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planos);

        // Inicializar componentes
        btnBackPlanos = findViewById(R.id.btnBackPlanos);
        btnPlanoGratuito = findViewById(R.id.btnPlanoGratuito);
        btnEscolherPremium = findViewById(R.id.btnEscolherPremium);

        // Recuperar plano salvo
        preferencias = getSharedPreferences(PREFS_PLANOS, MODE_PRIVATE);

        planoAtual = preferencias.getString(CHAVE_PLANO, "gratuito");

        // Atualizar aparência dos planos
        atualizarBotoes();

        // Voltar para a tela anterior
        btnBackPlanos.setOnClickListener(v -> finish());

        // Selecionar plano Gratuito
        btnPlanoGratuito.setOnClickListener(v -> {
            salvarPlano("gratuito");

            Toast.makeText(this,
                    "Plano Gratuito selecionado!",
                    Toast.LENGTH_SHORT).show();
        });

        // Selecionar plano Premium
        btnEscolherPremium.setOnClickListener(v -> {
            salvarPlano("premium");

            Toast.makeText(this,
                    "Plano Premium selecionado com sucesso!",
                    Toast.LENGTH_SHORT).show();
        });
    }

    // Salvar escolha do usuário
    private void salvarPlano(String novoPlano) {

        planoAtual = novoPlano;

        preferencias.edit()
                .putString(CHAVE_PLANO, planoAtual)
                .apply();

        atualizarBotoes();
    }

    // Atualizar conforme o plano selecionado
    private void atualizarBotoes() {

        boolean gratuitoAtivo = planoAtual.equals("gratuito");

        btnPlanoGratuito.setEnabled(!gratuitoAtivo);
        btnEscolherPremium.setEnabled(gratuitoAtivo);

        if (gratuitoAtivo) {

            btnPlanoGratuito.setText("Plano em utilização");
            btnEscolherPremium.setText("Conhecer plano Premium");

        } else {

            btnPlanoGratuito.setText("Voltar ao plano Gratuito");
            btnEscolherPremium.setText("Plano em utilização");
        }
    }
}