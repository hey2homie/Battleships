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

    @FXML
    private void toPlayer2(ActionEvent event) throws IOException {
        Utilities.prepareBoards(gridPane1, 1);
        Utilities.changeScene(event, "../../stylefiles/placement2.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        orientation.getStylesheets().add(Utilities.class.getResource(
                "../../stylefiles/checkBox.css").toExternalForm());

        for(int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                Pane pane = new Pane();
                pane.setStyle("-fx-background-color: null;");
                gridPane1.add(pane, i, j);
            }
        }

        name1.setText(Players.getNamePlayer1());

        // TODO: I'm not sure if it can be done in another way but it doesn't feel right to call events on each ship

        ships = new ImageView[4];
        ships[0] = new ImageView(getClass().getResource("../images/1x1.png").toExternalForm());
        ships[1] = new ImageView(getClass().getResource("../images/2x1.png").toExternalForm());
        ships[2] = new ImageView(getClass().getResource("../images/3x1.png").toExternalForm());
        ships[3] = new ImageView(getClass().getResource("../images/4x1.png").toExternalForm());

        gridPane2.add(ships[0], 0, 0);
        gridPane2.add(ships[1], 0, 1);
        gridPane2.add(ships[2], 0, 2);
        gridPane2.add(ships[3], 0, 3);

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
            if (node != gridPane1) {
                Integer cIndex = GridPane.getColumnIndex(node);
                Integer rIndex = GridPane.getRowIndex(node);
                int x = cIndex == null ? 0 : cIndex;
                int y = rIndex == null ? 0 : rIndex;

                Pane pane = new Pane();
                pane.setStyle("-fx-background-color: red; -fx-border-color: #827670");

                if (!vertical) {
                    gridPane1.add(pane, x, y, 1 + shipDims, 1);
                } else {
                    gridPane1.add(pane, x, y, 1, 1 + shipDims);
                }

                if (shipDims == 0) {
                    Players.SHIP1X1PLAYER1--;
                    ship1x1.setText(String.valueOf(Players.SHIP1X1PLAYER1));
                    if (Players.SHIP1X1PLAYER1 == 0) {
                        ships[shipDims].setVisible(false);
                    }
                } else if (shipDims == 1) {
                    Players.SHIP2X1PLAYER1--;
                    ship2x1.setText(String.valueOf(Players.SHIP2X1PLAYER1));
                    if (Players.SHIP2X1PLAYER1 == 0) {
                        ships[shipDims].setVisible(false);
                    }
                } else if (shipDims == 2) {
                    Players.SHIP3X1PLAYER1--;
                    ship3x1.setText(String.valueOf(Players.SHIP3X1PLAYER1));
                    if (Players.SHIP3X1PLAYER1 == 0) {
                        ships[shipDims].setVisible(false);
                    }
                } else {
                    Players.SHIP4X1PLAYER1--;
                    ship4x1.setText(String.valueOf(Players.SHIP4X1PLAYER1));
                    if (Players.SHIP4X1PLAYER1 == 0) {
                        ships[shipDims].setVisible(false);
                    }
                }
            }
            event.consume();
        });
    }

    public void SetRandomly1() {
        Utilities.fillRandomly(gridPane1, 1);
    }

    public void setVertical() {
        this.vertical = orientation.isSelected();
    }
}