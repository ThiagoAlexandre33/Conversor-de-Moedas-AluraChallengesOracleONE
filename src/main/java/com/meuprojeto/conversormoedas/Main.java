package com.meuprojeto.conversormoedas;

import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ExchangeRateClient client = new ExchangeRateClient();

        System.out.println("Bem-vindo ao conversor de moedas!");

        while (true) {
            System.out.println("\nEscolha uma opção:");
            System.out.println("1. Converter moeda");
            System.out.println("2. Listar moedas disponíveis");
            System.out.println("0. Sair");

            int opcao = scanner.nextInt();
            scanner.nextLine();

            if (opcao == 0) {
                break;
            } else if (opcao == 1) {
                System.out.println("\nEscolha uma moeda base:");
                System.out.println("1. USD - Dólar americano");
                System.out.println("2. EUR - Euro");
                System.out.println("3. BRL - Real brasileiro");
                System.out.println("4. ARS - Peso argentino");
                System.out.println("5. BOB - Boliviano");
                System.out.println("6. CLP - Peso chileno");

                int opcaoMoedaBase = scanner.nextInt();
                scanner.nextLine();

                String moedaBase;
                switch (opcaoMoedaBase) {
                    case 1 -> moedaBase = "USD";
                    case 2 -> moedaBase = "EUR";
                    case 3 -> moedaBase = "BRL";
                    case 4 -> moedaBase = "ARS";
                    case 5 -> moedaBase = "BOB";
                    case 6 -> moedaBase = "CLP";
                    default -> {
                        System.out.println("Opção inválida.");
                        continue;
                    }
                }

                try {
                    Map<String, Double> taxas = client.obterTaxasDeCambio(moedaBase);

                    System.out.println("\nEscolha uma moeda de destino:");
                    int i = 1;
                    for (String moeda : taxas.keySet()) {
                        System.out.println(i + ". " + moeda);
                        i++;
                    }

                    int opcaoMoedaDestino = scanner.nextInt();
                    scanner.nextLine();

                    String moedaDestino = (String) taxas.keySet().toArray()[opcaoMoedaDestino - 1];

                    System.out.print("Digite o valor a ser convertido: ");
                    double valor = scanner.nextDouble();
                    scanner.nextLine();

                    if (taxas.containsKey(moedaDestino)) {
                        double taxa = taxas.get(moedaDestino);
                        double valorConvertido = valor * taxa;
                        System.out.printf("%.2f %s = %.2f %s%n", valor, moedaBase, valorConvertido, moedaDestino);
                    } else {
                        System.out.println("Moeda de destino inválida.");
                    }
                } catch (IOException e) {
                    System.err.println("Erro ao obter as taxas de câmbio: " + e.getMessage());
                }
            } else if (opcao == 2) {
                try {
                    Map<String, Double> taxas = client.obterTaxasDeCambio("USD");
                    System.out.println("\nMoedas disponíveis:");
                    for (String moeda : taxas.keySet()) {
                        System.out.println(moeda);
                    }
                } catch (IOException e) {
                    System.err.println("Erro ao obter a lista de moedas: " + e.getMessage());
                }
            } else {
                System.out.println("Opção inválida.");
            }
        }

        System.out.println("Encerrando o programa...");
        scanner.close();
    }
}
