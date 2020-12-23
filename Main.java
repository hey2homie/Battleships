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

        try {   // Add dock icon for MacOS users
            URL iconURL = Main.class.getResource("./images/icon.png");
            java.awt.Image image = new ImageIcon(iconURL).getImage();
            // This can cause an error during compilation. At least it happened to me. As IDE suggests, need to add
            // some kind of parameter to the сompiler options or ¯\_(ツ)_/¯
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
