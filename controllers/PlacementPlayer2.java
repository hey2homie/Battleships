package controllers;

import controllers.classes.Players;
import controllers.classes.Utilities;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PlacementPlayer2 implements Initializable {

    @FXML
    Button beginGame;
    @FXML
    Label name2;
    @FXML
    GridPane gridPane2;

    @FXML
    private void beginGame(ActionEvent event) throws IOException {

        Utilities.prepareBoards(gridPane2, 2);

        Utilities.changeScene(event, "../../stylefiles/game1.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        name2.setText(Players.getNamePlayer2());
    }

    public void SetRandomly2() {

        Utilities.fillRandomly(gridPane2, 2);
    }
}
