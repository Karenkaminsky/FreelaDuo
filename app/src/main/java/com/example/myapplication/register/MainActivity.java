
package com.example.myapplication.register;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;

import com.example.myapplication.FeedActivity;
import com.example.myapplication.R;
import com.example.myapplication.RegisterSelectionActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

public class MainActivity extends AppCompatActivity {

    private EditText editEmail, editSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Verificação de sessão
        SharedPreferences preferences = getSharedPreferences(
                "FreelaDuoPrefs", MODE_PRIVATE
        );

        boolean isLoggedIn = preferences.getBoolean("isLoggedIn", false);
        String tipoContaSalva = preferences.getString("tipo_conta", "");

        if (isLoggedIn) {
            redirecionarPorPerfil(tipoContaSalva);
            return;
        }

        setContentView(R.layout.activity_main);

        // Mapeamento dos campos
        editEmail = findViewById(R.id.editEmail);
        editSenha = findViewById(R.id.editPassword);

        // Mapeamento dos botões
        MaterialButton btnLogin = findViewById(R.id.btnLogin);

        MaterialCardView btnGoogle = findViewById(R.id.btnGoogle);
        MaterialCardView btnGithub = findViewById(R.id.btnGithub);
        MaterialCardView btnLinkedin = findViewById(R.id.btnLinkedin);

        TextView txtRegister = findViewById(R.id.txtRegister);
        TextView txtForgotPassword = findViewById(R.id.txtForgotPassword);

        // Ação do botão Entrar
        if (btnLogin != null) {
            btnLogin.setOnClickListener(v -> realizarLogin());
        }

        // Login com Google
        if (btnGoogle != null) {
            btnGoogle.setOnClickListener(v ->
                    Toast.makeText(
                            this,
                            "Redirecionando para login com Google...",
                            Toast.LENGTH_SHORT
                    ).show()
            );
        }

        // Login com GitHub
        if (btnGithub != null) {
            btnGithub.setOnClickListener(v ->
                    Toast.makeText(
                            this,
                            "Redirecionando para login com GitHub...",
                            Toast.LENGTH_SHORT
                    ).show()
            );
        }

        // Login com LinkedIn
        if (btnLinkedin != null) {
            btnLinkedin.setOnClickListener(v ->
                    Toast.makeText(
                            this,
                            "Redirecionando para login com LinkedIn...",
                            Toast.LENGTH_SHORT
                    ).show()
            );
        }

        // Redirecionamento para cadastro
        if (txtRegister != null) {
            txtRegister.setOnClickListener(v -> {
                Intent intent = new Intent(
                        MainActivity.this,
                        RegisterSelectionActivity.class
                );

                startActivity(intent);
            });
        }

        // Recuperação de senha
        if (txtForgotPassword != null) {
            txtForgotPassword.setOnClickListener(v -> {
                new SenhaRecup(MainActivity.this).mostrar();
            });
        }
    }

    private void realizarLogin() {

        String email = editEmail != null
                ? editEmail.getText().toString().trim()
                : "";

        String senha = editSenha != null
                ? editSenha.getText().toString().trim()
                : "";

        // Validação dos campos
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(senha)) {
            Toast.makeText(
                    this,
                    "Preencha e-mail e senha!",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        // AJUSTE TEMPORÁRIO PARA A APRESENTAÇÃO
        SharedPreferences pref = getSharedPreferences(
                "FreelaDuoPrefs", MODE_PRIVATE
        );

        pref.edit()
                .putBoolean("isLoggedIn", true)
                .putInt("user_id", 1)
                .putString("tipo_conta", "Freelancer")
                .apply();

        Toast.makeText(
                MainActivity.this,
                "Login realizado com sucesso!",
                Toast.LENGTH_SHORT
        ).show();

        redirecionarPorPerfil("Freelancer");
    }

    // Redirecionamento conforme o perfil
    private void redirecionarPorPerfil(String tipoConta) {

        Intent intent = new Intent(
                MainActivity.this,
                FeedActivity.class
        );

        startActivity(intent);
        finish();
    }
}