package services;

import api.ApiClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import models.ExchangeRateInfo;
import models.ExchangeRateInfoOmdb;

// Clase que proporciona servicios relacionados con la tasa de cambio
public class ExchangeRateService {
    private final ApiClient apiClient; // Cliente para realizar solicitudes a la API
    private final Gson gson; // Objeto Gson para convertir JSON a objetos Java

    // Constructor que inicializa el cliente de la API y el objeto Gson
    public ExchangeRateService() {
        this.apiClient = new ApiClient();
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    // Método que obtiene la información de la tasa de cambio entre dos monedas
    public ExchangeRateInfo getExchangeRateInfo(String baseCurrency, String targetCurrency, String amount) {
        // Obtiene el JSON de la tasa de cambio desde la API
        String exchangeRateJson = apiClient.getJsonExchangeRate(baseCurrency, targetCurrency, amount);
        // Convierte el JSON en un objeto ExchangeRateInfoOmdb
        ExchangeRateInfoOmdb exchangeRateInfoOmdb = gson.fromJson(exchangeRateJson, ExchangeRateInfoOmdb.class);
        // Crea y devuelve un objeto ExchangeRateInfo a partir del objeto ExchangeRateInfoOmdb
        return new ExchangeRateInfo(exchangeRateInfoOmdb);
    }
}