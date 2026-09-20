package com.example.myapplication.register;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.myapplication.FeedActivity;
import com.example.myapplication.R;
import com.example.myapplication.RegisterSelectionActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MaterialButton btnLogin = findViewById(R.id.btnLogin);
        MaterialCardView btnGoogle = findViewById(R.id.btnGoogle);
        MaterialCardView btnGithub = findViewById(R.id.btnGithub);
        MaterialCardView btnLinkedin = findViewById(R.id.btnLinkedin);
        TextView txtRegister = findViewById(R.id.txtRegister);

        btnLogin.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, FeedActivity.class);
            startActivity(intent);
        });

        btnGoogle.setOnClickListener(v ->
                Toast.makeText(this, "Redirecionando para login com Google...", Toast.LENGTH_SHORT).show()
        );

        btnGithub.setOnClickListener(v ->
                Toast.makeText(this, "Redirecionando para login com GitHub...", Toast.LENGTH_SHORT).show()
        );

        btnLinkedin.setOnClickListener(v ->
                Toast.makeText(this, "Redirecionando para login com LinkedIn...", Toast.LENGTH_SHORT).show()
        );

        txtRegister.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, RegisterSelectionActivity.class);
            startActivity(intent);
        });
    }
}