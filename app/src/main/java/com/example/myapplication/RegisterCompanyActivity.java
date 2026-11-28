package com.example.myapplication;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.freeladuo.util.FormattersAndValidators;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.regex.Pattern;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterCompanyActivity extends AppCompatActivity {

    private TextInputEditText edtCorporateName, edtCNPJ, edtCompanyEmail, edtCompanyPhone;
    private TextInputEditText edtCompanyPassword, edtConfirmPassword;
    private RadioGroup rgContractType;
    private CheckBox cbTerms;
    private TextView txtTermsLink, txtRuleLength, txtRuleUppercase, txtRuleNumber, txtRuleSpecialChar;
    private MaterialButton btnRegisterCompany;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_company);

        initViews();
        setupMasks();
        setupPasswordValidation();
        setupTermsLink();

        btnRegisterCompany.setOnClickListener(v -> cadastrarEmpresa());
    }

    private void initViews() {
        edtCorporateName = findViewById(R.id.edtCorporateName);
        edtCNPJ = findViewById(R.id.edtCNPJ);
        edtCompanyEmail = findViewById(R.id.edtCompanyEmail);
        edtCompanyPhone = findViewById(R.id.edtCompanyPhone);
        edtCompanyPassword = findViewById(R.id.edtCompanyPassword);
        edtConfirmPassword = findViewById(R.id.edtConfirmPassword);
        rgContractType = findViewById(R.id.rgContractType);
        cbTerms = findViewById(R.id.cbTerms);
        txtTermsLink = findViewById(R.id.txtTermsLink);

        txtRuleLength = findViewById(R.id.txtRuleLength);
        txtRuleUppercase = findViewById(R.id.txtRuleUppercase);
        txtRuleNumber = findViewById(R.id.txtRuleNumber);
        txtRuleSpecialChar = findViewById(R.id.txtRuleSpecialChar);

        btnRegisterCompany = findViewById(R.id.btnRegisterCompany);
    }

    private void setupMasks() {
        if (edtCNPJ != null) edtCNPJ.addTextChangedListener(FormattersAndValidators.cnpjTextWatcher());
        if (edtCompanyPhone != null) edtCompanyPhone.addTextChangedListener(FormattersAndValidators.phoneTextWatcher());
    }

    private void setupPasswordValidation() {
        if (edtCompanyPassword == null) return;

        edtCompanyPassword.addTextChangedListener(new TextWatcher() {
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

    // AQUI ESTÁ A MÁGICA DO TEXTO AZUL CLICÁVEL
    private void setupTermsLink() {
        if (txtTermsLink != null) {
            String fullText = "Concordo com os Termos e diretrizes. Clique aqui";
            SpannableString spannableString = new SpannableString(fullText);

            ClickableSpan clickableSpan = new ClickableSpan() {
                @Override
                public void onClick(@NonNull View widget) {
                    new AlertDialog.Builder(RegisterCompanyActivity.this)
                            .setTitle("Termos Corporativos")
                            .setMessage("Ao concordar, a empresa se compromete a fornecer um ambiente ético, pagar os valores acordados e respeitar as diretrizes do FreelaDuo.")
                            .setPositiveButton("Entendi", null)
                            .show();
                }

                @Override
                public void updateDrawState(@NonNull TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setColor(Color.parseColor("#3B82F6")); // Cor azul
                    ds.setUnderlineText(true); // Sublinhado
                }
            };

            // Encontra a palavra "Clique aqui" e aplica o span azul
            int start = fullText.indexOf("Clique aqui");
            int end = start + "Clique aqui".length();

            if (start != -1) {
                spannableString.setSpan(clickableSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }

            txtTermsLink.setText(spannableString);
            txtTermsLink.setMovementMethod(LinkMovementMethod.getInstance());
        }
    }

    private void cadastrarEmpresa() {
        String name = edtCorporateName.getText().toString().trim();
        String cnpj = edtCNPJ.getText().toString().trim();
        String email = edtCompanyEmail.getText().toString().trim();
        String password = edtCompanyPassword.getText().toString().trim();
        String confirmPassword = edtConfirmPassword.getText().toString().trim();

        int selectedContractId = rgContractType.getCheckedRadioButtonId();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(cnpj) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || selectedContractId == -1) {
            Toast.makeText(this, "Preencha todos os campos e selecione o modelo de demanda.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(confirmPassword)) {
            edtConfirmPassword.setError("As senhas não coincidem");
            edtConfirmPassword.requestFocus();
            return;
        }

        if (!cbTerms.isChecked()) {
            Toast.makeText(this, "Você precisa aceitar os Termos Corporativos.", Toast.LENGTH_SHORT).show();
            return;
        }

        String cnpjLimpo = cnpj.replaceAll("[^0-9]", "");

        RegisterRequestDto novoUsuario = new RegisterRequestDto(
                name,
                email,
                password,
                "Empresa",
                cnpjLimpo
        );

        btnRegisterCompany.setEnabled(false);
        btnRegisterCompany.setText("Cadastrando...");

        ApiClient.getApiService().cadastrarUsuario(novoUsuario).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(RegisterCompanyActivity.this, "Empresa cadastrada com sucesso!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    btnRegisterCompany.setEnabled(true);
                    btnRegisterCompany.setText("Finalizar Cadastro");
                    Toast.makeText(RegisterCompanyActivity.this, "Erro no servidor ao cadastrar.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                btnRegisterCompany.setEnabled(true);
                btnRegisterCompany.setText("Finalizar Cadastro");
                Toast.makeText(RegisterCompanyActivity.this, "Falha na conexão: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}