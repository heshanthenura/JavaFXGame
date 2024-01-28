package com.heshanthenura.platformergame.Components;

import com.heshanthenura.platformergame.MainApplication;
import javafx.animation.AnimationTimer;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;

import java.util.List;
import java.util.logging.Logger;

public class Bullet {
    Sphere bullet;
    double playerX;
    List<Enemy> enemies;
    public Logger logger = Logger.getLogger("info");
    Group group;
    AnimationTimer moveAnimation;

    final static Image lavaImg = new Image(MainApplication.class.getResourceAsStream("assets/lava.jpg"));

    public Bullet(double playerX, List<Enemy> enemies, Group group) {
        this.enemies = enemies;
        this.group = group;
        this.playerX = playerX;

        PhongMaterial bulletMaterial = new PhongMaterial();
        bulletMaterial.setSelfIlluminationMap(lavaImg);
        bulletMaterial.setDiffuseMap(lavaImg);

        bullet = new Sphere(10);
        bullet.setMaterial(bulletMaterial);
        bullet.setTranslateY(-150);
        bullet.setTranslateZ(-900);
        bullet.setTranslateX(playerX);
        moveBullet();
    }

    public Sphere getBullet() {
        return bullet;
    }

    void moveBullet() {
        moveAnimation = new AnimationTimer() {
            @Override
            public void handle(long l) {
                bullet.setTranslateZ(bullet.getTranslateZ() + 10);
                hitEnemy();
                if (bullet.getTranslateZ() > 10000) {
                    group.getChildren().remove(bullet);
                    logger.info("Bullet removed");
                    stop();
                }
            }
        };
        moveAnimation.start();
    }

    void hitEnemy() {
        for (Enemy e : enemies) {
            Bounds enemyBounds = e.getEnemy().getBoundsInParent();
            Bounds bulletBounds = bullet.getBoundsInParent();
            if (enemyBounds.intersects(bulletBounds)) {
                e.killEnemy();
                moveAnimation.stop();
                group.getChildren().remove(bullet);
                break;
            }
        }

    }

}