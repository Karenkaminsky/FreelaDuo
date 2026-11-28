package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Publicar extends AppCompatActivity {

    private static final int REQ_GALERIA = 100;
    private static final int REQ_ARQUIVOS = 101;
    private static final int REQ_DRIVE = 102;

    private EditText edtTexto;
    private EditText edtLink;
    private TextView txtAnexos;

    private final ArrayList<Uri> anexos = new ArrayList<>();
    private final ArrayList<String> nomesAnexos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publicar);

        edtTexto = findViewById(R.id.edtTexto);
        edtLink = findViewById(R.id.edtLink);
        txtAnexos = findViewById(R.id.txtAnexos);

        ImageButton btnGaleria = findViewById(R.id.btnGaleria);
        ImageButton btnArquivos = findViewById(R.id.btnArquivos);
        ImageButton btnDrive = findViewById(R.id.btnDrive);
        ImageButton btnLink = findViewById(R.id.btnLink);

        MaterialButton btnPublicar = findViewById(R.id.btnPublicar);

        btnGaleria.setOnClickListener(v -> abrirGaleria());
        btnArquivos.setOnClickListener(v -> abrirArquivos());
        btnDrive.setOnClickListener(v -> abrirDrive());
        btnLink.setOnClickListener(v -> edtLink.requestFocus());

        btnPublicar.setOnClickListener(v -> enviarPublicacao());
    }

    private void abrirGaleria() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(Intent.createChooser(intent, "Escolha suas fotos"), REQ_GALERIA);
    }

    private void abrirArquivos() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(Intent.createChooser(intent, "Escolha seus arquivos"), REQ_ARQUIVOS);
    }

    private void abrirDrive() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(Intent.createChooser(intent, "Escolha um serviço ou arquivo"), REQ_DRIVE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK || data == null) return;

        if (data.getClipData() != null) {
            int quantidade = data.getClipData().getItemCount();
            for (int i = 0; i < quantidade; i++) {
                adicionarAnexo(data.getClipData().getItemAt(i).getUri());
            }
        } else if (data.getData() != null) {
            adicionarAnexo(data.getData());
        }

        atualizarListaAnexos();
    }

    private void adicionarAnexo(Uri uri) {
        if (anexos.contains(uri)) return;
        anexos.add(uri);
        nomesAnexos.add(obterNomeArquivo(uri));
    }

    private String obterNomeArquivo(Uri uri) {
        String nome = uri.getLastPathSegment();
        try (Cursor cursor = getContentResolver().query(uri, new String[]{OpenableColumns.DISPLAY_NAME}, null, null, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                int coluna = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                if (coluna >= 0) nome = cursor.getString(coluna);
            }
        } catch (Exception e) { }
        return nome != null ? nome : "Arquivo selecionado";
    }

    private void atualizarListaAnexos() {
        if (nomesAnexos.isEmpty()) {
            txtAnexos.setText("Nenhum arquivo anexado");
            return;
        }
        StringBuilder lista = new StringBuilder("Arquivos anexados:\n");
        for (String nome : nomesAnexos) lista.append("• ").append(nome).append("\n");
        txtAnexos.setText(lista.toString().trim());
    }

    private void enviarPublicacao() {
        String texto = edtTexto.getText().toString().trim();

        if (texto.isEmpty()) {
            Toast.makeText(this, "Escreva algo para publicar.", Toast.LENGTH_SHORT).show();
            return;
        }

        SharedPreferences pref = getSharedPreferences("FreelaDuoPrefs", MODE_PRIVATE);
        int idUsuario = pref.getInt("user_id", 1);

        PostRequestDto novoPost = new PostRequestDto(idUsuario, texto);

        ApiService api = ApiClient.getApiService();
        Call<okhttp3.ResponseBody> call = api.criarPost(novoPost);

        call.enqueue(new Callback<okhttp3.ResponseBody>() {
            @Override
            public void onResponse(Call<okhttp3.ResponseBody> call, Response<okhttp3.ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(Publicar.this, "Publicado com sucesso!", Toast.LENGTH_SHORT).show();

                    edtTexto.setText("");
                    edtLink.setText("");
                    anexos.clear();
                    nomesAnexos.clear();
                    atualizarListaAnexos();
                } else {
                    Toast.makeText(Publicar.this, "Erro ao publicar. Código: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<okhttp3.ResponseBody> call, Throwable t) {
                Toast.makeText(Publicar.this, "Erro de rede: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        BottomNavigationHelper.atualizarAbaSelecionada(this, R.id.nav_post);
    }
}