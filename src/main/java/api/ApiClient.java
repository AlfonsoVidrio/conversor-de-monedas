package api;

import io.github.cdimascio.dotenv.Dotenv;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

// Clase que maneja las solicitudes a la API
public class ApiClient {
    // Carga las variables de entorno
    Dotenv dotenv = Dotenv.load();
    // Obtiene la clave de la API desde las variables de entorno
    public final String API_KEY = dotenv.get("API_KEY");

    // Método que obtienela tasa de cambio entre dos monedas en formato JSON
    public String getJsonExchangeRate(String baseCurrency, String targetCurrency, String amount) {
        // URL de la solicitud
        String url = "https://v6.exchangerate-api.com/v6/" + API_KEY + "/pair/" + baseCurrency + "/" + targetCurrency + "/" + amount;

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

            // Obtiene el cuerpo de la respuesta como una cadena JSON
            String json = response.body();

            // Devuelve el JSON
            return json;
        } catch (Exception e) {
            // Maneja cualquier excepción que ocurra
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }
}