import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConsultaTasaCambio {
    private static final String API_KEY = "251188b94354d776a80371e7";
    private static final String API_URL = "https://v6.exchangerate-api.com/v6/" + API_KEY + "/latest/USD";

    public TasaCambio obtenerTasasDeCambio() throws IOException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JsonObject jsonResponse = new Gson().fromJson(response.body(), JsonObject.class);

            JsonObject rates = jsonResponse.getAsJsonObject("conversion_rates");

            double tasaDolarAPesoArgentino = rates.get("ARS").getAsDouble();
            double tasaDolarARealBrasileno = rates.get("BRL").getAsDouble();
            double tasaDolarAPesoColombiano = rates.get("COP").getAsDouble();

            return new TasaCambio(tasaDolarAPesoArgentino, tasaDolarARealBrasileno, tasaDolarAPesoColombiano);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IOException("Error al obtener las tasas de cambio", e);
        } catch (Exception e) {
            throw new IOException("Error al procesar la respuesta de la API", e);
        }
    }
}
