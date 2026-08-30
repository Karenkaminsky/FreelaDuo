package com.example.myapplication;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterCompanyActivity extends AppCompatActivity {

    private EditText edtCompanyName;
    private EditText edtCNPJ;
    private EditText edtCompanyEmail;
    private EditText edtCompanyPassword;
    private RadioGroup rgContractType;
    private Button btnRegisterCompany;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_company);

        initViews();

        btnRegisterCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cadastrarEmpresa();
            }
        });
    }

    private void initViews() {
        edtCompanyName = findViewById(R.id.edtCompanyName);
        edtCNPJ = findViewById(R.id.edtCNPJ);
        edtCompanyEmail = findViewById(R.id.edtCompanyEmail);
        edtCompanyPassword = findViewById(R.id.edtCompanyPassword);
        rgContractType = findViewById(R.id.rgContractType);
        btnRegisterCompany = findViewById(R.id.btnRegisterCompany);
    }

    private void cadastrarEmpresa() {
        String name = edtCompanyName.getText().toString().trim();
        String cnpj = edtCNPJ.getText().toString().trim();
        String email = edtCompanyEmail.getText().toString().trim();
        String password = edtCompanyPassword.getText().toString().trim();

        int selectedContractId = rgContractType.getCheckedRadioButtonId();
        String contractType = "";

        if (selectedContractId == R.id.rbProject) {
            contractType = "Projeto";
        } else if (selectedContractId == R.id.rbHourly) {
            contractType = "Hora";
        } else if (selectedContractId == R.id.rbBoth) {
            contractType = "Ambos";
        }

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(cnpj) ||
                TextUtils.isEmpty(email) || TextUtils.isEmpty(password) ||
                selectedContractId == -1) {

            Toast.makeText(this, "Por favor, preencha todos os campos e selecione o modelo de demanda.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Criando o objeto DTO com os dados digitados pelo usuário
        UsuarioDto novoUsuario = new UsuarioDto(email, password, cnpj, "EMPRESA");

        // Enviando os dados para a API Spring Boot
        ApiClient.getApiService().cadastrarUsuario(novoUsuario).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(RegisterCompanyActivity.this, "Empresa cadastrada com sucesso!", Toast.LENGTH_SHORT).show();
                    finish(); // Fecha a tela após o cadastro
                } else {
                    Toast.makeText(RegisterCompanyActivity.this, "Erro no servidor ao cadastrar.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(RegisterCompanyActivity.this, "Falha na conexão: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}