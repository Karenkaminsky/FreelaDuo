package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.register.MainActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {

    private ShapeableImageView imgProfile;
    private FloatingActionButton btnChangePhoto;

    private TextInputEditText edtName, edtTitle, edtBio;

    private MaterialButton btnSaveProfile, btnLogout, btnViewPlans;
    private Button btnDeleteAccount;
    private ImageButton btnBack;

    // Elementos do topo
    private TextView txtUserBadge;
    private TextView txtTituloTopo;

    // Elementos visuais do plano
    private TextView txtCurrentPlan, txtPlanLabel, txtPlanBadge;
    private ImageView imgCurrentPlanIcon;
    private LinearLayout containerPlanIcon;
    private MaterialCardView cardPlan;

    private Uri selectedImageUri;
    private boolean isDataChanged = false;

    private static final String PREFS_PLANOS = "FreelaDuoPlanos";
    private static final String CHAVE_PLANO = "plano_atual";

    private static final int ROXO = Color.rgb(192, 132, 252);
    private static final int ROXO_FUNDO = Color.rgb(41, 32, 68);
    private static final int ROXO_BOTAO = Color.rgb(37, 32, 68);
    private static final int DOURADO = Color.rgb(251, 191, 36);
    private static final int DOURADO_FUNDO = Color.rgb(59, 48, 27);
    private static final int DOURADO_CARD = Color.rgb(33, 31, 26);

    private final ActivityResultLauncher<String> galleryLauncher =
            registerForActivityResult(
                    new ActivityResultContracts.GetContent(),
                    uri -> {
                        if (uri != null) {
                            selectedImageUri = uri;
                            imgProfile.setImageURI(uri);
                            isDataChanged = true;
                            Toast.makeText(this, "Foto atualizada! Lembre-se de salvar.", Toast.LENGTH_SHORT).show();
                        }
                    }
            );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        imgProfile = findViewById(R.id.imgProfile);
        btnChangePhoto = findViewById(R.id.btnChangePhoto);
        edtName = findViewById(R.id.edtName);
        edtTitle = findViewById(R.id.edtTitle);
        edtBio = findViewById(R.id.edtBio);
        btnSaveProfile = findViewById(R.id.btnSaveProfile);
        btnLogout = findViewById(R.id.btnLogout);
        btnDeleteAccount = findViewById(R.id.btnDeleteAccount);
        btnBack = findViewById(R.id.btnBack);
        btnViewPlans = findViewById(R.id.btnViewPlans);

        txtUserBadge = findViewById(R.id.txtUserBadge);
        txtTituloTopo = findViewById(R.id.txtTituloTopo);

        cardPlan = findViewById(R.id.cardPlan);
        txtCurrentPlan = findViewById(R.id.txtCurrentPlan);
        txtPlanLabel = findViewById(R.id.txtPlanLabel);
        txtPlanBadge = findViewById(R.id.txtPlanBadge);
        imgCurrentPlanIcon = findViewById(R.id.imgCurrentPlanIcon);
        containerPlanIcon = findViewById(R.id.containerPlanIcon);

        verificarTipoConta();
        loadUserProfile();
        setupChangeListeners();
        atualizarPlanoAtual();

        btnChangePhoto.setOnClickListener(v -> galleryLauncher.launch("image/*"));
        btnSaveProfile.setOnClickListener(v -> saveUserProfile());
        btnLogout.setOnClickListener(v -> confirmLogout());

        btnDeleteAccount.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("Excluir Conta")
                    .setMessage("Tem certeza que deseja excluir sua conta permanentemente? Esta ação não pode ser desfeita.")
                    .setPositiveButton("Sim, excluir", (dialog, which) -> executarExclusaoConta())
                    .setNegativeButton("Cancelar", null)
                    .show();
        });

        btnViewPlans.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, PlanosActivity.class);
            startActivity(intent);
        });

        btnBack.setOnClickListener(v -> finish());

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                finish();
            }
        });
    }

    private void verificarTipoConta() {
        SharedPreferences pref = getSharedPreferences("FreelaDuoPrefs", MODE_PRIVATE);
        String tipoConta = pref.getString("tipo_conta", "Freelancer");

        if ("Empresa".equalsIgnoreCase(tipoConta)) {
            if (txtUserBadge != null) txtUserBadge.setText("✦ EMPRESA");
            if (txtTituloTopo != null) txtTituloTopo.setText("Perfil Corporativo");

            // Deixa a foto visível, mas com ícone de usuário
            imgProfile.setImageResource(R.drawable.outline_account_circle_24);
            btnChangePhoto.setVisibility(View.VISIBLE);

        } else {
            if (txtUserBadge != null) txtUserBadge.setText("✦ FREELANCER");
            if (txtTituloTopo != null) txtTituloTopo.setText("Meu Perfil");

            imgProfile.setImageResource(R.drawable.perfilkaren);
            btnChangePhoto.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        atualizarPlanoAtual();
        BottomNavigationHelper.atualizarAbaSelecionada(this, R.id.nav_profile);
    }

    private void atualizarPlanoAtual() {
        SharedPreferences preferencias = getSharedPreferences(PREFS_PLANOS, MODE_PRIVATE);
        String plano = preferencias.getString(CHAVE_PLANO, "gratuito");
        boolean premium = plano.equals("premium");

        if (premium) {
            txtCurrentPlan.setText("Plano Premium");
            imgCurrentPlanIcon.setImageResource(R.drawable.outline_diamond_24);
            imgCurrentPlanIcon.setImageTintList(ColorStateList.valueOf(DOURADO));
            containerPlanIcon.setBackgroundTintList(ColorStateList.valueOf(DOURADO_FUNDO));
            cardPlan.setCardBackgroundColor(DOURADO_CARD);
            cardPlan.setStrokeColor(DOURADO);
            txtPlanLabel.setTextColor(DOURADO);
            txtPlanBadge.setTextColor(DOURADO);
            txtPlanBadge.setBackgroundTintList(ColorStateList.valueOf(DOURADO_FUNDO));
            btnViewPlans.setTextColor(DOURADO);
            btnViewPlans.setBackgroundTintList(ColorStateList.valueOf(DOURADO_FUNDO));
        } else {
            txtCurrentPlan.setText("Plano Gratuito");
            imgCurrentPlanIcon.setImageResource(R.drawable.ic_plan_gift);
            imgCurrentPlanIcon.setImageTintList(ColorStateList.valueOf(ROXO));
            containerPlanIcon.setBackgroundTintList(ColorStateList.valueOf(ROXO_FUNDO));
            cardPlan.setCardBackgroundColor(Color.parseColor("#111D30"));
            cardPlan.setStrokeColor(ROXO);
            txtPlanLabel.setTextColor(ROXO);
            txtPlanBadge.setTextColor(ROXO);
            txtPlanBadge.setBackgroundTintList(ColorStateList.valueOf(ROXO_FUNDO));
            btnViewPlans.setTextColor(ROXO);
            btnViewPlans.setBackgroundTintList(ColorStateList.valueOf(ROXO_BOTAO));
        }
    }

    private void loadUserProfile() {
        SharedPreferences pref = getSharedPreferences("FreelaDuoPrefs", MODE_PRIVATE);
        String nome = pref.getString("nome", "Usuário");
        String tipo = pref.getString("tipo_conta", "Freelancer");

        edtName.setText(nome);

        if ("Empresa".equalsIgnoreCase(tipo)) {
            edtTitle.setText("Empresa Parceira");
            edtBio.setText("Empresa focada em inovação e contratação de talentos.");
        } else {
            edtTitle.setText("Estagiária de TI | Estudante de Informática");
            edtBio.setText("Entusiasta de desenvolvimento mobile e suporte técnico.");
        }
        isDataChanged = false;
    }

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

    private void saveUserProfile() {
        String name = edtName.getText().toString().trim();
        if (name.isEmpty()) {
            edtName.setError("O nome é obrigatório");
            return;
        }

        isDataChanged = false;
        Toast.makeText(this, "Perfil atualizado com sucesso!", Toast.LENGTH_LONG).show();
    }

    private void confirmLogout() {
        new AlertDialog.Builder(this)
                .setTitle("Sair da Conta")
                .setMessage("Tem certeza de que deseja sair? Você precisará realizar o login novamente.")
                .setPositiveButton("Sair", (dialog, which) -> logoutUser())
                .setNegativeButton("Cancelar", null)
                .show();
    }

    private void logoutUser() {
        SharedPreferences preferences = getSharedPreferences("FreelaDuoPrefs", MODE_PRIVATE);
        preferences.edit().clear().apply();

        Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void executarExclusaoConta() {
        SharedPreferences pref = getSharedPreferences("FreelaDuoPrefs", MODE_PRIVATE);
        int idUsuario = pref.getInt("user_id", -1);

        if (idUsuario == -1) {
            Toast.makeText(this, "Erro: Usuário não encontrado na sessão.", Toast.LENGTH_SHORT).show();
            return;
        }

        DeletarUsuarioDto dto = new DeletarUsuarioDto(idUsuario);
        ApiService api = ApiClient.getApiService();

        api.deletarUsuario(dto).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(ProfileActivity.this, "Conta excluída com sucesso!", Toast.LENGTH_SHORT).show();

                    pref.edit().clear().apply();
                    getSharedPreferences(PREFS_PLANOS, MODE_PRIVATE).edit().clear().apply();

                    Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(ProfileActivity.this, "Erro ao excluir conta. Tente novamente.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(ProfileActivity.this, "Erro de conexão: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}