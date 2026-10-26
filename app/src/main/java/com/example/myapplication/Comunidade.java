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
    private final int VERDE = Color.rgb(52, 211, 153);

    private FrameLayout areaConteudo;

    private Button btnFeed;
    private Button btnProjetos;
    private Button btnChat;

    private String canalSelecionado = "geral";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comunidade);

        areaConteudo = findViewById(R.id.areaConteudo);
        btnFeed = findViewById(R.id.btnFeed);
        btnProjetos = findViewById(R.id.btnProjetos);
        btnChat = findViewById(R.id.btnChat);

        btnFeed.setOnClickListener(v -> mostrarFeed());
        btnProjetos.setOnClickListener(v -> mostrarProjetos());
        btnChat.setOnClickListener(v -> mostrarChat());

        mostrarFeed();
    }

    private void selecionarAba(String aba) {
        estilizarAba(btnFeed, aba.equals("feed"));
        estilizarAba(btnProjetos, aba.equals("projetos"));
        estilizarAba(btnChat, aba.equals("chat"));
    }

    private void estilizarAba(Button botao, boolean selecionado) {
        botao.setBackground(fundoArredondado(
                selecionado ? ROXO : CARD, 14));
        botao.setTextColor(selecionado ? Color.WHITE : TEXTO_SECUNDARIO);
    }

    private GradientDrawable fundoArredondado(int cor, int raio) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(cor);
        drawable.setCornerRadius(raio * getResources().getDisplayMetrics().density);
        return drawable;
    }

    private TextView criarTexto(String texto, float tamanho, int cor) {
        TextView view = new TextView(this);
        view.setText(texto);
        view.setTextSize(tamanho);
        view.setTextColor(cor);
        return view;
    }

    private TextView criarTitulo(String texto) {
        TextView view = criarTexto(texto, 18, TEXTO);
        view.setTypeface(null, Typeface.BOLD);
        return view;
    }

    private LinearLayout criarColuna() {
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        return layout;
    }

    private LinearLayout criarLinha() {
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.HORIZONTAL);
        layout.setGravity(Gravity.CENTER_VERTICAL);
        return layout;
    }

    private LinearLayout criarCard() {
        LinearLayout card = criarColuna();
        card.setPadding(dp(16), dp(16), dp(16), dp(16));
        card.setBackground(fundoArredondado(CARD, 16));

        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);

        params.bottomMargin = dp(12);
        card.setLayoutParams(params);

        return card;
    }

    private Button criarBotao(String texto) {
        Button botao = new Button(this);
        botao.setText(texto);
        botao.setAllCaps(false);
        botao.setTextColor(Color.WHITE);
        botao.setTextSize(13);
        botao.setPadding(dp(8), 0, dp(8), 0);
        botao.setMinHeight(dp(44));
        botao.setBackground(fundoArredondado(ROXO, 12));
        return botao;
    }

    private int dp(int valor) {
        return (int) (valor *
                getResources().getDisplayMetrics().density + 0.5f);
    }

    private void prepararConteudo(String aba) {
        areaConteudo.removeAllViews();
        selecionarAba(aba);

        ScrollView scroll = new ScrollView(this);
        scroll.setFillViewport(true);

        LinearLayout coluna = criarColuna();
        coluna.setPadding(dp(16), dp(16), dp(16), dp(20));

        scroll.addView(coluna);
        areaConteudo.addView(scroll);

        // O conteúdo da aba será adicionado à coluna retornada.
        areaConteudo.setTag(coluna);
    }

    private LinearLayout obterColunaConteudo() {
        return (LinearLayout) areaConteudo.getTag();
    }

    // ---------------------------------------------------------
    // ABA 1: FEED DA COMUNIDADE
    // ---------------------------------------------------------

    private void mostrarFeed() {
        prepararConteudo("feed");

        LinearLayout coluna = obterColunaConteudo();

        coluna.addView(criarTitulo("Feed da comunidade"));

        TextView descricao = criarTexto(
                "Compartilhe ideias, encontre profissionais e acompanhe novidades.",
                14, TEXTO_SECUNDARIO);

        LinearLayout.LayoutParams descParams =
                new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);

        descParams.topMargin = dp(6);
        descParams.bottomMargin = dp(18);
        coluna.addView(descricao, descParams);

        LinearLayout publicar = criarCard();
        publicar.addView(criarTitulo("Compartilhe com a comunidade"));

        TextView dica = criarTexto(
                "Publique uma ideia, uma conquista ou uma novidade.",
                14, TEXTO_SECUNDARIO);

        LinearLayout.LayoutParams dicaParams =
                new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);

        dicaParams.topMargin = dp(8);
        dicaParams.bottomMargin = dp(14);
        publicar.addView(dica, dicaParams);

        Button btnPublicar = criarBotao("Criar publicação");
        btnPublicar.setOnClickListener(v ->
                Toast.makeText(this,
                        "A criação de publicações será implementada na próxima etapa.",
                        Toast.LENGTH_SHORT).show());

        publicar.addView(btnPublicar);
        coluna.addView(publicar);

        adicionarPublicacao(
                coluna,
                "KM",
                "Karen Martins",
                "Designer freelancer",
                "Acabei de finalizar um projeto de identidade visual! " +
                        "Estou compartilhando um pouco do meu trabalho com a comunidade.",
                "Projeto de identidade visual",
                "Curtidas: 12   •   Comentários: 4"
        );

        adicionarPublicacao(
                coluna,
                "IT",
                "Itaú",
                "Oportunidade profissional",
                "Estamos buscando profissionais freelancers para colaborar " +
                        "em novos projetos. Compartilhe seu portfólio e conheça oportunidades.",
                "Oportunidade para freelancers",
                "Curtidas: 28   •   Comentários: 9"
        );
    }

    private void adicionarPublicacao(
            LinearLayout coluna,
            String iniciais,
            String nome,
            String subtitulo,
            String mensagem,
            String tituloProjeto,
            String engajamento) {

        LinearLayout card = criarCard();

        LinearLayout cabecalho = criarLinha();

        TextView avatar = criarTexto(iniciais, 15, Color.WHITE);
        avatar.setTypeface(null, Typeface.BOLD);
        avatar.setGravity(Gravity.CENTER);
        avatar.setBackground(fundoArredondado(ROXO, 30));

        LinearLayout.LayoutParams avatarParams =
                new LinearLayout.LayoutParams(dp(44), dp(44));

        cabecalho.addView(avatar, avatarParams);

        LinearLayout identificacao = criarColuna();
        identificacao.setPadding(dp(10), 0, 0, 0);
        identificacao.addView(criarTitulo(nome));
        identificacao.addView(criarTexto(subtitulo, 12, TEXTO_SECUNDARIO));

        cabecalho.addView(identificacao);
        card.addView(cabecalho);

        TextView texto = criarTexto(mensagem, 14, TEXTO);

        LinearLayout.LayoutParams textoParams =
                new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);

        textoParams.topMargin = dp(16);
        textoParams.bottomMargin = dp(14);
        card.addView(texto, textoParams);

        LinearLayout projeto = criarColuna();
        projeto.setPadding(dp(12), dp(12), dp(12), dp(12));
        projeto.setBackground(fundoArredondado(
                Color.rgb(29, 42, 63), 12));

        projeto.addView(criarTitulo(tituloProjeto));

        LinearLayout.LayoutParams projetoParams =
                new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);

        projetoParams.bottomMargin = dp(12);
        card.addView(projeto, projetoParams);

        card.addView(criarTexto(engajamento, 12, TEXTO_SECUNDARIO));

        LinearLayout acoes = criarLinha();
        acoes.setGravity(Gravity.CENTER_VERTICAL);

        Button curtir = criarBotao("Curtir");
        Button comentar = criarBotao("Comentar");

        LinearLayout.LayoutParams acaoParams =
                new LinearLayout.LayoutParams(0, dp(44), 1);

        acaoParams.topMargin = dp(12);
        acaoParams.rightMargin = dp(6);

        acoes.addView(curtir, acaoParams);

        LinearLayout.LayoutParams comentarParams =
                new LinearLayout.LayoutParams(0, dp(44), 1);

        comentarParams.topMargin = dp(12);
        comentarParams.leftMargin = dp(6);

        acoes.addView(comentar, comentarParams);

        curtir.setOnClickListener(v ->
                Toast.makeText(this,
                        "A interação de curtida será conectada depois.",
                        Toast.LENGTH_SHORT).show());

        comentar.setOnClickListener(v ->
                Toast.makeText(this,
                        "Os comentários serão implementados depois.",
                        Toast.LENGTH_SHORT).show());

        card.addView(acoes);
        coluna.addView(card);
    }

    // ---------------------------------------------------------
    // ABA 2: PROJETOS E OPORTUNIDADES
    // ---------------------------------------------------------

    private void mostrarProjetos() {
        prepararConteudo("projetos");

        LinearLayout coluna = obterColunaConteudo();

        coluna.addView(criarTitulo("Projetos e oportunidades"));

        TextView descricao = criarTexto(
                "Apresente seu trabalho ou descubra novas oportunidades.",
                14, TEXTO_SECUNDARIO);

        LinearLayout.LayoutParams descParams =
                new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);

        descParams.topMargin = dp(6);
        descParams.bottomMargin = dp(18);
        coluna.addView(descricao, descParams);

        Button novoProjeto = criarBotao("+ Apresentar projeto");
        novoProjeto.setOnClickListener(v ->
                Toast.makeText(this,
                        "O formulário de projetos será implementado depois.",
                        Toast.LENGTH_SHORT).show());

        coluna.addView(novoProjeto);

        adicionarProjeto(
                coluna,
                "Plataforma de e-commerce",
                "Desenvolvimento Web",
                "Projeto de loja virtual com catálogo de produtos, " +
                        "carrinho e interface responsiva.",
                "Java • API • Banco de dados"
        );

        adicionarProjeto(
                coluna,
                "Identidade visual de marca",
                "Design",
                "Criação de identidade visual, paleta de cores e materiais " +
                        "gráficos para uma nova marca.",
                "Branding • Design • Portfólio"
        );

        adicionarProjeto(
                coluna,
                "Desenvolvedor freelancer",
                "Oportunidade",
                "Empresa procurando profissional para colaborar em um " +
                        "projeto digital por demanda.",
                "Freelancer • Projeto remoto"
        );
    }

    private void adicionarProjeto(
            LinearLayout coluna,
            String titulo,
            String categoria,
            String descricao,
            String tecnologias) {

        LinearLayout card = criarCard();

        TextView tag = criarTexto(categoria.toUpperCase(), 11, VERDE);
        tag.setTypeface(null, Typeface.BOLD);

        card.addView(tag);

        LinearLayout.LayoutParams tituloParams =
                new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);

        tituloParams.topMargin = dp(8);
        tituloParams.bottomMargin = dp(8);

        card.addView(criarTitulo(titulo), tituloParams);

        TextView texto = criarTexto(descricao, 14, TEXTO_SECUNDARIO);

        LinearLayout.LayoutParams textoParams =
                new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);

        textoParams.bottomMargin = dp(12);
        card.addView(texto, textoParams);

        card.addView(criarTexto(tecnologias, 12, TEXTO));

        Button verMais = criarBotao("Ver detalhes");

        LinearLayout.LayoutParams botaoParams =
                new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        dp(44));

        botaoParams.topMargin = dp(14);

        verMais.setOnClickListener(v ->
                Toast.makeText(this,
                        "Os detalhes deste item serão implementados depois.",
                        Toast.LENGTH_SHORT).show());

        card.addView(verMais, botaoParams);
        coluna.addView(card);
    }

    // ---------------------------------------------------------
    // ABA 3: CHAT DA COMUNIDADE
    // ---------------------------------------------------------

    private void mostrarChat() {
        prepararConteudo("chat");

        LinearLayout coluna = obterColunaConteudo();

        coluna.addView(criarTitulo("Chat da comunidade"));

        TextView descricao = criarTexto(
                "Converse com profissionais e participe dos canais.",
                14, TEXTO_SECUNDARIO);

        LinearLayout.LayoutParams descParams =
                new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);

        descParams.topMargin = dp(6);
        descParams.bottomMargin = dp(16);

        coluna.addView(descricao, descParams);

        LinearLayout canaisCard = criarCard();
        canaisCard.addView(criarTitulo("Canais"));

        adicionarCanal(canaisCard, "# geral", "geral");
        adicionarCanal(canaisCard, "# oportunidades", "oportunidades");
        adicionarCanal(canaisCard, "# projetos", "projetos");
        adicionarCanal(canaisCard, "# dúvidas", "duvidas");

        coluna.addView(canaisCard);

        LinearLayout conversa = criarCard();
        conversa.setMinimumHeight(dp(240));

        TextView tituloCanal = criarTitulo("# " + canalSelecionado);
        tituloCanal.setTag("tituloCanal");
        conversa.addView(tituloCanal);

        TextView status = criarTexto(
                "Mensagens de demonstração",
                12, TEXTO_SECUNDARIO);

        LinearLayout.LayoutParams statusParams =
                new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);

        statusParams.topMargin = dp(4);
        statusParams.bottomMargin = dp(16);

        conversa.addView(status, statusParams);

        LinearLayout mensagens = criarColuna();
        mensagens.setTag("mensagens");

        conversa.addView(mensagens);

        adicionarMensagem(mensagens, "Marina", "Olá, pessoal! Bem-vindos!");
        adicionarMensagem(mensagens, "Rafael", "Alguém trabalhando em um projeto novo?");
        adicionarMensagem(mensagens, "Camila", "Estou procurando colaboradores para um app.");

        LinearLayout.LayoutParams mensagensParams =
                new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);

        mensagensParams.topMargin = dp(8);
        conversa.addView(criarTexto(
                "As mensagens desta versão são apenas exemplos.",
                11, TEXTO_SECUNDARIO), mensagensParams);

        coluna.addView(conversa);

        LinearLayout envio = criarLinha();

        EditText campoMensagem = new EditText(this);
        campoMensagem.setSingleLine(true);
        campoMensagem.setTextColor(TEXTO);
        campoMensagem.setHintTextColor(TEXTO_SECUNDARIO);
        campoMensagem.setHint("Digite uma mensagem...");
        campoMensagem.setTextSize(14);
        campoMensagem.setPadding(dp(12), 0, dp(12), 0);
        campoMensagem.setBackground(fundoArredondado(CARD, 12));

        LinearLayout.LayoutParams campoParams =
                new LinearLayout.LayoutParams(0, dp(50), 1);

        campoParams.rightMargin = dp(8);
        envio.addView(campoMensagem, campoParams);

        Button enviar = criarBotao("Enviar");
        envio.addView(enviar, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, dp(50)));

        enviar.setOnClickListener(v -> {
            String mensagem = campoMensagem.getText().toString().trim();

            if (mensagem.isEmpty()) {
                Toast.makeText(this,
                        "Digite uma mensagem antes de enviar.",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            adicionarMensagem(mensagens, "Você", mensagem);
            campoMensagem.setText("");
        });

        LinearLayout.LayoutParams envioParams =
                new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);

        envioParams.topMargin = dp(12);
        coluna.addView(envio, envioParams);
    }

    private void adicionarCanal(
            LinearLayout canaisCard,
            String texto,
            String canal) {

        Button botao = new Button(this);
        botao.setText(texto);
        botao.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
        botao.setAllCaps(false);
        botao.setTextSize(14);
        botao.setTextColor(TEXTO);
        botao.setBackgroundColor(Color.TRANSPARENT);

        canaisCard.addView(botao, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, dp(44)));

        botao.setOnClickListener(v -> {
            canalSelecionado = canal;
            mostrarChat();
        });
    }

    private void adicionarMensagem(
            LinearLayout mensagens,
            String autor,
            String texto) {

        LinearLayout mensagemCard = criarColuna();
        mensagemCard.setPadding(dp(10), dp(10), dp(10), dp(10));
        mensagemCard.setBackground(fundoArredondado(
                Color.rgb(29, 42, 63), 10));

        LinearLayout.LayoutParams cardParams =
                new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);

        cardParams.bottomMargin = dp(8);
        mensagemCard.setLayoutParams(cardParams);

        TextView nome = criarTexto(autor, 13, VERDE);
        nome.setTypeface(null, Typeface.BOLD);

        mensagemCard.addView(nome);

        LinearLayout.LayoutParams textoParams =
                new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);

        textoParams.topMargin = dp(4);

        mensagemCard.addView(
                criarTexto(texto, 14, TEXTO), textoParams);

        mensagens.addView(mensagemCard);
    }
}

