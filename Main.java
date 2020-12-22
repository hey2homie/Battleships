import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;


public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("stylefiles/begin.fxml"));
        stage.setScene(new Scene(root));
        try {   // For MacOS users
            URL iconURL = Main.class.getResource("./images/icon.png");
            java.awt.Image image = new ImageIcon(iconURL).getImage();
            com.apple.eawt.Application.getApplication().setDockIconImage(image);
        } catch (Exception ignored) {
        }
        stage.setTitle("Battleships");
        stage.setResizable(false);  // Unfortunately :(
        stage.getIcons().add(new Image("./images/icon.png"));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
