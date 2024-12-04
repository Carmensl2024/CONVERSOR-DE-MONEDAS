package com.alurachallenge.conversormonedas1;

import com.google.gson.Gson;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;
import java.util.Scanner;

public class Conversor {

    private final Scanner teclado = new Scanner(System.in);

    public double conversor(double cantidad, double tasaConversion) {
        return cantidad * tasaConversion;
    }

    public void muestraElMenu() {
        int opcion = -1;
        while (opcion != 0) {
            String menu = """
                    ********************************************
                    1-       EUR      euro
                    2-       USD      dólares americanos
                    3-       COP      pesos colombianos
                    4-       JPY      yen japonés
                    5-       BRL      real brasileño
                    6-       CAD      dolar canadiense
                    7-       MXN      pesos mexicanos
                    8-       PEN      pesos peruano
                    9-       CLP      pesos chilenos
                    0-       Salir
                    ********************************************
                    """;
            System.out.println(menu);
            System.out.print("Escriba el número de la opción ");
            try {
                opcion = Integer.parseInt(teclado.nextLine()); // Evita problemas al ingresar texto
                switch (opcion) {
                    case 1 -> convertirMoneda("EUR");
                    case 2 -> convertirMoneda("USD");
                    case 3 -> convertirMoneda("COP");
                    case 4 -> convertirMoneda("JPY");
                    case 5 -> convertirMoneda("BRL");
                    case 6 -> convertirMoneda("CAD");
                    case 7 -> convertirMoneda("MXN");
                    case 8 -> convertirMoneda("PEN");
                    case 9 -> convertirMoneda("CLP");
                    case 0 -> System.out.println("¡Hasta pronto!");
                    default -> System.out.println("Opción inválida. Por favor, selecciona una opción válida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Por favor, introduce un número.");
            }
        }
    }

    private void convertirMoneda(String monedaOrigen) {
        System.out.println("""
                ********************************************
                ¿Qué cantidad deseas convertir?
                ********************************************
                """);
        double cantidad;
        try {
            cantidad = Double.parseDouble(teclado.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida. Por favor, introduce un número válido.");
            return;
        }

        System.out.println("""
                **********************************************************
                Escribe el código de la moneda que vas a convertir:
                  (EUR, USD, COP, JPY, BRL, CAD, MXN, PEN, CLP)
                **********************************************************
                """);
        String monedaDestino = teclado.nextLine().toUpperCase();

        try {
            double tasaConversion = obtenerTasaConversion(monedaOrigen, monedaDestino);
            double resultado = conversor(cantidad, tasaConversion);
            System.out.printf("Resultado: %.2f %s%n", resultado, monedaDestino);
        } catch (Exception e) {
            System.out.println("Error al realizar la conversión: " + e.getMessage());
        }
    }

    private double obtenerTasaConversion(String monedaOrigen, String monedaDestino) throws Exception {
        String url = String.format("https://v6.exchangerate-api.com/v6/efdeed70d3179e3e43699de9/latest/%s", monedaOrigen);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Gson gson = new Gson();
        Moneda moneda = gson.fromJson(response.body().strip(), Moneda.class);

        Map<String, Double> tasasConversion = moneda.tasasConversion();

        Double tasaConversion = tasasConversion.get(monedaDestino);

        if (tasaConversion != null) {
            return tasaConversion;
        } else {
            throw new RuntimeException("Tasa de conversión no encontrada para la moneda destino.");
        }
    }
}





