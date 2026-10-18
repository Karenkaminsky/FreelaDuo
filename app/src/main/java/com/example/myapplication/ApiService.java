package com.example.myapplication;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {

    // Endpoint para autenticação/login
    @POST("login.php")
    Call<LoginResponseDto> realizarLogin(@Body LoginRequestDto loginData);

    // Endpoint para cadastro de novo usuário
    @POST("cadastrar.php")
    Call<ResponseBody> cadastrarUsuario(@Body RegisterRequestDto registerRequest);

    // Endpoint para exclusão de conta de usuário
    @POST("deletar_usuario.php")
    Call<Void> deletarUsuario(@Body DeletarUsuarioDto deletarData);
}