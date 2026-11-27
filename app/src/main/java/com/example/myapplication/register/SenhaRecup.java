package com.example.myapplication.register;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.myapplication.R;

public class SenhaRecup {

    private final Activity activity;
    private Dialog dialog;

    public SenhaRecup(Activity activity) {
        this.activity = activity;
    }

    public void mostrar() {

        dialog = new Dialog(activity);
        dialog.setContentView(R.layout.senharecup);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);

        // --- Componentes principais ---
        LinearLayout modal = dialog.findViewById(R.id.modalSenhaRecup);
        LinearLayout formulario = dialog.findViewById(R.id.layoutFormularioRecup);
        LinearLayout sucesso = dialog.findViewById(R.id.layoutSucessoRecup);
        LinearLayout validarCodigo = dialog.findViewById(R.id.layoutValidarCodigo);

        EditText edtEmail = dialog.findViewById(R.id.edtEmailRecup);
        EditText edtCodigo = dialog.findViewById(R.id.edtCodigo);

        Button btnEnviar = dialog.findViewById(R.id.btnEnviarRecup);
        Button btnEntendi = dialog.findViewById(R.id.btnEntendiRecup);
        Button btnValidarCodigo = dialog.findViewById(R.id.btnValidarCodigo);

        TextView btnFechar = dialog.findViewById(R.id.btnFecharRecup);
        TextView btnVoltar = dialog.findViewById(R.id.btnVoltarLogin);
        TextView btnFecharSucesso = dialog.findViewById(R.id.btnFecharSucessoRecup);
        TextView txtEmailConfirmacao = dialog.findViewById(R.id.txtEmailConfirmacaoRecup);

        View circulo = dialog.findViewById(R.id.circuloSucessoRecup);
        View check = dialog.findViewById(R.id.txtCheckSucesso);

        // Fundo do modal e campo de e-mail
        modal.setBackground(criarFundo(Color.rgb(11, 13, 25), Color.rgb(62, 84, 190), 26, 1));
        edtEmail.setBackground(criarFundo(Color.rgb(18, 21, 37), Color.rgb(52, 62, 112), 12, 1));

        // Gradiente dos botões
        GradientDrawable gradiente = new GradientDrawable(
                GradientDrawable.Orientation.LEFT_RIGHT,
                new int[]{Color.rgb(55, 126, 247), Color.rgb(126, 49, 239)}
        );
        gradiente.setCornerRadius(dp(12));

        // Aplica o gradiente nos 3 botões
        btnEnviar.setBackground(gradiente);
        btnEnviar.setBackgroundTintList(null);
        btnEnviar.setTextColor(Color.WHITE);

        btnEntendi.setBackground(gradiente.getConstantState() != null ? gradiente.getConstantState().newDrawable().mutate() : gradiente);
        btnEntendi.setBackgroundTintList(null);
        btnEntendi.setTextColor(Color.WHITE);

        btnValidarCodigo.setBackground(gradiente.getConstantState() != null ? gradiente.getConstantState().newDrawable().mutate() : gradiente);
        btnValidarCodigo.setBackgroundTintList(null);
        btnValidarCodigo.setTextColor(Color.WHITE);

        // Fechar o modal
        btnFechar.setOnClickListener(v -> fechar());
        btnVoltar.setOnClickListener(v -> fechar());
        btnFecharSucesso.setOnClickListener(v -> fechar());

        // --- AÇÃO 1: ENVIAR E-MAIL E MOSTRAR SUCESSO ---
        btnEnviar.setOnClickListener(v -> {
            String email = edtEmail.getText().toString().trim();

            if (TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                edtEmail.setError("Informe um e-mail válido");
                edtEmail.requestFocus();
                return;
            }

            txtEmailConfirmacao.setText(email);
            formulario.setVisibility(View.GONE);
            sucesso.setVisibility(View.VISIBLE);

            // Prepara a animação de entrada
            sucesso.setAlpha(0f);
            sucesso.setTranslationY(dp(18));
            circulo.setScaleX(0.1f); circulo.setScaleY(0.1f); circulo.setAlpha(0f);
            check.setScaleX(0.3f); check.setScaleY(0.3f); check.setAlpha(0f);

            sucesso.animate().alpha(1f).translationY(0f).setDuration(350).start();

            // O círculo cresce com um efeito elástico
            AnimatorSet animacaoCirculo = new AnimatorSet();
            animacaoCirculo.playTogether(
                    ObjectAnimator.ofFloat(circulo, View.SCALE_X, 0.1f, 1f),
                    ObjectAnimator.ofFloat(circulo, View.SCALE_Y, 0.1f, 1f),
                    ObjectAnimator.ofFloat(circulo, View.ALPHA, 0f, 1f)
            );
            animacaoCirculo.setDuration(550);
            animacaoCirculo.setInterpolator(new OvershootInterpolator(1.3f));
            animacaoCirculo.start();

            // O check aparece em seguida
            check.postDelayed(() -> {
                if (dialog != null && dialog.isShowing()) {
                    AnimatorSet animacaoCheck = new AnimatorSet();
                    animacaoCheck.playTogether(
                            ObjectAnimator.ofFloat(check, View.SCALE_X, 0.3f, 1f),
                            ObjectAnimator.ofFloat(check, View.SCALE_Y, 0.3f, 1f),
                            ObjectAnimator.ofFloat(check, View.ALPHA, 0f, 1f)
                    );
                    animacaoCheck.setDuration(300);
                    animacaoCheck.setInterpolator(new OvershootInterpolator());
                    animacaoCheck.start();
                }
            }, 220);
        });

        // --- AÇÃO 2: TELA DE SUCESSO -> TELA DE VALIDAR CÓDIGO ---
        btnEntendi.setOnClickListener(v -> {
            sucesso.setVisibility(View.GONE);
            validarCodigo.setVisibility(View.VISIBLE);
        });

        // --- AÇÃO 3: VALIDAR O CÓDIGO DIGITADO ---
        btnValidarCodigo.setOnClickListener(v -> {
            String codigo = edtCodigo.getText().toString().trim();

            if (codigo.length() != 6) {
                edtCodigo.setError("Digite os 6 dígitos");
                edtCodigo.requestFocus();
                return;
            }

            // Exibe o alerta perguntando se deseja ir para o site
            new AlertDialog.Builder(activity)
                    .setTitle("Código Validado!")
                    .setMessage("Seu código está correto. Deseja ir para a página de redefinição de senha?")
                    .setPositiveButton("Redefinir Senha", (alertDialog, which) -> {
                        // Abre o navegador do celular no site do FreelaDuo
                        // ATENÇÃO: Troque o IP abaixo pelo IP da sua máquina na hora de apresentar!
                        String url = "http://192.168.1.14:8000/redefinir_senha.html";
                        android.content.Intent browserIntent = new android.content.Intent(android.content.Intent.ACTION_VIEW, android.net.Uri.parse(url));
                        activity.startActivity(browserIntent);
                        fechar();
                    })
                    .setNegativeButton("Cancelar", (alertDialog, which) -> fechar())
                    .show();
        });

        // --- Configuração da janela (Desfoque, Tamanho, etc) ---
        Window window = dialog.getWindow();
        if (window != null) {
            window.setBackgroundDrawableResource(android.R.color.transparent);
            window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            WindowManager.LayoutParams params = window.getAttributes();
            params.dimAmount = 0.78f;
            params.gravity = Gravity.CENTER;
            window.setAttributes(params);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                window.addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
                WindowManager.LayoutParams blurParams = window.getAttributes();
                blurParams.setBlurBehindRadius(dp(24));
                window.setAttributes(blurParams);
            }
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        }

        dialog.show();

        Window janela = dialog.getWindow();
        if (janela != null) {
            int largura = (int) (activity.getResources().getDisplayMetrics().widthPixels * 0.92f);
            janela.setLayout(largura, WindowManager.LayoutParams.WRAP_CONTENT);
        }
    }

    private GradientDrawable criarFundo(int cor, int borda, int raioDp, int espessuraDp) {
        GradientDrawable fundo = new GradientDrawable();
        fundo.setColor(cor);
        fundo.setCornerRadius(dp(raioDp));
        fundo.setStroke(dp(espessuraDp), borda);
        return fundo;
    }

    private int dp(float valor) {
        return (int) (valor * activity.getResources().getDisplayMetrics().density + 0.5f);
    }

    public void fechar() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}