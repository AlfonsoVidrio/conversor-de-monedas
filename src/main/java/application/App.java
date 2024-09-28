package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import utils.Paths;

public class App extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(Paths.MAIN_VIEW));
        AnchorPane pane = loader.load();

        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.setResizable(false);

        stage.setTitle("Conversor de monedas ONE");
        stage.getIcons().add(new Image(getClass().getResourceAsStream(Paths.IMAGE_ICON)));

        stage.show();
    }
}