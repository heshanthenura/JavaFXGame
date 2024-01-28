package com.heshanthenura.platformergame.Components;

import com.heshanthenura.platformergame.MainApplication;
import javafx.animation.AnimationTimer;
import javafx.animation.TranslateTransition;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.util.Duration;

import java.util.List;
import java.util.logging.Logger;

public class Player {
    public Logger logger = Logger.getLogger("info");
    Box player = new Box(50, 50, 50);
    int playerTrack = 2;
    AnchorPane root;
    Group group;
    TranslateTransition translateTransition = new TranslateTransition(Duration.millis(200), player);
    List<Enemy> enemies;
    List<Bullet> bullets;

    AnimationTimer moveAnimation;

    final static Image texture = new Image(MainApplication.class.getResourceAsStream("assets/player.png"));

    public Player(AnchorPane root, Group group, List<Enemy> enemies) {
        this.root = root;
        this.group = group;
        this.enemies = enemies;

        PhongMaterial material = new PhongMaterial();
        material.setDiffuseMap(texture);
        material.setSelfIlluminationMap(texture);

        player.setMaterial(material);
        player.setTranslateY(-150);
        player.setTranslateZ(-900);
        changeTrack();
    }

    private void animateTrackChange(double targetX) {
        // Stop the existing transition if it is still running
        translateTransition.stop();

        // Set the new target X position
        translateTransition.setToX(targetX);

        // Play the new transition
        translateTransition.play();
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
            } else if (e.getCode().getName().equalsIgnoreCase("space")) {
                Bullet bullet = new Bullet(player.getTranslateX(), enemies, group);
                group.getChildren().add(bullet.getBullet());
            }
        });
    }

    public Box getPlayer() {
        return player;
    }

}