package controllers.classes;

import controllers.SettingsPage;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Random;

public class Utilities {

    public static TextArea textPlayer1 = new TextArea();
    public static TextArea textPlayer2 = new TextArea();
    public static boolean clickAllowance = true;
    public static boolean turn2 = false;

    public static int mishitsPlayer1 = 0;
    public static int mishitsPlayer2 = 0;
    public static int timePlayer1 = 0;
    public static int timePlayer2 = 0;
    public static String winner;
    public static String usedSettings;

    public static void setClickAllowance(boolean clickAllowance) {
        Utilities.clickAllowance = clickAllowance;
    }

    public static void changeScene(Event event, String scene) throws IOException {
        Stage stageTheEventSourceNodeBelongs = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene nextScene = new Scene(FXMLLoader.load(Utilities.class.getResource(scene)));
        stageTheEventSourceNodeBelongs.setScene(nextScene);
    }

    public static void raiseAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setGraphic(null);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);

        /*
        Since alert is the subclass of dialogs, we are changing the style from the parent class
         */

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(Utilities.class.getResource("../../stylefiles/alert.css").toExternalForm());
        dialogPane.getStyleClass().add("myDialog");

        alert.showAndWait();
    }

    public static void fillRandomly(GridPane gridpane, int number) {
        int[][] board = randomPlacement();
        gridpane.getChildren().retainAll(gridpane.getChildren().get(0)); // Remove nodes when replacing random placement

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                Pane pane = new Pane();
                String color = board[i][j] == 1 ? "-fx-background-color: #f44336;" : "-fx-background-color: null;";
                pane.setStyle(color);
                gridpane.add(pane, j, i);
            }
        }

        if (number == 1) {
            Players.initialBoardPlayer1 = board;
            Players.gameBoardPlayer1 = gridpane;
        } else {
            Players.initialBoardPlayer2 = board;
            Players.gameBoardPlayer2 = gridpane;
        }
    }

    public static void prepareBoards(GridPane gridpane, int number) {

        /*
        In this method we are adding colorless panes to the grid to make it clickable
         */

        gridpane.getChildren().retainAll(gridpane.getChildren().get(0));
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                Pane pane = new Pane();
                pane.setStyle("-fx-background-color: null;");
                if (number == 1) {
                    Players.gameBoardPlayer1.add(pane, j, i);
                } else if (number == 2) {
                    Players.gameBoardPlayer2.add(pane, j, i);
                } else {
                    gridpane.add(pane, j, i);
                }
            }
        }
    }

    public static void newMove(Pane pane, int number, Label timerLabel, Timing timer) {
        if (SettingsPage.mills == 30000) {
            timerLabel.setText("30");
        } else {
            timerLabel.setText("60");
        }

        timer.setTimer(timerLabel);
        timer.runTimer();

        if (number == 1) {
            pane.getChildren().add(Players.gameBoardPlayer2);
        } else {
            pane.getChildren().add(Players.gameBoardPlayer1);
        }
    }

    public static void nextMove(int number, GridPane gridPane, Timing timer) {
        timer.stop();
        setClickAllowance(true);

        if (number == 1) {
            turn2 = true;
            Players.gameBoardPlayer2 = gridPane;
        } else {
            turn2 = false;
            Players.gameBoardPlayer1 = gridPane;
        }
    }

    public static void clickEvent(GridPane gridPane, Node clickedNode, int number, TextArea textArea) {
        Integer colIndex = GridPane.getColumnIndex(clickedNode);
        Integer rowIndex = GridPane.getRowIndex(clickedNode);

        int[][] board;
        String previousText1 = null;
        String previousText2 = null;

        if (number == 1) {
            board = Players.initialBoardPlayer2;
            previousText2 = textPlayer2.getText();
        } else {
            board = Players.initialBoardPlayer1;
            previousText1 = textPlayer1.getText();
        }

        Pane pane = new Pane(); // This pane with appropriate color will be added when user clicks on the tile

        if (clickAllowance) { // Do nothing if previous click was a fail

            String color;   // Color of the pane. Not initialised since Strings are immutable. Can use StringBuilder...

            if (board[rowIndex][colIndex] == 1) {
                color = "-fx-background-color: red; -fx-border-color: #827670";
                if (number == 1) {
                    Players.takeDamagePlayer2();
                    Players.initialBoardPlayer2[rowIndex][colIndex] = 3;    // To avoid hitting at the same time
                    addMoves(textArea, textPlayer2, previousText2 + "Hit ", rowIndex, colIndex);
                } else {
                    Players.takeDamagePlayer1();
                    Players.initialBoardPlayer1[rowIndex][colIndex] = 3;
                    addMoves(textArea, textPlayer1, previousText1 + "Hit ", rowIndex, colIndex);
                }
            } else if (board[rowIndex][colIndex] != 3) {
                if (Players.HEALTH_PLAYER2 != 0 && Players.HEALTH_PLAYER1 != 0) {
                    color = "-fx-background-color: blue; -fx-border-color: #827670";
                    setClickAllowance(false);   // Preventing future clicks after failed attempt to hit battleship
                    if (number == 1) {
                        mishitsPlayer1 += 1;
                        Players.initialBoardPlayer2[rowIndex][colIndex] = 3;
                        addMoves(textArea, textPlayer2, previousText2 + "Miss ", rowIndex, colIndex);
                    } else {
                        mishitsPlayer2 += 1;
                        Players.initialBoardPlayer1[rowIndex][colIndex] = 3;
                        addMoves(textArea, textPlayer1, previousText1 + "Miss ", rowIndex, colIndex);
                    }
                } else {
                    color = "";
                }
            } else {
                color = "";
            }
            pane.setStyle(color);
            gridPane.add(pane, colIndex, rowIndex); // adding pane to the board where it was clicked
        }
    }

    public static int[][] randomPlacement() {
        Random random = new Random();
        int[][] board = new int[10][10];

        for (int i = 0; i < 10; i++) {  // each i represent a ship

            boolean isHorizontal = random.nextInt(2) == 0;  // set ship orientation
            boolean battleShipFilled = false;   // exist condition

            while (!battleShipFilled) {

                int row = random.nextInt(9);    // choosing random coordinates
                int column = random.nextInt(9);

                while (board[row][column] == 1) {
                    row = random.nextInt(9);    // change location if it's occupied
                    column = random.nextInt(9);
                }

                int lengthOfBattleship;

                /*
                Placing ships starting from the biggest to avoid difficulties with placing big ships when board is
                filled
                 */

                if (i < 1) {
                    lengthOfBattleship = 4;
                } else if (i < 3) {
                    lengthOfBattleship = 3;
                } else if (i < 6) {
                    lengthOfBattleship = 2;
                } else {
                    lengthOfBattleship = 1;
                }

                int numberOfCorrectLocation = 0;

                for (int k = 0; k < lengthOfBattleship; k++) {
                    // Terminate loop if condition met
                    if (isHorizontal && row + k > 0 && row + k < 10){
                        if (board[row + k][column] == 1 || board[row + k][column] == 2) {
                            break;
                        }
                    } else if (!isHorizontal && column + k > 0 && column + k < 10){
                        if (board[row][column + k] == 1 || board[row][column + k] == 2) {
                            break;
                        }
                    } else {
                        break;
                    }

                    numberOfCorrectLocation++;
                }

                if (numberOfCorrectLocation == lengthOfBattleship) {
                    // Place ship if there are available cells
                    for (int k = 0; k < lengthOfBattleship; k++) {

                        int l;
                        int o;

                        if (isHorizontal){
                            o = row + k;
                            l = column;
                        } else {
                            o = row;
                            l = column + k;
                        }

                        /*
                        This blocks are to avoid Index out of Range when placing cells which are occupied with ship
                        boarder
                        // TODO: Simplify it like in the addUnavailableCells method

                         */

                        try {
                            if (board[o][l - 1] != 1) {
                                board[o][l - 1] = 2;
                            }
                        } catch (Exception ignored) {
                        }
                        try {
                            if (board[o - 1][l] != 1) {
                                board[o - 1][l] = 2;
                            }
                        } catch (Exception ignored) {
                        }
                        try {
                            if (board[o + 1][l] != 1) {
                                board[o + 1][l] = 2;
                            }
                        } catch (Exception ignored) {
                        }
                        try {
                            board[o - 1][l - 1] = 2;
                        } catch (Exception ignored) {
                        }
                        try {
                            board[o + 1][l - 1] = 2;
                        } catch (Exception ignored) {
                        }
                        try {
                            board[o + 1][l + 1] = 2;
                        } catch (Exception ignored) {
                        }
                        try {
                            board[o][l + 1] = 2;
                        } catch (Exception ignored) {
                        }
                        try {
                            board[o - 1][l + 1] = 2;
                        } catch (Exception ignored) {
                        }

                        if (isHorizontal) {
                            board[row + k][column] = 1;
                        } else {
                            board[row][column + k] = 1;
                        }
                    }

                    battleShipFilled = true;
                }
            }
        }
        return board;
    }

    public static void addMoves(TextArea textArea1, TextArea textArea2, String previousText, int i, int j) {

        /*
        Add history of moves to the TextArea in game
         */

        int row = i + 1;    // Not to show zero indexes
        int col = j + 1;
        textArea1.setText(previousText + row + ":" + col + "\n");
        textArea2.setText(previousText + row + ":" + col + "\n");
    }

    public static void addUnavailableCells(int[][] board, int row, int column, int length, boolean vertical) {

        /*
        In this method we resolving the issue of ships collision and don't allow them to occupy ships surroundings.
        I know that it's not the best way to approaches this but I can't find another one
         */

        if (length == 0) {
            for (int i = -1; i < 2; i++) {
                for (int j = -1; j < 2; j++) {
                    try {
                        board[row + i][column + j] = 2;
                    } catch (Exception ignored) {
                    }
                }
            }
            board[row][column] = 1;
        }
        if (!vertical) {
            if (length == 1) {
                for (int i = -1; i < 2; i++) {
                    for (int j = -1; j < 3; j++) {
                        try {
                            board[row + i][column + j] = 2;
                        } catch (Exception ignored) {
                        }
                    }
                }
                board[row][column] = 1;
                board[row][column + 1] = 1;
            } else if (length == 2) {
                for (int i = -1; i < 2; i++) {
                    for (int j = -1; j < 4; j++) {
                        try {
                            board[row + i][column + j] = 2;
                        } catch (Exception ignored) {
                        }
                    }
                }
                board[row][column] = 1;
                board[row][column + 1] = 1;
                board[row][column + 2] = 1;
            } else if (length == 3) {
                for (int i = -1; i < 2; i++) {
                    for (int j = -1; j < 5; j++) {
                        try {
                            board[row + i][column + j] = 2;
                        } catch (Exception ignored) {
                        }
                    }
                }
                board[row][column] = 1;
                board[row][column + 1] = 1;
                board[row][column + 2] = 1;
                board[row][column + 3] = 1;
            }
        } else {
            if (length == 1) {
                for (int i = -1; i < 3; i++) {
                    for (int j = -1; j < 2; j++) {
                        try {
                            board[row + i][column + j] = 2;
                        } catch (Exception ignored) {
                        }
                    }
                }
                board[row][column] = 1;
                board[row + 1][column] = 1;
            } else if (length == 2) {
                for (int i = -1; i < 4; i++) {
                    for (int j = -1; j < 2; j++) {
                        try {
                            board[row + i][column + j] = 2;
                        } catch (Exception ignored) {
                        }
                    }
                }
                board[row][column] = 1;
                board[row + 1][column] = 1;
                board[row + 2][column] = 1;
            } else if (length == 3){
                for (int i = -1; i < 5; i++) {
                    for (int j = -1; j < 2; j++) {
                        try {
                            board[row + i][column + j] = 2;
                        } catch (Exception ignored) {
                        }
                    }
                }
                board[row][column] = 1;
                board[row + 1][column] = 1;
                board[row + 2][column] = 1;
                board[row + 3][column] = 1;
            }
        }
    }

    public static void addShipsToGrid(GridPane gridPane, boolean vertical, int length, Pane pane, int column, int row) {

        /*
        Add colored panes to the grid in the placement stage depending on the ship orientation
         */

        if (!vertical) {
            gridPane.add(pane, column, row, 1 + length, 1);
        } else {
            gridPane.add(pane, column, row, 1, 1 + length);
        }
    }

    public static boolean canPlace(int[][] board, int length, int column, int row, boolean vertical) {

        /*
        In this method we are looking whether the ship can be added to this location or not to avoid possible collisions
        and occupation of other ships surroundings. If we can place ship to that location, then we return true, and vice
        versa.
        Also seems that I found not the most optimal solution for this part
         */

        boolean condition = false;
        if (length == 0) {
            if (board[row][column] != 1 && board[row][column] != 2) {
                condition = true;
            }
        }
        try {
            if (!vertical) {
                if (length == 1) {
                    if (board[row][column] != 1 && board[row][column] != 2 && board[row][column + 1] != 1 &&
                            board[row][column + 1] != 2) {
                        condition = true;
                    }
                } else if (length == 2) {
                    if (board[row][column] != 1 && board[row][column] != 2 && board[row][column + 1] != 1 &&
                            board[row][column + 1] != 2 && board[row][column + 2] != 1 && board[row][column + 2] != 2) {
                        condition = true;
                    }
                } else if (length == 3) {
                    if (board[row][column] != 1 && board[row][column] != 2 && board[row][column + 1] != 1 &&
                            board[row][column + 1] != 2 && board[row][column + 2] != 1 && board[row][column + 2] != 2 &&
                            board[row][column + 3] != 1 && board[row][column + 3] != 3) {
                        condition = true;
                    }
                }
            } else {
                if (length == 1) {
                    if (board[row][column] != 1 && board[row][column] != 2 && board[row + 1][column] != 1 &&
                            board[row + 1][column] != 2 && board[row + 1][column] <= 9) {
                        condition = true;
                    }
                } else if (length == 2) {
                    if (board[row][column] != 1 && board[row][column] != 2 && board[row + 1][column] != 1 &&
                            board[row + 1][column] != 2 && board[row + 2][column] != 1 && board[row + 2][column] != 2 &&
                            board[row + 2][column] <= 9) {
                        condition = true;
                    }
                } else if (length == 3) {
                    if (board[row][column] != 1 && board[row][column] != 2 && board[row + 1][column] != 1 &&
                            board[row + 1][column] != 2 && board[row + 2][column] != 1 && board[row + 2][column] != 2 &&
                            board[row + 3][column] != 1 && board[row + 3][column] != 2) {
                        condition = true;
                    }
                }
            }
        } catch (Exception ignored) { }
        return condition;
    }

    public static void restoreAfterRandomPlacement(int number, Label label1, Label label2, Label label3, Label label4) {
        if (number == 1) {
            Players.initialBoardPlayer1 = new int[10][10];
            Players.SHIPS_AVAILABLE_PLAYER1 = 10;
            Players.SHIP1X1PLAYER1 = 4;
            Players.SHIP2X1PLAYER1 = 3;
            Players.SHIP3X1PLAYER1 = 2;
            Players.SHIP4X1PLAYER1 = 1;
        } else {
            Players.initialBoardPlayer2 = new int[10][10];
            Players.SHIPS_AVAILABLE_PLAYER2 = 10;
            Players.SHIP1X1PLAYER2 = 4;
            Players.SHIP2X1PLAYER2 = 3;
            Players.SHIP3X1PLAYER2 = 2;
            Players.SHIP4X1PLAYER2 = 1;
        }
        label1.setText("4");
        label2.setText("3");
        label3.setText("2");
        label4.setText("1");
    }
}
