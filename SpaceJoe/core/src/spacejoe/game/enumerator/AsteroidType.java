package spacejoe.game.enumerator;

import java.util.Random;

/**
 * Rozne typy asteroidov a ich vlastnosti, generovanie random asteroid typu.
 */
public enum AsteroidType {
    SMALL(40, 40, 650, 1, 1),
    MEDIUM(60, 60, 475, 2, 2),
    LARGE(85, 85, 375, 3, 3);

    private final int width;
    private final int height;
    private final int speed;
    private final int hp;
    private final int reward;

    /**
     * @param width sirka asteroidu
     * @param height vyska asteroidu
     * @param speed rychlost asteroidu
     * @param hp odolnost asteroidu - kolko hitov vydrzi
     * @param reward odmena za znicenie asteroidu
     */
    AsteroidType(int width, int height, int speed, int hp, int reward) {
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.hp = hp;
        this.reward = reward;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getSpeed() {
        return speed;
    }

    public int getHp() {
        return hp;
    }

    public int getReward() {
        return reward;
    }

    /**
     * Generuje random asteroid typy.
     * @return AsteroidType typ asteroidu
     */
    public static AsteroidType randomAsteroidType() {
        Random r = new Random();
        int rInt = r.nextInt(13);
        if (rInt < 4) {
            return AsteroidType.SMALL;
        } else if (rInt >= 4 && rInt < 10) {
            return AsteroidType.MEDIUM;
        } else {
            return AsteroidType.LARGE;
        }
    }
}
