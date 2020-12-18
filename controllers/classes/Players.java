package controllers.classes;

import controllers.SettingsPage;
import javafx.scene.layout.GridPane;

public class Players {

    public static String namePlayer1 = null;
    public static String namePlayer2 = null;

    public static int HEALTH_PLAYER1 = 20;
    public static int HEALTH_PLAYER2 = 20;

    public static int[][] initialBoardPlayer1;
    public static int[][] initialBoardPlayer2;

    public static GridPane gameBoardPlayer1;
    public static GridPane gameBoardPlayer2;

    public Players(String namePlayer1, String namePlayer2) {
        Players.namePlayer1 = namePlayer1;
        Players.namePlayer2 = namePlayer2;
    }

    public static void takeDamagePlayer1() {
        Players.HEALTH_PLAYER1 -= 1;
    }

    public static void takeDamagePlayer2() {
        Players.HEALTH_PLAYER2 -= 1;
    }

    public static String getNamePlayer1() {
        return namePlayer1;
    }

    public static String getNamePlayer2() {
        return namePlayer2;
    }
}
