package controllers.classes;

import javafx.scene.layout.GridPane;

public class Players {

    // TODO: This can be turned into enum within the Utilities class

    public static String namePlayer1;
    public static String namePlayer2;

    public static int HEALTH_PLAYER1 = 20;
    public static int HEALTH_PLAYER2 = 20;
    public static int SHIPS_AVAILABLE_PLAYER1 = 10;
    public static int SHIPS_AVAILABLE_PLAYER2 = 10;

    public static int SHIP1X1PLAYER1 = 4;
    public static int SHIP1X1PLAYER2 = 4;
    public static int SHIP2X1PLAYER1 = 3;
    public static int SHIP2X1PLAYER2 = 3;
    public static int SHIP3X1PLAYER1 = 2;
    public static int SHIP3X1PLAYER2 = 2;
    public static int SHIP4X1PLAYER1 = 1;
    public static int SHIP4X1PLAYER2 = 1;

    public static int[][] initialBoardPlayer1 = new int[10][10];
    public static int[][] initialBoardPlayer2 = new int[10][10];

    public static GridPane gameBoardPlayer1;
    public static GridPane gameBoardPlayer2;

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
