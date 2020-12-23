package controllers;

import controllers.classes.Players;
import controllers.classes.Timing;
import controllers.classes.Utilities;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GamePlayer2 implements Initializable {

    @FXML
    Button nextPlayer1;
    @FXML
    Label timerLabel;
    @FXML
    GridPane gridPane = Players.gameBoardPlayer1;
    @FXML
    Pane pane;
    @FXML
    Label name;
    @FXML
    TextArea textArea;

    Timing timer = new Timing(SettingsPage.mills);

    /*
    Completely the same as GamePlayer1 controller except for the second player and another fxml file
     */

    @FXML
    private void toPlayer1Move(ActionEvent event) throws IOException {

        if (!Utilities.clickAllowance) {
            if (Players.HEALTH_PLAYER1 == 0) {
                Utilities.winner = Players.getNamePlayer2();
                Utilities.changeScene(event, "../../stylefiles/score.fxml");
            } else {
                Utilities.nextMove(2, gridPane, timer);
                Utilities.changeScene(event, "../../stylefiles/game1.fxml");
            }
        } else {
            Utilities.raiseAlert("You can make a move! Why don't you use this opportunity?");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        textArea.setText(Utilities.textPlayer1.getText());
        Utilities.putScrollBarDown(textArea);
        name.setText(Players.getNamePlayer2());
        Utilities.newMove(pane, 2, timerLabel, timer);
        gridPane.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
    }

    EventHandler<MouseEvent> eventHandler = e -> {
        textArea.setText(Utilities.textPlayer1.getText());
        Node clickedNode = e.getPickResult().getIntersectedNode();
        if (clickedNode != gridPane) {
            Utilities.clickEvent(gridPane, clickedNode, 2, textArea);
        }
    };
}
