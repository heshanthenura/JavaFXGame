package com.heshanthenura.platformergame.Components;

import javafx.animation.AnimationTimer;
import javafx.geometry.Bounds;
import javafx.scene.Group;
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

    public Bullet(double playerX, List<Enemy> enemies, Group group) {
        this.enemies = enemies;
        this.group = group;
        this.playerX = playerX;
        bullet = new Sphere(10);
        bullet.setMaterial(new PhongMaterial(Color.YELLOW));
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
            }
        };
        moveAnimation.start();
    }


    void hitEnemy(){
        for (Enemy e:enemies){
            Bounds enemyBounds= e.getEnemy().getBoundsInParent();
            Bounds bulletBounds= bullet.getBoundsInParent();
            if (enemyBounds.intersects(bulletBounds)){
               e.killEnemy();
               moveAnimation.stop();
               group.getChildren().remove(bullet);
               break;
            }
        }

    }

}