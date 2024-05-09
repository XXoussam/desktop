package com.esprit.financialhub_desktop.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.io.IOException;

public class ExchangeMoney {

    private static final OkHttpClient client = new OkHttpClient();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static double convertCurrency(String fromCurrency, String toCurrency, double amount) throws IOException {
        String apiKey = "979dfcce56msh0e9d861770190a6p13cbc9jsn6fc12d51cd0f";

        Request request = new Request.Builder()
                .url("https://currency-conversion-and-exchange-rates.p.rapidapi.com/convert?from=" + fromCurrency + "&to=" + toCurrency + "&amount=" + amount)
                .get()
                .addHeader("X-RapidAPI-Key", apiKey)
                .addHeader("X-RapidAPI-Host", "currency-conversion-and-exchange-rates.p.rapidapi.com")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected response code: " + response);
            }

            // Parse the JSON response
            JsonNode rootNode = objectMapper.readTree(response.body().byteStream());
            double convertedAmount = rootNode.get("result").asDouble();

            return convertedAmount;
        }
    }

   /* public static void main(String[] args) {
        try {
            double amount = 1200;
            String fromCurrency = "USD";
            String toCurrency = "TND";

            double convertedAmount = convertCurrency(fromCurrency, toCurrency, amount);
            System.out.println("Converted amount: " + convertedAmount);
        } catch (IOException e) {
            System.err.println("Error converting currency: " + e.getMessage());
        }
    }*/
}
