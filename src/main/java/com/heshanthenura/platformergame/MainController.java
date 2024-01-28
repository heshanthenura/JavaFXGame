package com.heshanthenura.platformergame;

import com.heshanthenura.platformergame.Components.Bullet;
import com.heshanthenura.platformergame.Components.Enemy;
import com.heshanthenura.platformergame.Components.GameStates;
import com.heshanthenura.platformergame.Components.Player;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.PointLight;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;


import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class MainController implements Initializable {

    public Logger logger = Logger.getLogger("info");

    @FXML
    private AnchorPane root;
    @FXML
    private VBox deadLbl;
    @FXML
    public Text healthLbl;

    Group mainGroup = new Group();
    Group floorGroup = new Group();
    Player player ;
    List<Bullet> bullets = new ArrayList<Bullet>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() -> {
            player=new Player(root,mainGroup, GameStates.getEnemies());
            init();
            spawnEnemiesPeriodically();
        });
    }

    public void spawnEnemiesPeriodically() {
        Timeline timeline = new Timeline();
        KeyFrame keyFrame = new KeyFrame(
                Duration.seconds(2), // Adjust the duration between enemy spawns
                event -> spawnSingleEnemy()
        );
        timeline.getKeyFrames().add(keyFrame);
        timeline.setCycleCount(Timeline.INDEFINITE); // Run the timeline indefinitely
        timeline.play();
    }

    private void spawnSingleEnemy() {
        Enemy enemy = new Enemy(root, player, deadLbl, GameStates.getEnemies(), healthLbl, mainGroup);

        double[] possibleXValues = {-300, 0, 300};
        double xVal = possibleXValues[(int) (Math.random() * possibleXValues.length)];

        double zVal = Math.random() * 1000 + 2000; // Random z in the range [2000, 3000]

        enemy.getEnemy().setTranslateX(xVal);
        enemy.getEnemy().setTranslateZ(zVal);

        mainGroup.getChildren().add(enemy.getEnemy());
    }

    public void init() {
        root.requestFocus();
        root.getScene().setCamera(new PerspectiveCamera());
        PointLight light = new PointLight();
//        light.setLightOn(false);
        root.getChildren().addAll(mainGroup,light);

        mainGroup.getChildren().add(player.getPlayer());
        initFloor();

        mainGroup.getChildren().add(floorGroup);
        mainGroup.setTranslateX(root.getWidth() / 2);
        mainGroup.setTranslateY((root.getHeight() / 2) + 300);
        mainGroup.setRotationAxis(Rotate.X_AXIS);
        mainGroup.setRotate(5);
    }

    public void initFloor() {
        Box floor = new Box(1000, 100, 1000000);
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

