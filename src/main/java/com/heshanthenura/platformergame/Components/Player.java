package com.heshanthenura.platformergame.Components;

import javafx.animation.AnimationTimer;
import javafx.animation.TranslateTransition;
import javafx.scene.Group;
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

    public Player(AnchorPane root, Group group, List<Enemy> enemies) {
        this.root = root;
        this.group = group;
        this.enemies = enemies;
        player.setMaterial(new PhongMaterial(Color.GREEN));
        player.setTranslateY(-150);
        player.setTranslateZ(-900);
        changeTrack();
    }

    private void animateTrackChange(double targetX) {
        translateTransition.setToX(targetX);
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