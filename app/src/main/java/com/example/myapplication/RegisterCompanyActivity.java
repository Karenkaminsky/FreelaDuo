package com.example.myapplication;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterCompanyActivity extends AppCompatActivity {

    private EditText edtCompanyName;
    private EditText edtCNPJ;
    private EditText edtCompanyEmail;
    private EditText edtCompanyPhone;
    private EditText edtCompanyPassword;
    private Button btnRegisterCompany;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Aponta para o XML correto (sem o número 2 no final)
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
        edtCompanyPhone = findViewById(R.id.edtCompanyPhone);
        edtCompanyPassword = findViewById(R.id.edtCompanyPassword);
        btnRegisterCompany = findViewById(R.id.btnRegisterCompany);
    }

    private void cadastrarEmpresa() {
        String name = edtCompanyName.getText().toString().trim();
        String cnpj = edtCNPJ.getText().toString().trim();
        String email = edtCompanyEmail.getText().toString().trim();
        String password = edtCompanyPassword.getText().toString().trim();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(cnpj) ||
                TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {

            Toast.makeText(this, "Por favor, preencha todos os campos obrigatórios.", Toast.LENGTH_SHORT).show();
            return;
        }

        Toast.makeText(this, "Empresa cadastrada com sucesso!", Toast.LENGTH_SHORT).show();
    }
}