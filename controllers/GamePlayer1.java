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

public class GamePlayer1 implements Initializable {

    @FXML
    Button nextPlayer2;
    @FXML
    Label timerLabel;
    @FXML
    GridPane gridPane = Players.gameBoardPlayer2;
    @FXML
    Pane pane;
    @FXML
    Label name;
    @FXML
    TextArea textArea;

    Timing timer = new Timing(SettingsPage.mills);

    @FXML
    private void toPlayer2Move(ActionEvent event) throws IOException {

        /*
        Checking if player has made a move or not. If so, checking whether or not all ships have sunk and if so, go to
        the score screen.
         */

        if (!Utilities.clickAllowance) {
            if (Players.HEALTH_PLAYER2 == 0) {
                Utilities.winner = Players.getNamePlayer1();
                Utilities.changeScene(event, "../../stylefiles/score.fxml");
            } else {
                Utilities.nextMove(1, gridPane, timer);
                Utilities.changeScene(event, "../../stylefiles/game2.fxml");
            }
        } else {
            Utilities.raiseAlert("You can make a move! Why don't you use this opportunity?");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        textArea.setText(Utilities.textPlayer2.getText());  // Get history of moves
        Utilities.putScrollBarDown(textArea);   // Put textArea to the bottom at the bottom when screen is turn on
        name.setText(Players.getNamePlayer1());
        Utilities.newMove(pane, 1, timerLabel, timer);  // Run timer, update grid pane with previous moves
        gridPane.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);    // Clicks on the grid pane
    }

    EventHandler<MouseEvent> eventHandler = e -> {
        textArea.setText(Utilities.textPlayer2.getText());  // Without it after turn text doesn't display dynamically
        Node clickedNode = e.getPickResult().getIntersectedNode();  // Understand where player clicked
        Utilities.putScrollBarDown(textArea);
        if (clickedNode != gridPane) {
            Utilities.clickEvent(gridPane, clickedNode, 1, textArea);   // Full click logic in this method
        }
    };
}
