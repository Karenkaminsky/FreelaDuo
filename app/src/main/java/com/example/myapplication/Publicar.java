package com.example.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

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

        Button btnGaleria = findViewById(R.id.btnGaleria);
        Button btnArquivos = findViewById(R.id.btnArquivos);
        Button btnDrive = findViewById(R.id.btnDrive);
        Button btnLink = findViewById(R.id.btnLink);
        Button btnPublicar = findViewById(R.id.btnPublicar);
        Button btnVoltar = findViewById(R.id.btnVoltar);

        btnGaleria.setOnClickListener(v -> abrirGaleria());
        btnArquivos.setOnClickListener(v -> abrirArquivos());
        btnDrive.setOnClickListener(v -> abrirDrive());
        btnLink.setOnClickListener(v -> edtLink.requestFocus());
        btnPublicar.setOnClickListener(v -> validarPublicacao());
        btnVoltar.setOnClickListener(v -> finish());
    }

    private void abrirGaleria() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);

        startActivityForResult(
                Intent.createChooser(intent, "Escolha suas fotos"),
                REQ_GALERIA
        );
    }

    private void abrirArquivos() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);

        startActivityForResult(
                Intent.createChooser(intent, "Escolha seus arquivos"),
                REQ_ARQUIVOS
        );
    }

    private void abrirDrive() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);

        startActivityForResult(
                Intent.createChooser(intent, "Escolha um serviço ou arquivo"),
                REQ_DRIVE
        );
    }

    @Override
    protected void onActivityResult(
            int requestCode,
            int resultCode,
            @Nullable Intent data
    ) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK || data == null) {
            return;
        }

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
        if (anexos.contains(uri)) {
            return;
        }

        anexos.add(uri);
        nomesAnexos.add(obterNomeArquivo(uri));
    }

    private String obterNomeArquivo(Uri uri) {
        String nome = uri.getLastPathSegment();

        try (Cursor cursor = getContentResolver().query(
                uri,
                new String[]{OpenableColumns.DISPLAY_NAME},
                null,
                null,
                null
        )) {
            if (cursor != null && cursor.moveToFirst()) {
                int coluna = cursor.getColumnIndex(
                        OpenableColumns.DISPLAY_NAME
                );

                if (coluna >= 0) {
                    nome = cursor.getString(coluna);
                }
            }
        } catch (Exception e) {
            // Mantém o nome alternativo caso não consiga consultar o arquivo.
        }

        return nome != null ? nome : "Arquivo selecionado";
    }

    private void atualizarListaAnexos() {
        if (nomesAnexos.isEmpty()) {
            txtAnexos.setText("Nenhum arquivo anexado");
            return;
        }

        StringBuilder lista = new StringBuilder("Arquivos anexados:\n");

        for (String nome : nomesAnexos) {
            lista.append("• ").append(nome).append("\n");
        }

        txtAnexos.setText(lista.toString().trim());
    }

    private void validarPublicacao() {
        String texto = edtTexto.getText().toString().trim();
        String link = edtLink.getText().toString().trim();

        if (texto.isEmpty() && anexos.isEmpty() && link.isEmpty()) {
            Toast.makeText(
                    this,
                    "Escreva algo ou adicione um anexo ou link.",
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }

        if (!link.isEmpty()
                && !link.matches("(?i)^https?://.+")) {
            Toast.makeText(
                    this,
                    "Informe um link começando com http:// ou https://.",
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }

        Toast.makeText(
                this,
                "Publicação validada! A integração com o Feed será feita na próxima etapa.",
                Toast.LENGTH_LONG
        ).show();
    }
}
