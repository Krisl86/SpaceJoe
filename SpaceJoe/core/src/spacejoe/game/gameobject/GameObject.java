package spacejoe.game.gameobject;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Template pre objekty na obrazovke ako hrac, projektily, metoery a pozadie.
 */
public abstract class GameObject {
    private double x;
    private double y;
    private int width;
    private int height;
    private boolean hasCollided;

    /**
     * @param x x pozicia
     * @param y y pozicia
     * @param width sirka objektu
     * @param height vyska objektu
     */
    public GameObject(double x, double y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.hasCollided = false;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean getHasCollided() {
        return hasCollided;
    }

    protected void setX(double x) {
        this.x = x;
    }

    protected void setY(double y) {
        this.y = y;
    }

    protected void setHasCollided(boolean hasCollided) {
        this.hasCollided = hasCollided;
    }

    /**
     * Vykreslovanie
     * @param batch
     */
    public abstract void draw(SpriteBatch batch);
}
