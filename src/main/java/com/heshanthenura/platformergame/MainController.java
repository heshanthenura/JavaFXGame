package com.heshanthenura.platformergame;

import javafx.animation.AnimationTimer;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.AmbientLight;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.PointLight;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class MainController implements Initializable {

    @FXML
    private AnchorPane root;

    Group mainGroup = new Group();
    Group floorGroup = new Group();
    Player player;
    public Logger logger = Logger.getLogger("info");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() -> {
            init();
        });
    }

    public void init() {
        root.requestFocus();

        root.getScene().setCamera(new PerspectiveCamera());

        player = new Player(root);
        player.addPlayer(mainGroup);
        initFloor();

        root.getChildren().addAll(mainGroup, new PointLight());
        mainGroup.getChildren().add(floorGroup);
        mainGroup.setTranslateX(root.getWidth() / 2);
        mainGroup.setTranslateY((root.getHeight() / 2) + 300);

        mainGroup.setRotationAxis(Rotate.X_AXIS);
        mainGroup.setRotate(5);
    }

    public void initFloor() {
        Box floor = new Box(1000, 100, 1000000);
        floor.setMaterial(new PhongMaterial(Color.AQUA));

        Box leftWall = new Box(100, 50, 1000000);
        leftWall.setMaterial(new PhongMaterial(Color.RED));
        leftWall.setTranslateY(-50 - 25);
        leftWall.setTranslateX(-500);

        Box rightWall = new Box(100, 50, 1000000);
        rightWall.setMaterial(new PhongMaterial(Color.RED));
        rightWall.setTranslateY(-50 - 25);
        rightWall.setTranslateX(500);


        floorGroup.getChildren().addAll(floor, leftWall, rightWall);


    }


}

class Player {
    public Logger logger = Logger.getLogger("info");
    Box player = new Box(50, 50, 50);
    int playerTrack = 2;
    AnchorPane root;
    TranslateTransition translateTransition = new TranslateTransition(Duration.millis(200), player);

    public Player(AnchorPane root) {
        this.root = root;

        player.setMaterial(new PhongMaterial(Color.GREEN));
        player.setTranslateY(-150);
        player.setTranslateZ(-900);
        changeTrack();
    }

    private void animateTrackChange(double targetX) {
        translateTransition.setToX(targetX);
        translateTransition.play();
    }

    void addPlayer(Group group) {
        group.getChildren().add(player);
    }

    void changeTrack() {
        root.setOnKeyPressed(e -> {
            if (e.getCode().getName().equals("D")) {
                logger.info("Move to Right Track");
                if (playerTrack != 3) {
                    playerTrack += 1;
                    animateTrackChange(player.getTranslateX() + 300); // Move to the next track
                    logger.info("Track: " + playerTrack);
                } else {
                    logger.info("Already on the Rightmost Track");
                }
            } else if (e.getCode().getName().equals("A")) {
                logger.info("Move to Left Track");
                if (playerTrack != 1) {
                    playerTrack -= 1;
                    animateTrackChange(player.getTranslateX() - 300); // Move to the previous track
                    logger.info("Track: " + playerTrack);
                } else {
                    logger.info("Already on the Leftmost Track");
                }
            }
        });
    }



    public int getPlayerTrack() {
        return playerTrack;
    }

    public void setPlayerTrack(int playerTrack) {
        this.playerTrack = playerTrack;
    }
}