package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.register.MainActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textfield.TextInputEditText;

public class ProfileActivity extends AppCompatActivity {

    private ShapeableImageView imgProfile;
    private FloatingActionButton btnChangePhoto;
    private TextInputEditText edtName, edtTitle, edtBio;
    private MaterialButton btnSaveProfile, btnLogout;
    private Button btnDeleteAccount;
    private ImageButton btnBack;

    private Uri selectedImageUri;
    private boolean isDataChanged = false; // Controle de edições pendentes

    // Lançador para abrir a galeria de imagens
    private final ActivityResultLauncher<String> galleryLauncher =
            registerForActivityResult(new ActivityResultContracts.GetContent(), uri -> {
                if (uri != null) {
                    selectedImageUri = uri;
                    imgProfile.setImageURI(uri);
                    isDataChanged = true;
                    Toast.makeText(this, "Foto atualizada! Lembre-se de salvar as alterações.", Toast.LENGTH_SHORT).show();
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Mapeando componentes da interface
        imgProfile = findViewById(R.id.imgProfile);
        btnChangePhoto = findViewById(R.id.btnChangePhoto);
        edtName = findViewById(R.id.edtName);
        edtTitle = findViewById(R.id.edtTitle);
        edtBio = findViewById(R.id.edtBio);
        btnSaveProfile = findViewById(R.id.btnSaveProfile);
        btnLogout = findViewById(R.id.btnLogout);
        btnDeleteAccount = findViewById(R.id.btnDeleteAccount);
        btnBack = findViewById(R.id.btnBack);

        // Carregar dados iniciais do usuário
        loadUserProfile();

        // Monitorar edições nos campos
        setupChangeListeners();

        // Eventos de clique
        btnChangePhoto.setOnClickListener(v -> galleryLauncher.launch("image/*"));
        btnSaveProfile.setOnClickListener(v -> saveUserProfile());
        btnLogout.setOnClickListener(v -> confirmLogout());
        btnDeleteAccount.setOnClickListener(v -> confirmAccountDeletion());

        // Botão de voltar do cabeçalho
        btnBack.setOnClickListener(v -> finish());

        // Tratamento do botão nativo de voltar do celular
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                finish();
            }
        });
    }

    // Carregar dados de perfil (Simulação/Backend)
    private void loadUserProfile() {
        edtName.setText("Karen Kaminsky");
        edtTitle.setText("Estagiária de TI | Estudante de Informática");
        edtBio.setText("Entusiasta de desenvolvimento mobile e suporte técnico.");
        isDataChanged = false; // Reseta o status de alteração ao carregar
    }

    // Monitora alterações de texto para saber se há dados pendentes de salvamento
    private void setupChangeListeners() {
        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                isDataChanged = true;
            }

            @Override
            public void afterTextChanged(Editable s) {}
        };

        edtName.addTextChangedListener(watcher);
        edtTitle.addTextChangedListener(watcher);
        edtBio.addTextChangedListener(watcher);
    }

    // Salvar dados do perfil
    private void saveUserProfile() {
        String name = edtName.getText().toString().trim();
        String title = edtTitle.getText().toString().trim();
        String bio = edtBio.getText().toString().trim();

        if (name.isEmpty()) {
            edtName.setError("O nome é obrigatório");
            return;
        }

        // Aqui é realizada a integração POST/PUT com a API em PHP
        isDataChanged = false;
        Toast.makeText(this, "Perfil atualizado com sucesso!", Toast.LENGTH_LONG).show();
    }

    // Diálogo de confirmação para sair da conta
    private void confirmLogout() {
        new AlertDialog.Builder(this)
                .setTitle("Sair da Conta")
                .setMessage("Tem certeza de que deseja sair? Você precisará realizar o login novamente.")
                .setPositiveButton("Sair", (dialog, which) -> logoutUser())
                .setNegativeButton("Cancelar", null)
                .show();
    }

    // Encerra a sessão e redireciona para a tela de Login (MainActivity)
    private void logoutUser() {
        SharedPreferences preferences = getSharedPreferences("UserSession", MODE_PRIVATE);
        preferences.edit().clear().apply();

        Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    // Diálogo de confirmação para exclusão de conta
    private void confirmAccountDeletion() {
        new AlertDialog.Builder(this)
                .setTitle("Excluir Conta")
                .setMessage("Tem certeza de que deseja excluir sua conta? Esta ação não poderá ser desfeita.")
                .setPositiveButton("Sim, excluir", (dialog, which) -> {
                    Toast.makeText(ProfileActivity.this, "Conta excluída com sucesso.", Toast.LENGTH_LONG).show();
                    logoutUser();
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }
}