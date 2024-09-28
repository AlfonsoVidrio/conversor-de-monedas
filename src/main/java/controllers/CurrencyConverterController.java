package controllers;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import models.ExchangeRateInfo;
import services.CodesCurrenciesService;
import models.CodesCurrencies;
import services.ExchangeRateService;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CurrencyConverterController implements Initializable {
    @FXML
    private Button btnSwap; // Botón para intercambiar las monedas base y objetivo

    @FXML
    private ComboBox<String> cbxBaseCurrency; // ComboBox para seleccionar la moneda base

    @FXML
    private ComboBox<String> cbxTargetCurrency; // ComboBox para seleccionar la moneda objetivo

    @FXML
    private TextField txtAmount; // Campo de texto para ingresar el monto a convertir

    @FXML
    private Label lbBaseToTarget; // Etiqueta para mostrar la tasa de cambio de base a objetivo

    @FXML
    private Label lbTargetToCode; // Etiqueta para mostrar la tasa de cambio de objetivo a base

    @FXML
    private Label lbMessage; // Etiqueta para mostrar mensajes al usuario

    @FXML
    private Label lbTitle; // Etiqueta para el título (no se usa en el código actual)

    private String[] currencies; // Arreglo de monedas soportadas

    private String lastAmount = ""; // Último valor del TextField
    private String lastBaseCurrency = ""; // Último valor del ComboBox de moneda base
    private String lastTargetCurrency = ""; // Último valor del ComboBox de moneda objetivo

    private PauseTransition pause; // Transición para el debounce

    // Constructor del controlador
    public CurrencyConverterController() {
        // Servicio para obtener los códigos de monedas soportadas
        CodesCurrenciesService codesCurrenciesService = new CodesCurrenciesService();
        CodesCurrencies codesCurrencies = codesCurrenciesService.getCodesCurrencies();

        this.currencies = codesCurrencies.getSupportedCode(); // Inicializa el arreglo de monedas
        this.pause = new PauseTransition(Duration.seconds(0.5)); // Configura el debounce con 0.5 segundos
    }

    // Método que se llama al inicializar el controlador
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Agrega las monedas al ComboBox de moneda base y objetivo
        cbxBaseCurrency.getItems().addAll(currencies);
        cbxTargetCurrency.getItems().addAll(currencies);

        // Agrega manejadores de eventos para filtrar los elementos del ComboBox al escribir
        cbxBaseCurrency.getEditor().addEventHandler(KeyEvent.KEY_RELEASED, event -> filterItems(cbxBaseCurrency));
        cbxTargetCurrency.getEditor().addEventHandler(KeyEvent.KEY_RELEASED, event -> filterItems(cbxTargetCurrency));

        // Agrega manejadores de eventos para limpiar la selección y mostrar todos los elementos al hacer clic en el editor
        cbxBaseCurrency.getEditor().addEventHandler(MouseEvent.MOUSE_CLICKED, event -> clearSelectionAndShow(cbxBaseCurrency));
        cbxTargetCurrency.getEditor().addEventHandler(MouseEvent.MOUSE_CLICKED, event -> clearSelectionAndShow(cbxTargetCurrency));

        // Agrega listeners para detectar cambios en los valores
        addListeners();
    }

    // Método para filtrar los elementos del ComboBox según el texto ingresado
    private void filterItems(ComboBox<String> comboBox) {
        String filter = comboBox.getEditor().getText().toLowerCase();
        comboBox.getItems().setAll(
                Stream.of(currencies) // Usa el arreglo original para filtrar
                        .filter(item -> item.toLowerCase().contains(filter))
                        .collect(Collectors.toList())
        );
        comboBox.show(); // Muestra el ComboBox con los elementos filtrados
    }

    // Método para limpiar la selección y mostrar todos los elementos del ComboBox
    private void clearSelectionAndShow(ComboBox<String> comboBox) {
        comboBox.getEditor().clear(); // Limpia el texto del editor
        comboBox.getItems().setAll(currencies); // Restaura los elementos originales
        comboBox.show(); // Muestra el ComboBox con todos los elementos
    }

    // Método para agregar listeners a los componentes
    private void addListeners() {
        txtAmount.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*(\\.\\d*)?")) { // Verifica que el nuevo valor sea un número válido
                txtAmount.setText(oldValue); // Si no es válido, restaura el valor anterior
            }
            // Configura el manejador de eventos para el debounce para evitar llamadas innecesarias a la API
            pause.setOnFinished(event -> handleChange()); // Configura el debounce para manejar el cambio
            pause.playFromStart(); // Inicia el debounce
        });
        cbxBaseCurrency.valueProperty().addListener((observable, oldValue, newValue) -> handleChange());
        cbxTargetCurrency.valueProperty().addListener((observable, oldValue, newValue) -> handleChange());
    }

    // Método para manejar cambios en los valores
    private void handleChange() {
        String amount = txtAmount.getText();
        // Obtiene los códigos de moneda seleccionados en los ComboBox de moneda base y objetivo (si no hay ninguno, asigna una cadena vacía)
        String baseCurrency = cbxBaseCurrency.getValue() != null && !cbxBaseCurrency.getValue().isEmpty() ? cbxBaseCurrency.getValue().substring(0, 3) : "";
        String targetCurrency = cbxTargetCurrency.getValue() != null && !cbxTargetCurrency.getValue().isEmpty() ? cbxTargetCurrency.getValue().substring(0, 3) : "";

        // Verifica que los valores no sean nulos y que sean diferentes a los valores anteriores
        if (amount != null && baseCurrency != null && targetCurrency != null &&
                (!amount.equals(lastAmount) || !baseCurrency.equals(lastBaseCurrency) || !targetCurrency.equals(lastTargetCurrency))) {
            // Verifica que los valores no estén vacíos
            if (!amount.isEmpty() && !baseCurrency.isEmpty() && !targetCurrency.isEmpty()) {
                try {
                    // Convierte el importe a un valor numérico
                    double amountValue = Double.parseDouble(amount);
                    // Verifica que el importe no sea negativo
                    if (amountValue < 0) {
                        lbMessage.setText("El importe ingresado no puede ser negativo.");
                    } else {
                        // Servicio para obtener la información de la tasa de cambio
                        ExchangeRateService exchangeRateService = new ExchangeRateService();
                        ExchangeRateInfo exchangeRateInfo = exchangeRateService.getExchangeRateInfo(baseCurrency, targetCurrency, amount);

                        exchangeRateInfo.setBaseCode(cbxBaseCurrency.getValue());
                        exchangeRateInfo.setTargetCode(cbxTargetCurrency.getValue());
                        exchangeRateInfo.setAmount(amount);

                        lbMessage.setText(exchangeRateInfo.toString());

                        // Muestra el valor de la moneda de la base a la objetivo
                        lbBaseToTarget.setText(String.format("1 %s = %.4f %s", baseCurrency, exchangeRateInfo.getRate(), targetCurrency));

                        // Muestra el valor de la moneda de la objetivo a la base
                        lbTargetToCode.setText(String.format("1 %s = %.4f %s", targetCurrency, 1 / exchangeRateInfo.getRate(), baseCurrency));
                    }
                } catch (NumberFormatException e) {
                    lbMessage.setText("El importe ingresado no es válido.");
                }
            }

            lastAmount = amount;
            lastBaseCurrency = baseCurrency;
            lastTargetCurrency = targetCurrency;
        }
    }

    // Método manejador del evento del botón de intercambio
    @FXML
    void btnSwapEvent(ActionEvent event) {
        String temp = cbxBaseCurrency.getValue();
        cbxBaseCurrency.setValue(cbxTargetCurrency.getValue());
        cbxTargetCurrency.setValue(temp);
    }

    @FXML
    void cbxBaseCurrencyEvent(ActionEvent event) {}

    @FXML
    void cbxTargetCurrencyEvent(ActionEvent event) {}
}