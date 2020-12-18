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

public class PlacementPlayer1 implements Initializable {

    @FXML
    Button nextPlacement;
    @FXML
    Label name1;
    @FXML
    GridPane gridPane1;

    @FXML
    private void toPlayer2(ActionEvent event) throws IOException {

        Utilities.prepareBoards(gridPane1, 1);

        Utilities.changeScene(event, "../../stylefiles/placement2.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        name1.setText(Players.getNamePlayer1());
    }

    public void SetRandomly1() {

        Utilities.fillRandomly(gridPane1, 1);
    }
}