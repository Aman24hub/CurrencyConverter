package org.example;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3. Response;
import org.json.JSONObject;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Scanner;

public class CurrencyConverter {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the Source Currency (e.g., INR): ");
        String convertFrom = scanner.nextLine();
        System.out.println("Enter the Target Currency (e.g., USD): ");
        String convertTo = scanner.nextLine();
        System.out.println("Enter the Amount to Convert");
        BigDecimal quantity = scanner.nextBigDecimal();

        String urlString = "https://api.exchangerate.host/latest?base=" + convertFrom.toUpperCase();

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(urlString)
                .get()
                .build();

        Response response = null;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String stringResponse = null;
        try {
            stringResponse = response.body().string();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        JSONObject jsonObject = new JSONObject(stringResponse);
        JSONObject ratesObject = jsonObject.getJSONObject("rates");
        BigDecimal rate = ratesObject.getBigDecimal(convertTo.toUpperCase());

        BigDecimal result = rate.multiply(quantity);
        System.out.println(result);
    }
}