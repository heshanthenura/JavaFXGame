package com.heshanthenura.platformergame.Components;

import java.util.ArrayList;
import java.util.List;

public class GameStates {
    public static List<Enemy> enemies = new ArrayList<Enemy>();

    public static List<Enemy> getEnemies() {
        return enemies;
    }

    public static void setEnemies(List<Enemy> enemies) {
        GameStates.enemies = enemies;
    }
}
