package application;

import models.ExchangeRateInfo;
import services.ExchangeRateService;

public class App {
    public static void main(String[] args) {
        // Crea una instancia del servicio de tasa de cambio
        ExchangeRateService exchangeRateService = new ExchangeRateService();
        // Obtiene la información de la tasa de cambio
        ExchangeRateInfo exchangeRateInfo = exchangeRateService.getExchangeRateInfo("USD", "MXN", "2");
        System.out.println(exchangeRateInfo);
    }
}