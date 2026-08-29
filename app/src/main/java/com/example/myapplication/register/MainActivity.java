package com.example.myapplication.register;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.myapplication.R;
import com.example.myapplication.RegisterSelectionActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout btnGoogle = findViewById(R.id.btnGoogle);
        LinearLayout btnGithub = findViewById(R.id.btnGithub);
        LinearLayout btnLinkedin = findViewById(R.id.btnLinkedin);
        TextView txtRegister = findViewById(R.id.txtRegister);

        btnGoogle.setOnClickListener(v ->
            Toast.makeText(this, "Redirecionando para login com Google...", Toast.LENGTH_SHORT).show()
        );

        btnGithub.setOnClickListener(v ->
            Toast.makeText(this, "Redirecionando para login com GitHub...", Toast.LENGTH_SHORT).show()
        );

        btnLinkedin.setOnClickListener(v ->
            Toast.makeText(this, "Redirecionando para login com LinkedIn...", Toast.LENGTH_SHORT).show()
        );

        // Aponta para a tela de seleção (RegisterSelectionActivity)
        txtRegister.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, RegisterSelectionActivity.class);
            startActivity(intent);
        });
    }
}