package com.meuprojeto.conversormoedas;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class ExchangeRateClient {
    private static final String API_URL = "https://api.exchangerate-api.com/v4/latest/";
    private static final String API_KEY = "ab26128d2e1908259d1d8c38"; // Substitua pela sua chave de API real

    public Map<String, Double> obterTaxasDeCambio(String moedaBase) throws IOException {
        String urlString = API_URL + moedaBase + "?access_key=" + API_KEY;
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String linha;
        StringBuilder response = new StringBuilder();
        while ((linha = reader.readLine()) != null) {
            response.append(linha);
        }
        reader.close();

        JsonObject jsonObject = JsonParser.parseString(response.toString()).getAsJsonObject();
        JsonObject rates = jsonObject.getAsJsonObject("rates");

        Map<String, Double> taxas = new HashMap<>();
        for (String moeda : rates.keySet()) {
            taxas.put(moeda, rates.get(moeda).getAsDouble());
        }

        return taxas;
    }
}

