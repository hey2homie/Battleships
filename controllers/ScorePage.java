package controllers;

import controllers.classes.Utilities;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ScorePage implements Initializable {

    @FXML
    Button mainMenu;
    @FXML
    Label name;
    @FXML
    Label score;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        name.setText("Congratulations," + Utilities.winner + "!");
        String settings = Utilities.usedSettings;
        String scores = settings.equals("timing") ? Utilities.timePlayer1 + ":" + Utilities.timePlayer2 :
                Utilities.mishitsPlayer1 + ":" + Utilities.mishitsPlayer2;
        score.setText("You won with the following amount of " + settings + ": " + scores);
    }

    @FXML
    private void mainMenu(ActionEvent event) throws IOException {
        Utilities.changeScene(event, "../../stylefiles/begin.fxml");
    }
}
