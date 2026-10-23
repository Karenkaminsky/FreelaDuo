package com.example.myapplication.register;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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

        // Referências dos componentes do modal
        LinearLayout modal =
                dialog.findViewById(R.id.modalSenhaRecup);

        EditText edtEmail =
                dialog.findViewById(R.id.edtEmailRecup);

        Button btnEnviar =
                dialog.findViewById(R.id.btnEnviarRecup);

        TextView btnFechar =
                dialog.findViewById(R.id.btnFecharRecup);

        TextView btnVoltar =
                dialog.findViewById(R.id.btnVoltarLogin);

        // Fundo escuro e borda azul
        modal.setBackground(criarFundo(
                Color.rgb(11, 13, 25),
                Color.rgb(62, 84, 190),
                26,
                1
        ));

        // Campo de e-mail
        edtEmail.setBackground(criarFundo(
                Color.rgb(18, 21, 37),
                Color.rgb(52, 62, 112),
                12,
                1
        ));

        // Gradiente azul e roxo do botão
        GradientDrawable gradiente =
                new GradientDrawable(
                        GradientDrawable.Orientation.LEFT_RIGHT,
                        new int[]{
                                Color.rgb(55, 126, 247),
                                Color.rgb(126, 49, 239)
                        }
                );

        gradiente.setCornerRadius(dp(12));

        btnEnviar.setBackground(gradiente);
        btnEnviar.setBackgroundTintList(null);
        btnEnviar.setTextColor(Color.WHITE);
        btnEnviar.setAllCaps(false);
        btnEnviar.setElevation(dp(2));

        // Fechar pelo X
        btnFechar.setOnClickListener(v -> fechar());

        // Voltar para o login
        btnVoltar.setOnClickListener(v -> fechar());

        // Validação visual do e-mail
        btnEnviar.setOnClickListener(v -> {

            String email = edtEmail.getText()
                    .toString()
                    .trim();

            if (TextUtils.isEmpty(email)) {
                edtEmail.setError("Informe seu e-mail");
                edtEmail.requestFocus();
                return;
            }

            if (!Patterns.EMAIL_ADDRESS
                    .matcher(email)
                    .matches()) {

                edtEmail.setError(
                        "Informe um e-mail válido"
                );

                edtEmail.requestFocus();
                return;
            }

            // A autenticação será conectada futuramente.
            Toast.makeText(
                    activity,
                    "Visual validado! O envio será implementado depois.",
                    Toast.LENGTH_LONG
            ).show();
        });

        // Configura a janela
        Window window = dialog.getWindow();

        if (window != null) {
            window.setBackgroundDrawableResource(
                    android.R.color.transparent
            );

            window.addFlags(
                    WindowManager.LayoutParams.FLAG_DIM_BEHIND
            );

            WindowManager.LayoutParams params =
                    window.getAttributes();

            params.dimAmount = 0.78f;
            params.gravity = Gravity.CENTER;

            window.setAttributes(params);

            // Desfoque real em versões compatíveis (Android 12+)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {

                window.addFlags(
                        WindowManager.LayoutParams.FLAG_BLUR_BEHIND
                );

                WindowManager.LayoutParams blurParams =
                        window.getAttributes();

                blurParams.setBlurBehindRadius(dp(24));

                window.setAttributes(blurParams);
            }

            window.setSoftInputMode(
                    WindowManager.LayoutParams
                            .SOFT_INPUT_ADJUST_RESIZE
            );
        }

        dialog.show();

        // Ocupa 92% da largura da tela
        Window janela = dialog.getWindow();

        if (janela != null) {
            int largura = (int) (
                    activity.getResources()
                            .getDisplayMetrics()
                            .widthPixels * 0.92f
            );

            janela.setLayout(
                    largura,
                    WindowManager.LayoutParams.WRAP_CONTENT
            );
        }
    }

    private GradientDrawable criarFundo(
            int cor,
            int borda,
            int raioDp,
            int espessuraDp) {

        GradientDrawable fundo = new GradientDrawable();

        fundo.setColor(cor);
        fundo.setCornerRadius(dp(raioDp));

        fundo.setStroke(
                dp(espessuraDp),
                borda
        );

        return fundo;
    }

    private int dp(float valor) {
        return (int) (
                valor * activity.getResources()
                        .getDisplayMetrics()
                        .density + 0.5f
        );
    }

    public void fechar() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}