package controllers;

import controllers.classes.Players;
import controllers.classes.Utilities;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class PlacementPlayer2 implements Initializable {

    @FXML
    Button beginGame;
    @FXML
    Label name2;
    @FXML
    GridPane gridPane1;
    @FXML
    GridPane gridPane2;
    @FXML
    CheckBox orientation1;
    @FXML
    Label ship1x1;
    @FXML
    Label ship2x1;
    @FXML
    Label ship3x1;
    @FXML
    Label ship4x1;
    @FXML
    public static ImageView[] ships;

    int shipDims;
    boolean vertical = false;
    boolean setRandomly = false;

    /*
    Completely the same as PlacementPlayer1 controller except for the second player and another fxml file
     */

    @FXML
    private void beginGame(ActionEvent event) throws IOException {
        if (Players.SHIPS_AVAILABLE_PLAYER2 == 0 || setRandomly) {
            Players.gameBoardPlayer2 = gridPane1;
            Utilities.prepareBoards(Players.gameBoardPlayer2);
            Utilities.changeScene(event, "../../stylefiles/game1.fxml");
        } else {
            Utilities.raiseAlert("You didn't put all ships to board!");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Utilities.addStyleSheets(orientation1);

        Utilities.prepareBoards(gridPane1);

        name2.setText(Players.getNamePlayer2());

        // TODO: I'm not sure if it can be done in another way but it doesn't feel right to call events on each ship

        ships = new ImageView[4];
        for (int i = 1; i < 5; i++) {
            ships[i - 1] = new ImageView(getClass().getResource("../images/1x1.png".replace(
                    "1x", i + "x")).toExternalForm());
        }

        for (int i = 0; i < 4; i++){
            gridPane2.add(ships[i], 0, i);
        }

        ships[0].setOnDragDetected(event -> {
            shipDims = 0;
            Dragboard db = ships[0].startDragAndDrop(TransferMode.MOVE);
            ClipboardContent cbContent = new ClipboardContent();
            cbContent.putImage(ships[0].getImage());
            db.setContent(cbContent);
            event.consume();
        });

        ships[1].setOnDragDetected(event -> {
            shipDims = 1;
            Dragboard db = ships[1].startDragAndDrop(TransferMode.MOVE);
            ClipboardContent cbContent = new ClipboardContent();
            if (!vertical) {
                cbContent.putImage(ships[1].getImage());
            } else {
                cbContent.putImage(new
                        ImageView(getClass().getResource("../images/1x2.png").toExternalForm()).getImage());
            }
            db.setContent(cbContent);
            event.consume();
        });

        ships[2].setOnDragDetected(event -> {
            shipDims = 2;
            Dragboard db = ships[2].startDragAndDrop(TransferMode.MOVE);
            ClipboardContent cbContent = new ClipboardContent();
            if (!vertical) {
                cbContent.putImage(ships[2].getImage());
            } else {
                cbContent.putImage(new
                        ImageView(getClass().getResource("../images/1x3.png").toExternalForm()).getImage());
            }
            db.setContent(cbContent);
            event.consume();
        });

        ships[3].setOnDragDetected(event -> {
            shipDims = 3;
            Dragboard db = ships[3].startDragAndDrop(TransferMode.MOVE);
            ClipboardContent cbContent = new ClipboardContent();
            if (!vertical) {
                cbContent.putImage(ships[3].getImage());
            } else {
                cbContent.putImage(new
                        ImageView(getClass().getResource("../images/1x4.png").toExternalForm()).getImage());
            }
            db.setContent(cbContent);
            event.consume();
        });

        gridPane1.setOnDragOver(event -> {
            if (event.getGestureSource() != gridPane1 && event.getDragboard().hasImage()) {
                event.acceptTransferModes(TransferMode.MOVE);
            }
            event.consume();
        });

        gridPane1.setOnDragDropped(event -> {
            Node node = event.getPickResult().getIntersectedNode();
            boolean conditionsForPlacement;

            if (node != gridPane1) {
                if (setRandomly) {
                    setRandomly = false;
                    Utilities.prepareBoards(gridPane1);
                    Utilities.restoreAfterRandomPlacement(2, ship1x1, ship2x1, ship3x1, ship4x1);
                }

                Integer cIndex = GridPane.getColumnIndex(node);
                Integer rIndex = GridPane.getRowIndex(node);
                int x = cIndex == null ? 0 : cIndex;
                int y = rIndex == null ? 0 : rIndex;

                Pane pane = new Pane();
                pane.setStyle("-fx-background-color: #f44336; -fx-border-color: #827670");

                if (shipDims == 0) {
                    conditionsForPlacement = Utilities.canPlace(Players.initialBoardPlayer2, shipDims, x, y, vertical);
                    if (conditionsForPlacement) {
                        if (Players.initialBoardPlayer2[y][x] != 1 && Players.initialBoardPlayer2[y][x] != 2) {
                            Players.SHIP1X1PLAYER2--;
                            ship1x1.setText(String.valueOf(Players.SHIP1X1PLAYER2));
                            if (Players.SHIP1X1PLAYER2 == 0) {
                                ships[shipDims].setVisible(false);
                            }
                            Utilities.addUnavailableCells(Players.initialBoardPlayer2, y, x, shipDims, vertical);
                            Utilities.addShipsToGrid(gridPane1, vertical, shipDims, pane, x, y);
                            Players.SHIPS_AVAILABLE_PLAYER2--;
                        }
                    }
                } else if (shipDims == 1) {
                    conditionsForPlacement = Utilities.canPlace(Players.initialBoardPlayer2, shipDims, x, y, vertical);
                    if (conditionsForPlacement) {
                        Players.SHIP2X1PLAYER2--;
                        ship2x1.setText(String.valueOf(Players.SHIP2X1PLAYER2));
                        if (Players.SHIP2X1PLAYER2 == 0) {
                            ships[shipDims].setVisible(false);
                        }
                        Utilities.addUnavailableCells(Players.initialBoardPlayer2, y, x, shipDims, vertical);
                        Utilities.addShipsToGrid(gridPane1, vertical, shipDims, pane, x, y);
                        Players.SHIPS_AVAILABLE_PLAYER2--;
                    }
                } else if (shipDims == 2) {
                    conditionsForPlacement = Utilities.canPlace(Players.initialBoardPlayer2, shipDims, x, y, vertical);
                    if (conditionsForPlacement) {
                        Players.SHIP3X1PLAYER2--;
                        ship3x1.setText(String.valueOf(Players.SHIP3X1PLAYER2));
                        if (Players.SHIP3X1PLAYER2 == 0) {
                            ships[shipDims].setVisible(false);
                        }
                        Utilities.addUnavailableCells(Players.initialBoardPlayer2, y, x, shipDims, vertical);
                        Utilities.addShipsToGrid(gridPane1, vertical, shipDims, pane, x, y);
                        Players.SHIPS_AVAILABLE_PLAYER2--;
                    }
                } else {
                    conditionsForPlacement = Utilities.canPlace(Players.initialBoardPlayer2, shipDims, x, y, vertical);
                    if (conditionsForPlacement) {
                        Players.SHIP4X1PLAYER2--;
                        ship4x1.setText(String.valueOf(Players.SHIP4X1PLAYER2));
                        if (Players.SHIP4X1PLAYER2 == 0) {
                            ships[shipDims].setVisible(false);
                        }
                        Utilities.addUnavailableCells(Players.initialBoardPlayer2, y, x, shipDims, vertical);
                        Utilities.addShipsToGrid(gridPane1, vertical, shipDims, pane, x, y);
                        Players.SHIPS_AVAILABLE_PLAYER2--;
                    }
                }
            }
            event.consume();
        });
    }

    @FXML
    public void SetRandomly2() {
        Utilities.fillRandomly(gridPane1, 2);
        Players.SHIPS_AVAILABLE_PLAYER2 = 0;
        setRandomly = true;
        if (!setRandomly) {
            Utilities.restoreAfterRandomPlacement(2, ship1x1, ship2x1, ship3x1, ship4x1);
        }
    }

    @FXML
    public void setVertical() {
        this.vertical = orientation1.isSelected();
    }
}
