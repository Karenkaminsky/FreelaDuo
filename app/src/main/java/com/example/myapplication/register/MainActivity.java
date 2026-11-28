package com.example.myapplication.register;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.text.TextUtils;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.ApiClient;
import com.example.myapplication.ApiService;
import com.example.myapplication.FeedActivity;
import com.example.myapplication.LoginRequestDto;
import com.example.myapplication.LoginResponse;
import com.example.myapplication.R;
import com.example.myapplication.RegisterSelectionActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private EditText editEmail, editSenha;
    private MaterialButton btnLogin; // Transformado em variável de classe

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Verificação de sessão (se o usuário já logou antes, vai direto pro Feed)
        SharedPreferences preferences = getSharedPreferences("FreelaDuoPrefs", MODE_PRIVATE);
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
        btnLogin = findViewById(R.id.btnLogin);
        MaterialCardView btnGoogle = findViewById(R.id.btnGoogle);
        MaterialCardView btnGithub = findViewById(R.id.btnGithub);
        MaterialCardView btnLinkedin = findViewById(R.id.btnLinkedin);
        TextView txtRegister = findViewById(R.id.txtRegister);
        TextView txtForgotPassword = findViewById(R.id.txtForgotPassword);

        // Ação do botão Entrar
        if (btnLogin != null) {
            btnLogin.setOnClickListener(v -> realizarLogin());
        }

        // Login com Google (Mantido para visual, ideal para o TCC)
        if (btnGoogle != null) {
            btnGoogle.setOnClickListener(v ->
                    Toast.makeText(this, "Integração com Google em desenvolvimento", Toast.LENGTH_SHORT).show()
            );
        }

        // Login com GitHub
        if (btnGithub != null) {
            btnGithub.setOnClickListener(v ->
                    Toast.makeText(this, "Integração com GitHub em desenvolvimento", Toast.LENGTH_SHORT).show()
            );
        }

        // Login com LinkedIn
        if (btnLinkedin != null) {
            btnLinkedin.setOnClickListener(v ->
                    Toast.makeText(this, "Integração com LinkedIn em desenvolvimento", Toast.LENGTH_SHORT).show()
            );
        }

        // Redirecionamento para cadastro
        if (txtRegister != null) {
            txtRegister.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, RegisterSelectionActivity.class);
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
        String email = editEmail != null ? editEmail.getText().toString().trim() : "";
        String senha = editSenha != null ? editSenha.getText().toString().trim() : "";

        // Validação dos campos
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(senha)) {
            Toast.makeText(this, "Preencha e-mail e senha!", Toast.LENGTH_SHORT).show();
            return;
        }

        // MELHORIA: Desativa o botão e mostra "Entrando..." enquanto espera o servidor
        btnLogin.setEnabled(false);
        btnLogin.setText("Entrando...");

        // Cria o objeto que o Retrofit vai enviar no formato JSON
        LoginRequestDto loginData = new LoginRequestDto(email, senha);

        ApiService api = ApiClient.getApiService();
        Call<LoginResponse> call = api.realizarLogin(loginData);

        // Faz a chamada assíncrona para não travar a tela do app
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    LoginResponse res = response.body();

                    // O login.php retorna "sucesso" no status
                    if ("sucesso".equals(res.getStatus())) {
                        // Salva a sessão no celular
                        SharedPreferences pref = getSharedPreferences("FreelaDuoPrefs", MODE_PRIVATE);
                        pref.edit()
                                .putBoolean("isLoggedIn", true)
                                .putInt("user_id", res.getId_usuario())
                                .putString("tipo_conta", res.getTipo_conta())
                                .putString("nome", res.getNome())
                                .apply();

                        Toast.makeText(MainActivity.this, "Login realizado com sucesso!", Toast.LENGTH_SHORT).show();
                        redirecionarPorPerfil(res.getTipo_conta());
                    } else {
                        // Erro de senha/email tratado no PHP
                        Toast.makeText(MainActivity.this, res.getMessage(), Toast.LENGTH_SHORT).show();

                        // MELHORIA: Reativa o botão em caso de erro
                        btnLogin.setEnabled(true);
                        btnLogin.setText("Entrar na Conta");
                    }
                } else {
                    Toast.makeText(MainActivity.this, "E-mail ou senha incorretos.", Toast.LENGTH_SHORT).show();

                    // MELHORIA: Reativa o botão em caso de erro
                    btnLogin.setEnabled(true);
                    btnLogin.setText("Entrar na Conta");
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Erro de conexão com o servidor: " + t.getMessage(), Toast.LENGTH_LONG).show();

                // MELHORIA: Reativa o botão em caso de falha de rede
                btnLogin.setEnabled(true);
                btnLogin.setText("Entrar na Conta");
            }
        });
    }

    // Redirecionamento conforme o perfil
    private void redirecionarPorPerfil(String tipoConta) {
        Intent intent = new Intent(MainActivity.this, FeedActivity.class);
        startActivity(intent);
        finish();
    }
}