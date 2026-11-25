package com.example.myapplication;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class PlanosActivity extends AppCompatActivity {

    private ImageButton btnBackPlanos;
    private MaterialButton btnPlanoGratuito;
    private MaterialButton btnEscolherPremium;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planos);

        // Inicializar componentes
        btnBackPlanos = findViewById(R.id.btnBackPlanos);
        btnPlanoGratuito = findViewById(R.id.btnPlanoGratuito);
        btnEscolherPremium = findViewById(R.id.btnEscolherPremium);

        // Voltar para a tela anterior
        btnBackPlanos.setOnClickListener(v -> finish());

        // Plano gratuito
        btnPlanoGratuito.setOnClickListener(v ->
                Toast.makeText(this,
                        "Você já está utilizando o plano Gratuito.",
                        Toast.LENGTH_SHORT).show()
        );

        // Plano Premium
        btnEscolherPremium.setOnClickListener(v ->
                Toast.makeText(this,
                        "A contratação do plano Premium estará disponível futuramente.",
                        Toast.LENGTH_LONG).show()
        );
    }
}