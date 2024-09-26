package application;

import io.github.cdimascio.dotenv.Dotenv;

public class App {
    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.load();
        String value = dotenv.get("MI_VARIABLE");
        System.out.println("El valor de MI_VARIABLE es: " + value);
    }
}