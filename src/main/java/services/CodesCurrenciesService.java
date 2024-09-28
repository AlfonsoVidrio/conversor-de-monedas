package services;

import api.ApiClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import models.CodesCurrencies;
import models.CodesCurrenciesOmdb;

public class CodesCurrenciesService {
    ApiClient apiClient = new ApiClient();
    private final Gson gson; // Objeto Gson para convertir JSON a objetos Java

    public CodesCurrenciesService() {
        this.apiClient = new ApiClient();
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    public CodesCurrencies getCodesCurrencies() {
        // Obtiene el JSON de los códigos de moneda desde la API
        String codesCurrenciesJson = apiClient.getCodesCurrencies();
        // Convierte el JSON en un objeto CodesCurrenciesOmdb
        CodesCurrenciesOmdb codesCurrenciesOmdb = gson.fromJson(codesCurrenciesJson, CodesCurrenciesOmdb.class);
        // Crea y devuelve un objeto CodesCurrencies a partir del objeto CodesCurrenciesOmdb
        return new CodesCurrencies(codesCurrenciesOmdb);
    }
}
