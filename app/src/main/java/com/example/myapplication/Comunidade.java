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
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Comunidade extends AppCompatActivity {

    private final int FUNDO = Color.rgb(7, 13, 23);
    private final int CARD = Color.rgb(18, 28, 45);
    private final int ROXO = Color.rgb(106, 90, 205);
    private final int LILAS = Color.rgb(192, 132, 252);
    private final int TEXTO = Color.rgb(240, 243, 250);
    private final int SECUNDARIO = Color.rgb(160, 174, 197);

    private FrameLayout areaConteudo;
    private LinearLayout listaCanais;
    private LinearLayout listaMensagens;
    private ScrollView scrollMensagens;
    private EditText campoMensagem;

    private String canalSelecionado = "geral";

    private final String[] canais = {
            "geral", "oportunidades", "projetos", "duvidas"
    };

    private final Map<String, ArrayList<Mensagem>> mensagensPorCanal =
            new HashMap<>();

    private static class Mensagem {
        String nome;
        String texto;
        int imagem;

        Mensagem(String nome, String texto, int imagem) {
            this.nome = nome;
            this.texto = texto;
            this.imagem = imagem;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comunidade);

        TextView identificacaoTela = findViewById(R.id.txtIdentificacaoTela);
        if (identificacaoTela != null) {
            identificacaoTela.setText("✦ COMUNIDADE");
        }

        areaConteudo = findViewById(R.id.areaConteudo);

        carregarMensagensExemplo();
        montarTela();
    }

    private void carregarMensagensExemplo() {

        // CANAL GERAL
        ArrayList<Mensagem> geral = new ArrayList<>();

        geral.add(new Mensagem(
                "Carol",
                "Oi, pessoal! Como estão os projetos de vocês?",
                R.drawable.carol
        ));

        geral.add(new Mensagem(
                "João",
                "Estou procurando pessoas para colaborar em um projeto novo.",
                R.drawable.joao
        ));

        geral.add(new Mensagem(
                "Lucas",
                "Também estou por aqui! Vamos trocar algumas ideias.",
                R.drawable.lucas
        ));

        mensagensPorCanal.put("geral", geral);

        // CANAL OPORTUNIDADES
        ArrayList<Mensagem> oportunidades = new ArrayList<>();

        oportunidades.add(new Mensagem(
                "Nubank",
                "VAGA ABERTA: Desenvolvedor(a) Front-end Jr.\n\n" +
                        "Estamos buscando profissionais criativos e apaixonados por tecnologia para integrar nosso time de desenvolvimento!\n\n" +
                        "Requisitos: conhecimentos em HTML, CSS, JavaScript e React.\n\n" +
                        "Modelo: Remoto\n" +
                        "Tipo: CLT\n\n" +
                        "Candidate-se pelo link:\n" +
                        "https://freeladuo.com.br/vagas/nubank-001",
                R.drawable.logo_nubank
        ));

        oportunidades.add(new Mensagem(
                "Itaú",
                "VAGA ABERTA: Desenvolvedor(a) Back-end Jr.\n\n" +
                        "Estamos em busca de talentos para atuar no desenvolvimento e evolução de soluções financeiras digitais!\n\n" +
                        "Requisitos: conhecimentos em Java, Spring Boot, APIs REST e SQL.\n\n" +
                        "Diferenciais: experiência com microsserviços e AWS.\n\n" +
                        "Modelo: Híbrido\n" +
                        "Tipo: CLT\n\n" +
                        "Candidate-se pelo link:\n" +
                        "https://freeladuo.example/vagas/itau-002",
                R.drawable.logo_itau
        ));

        oportunidades.add(new Mensagem(
                "TOTVS",
                "VAGA ABERTA: Desenvolvedor(a) Full Stack Jr.\n\n" +
                        "Estamos procurando profissionais interessados em desenvolver sistemas de gestão empresarial e soluções inovadoras para nossos clientes!\n\n" +
                        "Requisitos: conhecimentos em C#, .NET, JavaScript, HTML e CSS.\n\n" +
                        "Diferenciais: experiência com SQL Server e APIs REST.\n\n" +
                        "Modelo: Híbrido\n" +
                        "Tipo: CLT\n\n" +
                        "Candidate-se pelo link:\n" +
                        "https://freeladuo.example/vagas/totvs-003",
                R.drawable.logo_totvs
        ));

        mensagensPorCanal.put("oportunidades", oportunidades);

        // CANAL PROJETOS
        ArrayList<Mensagem> projetos = new ArrayList<>();

        projetos.add(new Mensagem(
                "Miguel",
                "Estou desenvolvendo uma aplicação e gostaria de encontrar parceiros.",
                R.drawable.miguel
        ));

        projetos.add(new Mensagem(
                "Lucas",
                "Tenho interesse! Qual tecnologia vocês estão utilizando?",
                R.drawable.lucas
        ));

        projetos.add(new Mensagem(
                "Pedro",
                "Posso ajudar na parte de desenvolvimento também!",
                R.drawable.pedro
        ));

        mensagensPorCanal.put("projetos", projetos);

        // CANAL DÚVIDAS
        ArrayList<Mensagem> duvidas = new ArrayList<>();

        duvidas.add(new Mensagem(
                "Carol",
                "Alguém pode me ajudar com uma dúvida sobre desenvolvimento web?",
                R.drawable.carol
        ));

        duvidas.add(new Mensagem(
                "Lucas",
                "Claro! Pode mandar sua dúvida.",
                R.drawable.lucas
        ));

        duvidas.add(new Mensagem(
                "João",
                "Também posso tentar ajudar!",
                R.drawable.joao
        ));

        mensagensPorCanal.put("duvidas", duvidas);
    }

    private void montarTela() {

        LinearLayout principal = new LinearLayout(this);
        principal.setOrientation(LinearLayout.VERTICAL);
        principal.setPadding(dp(16), dp(8), dp(16), dp(12));
        principal.setBackgroundColor(FUNDO);

        // TÍTULO DOS CANAIS
        TextView tituloCanais = criarTexto(
                "CANAIS DA COMUNIDADE",
                11,
                LILAS,
                true
        );

        tituloCanais.setLetterSpacing(0.06f);

        LinearLayout.LayoutParams tituloParams =
                new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                );

        tituloParams.bottomMargin = dp(10);
        principal.addView(tituloCanais, tituloParams);

        // LISTA HORIZONTAL DE CANAIS
        HorizontalScrollView scrollCanais = new HorizontalScrollView(this);
        scrollCanais.setHorizontalScrollBarEnabled(false);

        listaCanais = new LinearLayout(this);
        listaCanais.setOrientation(LinearLayout.HORIZONTAL);

        scrollCanais.addView(listaCanais);

        principal.addView(
                scrollCanais,
                new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        dp(44)
                )
        );

        // IDENTIFICAÇÃO DO CANAL SELECIONADO
        LinearLayout cabecalhoCanal = new LinearLayout(this);
        cabecalhoCanal.setOrientation(LinearLayout.VERTICAL);
        cabecalhoCanal.setPadding(dp(15), dp(13), dp(15), dp(13));
        cabecalhoCanal.setBackground(fundoArredondado(CARD, 14));

        TextView nomeCanal = criarTexto("# geral", 16, TEXTO, true);
        TextView descricaoCanal = criarTexto(
                "Converse com outros profissionais",
                12,
                SECUNDARIO,
                false
        );

        LinearLayout.LayoutParams descricaoParams =
                new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                );

        descricaoParams.topMargin = dp(4);

        cabecalhoCanal.addView(nomeCanal);
        cabecalhoCanal.addView(descricaoCanal, descricaoParams);

        LinearLayout.LayoutParams cabecalhoParams =
                new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                );

        cabecalhoParams.topMargin = dp(13);
        cabecalhoParams.bottomMargin = dp(10);

        principal.addView(cabecalhoCanal, cabecalhoParams);

        // ÁREA DE MENSAGENS
        scrollMensagens = new ScrollView(this);
        scrollMensagens.setFillViewport(true);
        scrollMensagens.setVerticalScrollBarEnabled(false);

        listaMensagens = new LinearLayout(this);
        listaMensagens.setOrientation(LinearLayout.VERTICAL);
        listaMensagens.setPadding(0, dp(4), 0, dp(8));

        scrollMensagens.addView(listaMensagens);

        principal.addView(
                scrollMensagens,
                new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        0,
                        1
                )
        );

        // CAMPO DE ENVIO
        LinearLayout areaEnvio = new LinearLayout(this);
        areaEnvio.setOrientation(LinearLayout.HORIZONTAL);
        areaEnvio.setGravity(Gravity.CENTER_VERTICAL);

        campoMensagem = new EditText(this);
        campoMensagem.setHint("Escreva uma mensagem...");
        campoMensagem.setHintTextColor(SECUNDARIO);
        campoMensagem.setTextColor(TEXTO);
        campoMensagem.setTextSize(14);
        campoMensagem.setSingleLine(true);
        campoMensagem.setPadding(dp(14), 0, dp(14), 0);
        campoMensagem.setBackground(fundoArredondado(CARD, 14));

        LinearLayout.LayoutParams campoParams =
                new LinearLayout.LayoutParams(0, dp(48), 1);

        campoParams.setMargins(0, 0, dp(8), 0);

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

        btnEnviar.setOnClickListener(v -> enviarMensagem());

        LinearLayout.LayoutParams envioParams =
                new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                );

        envioParams.topMargin = dp(10);

        principal.addView(areaEnvio, envioParams);

        areaConteudo.removeAllViews();

        areaConteudo.addView(
                principal,
                new FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                )
        );

        atualizarCanais();
        atualizarMensagens();
    }

    private void atualizarCanais() {

        listaCanais.removeAllViews();

        for (String canal : canais) {

            TextView botaoCanal = criarTexto(
                    "# " + canal,
                    12,
                    canal.equals(canalSelecionado) ? Color.WHITE : SECUNDARIO,
                    true
            );

            botaoCanal.setGravity(Gravity.CENTER);
            botaoCanal.setPadding(dp(15), 0, dp(15), 0);

            botaoCanal.setBackground(
                    fundoArredondado(
                            canal.equals(canalSelecionado) ? ROXO : CARD,
                            30
                    )
            );

            LinearLayout.LayoutParams params =
                    new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            dp(38)
                    );

            params.setMargins(0, 0, dp(8), 0);

            botaoCanal.setOnClickListener(v -> {
                canalSelecionado = canal;
                atualizarCanais();
                atualizarMensagens();
            });

            listaCanais.addView(botaoCanal, params);
        }
    }

    private void atualizarMensagens() {

        listaMensagens.removeAllViews();

        ArrayList<Mensagem> mensagens =
                mensagensPorCanal.get(canalSelecionado);

        for (Mensagem mensagem : mensagens) {
            adicionarCartaoMensagem(mensagem);
        }

        scrollMensagens.post(() ->
                scrollMensagens.fullScroll(View.FOCUS_DOWN)
        );
    }

    private void adicionarCartaoMensagem(Mensagem mensagem) {

        LinearLayout linha = new LinearLayout(this);
        linha.setOrientation(LinearLayout.HORIZONTAL);
        linha.setGravity(Gravity.TOP);

        ImageView foto = new ImageView(this);
        foto.setImageResource(mensagem.imagem);
        foto.setScaleType(ImageView.ScaleType.CENTER_CROP);
        foto.setBackground(fundoCircular());
        foto.setClipToOutline(true);

        LinearLayout.LayoutParams fotoParams =
                new LinearLayout.LayoutParams(dp(42), dp(42));

        fotoParams.setMargins(0, 0, dp(10), 0);

        linha.addView(foto, fotoParams);

        LinearLayout cartao = new LinearLayout(this);
        cartao.setOrientation(LinearLayout.VERTICAL);
        cartao.setPadding(dp(13), dp(11), dp(13), dp(11));
        cartao.setBackground(fundoArredondado(CARD, 14));

        TextView nome = criarTexto(
                mensagem.nome,
                13,
                LILAS,
                true
        );

        TextView texto = criarTexto(
                mensagem.texto,
                14,
                TEXTO,
                false
        );

        texto.setLineSpacing(dp(3), 1f);

        LinearLayout.LayoutParams textoParams =
                new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                );

        textoParams.topMargin = dp(5);

        cartao.addView(nome);
        cartao.addView(texto, textoParams);

        linha.addView(
                cartao,
                new LinearLayout.LayoutParams(
                        0,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        1
                )
        );

        LinearLayout.LayoutParams linhaParams =
                new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                );

        linhaParams.bottomMargin = dp(12);

        listaMensagens.addView(linha, linhaParams);
    }

    private void enviarMensagem() {

        String texto = campoMensagem.getText().toString().trim();

        if (texto.isEmpty()) {
            Toast.makeText(
                    this,
                    "Digite uma mensagem.",
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }

        mensagensPorCanal.get(canalSelecionado).add(
                new Mensagem("Você", texto, R.drawable.logo)
        );

        campoMensagem.setText("");
        atualizarMensagens();
    }

    private TextView criarTexto(
            String texto,
            int tamanho,
            int cor,
            boolean negrito
    ) {
        TextView view = new TextView(this);
        view.setText(texto);
        view.setTextSize(tamanho);
        view.setTextColor(cor);

        if (negrito) {
            view.setTypeface(null, Typeface.BOLD);
        }

        return view;
    }

    private GradientDrawable fundoArredondado(int cor, int raio) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(cor);
        drawable.setCornerRadius(dp(raio));
        return drawable;
    }

    private GradientDrawable fundoCircular() {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(ROXO);
        drawable.setShape(GradientDrawable.OVAL);
        return drawable;
    }

    private int dp(float valor) {
        return (int) (valor * getResources()
                .getDisplayMetrics().density + 0.5f);
    }

    @Override
    protected void onResume() {
        super.onResume();
        BottomNavigationHelper.atualizarAbaSelecionada(this, R.id.nav_network);
    }
}