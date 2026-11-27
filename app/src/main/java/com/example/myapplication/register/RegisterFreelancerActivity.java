package com.example.myapplication.register;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import com.example.myapplication.ApiClient;
import com.example.myapplication.RegisterRequestDto;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.example.freeladuo.util.FormattersAndValidators;
import com.example.myapplication.R;

import java.util.Calendar;
import java.util.regex.Pattern;

public class RegisterFreelancerActivity extends AppCompatActivity {

    // Só os campos que ficaram no XML
    private EditText edtFreelancerName, edtBirthDate, edtCPF;
    private EditText edtEmail;
    private EditText edtPassword, edtConfirmPassword;
    private TextView txtRuleLength, txtRuleUppercase, txtRuleNumber, txtRuleSpecialChar;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_freelance);

        initViews();
        setupDatePicker();
        setupFormattersAndMasks();
        setupPasswordValidation();

        btnRegister.setOnClickListener(v -> realizarCadastro());
    }

    private void initViews() {
        edtFreelancerName = findViewById(R.id.edtFreelancerName);
        edtBirthDate = findViewById(R.id.edtBirthDate);
        edtCPF = findViewById(R.id.edtCPF);

        edtEmail = findViewById(R.id.edtEmail);

        edtPassword = findViewById(R.id.edtPassword);
        edtConfirmPassword = findViewById(R.id.edtConfirmPassword);

        txtRuleLength = findViewById(R.id.txtRuleLength);
        txtRuleUppercase = findViewById(R.id.txtRuleUppercase);
        txtRuleNumber = findViewById(R.id.txtRuleNumber);
        txtRuleSpecialChar = findViewById(R.id.txtRuleSpecialChar);

        btnRegister = findViewById(R.id.btnRegisterFreelancer);
    }

    private void setupDatePicker() {
        if (edtBirthDate == null) return;

        edtBirthDate.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    RegisterFreelancerActivity.this,
                    (view, selectedYear, selectedMonth, selectedDay) -> {
                        String formattedDate = String.format("%02d/%02d/%04d", selectedDay, selectedMonth + 1, selectedYear);
                        edtBirthDate.setText(formattedDate);
                    },
                    year, month, day
            );
            datePickerDialog.show();
        });
    }

    private void setupFormattersAndMasks() {
        if (edtCPF != null) {
            edtCPF.addTextChangedListener(FormattersAndValidators.cpfTextWatcher());
        }
    }

    private void setupPasswordValidation() {
        if (edtPassword == null) return;

        edtPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String pass = s.toString();

                updateRuleStatus(txtRuleLength, pass.length() >= 8);
                updateRuleStatus(txtRuleUppercase, Pattern.compile("[A-Z]").matcher(pass).find());
                updateRuleStatus(txtRuleNumber, Pattern.compile("[0-9]").matcher(pass).find());
                updateRuleStatus(txtRuleSpecialChar, Pattern.compile("[^a-zA-Z0-9]").matcher(pass).find());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void updateRuleStatus(TextView textView, boolean isValid) {
        if (textView == null) return;
        if (isValid) {
            textView.setTextColor(Color.parseColor("#4CAF50")); // Verde
        } else {
            textView.setTextColor(Color.parseColor("#F44336")); // Vermelho
        }
    }

    private void realizarCadastro() {
        String name = edtFreelancerName != null ? edtFreelancerName.getText().toString().trim() : "";
        String email = edtEmail != null ? edtEmail.getText().toString().trim() : "";
        String cpf = edtCPF != null ? edtCPF.getText().toString().trim() : "";
        String password = edtPassword != null ? edtPassword.getText().toString().trim() : "";
        String confirmPassword = edtConfirmPassword != null ? edtConfirmPassword.getText().toString().trim() : "";

        // Validação de campos obrigatórios
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(cpf) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Preencha Nome, E-mail, CPF e Senha.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validação de CPF
        if (!FormattersAndValidators.isValidCpf(cpf)) {
            if (edtCPF != null) {
                edtCPF.setError("CPF inválido");
                edtCPF.requestFocus();
            } else {
                Toast.makeText(this, "CPF inválido.", Toast.LENGTH_SHORT).show();
            }
            return;
        }

        // Validação de confirmação de senha
        if (!password.equals(confirmPassword)) {
            if (edtConfirmPassword != null) {
                edtConfirmPassword.setError("As senhas não coincidem");
                edtConfirmPassword.requestFocus();
            } else {
                Toast.makeText(this, "As senhas não coincidem.", Toast.LENGTH_SHORT).show();
            }
            return;
        }

        btnRegister.setEnabled(false);

        // Envia apenas o essencial para o banco
        RegisterRequestDto registerRequest = new RegisterRequestDto(
                name,
                email,
                password,
                "Freelancer",
                cpf.replaceAll("[^0-9]", "") // CPF limpo
        );

        ApiClient.getApiService().cadastrarUsuario(registerRequest).enqueue(new retrofit2.Callback<okhttp3.ResponseBody>() {
            @Override
            public void onResponse(retrofit2.Call<okhttp3.ResponseBody> call, retrofit2.Response<okhttp3.ResponseBody> response) {
                btnRegister.setEnabled(true);
                if (response.isSuccessful()) {
                    Toast.makeText(RegisterFreelancerActivity.this, "Cadastro realizado com sucesso!", Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(RegisterFreelancerActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(RegisterFreelancerActivity.this, "Erro no cadastro. Verifique se o e-mail/CPF já existem.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(retrofit2.Call<okhttp3.ResponseBody> call, Throwable t) {
                btnRegister.setEnabled(true);
                Toast.makeText(RegisterFreelancerActivity.this, "Falha de conexão: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}