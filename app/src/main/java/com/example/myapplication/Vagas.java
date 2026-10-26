
package com.example.myapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class Vagas extends AppCompatActivity {

    private EditText edtPesquisarVaga;
    private HorizontalScrollView scrollFiltros;
    private LinearLayout containerFiltros;
    private LinearLayout listaVagas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vagas);

        // Referências aos componentes do layout
        edtPesquisarVaga = findViewById(R.id.edtPesquisarVaga);
        scrollFiltros = findViewById(R.id.scrollFiltrosVagas);
        containerFiltros = findViewById(R.id.containerFiltrosVagas);
        listaVagas = findViewById(R.id.listaVagas);

        Button btnDetalhesVaga = findViewById(R.id.btnDetalhesVaga);

        // Abrir detalhes da oportunidade
        btnDetalhesVaga.setOnClickListener(v -> abrirDetalhesVaga());

        // Exibir os filtros somente quando o usuário pesquisar
        edtPesquisarVaga.setOnFocusChangeListener((v, temFoco) -> {
            if (temFoco) {
                exibirFiltros();
            }
        });
    }

    private void exibirFiltros() {
        scrollFiltros.setVisibility(View.VISIBLE);

        if (containerFiltros.getChildCount() > 0) {
            return;
        }

        adicionarFiltro("Todas");
        adicionarFiltro("Desenvolvimento");
        adicionarFiltro("Design");
        adicionarFiltro("QA");
        adicionarFiltro("Cibersegurança");
        adicionarFiltro("Pleno");
        adicionarFiltro("Remoto");
    }

    private void adicionarFiltro(String nome) {
        TextView filtro = new TextView(this);

        filtro.setText(nome);
        filtro.setTextSize(12);
        filtro.setTextColor(0xFFDDD6FE);
        filtro.setPadding(dp(12), dp(9), dp(12), dp(9));

        android.graphics.drawable.GradientDrawable fundo =
                new android.graphics.drawable.GradientDrawable();

        fundo.setColor(0xFF373052);
        fundo.setCornerRadius(dp(20));

        filtro.setBackground(fundo);

        LinearLayout.LayoutParams parametros =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );

        parametros.setMargins(0, 0, dp(8), 0);
        filtro.setLayoutParams(parametros);

        filtro.setOnClickListener(v -> {
            edtPesquisarVaga.setText(nome.equals("Todas") ? "" : nome);
            edtPesquisarVaga.setSelection(
                    edtPesquisarVaga.getText().length()
            );
        });

        containerFiltros.addView(filtro);
    }

    private void abrirDetalhesVaga() {
        new android.app.AlertDialog.Builder(this)
                .setTitle("Desenvolvedor Full Stack")
                .setMessage(
                        "Empresa: TOTVS\n\n"
                                + "Tipo: Freelance temporário\n"
                                + "Nível: Pleno\n"
                                + "Modalidade: Remoto\n"
                                + "Início: 10/11/2026\n"
                                + "Término: 16/11/2026\n"
                                + "Dias: Segunda a sexta\n"
                                + "Carga diária: 4 horas por dia\n"
                                + "Valor: R$ 80,00 por hora\n\n"
                                + "Atividades:\n"
                                + "Desenvolver funcionalidades para uma "
                                + "plataforma web, trabalhar com APIs e "
                                + "colaborar com a equipe de produto."
                )
                .setPositiveButton("Fechar", null)
                .show();
    }

    private int dp(float valor) {
        return (int) (valor * getResources()
                .getDisplayMetrics().density + 0.5f);
    }
}