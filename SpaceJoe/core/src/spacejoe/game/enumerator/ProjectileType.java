package spacejoe.game.enumerator;

/**
 * Rozne typy projektilov pre zbrane (Weapon).
 */
public enum ProjectileType {
    DEFAULT(3, 7, 600),
    SLOW(6, 15, 375);


    private final int width;
    private final int height;
    private final int speed;

    /**
     * @param width sirka projektilu
     * @param height vyska projektilu
     * @param speed rychlost projektilu
     */
    ProjectileType(int width, int height, int speed) {
        this.width = width;
        this.height = height;
        this.speed = speed;
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


    public String toString() {
        switch (this) {
            case SLOW:
                return "SLOW";
            default:
                return "DEFAULT";
        }
    }

    public static ProjectileType stringToType(String string) {
        switch (string) {
            case "SLOW":
                return ProjectileType.SLOW;
            default:
                return ProjectileType.DEFAULT;
        }
    }
}
