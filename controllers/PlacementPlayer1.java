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
import java.util.ResourceBundle;

public class PlacementPlayer1 implements Initializable {

    @FXML
    Button nextPlacement;
    @FXML
    Label name1;
    @FXML
    GridPane gridPane1;
    @FXML
    GridPane gridPane2;
    @FXML
    CheckBox orientation;
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

    @FXML
    private void toPlayer2(ActionEvent event) throws IOException {
        if (Players.SHIPS_AVAILABLE_PLAYER1 == 0 || setRandomly) {
            Players.gameBoardPlayer1 = gridPane1;   // Save grid pane for later use in GamePlayer1
            Utilities.prepareBoards(Players.gameBoardPlayer1);  // Remove children and add transparent tiles
            Utilities.changeScene(event, "../../stylefiles/placement2.fxml");
        } else {
            Utilities.raiseAlert("You didn't put all ships to board!");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Utilities.addStyleSheets(orientation);  // Change style of the CheckBox from the CSS file
        Utilities.prepareBoards(gridPane1);     // Remove any children
        name1.setText(Players.getNamePlayer1());

        // TODO: I'm not sure if it can be done in another way but it doesn't feel right to call events on each ship

        ships = new ImageView[4];
        for (int i = 1; i < 5; i++) {
            ships[i - 1] = new ImageView(getClass().getResource("../images/1x1.png".replace(
                    "1x", i + "x")).toExternalForm());
        }
        for (int i = 0; i < 4; i++){
            gridPane2.add(ships[i], 0, i);
        }

        // For each ship type we are adding Drag event

        ships[0].setOnDragDetected(event -> {
            shipDims = 0;   // For later use
            Dragboard db = ships[0].startDragAndDrop(TransferMode.MOVE);
            ClipboardContent cbContent = new ClipboardContent();
            cbContent.putImage(ships[0].getImage());    // Add image to the clipboard so player see when drag begins
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
                cbContent.putImage(new  // Change clipboard image if the orientation is vertical
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

        gridPane1.setOnDragOver(event -> {  // Make possible to drop ships to the grid pane
            if (event.getGestureSource() != gridPane1 && event.getDragboard().hasImage()) {
                event.acceptTransferModes(TransferMode.MOVE);
            }
            event.consume();
        });

        gridPane1.setOnDragDropped(event -> { // Here we define what happens after the drop event.
            Node node = event.getPickResult().getIntersectedNode();
            boolean conditionsForPlacement; // Will be used for placing/not placing ships

            if (node != gridPane1) {
                if (setRandomly) {  // In case ships have been placed randomly and player decides to put them manually
                    setRandomly = false;
                    Utilities.prepareBoards(gridPane1);
                    Utilities.restoreAfterRandomPlacement(1, ship1x1, ship2x1, ship3x1, ship4x1);
                }
                Integer cIndex = GridPane.getColumnIndex(node); // When dropped, get the coordinates of the cursor
                Integer rIndex = GridPane.getRowIndex(node);
                int x = cIndex == null ? 0 : cIndex;
                int y = rIndex == null ? 0 : rIndex;

                Pane pane = new Pane(); // This pane will be used to mark ship location
                pane.setStyle("-fx-background-color: #f44336; -fx-border-color: #827670");

                if (shipDims == 0) {
                    // See if it can be places. Otherwise, do nothing
                    conditionsForPlacement = Utilities.canPlace(Players.initialBoardPlayer1, shipDims, x, y, vertical);
                    if (conditionsForPlacement) {
                        if (Players.initialBoardPlayer1[y][x] != 1 && Players.initialBoardPlayer1[y][x] != 2) {
                            Players.SHIP1X1PLAYER1--;   // Ships are not unlimited
                            ship1x1.setText(String.valueOf(Players.SHIP1X1PLAYER1));    // So player know how much left
                            if (Players.SHIP1X1PLAYER1 == 0) {
                                // When ships are not available, we don't display them
                                ships[shipDims].setVisible(false);
                            }
                            // Add unavailable cells around ship. Display it.
                            Utilities.addUnavailableCells(Players.initialBoardPlayer1, y, x, shipDims, vertical);
                            Utilities.addShipsToGrid(gridPane1, vertical, shipDims, pane, x, y);
                            Players.SHIPS_AVAILABLE_PLAYER1--;  // Will be used as condition when changing this scene
                        }
                    }
                } else if (shipDims == 1) {
                    conditionsForPlacement = Utilities.canPlace(Players.initialBoardPlayer1, shipDims, x, y, vertical);
                    if (conditionsForPlacement) {
                        Players.SHIP2X1PLAYER1--;
                        ship2x1.setText(String.valueOf(Players.SHIP2X1PLAYER1));
                        if (Players.SHIP2X1PLAYER1 == 0) {
                            ships[shipDims].setVisible(false);
                        }
                        Utilities.addUnavailableCells(Players.initialBoardPlayer1, y, x, shipDims, vertical);
                        Utilities.addShipsToGrid(gridPane1, vertical, shipDims, pane, x, y);
                        Players.SHIPS_AVAILABLE_PLAYER1--;
                    }
                } else if (shipDims == 2) {
                    conditionsForPlacement = Utilities.canPlace(Players.initialBoardPlayer1, shipDims, x, y, vertical);
                    if (conditionsForPlacement) {
                        Players.SHIP3X1PLAYER1--;
                        ship3x1.setText(String.valueOf(Players.SHIP3X1PLAYER1));
                        if (Players.SHIP3X1PLAYER1 == 0) {
                            ships[shipDims].setVisible(false);
                        }
                        Utilities.addUnavailableCells(Players.initialBoardPlayer1, y, x, shipDims, vertical);
                        Utilities.addShipsToGrid(gridPane1, vertical, shipDims, pane, x, y);
                        Players.SHIPS_AVAILABLE_PLAYER1--;
                    }
                } else {
                    conditionsForPlacement = Utilities.canPlace(Players.initialBoardPlayer1, shipDims, x, y, vertical);
                    if (conditionsForPlacement) {
                        Players.SHIP4X1PLAYER1--;
                        ship4x1.setText(String.valueOf(Players.SHIP4X1PLAYER1));
                        if (Players.SHIP4X1PLAYER1 == 0) {
                            ships[shipDims].setVisible(false);
                        }
                        Utilities.addUnavailableCells(Players.initialBoardPlayer1, y, x, shipDims, vertical);
                        Utilities.addShipsToGrid(gridPane1, vertical, shipDims, pane, x, y);
                        Players.SHIPS_AVAILABLE_PLAYER1--;
                    }
                }
            }
            event.consume();
        });
    }

    @FXML
    public void SetRandomly1() {
        Utilities.fillRandomly(gridPane1, 1);
        Players.SHIPS_AVAILABLE_PLAYER1 = 0;    // So player can go to the next scene
        setRandomly = true;
    }

    @FXML
    public void setVertical() {
        this.vertical = orientation.isSelected();
    }
}