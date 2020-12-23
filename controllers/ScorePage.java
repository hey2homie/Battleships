package controllers;

import controllers.classes.Players;
import controllers.classes.Utilities;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class ScorePage implements Initializable {

    @FXML
    Button exit;
    @FXML
    Label name;
    @FXML
    Label score;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        name.setText("Congratulations, " + Utilities.winner + "!");
        String looser = Utilities.winner.equals(Players.getNamePlayer1()) ? Players.getNamePlayer2() : // Know who lost
                Players.getNamePlayer1();

        // To display correctly score — winner:looser
        int playerNumber = Utilities.winner.equals(Players.getNamePlayer1()) ? 2 : 1;
        String scores;
        if (playerNumber == 1) {
            scores = Utilities.usedSettings.equals("timing") ? Utilities.timePlayer1 + ":" + Utilities.timePlayer2 :
                    Utilities.mishitsPlayer1 + ":" + Utilities.mishitsPlayer2;  // Display settings that players chose
        } else {
            scores = Utilities.usedSettings.equals("timing") ? Utilities.timePlayer2 + ":" + Utilities.timePlayer1 :
                    Utilities.mishitsPlayer2 + ":" + Utilities.mishitsPlayer1;  // Display settings that players chose
        }

        score.setText("You won with the following amount of " + Utilities.usedSettings + " " + scores);

        try {   // Writing the result to the file to display later on history screen
            String content = Utilities.winner + " won against " + looser + " with the following " +
                    Utilities.usedSettings + " settings " + scores + "&#10;";
            FileWriter fileWriter = new FileWriter("src/gameHistory.txt", true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(content);
            bufferedWriter.newLine();
            bufferedWriter.close();
        } catch (IOException ignored) {
        }
    }

    @FXML
    private void exit() {

        // TODO: Not to exit application, but to allow players play another game. The problem is to set everything fresh

        System.exit(1);
    }
}
