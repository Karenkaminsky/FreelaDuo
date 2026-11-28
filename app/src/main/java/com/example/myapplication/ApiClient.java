package com.example.myapplication;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    // =========================================================================
    // ⚠️ CONFIGURAÇÃO DE REDE DO FREELADUO
    // Deixe descomentada APENAS a opção que você está usando no momento.
    // =========================================================================

    // OPÇÃO 1: Wi-Fi de casa (IP do seu notebook no roteador de casa)
    private static final String BASE_URL = "http://192.168.1.145:8000/";

    // OPÇÃO 2: Dados móveis / Hotspot do celular
    // private static final String BASE_URL = "http://192.168.218.223:8000/";

    // OPÇÃO 3: Emulador do Android Studio
    // private static final String BASE_URL = "http://10.0.2.2:8000/";
   // private static final String BASE_URL = "http://192.168.1.14:8000/";

    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static ApiService getApiService() {
        return getClient().create(ApiService.class);
    }
}