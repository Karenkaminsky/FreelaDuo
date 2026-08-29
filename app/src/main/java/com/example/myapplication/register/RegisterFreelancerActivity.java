package com.example.myapplication.register;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class RegisterFreelancerActivity extends AppCompatActivity {

    private EditText edtFreelancerName;
    private EditText edtFreelancerCPF;
    private EditText edtFreelancerRole;
    private EditText edtFreelancerEmail;
    private EditText edtFreelancerPassword;
    private RadioGroup rgWorkAvailability;
    private Button btnRegisterFreelancer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_freelance);

        initViews();

        btnRegisterFreelancer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cadastrarFreelancer();
            }
        });
    }

    private void initViews() {
        edtFreelancerName = findViewById(R.id.edtFreelancerName);
        edtFreelancerCPF = findViewById(R.id.edtFreelancerCPF);
        edtFreelancerRole = findViewById(R.id.edtFreelancerRole);
        edtFreelancerEmail = findViewById(R.id.edtFreelancerEmail);
        edtFreelancerPassword = findViewById(R.id.edtFreelancerPassword);
        rgWorkAvailability = findViewById(R.id.rgWorkAvailability);
        btnRegisterFreelancer = findViewById(R.id.btnRegisterFreelancer);
    }

    private void cadastrarFreelancer() {
        String name = edtFreelancerName.getText().toString().trim();
        String cpf = edtFreelancerCPF.getText().toString().trim();
        String role = edtFreelancerRole.getText().toString().trim();
        String email = edtFreelancerEmail.getText().toString().trim();
        String password = edtFreelancerPassword.getText().toString().trim();

        int selectedId = rgWorkAvailability.getCheckedRadioButtonId();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(cpf) || TextUtils.isEmpty(role) ||
                TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || selectedId == -1) {

            Toast.makeText(this, "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show();
            return;
        }

        Toast.makeText(this, "Freelancer cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
    }
}