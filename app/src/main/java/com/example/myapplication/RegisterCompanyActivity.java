
package com.example.myapplication;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterCompanyActivity extends AppCompatActivity {

    private TextInputEditText edtCorporateName;
    private TextInputEditText edtCNPJ;
    private TextInputEditText edtCompanyEmail;
    private TextInputEditText edtCompanyPassword;
    private RadioGroup rgContractType;
    private MaterialButton btnRegisterCompany;

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
        edtCorporateName = findViewById(R.id.edtCorporateName);
        edtCNPJ = findViewById(R.id.edtCNPJ);
        edtCompanyEmail = findViewById(R.id.edtCompanyEmail);
        edtCompanyPassword = findViewById(R.id.edtCompanyPassword);
        rgContractType = findViewById(R.id.rgContractType);
        btnRegisterCompany = findViewById(R.id.btnRegisterCompany);
    }

    private void cadastrarEmpresa() {

        String name = edtCorporateName.getText().toString().trim();
        String cnpj = edtCNPJ.getText().toString().trim();
        String email = edtCompanyEmail.getText().toString().trim();
        String password = edtCompanyPassword.getText().toString().trim();

        int selectedContractId = rgContractType.getCheckedRadioButtonId();

        if (TextUtils.isEmpty(name)
                || TextUtils.isEmpty(cnpj)
                || TextUtils.isEmpty(email)
                || TextUtils.isEmpty(password)
                || selectedContractId == -1) {

            Toast.makeText(
                    this,
                    "Por favor, preencha todos os campos e selecione o modelo de demanda.",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        // Criação do cadastro da empresa
        RegisterRequestDto novoUsuario = new RegisterRequestDto(
                name,
                email,
                password,
                cnpj,
                "Empresa"
        );

        ApiClient.getApiService()
                .cadastrarUsuario(novoUsuario)
                .enqueue(new Callback<ResponseBody>() {

                    @Override
                    public void onResponse(
                            Call<ResponseBody> call,
                            Response<ResponseBody> response
                    ) {
                        if (response.isSuccessful()) {

                            Toast.makeText(
                                    RegisterCompanyActivity.this,
                                    "Empresa cadastrada com sucesso!",
                                    Toast.LENGTH_SHORT
                            ).show();

                            finish();

                        } else {

                            Toast.makeText(
                                    RegisterCompanyActivity.this,
                                    "Erro no servidor ao cadastrar.",
                                    Toast.LENGTH_SHORT
                            ).show();
                        }
                    }

                    @Override
                    public void onFailure(
                            Call<ResponseBody> call,
                            Throwable t
                    ) {
                        Toast.makeText(
                                RegisterCompanyActivity.this,
                                "Falha na conexão: " + t.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                });
    }
}