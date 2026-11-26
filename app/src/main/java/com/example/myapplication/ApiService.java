package com.example.myapplication;

import java.util.List; // Necessário para o List<Projeto>
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET; // Necessário para o @GET
import retrofit2.http.POST;

public interface ApiService {

    // Endpoint para autenticação/login
    @POST("login.php")
    Call<LoginResponse> realizarLogin(@Body LoginRequestDto loginData);

    // Endpoint para cadastro de novo usuário
    @POST("cadastro.php")
    Call<ResponseBody> cadastrarUsuario(@Body RegisterRequestDto registerRequest);

    // Endpoint para exclusão de conta de usuário
    @POST("deletar_usuario.php")
    Call<Void> deletarUsuario(@Body DeletarUsuarioDto deletarData);

    // ==========================================
    // Endpoints do CRUD de Projetos (O Feed)
    // ==========================================

    // Listar todos os projetos
    @GET("projetos.php")
    Call<List<Projeto>> listarProjetos();

    // Criar um novo projeto
    @POST("projetos.php")
    Call<ResponseBody> criarProjeto(@Body Projeto projeto);
}