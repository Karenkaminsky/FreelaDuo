package com.example.myapplication;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Comunidade extends AppCompatActivity {

    private final int FUNDO = Color.rgb(7, 13, 23);
    private final int CARD = Color.rgb(18, 28, 45);
    private final int ROXO = Color.rgb(106, 90, 205);
    private final int TEXTO = Color.rgb(240, 243, 250);
    private final int TEXTO_SECUNDARIO = Color.rgb(160, 174, 197);

    private LinearLayout listaMensagens;
    private ScrollView scrollMensagens;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comunidade);

        BottomNavigationHelper.configurar(this, R.id.nav_network);

        TextView identificacaoTela = findViewById(R.id.txtIdentificacaoTela);
        if (identificacaoTela != null) {
            identificacaoTela.setText("✦ COMUNIDADE");
        }

        FrameLayout areaConteudo = findViewById(R.id.areaConteudo);

        montarChat();

        areaConteudo.addView(
                criarConteudoChat(),
                new FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                )
        );
    }

    private View criarConteudoChat() {

        LinearLayout principal = new LinearLayout(this);
        principal.setOrientation(LinearLayout.VERTICAL);
        principal.setPadding(dp(16), dp(8), dp(16), dp(12));
        principal.setBackgroundColor(FUNDO);

        // Identificação do canal
        LinearLayout cabecalhoChat = new LinearLayout(this);
        cabecalhoChat.setOrientation(LinearLayout.VERTICAL);
        cabecalhoChat.setPadding(dp(16), dp(14), dp(16), dp(14));
        cabecalhoChat.setBackground(fundoArredondado(CARD, 16));

        TextView tituloCanal = criarTexto("# geral", 17, TEXTO, true);

        TextView descricaoCanal = criarTexto(
                "Canal principal da comunidade",
                12,
                TEXTO_SECUNDARIO,
                false
        );

        LinearLayout.LayoutParams descricaoParams =
                new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                );
        descricaoParams.topMargin = dp(5);

        cabecalhoChat.addView(tituloCanal);
        cabecalhoChat.addView(descricaoCanal, descricaoParams);

        principal.addView(cabecalhoChat);

        // Área de mensagens
        scrollMensagens = new ScrollView(this);
        scrollMensagens.setFillViewport(true);
        scrollMensagens.setVerticalScrollBarEnabled(false);

        LinearLayout.LayoutParams scrollParams =
                new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        0,
                        1
                );
        scrollParams.topMargin = dp(14);
        scrollParams.bottomMargin = dp(12);

        listaMensagens = new LinearLayout(this);
        listaMensagens.setOrientation(LinearLayout.VERTICAL);
        listaMensagens.setPadding(0, dp(4), 0, dp(8));

        scrollMensagens.addView(listaMensagens);

        principal.addView(scrollMensagens, scrollParams);

        // Campo de envio
        LinearLayout areaEnvio = new LinearLayout(this);
        areaEnvio.setOrientation(LinearLayout.HORIZONTAL);
        areaEnvio.setGravity(Gravity.CENTER_VERTICAL);

        EditText campoMensagem = new EditText(this);
        campoMensagem.setHint("Escreva uma mensagem...");
        campoMensagem.setHintTextColor(TEXTO_SECUNDARIO);
        campoMensagem.setTextColor(TEXTO);
        campoMensagem.setTextSize(14);
        campoMensagem.setSingleLine(true);
        campoMensagem.setPadding(dp(15), dp(12), dp(15), dp(12));
        campoMensagem.setBackground(fundoArredondado(CARD, 14));

        LinearLayout.LayoutParams campoParams =
                new LinearLayout.LayoutParams(0, dp(48), 1);
        campoParams.setMargins(0, 0, dp(9), 0);

        areaEnvio.addView(campoMensagem, campoParams);

        Button btnEnviar = new Button(this);
        btnEnviar.setText("Enviar");
        btnEnviar.setTextColor(Color.WHITE);
        btnEnviar.setTextSize(12);
        btnEnviar.setAllCaps(false);
        btnEnviar.setTypeface(null, Typeface.BOLD);
        btnEnviar.setPadding(dp(12), 0, dp(12), 0);
        btnEnviar.setBackground(fundoArredondado(ROXO, 14));

        areaEnvio.addView(
                btnEnviar,
                new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        dp(48)
                )
        );

        btnEnviar.setOnClickListener(v -> {
            String mensagem = campoMensagem.getText().toString().trim();

            if (mensagem.isEmpty()) {
                Toast.makeText(this, "Digite uma mensagem.", Toast.LENGTH_SHORT).show();
                return;
            }

            adicionarMensagem("Você", mensagem);
            campoMensagem.setText("");

            scrollMensagens.post(() ->
                    scrollMensagens.fullScroll(View.FOCUS_DOWN)
            );
        });

        principal.addView(areaEnvio);

        return principal;
    }

    private void montarChat() {
        // Mensagens demonstrativas
        adicionarMensagem(
                "Marina",
                "Oi, pessoal! Alguém está trabalhando em algum projeto novo?"
        );

        adicionarMensagem(
                "Rafael",
                "Estou desenvolvendo um site e procurando alguém para ajudar no design."
        );

        adicionarMensagem(
                "Camila",
                "Que legal! Também estou buscando oportunidades para colaborar."
        );
    }

    private void adicionarMensagem(String nome, String mensagem) {

        LinearLayout cartao = new LinearLayout(this);
        cartao.setOrientation(LinearLayout.VERTICAL);
        cartao.setPadding(dp(14), dp(11), dp(14), dp(11));
        cartao.setBackground(fundoArredondado(CARD, 14));

        TextView nomeUsuario = criarTexto(nome, 13, ROXO, true);

        TextView textoMensagem = criarTexto(
                mensagem,
                14,
                TEXTO,
                false
        );
        textoMensagem.setLineSpacing(dp(3), 1f);

        LinearLayout.LayoutParams mensagemParams =
                new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                );
        mensagemParams.topMargin = dp(6);

        cartao.addView(nomeUsuario);
        cartao.addView(textoMensagem, mensagemParams);

        LinearLayout.LayoutParams cartaoParams =
                new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                );
        cartaoParams.bottomMargin = dp(10);

        listaMensagens.addView(cartao, cartaoParams);
    }

    private TextView criarTexto(
            String texto,
            int tamanho,
            int cor,
            boolean negrito
    ) {
        TextView textView = new TextView(this);
        textView.setText(texto);
        textView.setTextSize(tamanho);
        textView.setTextColor(cor);

        if (negrito) {
            textView.setTypeface(null, Typeface.BOLD);
        }

        return textView;
    }

    private GradientDrawable fundoArredondado(int cor, int raio) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(cor);
        drawable.setCornerRadius(dp(raio));
        return drawable;
    }

    private int dp(float valor) {
        return (int) (valor * getResources()
                .getDisplayMetrics().density + 0.5f);
    }
}