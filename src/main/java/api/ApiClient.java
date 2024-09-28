package api;

import io.github.cdimascio.dotenv.Dotenv;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

// Clase que maneja las solicitudes a la API
public class ApiClient {
    // Carga las variables de entorno
    private static final Dotenv dotenv = Dotenv.load();
    // Obtiene la clave de la API desde las variables de entorno
    private static final String API_KEY = dotenv.get("API_KEY");
    // URL base de la API
    private static final String BASE_URL = "https://v6.exchangerate-api.com/v6/";

    // Método que obtiene la tasa de cambio entre dos monedas en formato JSON
    public String getJsonExchangeRate(String baseCurrency, String targetCurrency, String amount) {
        String url = BASE_URL + API_KEY + "/pair/" + baseCurrency + "/" + targetCurrency + "/" + amount;
        return sendHttpRequest(url);
    }

    // Método que obtiene los códigos de moneda soportados por la API en formato JSON
    public String getCodesCurrencies() {
        String url = BASE_URL + API_KEY + "/codes";
        return sendHttpRequest(url);
    }

    // Método auxiliar para enviar solicitudes HTTP y manejar respuestas
    private String sendHttpRequest(String url) {
        try {
            // Crea un cliente HTTP
            HttpClient client = HttpClient.newHttpClient();
            // Crea una solicitud HTTP
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .build();

            // Envía la solicitud y obtiene la respuesta
            HttpResponse<String> response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());

            // Devuelve el cuerpo de la respuesta como una cadena JSON
            return response.body();
        } catch (Exception e) {
            // Maneja cualquier excepción que ocurra
            System.err.println("Error al enviar la solicitud HTTP: " + e.getMessage());
            return null;
        }
    }
}