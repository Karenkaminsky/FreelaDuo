    package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

public class ProfileActivity extends AppCompatActivity {

    private ShapeableImageView imgProfile;
    private FloatingActionButton btnChangePhoto;

    private TextInputEditText edtName, edtTitle, edtBio;

    private MaterialButton btnSaveProfile, btnLogout, btnViewPlans;
    private Button btnDeleteAccount;
    private ImageButton btnBack;

    // Elementos visuais do plano
    private TextView txtCurrentPlan;
    private TextView txtPlanLabel;
    private TextView txtPlanBadge;
    private ImageView imgCurrentPlanIcon;
    private LinearLayout containerPlanIcon;
    private MaterialCardView cardPlan;

    private Uri selectedImageUri;
    private boolean isDataChanged = false;

    // Preferências dos planos
    private static final String PREFS_PLANOS = "FreelaDuoPlanos";
    private static final String CHAVE_PLANO = "plano_atual";

    // Cores dos planos
    private static final int ROXO = Color.rgb(192, 132, 252); // #C084FC
    private static final int ROXO_FUNDO = Color.rgb(41, 32, 68); // #292044
    private static final int ROXO_BOTAO = Color.rgb(37, 32, 68); // #252044

    private static final int DOURADO = Color.rgb(251, 191, 36); // #FBBF24
    private static final int DOURADO_FUNDO = Color.rgb(59, 48, 27); // #3B301B
    private static final int DOURADO_CARD = Color.rgb(33, 31, 26); // #211F1A

    // Lançador para abrir a galeria
    private final ActivityResultLauncher<String> galleryLauncher =
            registerForActivityResult(
                    new ActivityResultContracts.GetContent(),
                    uri -> {
                        if (uri != null) {
                            selectedImageUri = uri;
                            imgProfile.setImageURI(uri);
                            isDataChanged = true;

                            Toast.makeText(
                                    this,
                                    "Foto atualizada! Lembre-se de salvar as alterações.",
                                    Toast.LENGTH_SHORT
                            ).show();
                        }
                    }
            );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        BottomNavigationHelper.configurar(this, R.id.nav_profile);

        // Componentes do perfil
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

        // Componentes visuais do plano
        cardPlan = findViewById(R.id.cardPlan);
        txtCurrentPlan = findViewById(R.id.txtCurrentPlan);
        txtPlanLabel = findViewById(R.id.txtPlanLabel);
        txtPlanBadge = findViewById(R.id.txtPlanBadge);
        imgCurrentPlanIcon = findViewById(R.id.imgCurrentPlanIcon);
        containerPlanIcon = findViewById(R.id.containerPlanIcon);

        // Carregar dados iniciais
        loadUserProfile();

        // Monitorar edições
        setupChangeListeners();

        // Atualizar plano atual
        atualizarPlanoAtual();

        // Eventos de clique
        btnChangePhoto.setOnClickListener(v ->
                galleryLauncher.launch("image/*"));

        btnSaveProfile.setOnClickListener(v ->
                saveUserProfile());

        btnLogout.setOnClickListener(v ->
                confirmLogout());

        btnDeleteAccount.setOnClickListener(v ->
                confirmAccountDeletion());

        // Abrir tela de planos
        btnViewPlans.setOnClickListener(v -> {
            Intent intent = new Intent(
                    ProfileActivity.this,
                    PlanosActivity.class
            );
            startActivity(intent);
        });

        // Botão voltar do cabeçalho
        btnBack.setOnClickListener(v -> finish());

        // Botão nativo de voltar
        getOnBackPressedDispatcher().addCallback(this,
                new OnBackPressedCallback(true) {
                    @Override
                    public void handleOnBackPressed() {
                        finish();
                    }
                });
    }

    // Atualizar plano sempre que o Perfil voltar a ficar visível
    @Override
    protected void onResume() {
        super.onResume();
        atualizarPlanoAtual();
    }

    // Consultar o plano salvo e atualizar sua aparência
    private void atualizarPlanoAtual() {

        SharedPreferences preferencias = getSharedPreferences(
                PREFS_PLANOS,
                MODE_PRIVATE
        );

        String plano = preferencias.getString(
                CHAVE_PLANO,
                "gratuito"
        );

        boolean premium = plano.equals("premium");

        if (premium) {
            // Plano Premium
            txtCurrentPlan.setText("Plano Premium");

            imgCurrentPlanIcon.setImageResource(
                    R.drawable.outline_diamond_24
            );
            imgCurrentPlanIcon.setImageTintList(
                    ColorStateList.valueOf(DOURADO)
            );

            containerPlanIcon.setBackgroundTintList(
                    ColorStateList.valueOf(DOURADO_FUNDO)
            );

            cardPlan.setCardBackgroundColor(DOURADO_CARD);
            cardPlan.setStrokeColor(DOURADO);

            txtPlanLabel.setTextColor(DOURADO);
            txtPlanBadge.setTextColor(DOURADO);
            txtPlanBadge.setBackgroundTintList(
                    ColorStateList.valueOf(DOURADO_FUNDO)
            );

            btnViewPlans.setTextColor(DOURADO);
            btnViewPlans.setBackgroundTintList(
                    ColorStateList.valueOf(DOURADO_FUNDO)
            );

        } else {
            // Plano Gratuito
            txtCurrentPlan.setText("Plano Gratuito");

            imgCurrentPlanIcon.setImageResource(
                    R.drawable.ic_plan_gift
            );
            imgCurrentPlanIcon.setImageTintList(
                    ColorStateList.valueOf(ROXO)
            );

            containerPlanIcon.setBackgroundTintList(
                    ColorStateList.valueOf(ROXO_FUNDO)
            );

            cardPlan.setCardBackgroundColor(
                    Color.parseColor("#111D30")
            );
            cardPlan.setStrokeColor(ROXO);

            txtPlanLabel.setTextColor(ROXO);
            txtPlanBadge.setTextColor(ROXO);
            txtPlanBadge.setBackgroundTintList(
                    ColorStateList.valueOf(ROXO_FUNDO)
            );

            btnViewPlans.setTextColor(ROXO);
            btnViewPlans.setBackgroundTintList(
                    ColorStateList.valueOf(ROXO_BOTAO)
            );
        }
    }

    // Carregar dados do perfil
    private void loadUserProfile() {
        edtName.setText("Karen Kaminsky");
        edtTitle.setText("Estagiária de TI | Estudante de Informática");
        edtBio.setText("Entusiasta de desenvolvimento mobile e suporte técnico.");

        isDataChanged = false;
    }

    // Monitorar alterações nos campos
    private void setupChangeListeners() {

        TextWatcher watcher = new TextWatcher() {

            @Override
            public void beforeTextChanged(
                    CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(
                    CharSequence s, int start, int before, int count) {
                isDataChanged = true;
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
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

        // Integração POST/PUT com a API em PHP futuramente
        isDataChanged = false;

        Toast.makeText(
                this,
                "Perfil atualizado com sucesso!",
                Toast.LENGTH_LONG
        ).show();
    }

    // Confirmação para sair da conta
    private void confirmLogout() {

        new AlertDialog.Builder(this)
                .setTitle("Sair da Conta")
                .setMessage("Tem certeza de que deseja sair? Você precisará realizar o login novamente.")
                .setPositiveButton("Sair",
                        (dialog, which) -> logoutUser())
                .setNegativeButton("Cancelar", null)
                .show();
    }

    // Encerrar sessão
    private void logoutUser() {

        SharedPreferences preferences = getSharedPreferences(
                "FreelaDuoPrefs",
                MODE_PRIVATE
        );

        preferences.edit().clear().apply();

        Intent intent = new Intent(
                ProfileActivity.this,
                MainActivity.class
        );

        intent.setFlags(
                Intent.FLAG_ACTIVITY_NEW_TASK |
                        Intent.FLAG_ACTIVITY_CLEAR_TASK
        );

        startActivity(intent);
        finish();
    }

    // Confirmação para excluir conta
    private void confirmAccountDeletion() {

        new AlertDialog.Builder(this)
                .setTitle("Excluir Conta")
                .setMessage("Tem certeza de que deseja excluir sua conta? Esta ação não poderá ser desfeita.")
                .setPositiveButton("Sim, excluir", (dialog, which) -> {

                    Toast.makeText(
                            ProfileActivity.this,
                            "Conta excluída com sucesso.",
                            Toast.LENGTH_LONG
                    ).show();

                    logoutUser();
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }
}