package com.heshanthenura.platformergame.Components;

import javafx.animation.AnimationTimer;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.text.Text;

import java.util.List;
import java.util.logging.Logger;

public class Enemy {
    public Logger logger = Logger.getLogger("info");
    Box enemy = new Box(50, 50, 50);
    boolean collided = false;
    AnchorPane root;
    Player player;
    VBox deadLbl;
    Group group;
    Text healthLbl;
    AnimationTimer moveAnimation;
    List<Enemy> enemies;

    public Enemy(AnchorPane root, Player player, VBox deadLbl, List<Enemy> enemies, Text healthLbl, Group group) {
        this.enemies=enemies;
        this.healthLbl = healthLbl;
        this.deadLbl = deadLbl;
        this.root = root;
        this.player = player;
        this.group=group;

        enemy.setMaterial(new PhongMaterial(Color.ROYALBLUE));
        enemy.setTranslateY(-150);
        enemies.add(this);
        logger.info(String.valueOf(enemies.size()));
        moveEnemy();
    }

    public Box getEnemy() {
        return enemy;
    }
    public void moveEnemy(){
        moveAnimation= new AnimationTimer() {
            @Override
            public void handle(long l) {
                enemy.setTranslateZ(enemy.getTranslateZ()-10);
                removeEnemy();
                if (collideWithPlayer()){
                    stop();
                }
            }
        };
        moveAnimation.start();
    }
    public boolean collideWithPlayer(){
        Bounds playerBounds = player.getPlayer().getBoundsInParent();
        Bounds enemyBounds = enemy.getBoundsInParent();
        return playerBounds.intersects(enemyBounds);
    }
    public void removeEnemy(){
        if (enemy.getTranslateZ()<-1000){
            moveAnimation.stop();
            logger.info(String.valueOf(enemy.getTranslateZ()));
            enemies.remove(this);
            group.getChildren().remove(enemy);
            logger.info(String.valueOf(enemies.size()));
        }
    }

    public void killEnemy(){
        moveAnimation.stop();
        logger.info("Enemy died");
        enemies.remove(this);
        group.getChildren().remove(enemy);
        logger.info(String.valueOf(enemies.size()));
    }

}