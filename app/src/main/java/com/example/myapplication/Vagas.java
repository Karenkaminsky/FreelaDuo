package com.example.myapplication;

import android.app.AlertDialog;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Locale;

public class Vagas extends AppCompatActivity {

    private EditText edtPesquisarVaga;
    private HorizontalScrollView scrollFiltros;
    private LinearLayout containerFiltros;
    private LinearLayout listaVagas;

    private String filtroSelecionado = "Todas";

    private final ArrayList<Vaga> vagas = new ArrayList<>();

    private static class Vaga {
        String cargo;
        String empresa;
        String categoria;
        String nivel;
        String modalidade;
        String tipo;
        String descricao;
        String periodo;
        String horario;
        String remuneracao;

        int logoEmpresa;
        int fotoCompartilhador;
        String compartilhadaPor;

        Vaga(String cargo, String empresa, String categoria, String nivel, String modalidade, String tipo, String descricao, String periodo, String horario, String remuneracao, int logoEmpresa, int fotoCompartilhador, String compartilhadaPor) {
            this.cargo = cargo;
            this.empresa = empresa;
            this.categoria = categoria;
            this.nivel = nivel;
            this.modalidade = modalidade;
            this.tipo = tipo;
            this.descricao = descricao;
            this.periodo = periodo;
            this.horario = horario;
            this.remuneracao = remuneracao;
            this.logoEmpresa = logoEmpresa;
            this.fotoCompartilhador = fotoCompartilhador;
            this.compartilhadaPor = compartilhadaPor;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vagas);

        // Identificação da tela no cabeçalho
        TextView identificacaoTela = findViewById(R.id.txtIdentificacaoTela);
        if (identificacaoTela != null) {
            identificacaoTela.setText("✦ VAGAS");
        }

        edtPesquisarVaga = findViewById(R.id.edtPesquisarVaga);
        scrollFiltros = findViewById(R.id.scrollFiltrosVagas);
        containerFiltros = findViewById(R.id.containerFiltrosVagas);
        listaVagas = findViewById(R.id.listaVagas);

        carregarVagasExemplo();
        configurarFiltros();
        configurarPesquisa();

        // Remove o cartão estático do XML (os cartões são criados dinamicamente)
        listaVagas.removeAllViews();

        atualizarListaVagas();

        edtPesquisarVaga.setOnFocusChangeListener((v, temFoco) -> {
            if (temFoco) {
                scrollFiltros.setVisibility(View.VISIBLE);
            }
        });
    }

    // Carrega as oportunidades demonstrativas
    private void carregarVagasExemplo() {
        vagas.add(new Vaga("Desenvolvedor Full Stack", "TOTVS", "Desenvolvimento", "Pleno", "Remoto", "Temporário", "Desenvolver funcionalidades para uma plataforma web, trabalhar com APIs e colaborar com a equipe de produto.", "01/12/2026 a 15/12/2026", "Segunda a sexta - 4 horas por dia", "R$ 80,00 por hora", R.drawable.logo_totvs, R.drawable.carol, "Caroline Araújo"));
        vagas.add(new Vaga("Desenvolvedor Front-end", "Nubank", "Desenvolvimento", "Júnior", "Remoto", "Freelance", "Desenvolver interfaces modernas e responsivas utilizando HTML, CSS, JavaScript e React.", "05/12/2026 a 20/12/2026", "Segunda a sexta - 6 horas por dia", "R$ 70,00 por hora", R.drawable.logo_nubank, R.drawable.joao, "João Yarlei"));
        vagas.add(new Vaga("Desenvolvedor Back-end", "Itaú", "Desenvolvimento", "Pleno", "Híbrido", "Freelance", "Desenvolver e manter APIs utilizando Java e Spring Boot, além de trabalhar com bancos de dados SQL.", "02/12/2026 a 18/12/2026", "Segunda a sexta - 5 horas por dia", "R$ 85,00 por hora", R.drawable.logo_itau, R.drawable.lucas, "Lucas Ferreira Silva"));
        vagas.add(new Vaga("Desenvolvedor Android", "Tech Solutions", "Desenvolvimento", "Júnior", "Híbrido", "Freelance", "Criar e melhorar telas de aplicativos Android utilizando Java e integração com APIs.", "05/12/2026 a 20/12/2026", "Segunda a sexta - 6 horas por dia", "R$ 55,00 por hora", R.drawable.empresatech, R.drawable.miguel, "Antônio Miguel Leão Neto"));
        vagas.add(new Vaga("Analista de QA", "Quality Tech", "QA", "Júnior", "Remoto", "Temporário", "Executar testes manuais, registrar erros e auxiliar na validação de sistemas.", "03/12/2026 a 17/12/2026", "Segunda a sexta - 4 horas por dia", "R$ 45,00 por hora", R.drawable.logo, R.drawable.allan, "Allan Fernandes"));
        vagas.add(new Vaga("Designer UI/UX", "Creative Studio", "Design", "Pleno", "Remoto", "Freelance", "Desenvolver protótipos, interfaces e experiências digitais para aplicações web e mobile.", "10/12/2026 a 30/12/2026", "Horário flexível", "R$ 65,00 por hora", 0, R.drawable.pedro, "Pedro Araújo"));
        vagas.add(new Vaga("Desenvolvedora Full Stack", "NVIDIA", "Desenvolvimento", "Pleno", "Remoto", "Freelance", "Desenvolver e manter aplicações Full Stack, com foco principal no Back-end, criação de APIs, integração de sistemas, banco de dados e otimização de serviços.", "15/12/2026 a 30/12/2026", "Segunda a sexta - 6 horas por dia", "R$ 100,00 por hora", R.drawable.nvidia, R.drawable.perfilkaren, "Karen Kaminsky Silva"));
    }

    private void configurarPesquisa() {
        edtPesquisarVaga.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) { atualizarListaVagas(); }
            @Override public void afterTextChanged(Editable s) {}
        });
    }

    private void configurarFiltros() {
        adicionarFiltro("Todas");
        adicionarFiltro("Desenvolvimento");
        adicionarFiltro("Design");
        adicionarFiltro("QA");
        adicionarFiltro("Cibersegurança");
        adicionarFiltro("Júnior");
        adicionarFiltro("Pleno");
        adicionarFiltro("Sênior");
        adicionarFiltro("Remoto");
    }

    private void adicionarFiltro(String nome) {
        TextView filtro = new TextView(this);
        filtro.setText(nome);
        filtro.setTextSize(12);
        filtro.setGravity(Gravity.CENTER);
        filtro.setPadding(dp(12), dp(9), dp(12), dp(9));
        atualizarVisualFiltro(filtro, nome);

        LinearLayout.LayoutParams parametros = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        parametros.setMargins(0, 0, dp(8), 0);
        filtro.setLayoutParams(parametros);

        filtro.setOnClickListener(v -> {
            filtroSelecionado = nome;
            atualizarFiltrosVisuais();
            atualizarListaVagas();
        });
        containerFiltros.addView(filtro);
    }

    private void atualizarFiltrosVisuais() {
        for (int i = 0; i < containerFiltros.getChildCount(); i++) {
            View view = containerFiltros.getChildAt(i);
            if (view instanceof TextView) {
                TextView filtro = (TextView) view;
                atualizarVisualFiltro(filtro, filtro.getText().toString());
            }
        }
    }

    private void atualizarVisualFiltro(TextView filtro, String nome) {
        boolean selecionado = nome.equals(filtroSelecionado);
        GradientDrawable fundo = new GradientDrawable();
        fundo.setCornerRadius(dp(20));

        if (selecionado) {
            fundo.setColor(Color.rgb(106, 90, 205));
            filtro.setTextColor(Color.WHITE);
        } else {
            fundo.setColor(Color.rgb(55, 48, 82));
            filtro.setTextColor(Color.rgb(221, 214, 254));
        }
        filtro.setBackground(fundo);
    }

    private void atualizarListaVagas() {
        listaVagas.removeAllViews();
        String pesquisa = normalizar(edtPesquisarVaga.getText().toString().trim());
        int quantidadeEncontrada = 0;

        for (Vaga vaga : vagas) {
            boolean correspondePesquisa = normalizar(vaga.cargo).contains(pesquisa) || normalizar(vaga.empresa).contains(pesquisa) || normalizar(vaga.categoria).contains(pesquisa) || normalizar(vaga.nivel).contains(pesquisa) || normalizar(vaga.modalidade).contains(pesquisa) || normalizar(vaga.descricao).contains(pesquisa);
            boolean correspondeFiltro = filtroSelecionado.equals("Todas") || vaga.categoria.equalsIgnoreCase(filtroSelecionado) || vaga.nivel.equalsIgnoreCase(filtroSelecionado) || vaga.modalidade.equalsIgnoreCase(filtroSelecionado);

            if (correspondePesquisa && correspondeFiltro) {
                criarCartaoVaga(vaga);
                quantidadeEncontrada++;
            }
        }

        if (quantidadeEncontrada == 0) {
            TextView vazio = criarTexto("Nenhuma oportunidade encontrada.", 15, Color.rgb(167, 176, 192));
            vazio.setGravity(Gravity.CENTER);
            vazio.setPadding(dp(12), dp(30), dp(12), dp(30));
            listaVagas.addView(vazio);
        }
    }

    private void criarCartaoVaga(Vaga vaga) {
        LinearLayout cartao = new LinearLayout(this);
        cartao.setOrientation(LinearLayout.VERTICAL);
        cartao.setPadding(dp(16), dp(16), dp(16), dp(16));
        cartao.setBackgroundResource(R.drawable.bg_card_vaga);

        LinearLayout.LayoutParams parametrosCartao = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        parametrosCartao.setMargins(0, 0, 0, dp(16));
        cartao.setLayoutParams(parametrosCartao);

        LinearLayout cabecalho = new LinearLayout(this);
        cabecalho.setOrientation(LinearLayout.HORIZONTAL);
        cabecalho.setGravity(Gravity.CENTER_VERTICAL);

        if (vaga.logoEmpresa != 0) {
            ImageView logo = new ImageView(this);
            logo.setImageResource(vaga.logoEmpresa);
            logo.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            logo.setPadding(dp(5), dp(5), dp(5), dp(5));
            logo.setBackgroundResource(R.drawable.bg_logo_vaga);
            logo.setClipToOutline(true);
            logo.setLayoutParams(new LinearLayout.LayoutParams(dp(54), dp(54)));
            cabecalho.addView(logo);
        } else {
            TextView inicial = new TextView(this);
            inicial.setText(vaga.empresa.substring(0, 1).toUpperCase());
            inicial.setTextColor(Color.WHITE);
            inicial.setTextSize(20);
            inicial.setTypeface(null, Typeface.BOLD);
            inicial.setGravity(Gravity.CENTER);
            inicial.setBackgroundResource(R.drawable.bg_logo_vaga);
            inicial.setLayoutParams(new LinearLayout.LayoutParams(dp(54), dp(54)));
            cabecalho.addView(inicial);
        }

        LinearLayout informacoes = new LinearLayout(this);
        informacoes.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams parametrosInfo = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
        parametrosInfo.setMargins(dp(12), 0, 0, 0);
        informacoes.setLayoutParams(parametrosInfo);

        informacoes.addView(criarTexto(vaga.cargo, 17, Color.WHITE, true));
        informacoes.addView(criarTexto(vaga.empresa, 14, Color.rgb(196, 181, 253), true));
        informacoes.addView(criarTexto(vaga.tipo, 12, Color.rgb(167, 176, 192)));
        cabecalho.addView(informacoes);
        cartao.addView(cabecalho);

        LinearLayout etiquetas = new LinearLayout(this);
        etiquetas.setOrientation(LinearLayout.HORIZONTAL);
        etiquetas.setPadding(0, dp(14), 0, dp(8));
        etiquetas.addView(criarEtiqueta(vaga.nivel));
        etiquetas.addView(criarEtiqueta(vaga.modalidade));
        etiquetas.addView(criarEtiqueta(vaga.categoria));
        cartao.addView(etiquetas);

        TextView descricao = criarTexto(vaga.descricao, 14, Color.rgb(226, 232, 240));
        descricao.setPadding(0, dp(8), 0, dp(8));
        descricao.setLineSpacing(dp(3), 1);
        cartao.addView(descricao);

        cartao.addView(criarTexto("Período: " + vaga.periodo, 13, Color.WHITE));

        TextView horario = criarTexto(vaga.horario, 12, Color.rgb(167, 176, 192));
        horario.setPadding(0, dp(5), 0, dp(10));
        cartao.addView(horario);

        cartao.addView(criarTexto(vaga.remuneracao, 19, Color.WHITE, true));

        LinearLayout compartilhamento = new LinearLayout(this);
        compartilhamento.setOrientation(LinearLayout.HORIZONTAL);
        compartilhamento.setGravity(Gravity.CENTER_VERTICAL);
        compartilhamento.setPadding(0, dp(14), 0, dp(2));

        ImageView foto = new ImageView(this);
        foto.setImageResource(vaga.fotoCompartilhador);
        foto.setScaleType(ImageView.ScaleType.CENTER_CROP);
        GradientDrawable fundoFoto = new GradientDrawable();
        fundoFoto.setShape(GradientDrawable.OVAL);
        fundoFoto.setColor(Color.rgb(55, 48, 82));
        foto.setBackground(fundoFoto);
        foto.setClipToOutline(true);
        foto.setLayoutParams(new LinearLayout.LayoutParams(dp(34), dp(34)));
        compartilhamento.addView(foto);

        TextView textoCompartilhamento = criarTexto("Compartilhada por " + vaga.compartilhadaPor, 12, Color.rgb(167, 176, 192));
        LinearLayout.LayoutParams parametrosCompartilhamento = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        parametrosCompartilhamento.setMargins(dp(9), 0, 0, 0);
        textoCompartilhamento.setLayoutParams(parametrosCompartilhamento);
        compartilhamento.addView(textoCompartilhamento);
        cartao.addView(compartilhamento);

        Button detalhes = new Button(this);
        detalhes.setText("Ver detalhes da vaga");
        detalhes.setTransformationMethod(null);
        detalhes.setTextColor(Color.WHITE);
        detalhes.setTextSize(14);
        detalhes.setBackgroundTintList(ColorStateList.valueOf(Color.rgb(106, 90, 205)));

        LinearLayout.LayoutParams parametrosBotao = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, dp(50));
        parametrosBotao.setMargins(0, dp(14), 0, 0);
        detalhes.setLayoutParams(parametrosBotao);
        detalhes.setOnClickListener(v -> abrirDetalhesVaga(vaga));
        cartao.addView(detalhes);

        listaVagas.addView(cartao);
    }

    private TextView criarEtiqueta(String nome) {
        TextView etiqueta = new TextView(this);
        etiqueta.setText(nome);
        etiqueta.setTextSize(11);
        etiqueta.setTextColor(Color.rgb(221, 214, 254));
        etiqueta.setPadding(dp(9), dp(7), dp(9), dp(7));
        etiqueta.setBackgroundResource(R.drawable.bg_etiqueta_vaga);
        LinearLayout.LayoutParams parametros = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        parametros.setMargins(0, 0, dp(6), 0);
        etiqueta.setLayoutParams(parametros);
        return etiqueta;
    }

    private TextView criarTexto(String conteudo, int tamanho, int cor) {
        return criarTexto(conteudo, tamanho, cor, false);
    }

    private TextView criarTexto(String conteudo, int tamanho, int cor, boolean negrito) {
        TextView texto = new TextView(this);
        texto.setText(conteudo);
        texto.setTextSize(tamanho);
        texto.setTextColor(cor);
        if (negrito) texto.setTypeface(null, Typeface.BOLD);
        return texto;
    }

    private void abrirDetalhesVaga(Vaga vaga) {
        String mensagem = "Empresa: " + vaga.empresa + "\n\n" + "Categoria: " + vaga.categoria + "\n" + "Tipo: " + vaga.tipo + "\n" + "Nível: " + vaga.nivel + "\n" + "Modalidade: " + vaga.modalidade + "\n" + "Período: " + vaga.periodo + "\n" + "Horário: " + vaga.horario + "\n" + "Remuneração: " + vaga.remuneracao + "\n\n" + "Atividades:\n" + vaga.descricao;
        new AlertDialog.Builder(this).setTitle(vaga.cargo).setMessage(mensagem).setPositiveButton("Fechar", null).show();
    }

    private String normalizar(String texto) {
        return Normalizer.normalize(texto.toLowerCase(Locale.ROOT), Normalizer.Form.NFD).replaceAll("\\p{M}", "");
    }

    private int dp(float valor) {
        return (int) (valor * getResources().getDisplayMetrics().density + 0.5f);
    }

    @Override
    protected void onResume() {
        super.onResume();
        BottomNavigationHelper.atualizarAbaSelecionada(this, R.id.nav_jobs);
    }
}