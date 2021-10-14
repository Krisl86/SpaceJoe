package spacejoe.game.gameobject;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import spacejoe.game.enumerator.AsteroidType;

/**
 * Asteroidy ktore vidime na obrazovke.
 */
public class Asteroid extends GameObject {

    private Texture texture;
    private Animation animation;
    private int hp;
    private final AsteroidType asteroidType;
    private float deltaTimer;
    private boolean isRewarded;

    /**
     * @param texture textura
     * @param animation animacia
     * @param asteroidType typ asteroidu
     * @param x pozicia x
     * @param y pozicia y
     */
    public Asteroid(Texture texture, Animation animation, AsteroidType asteroidType, int x, int y) {
        super(x, y, asteroidType.getWidth(), asteroidType.getHeight());
        this.texture = texture;
        this.asteroidType = asteroidType;
        this.deltaTimer = 0;
        this.animation = animation;
        this.hp = asteroidType.getHp();
        this.isRewarded = false;
    }

    public AsteroidType getAsteroidType() {
        return asteroidType;
    }

    public boolean getIsRewarded() {
        return isRewarded;
    }

    public int getHp() {
        return hp;
    }

    public boolean getHasCollided() {
        return super.getHasCollided();
    }

    public void setHasCollided(boolean hasCollided) {
        super.setHasCollided(hasCollided);
    }

    public void setIsRewarded(boolean rewarded) {
        isRewarded = rewarded;
    }

    public void subHp(int amount) {
        this.hp -= amount;
    }

    /**
     * Pohyb asteroidu.
     * @param delta cas delta
     */
    public void move(float delta) {
        this.setY(this.getY() - this.asteroidType.getSpeed() * delta);
    }

    /**
     * Prehravanie animacie.
     * @param delta
     */
    public void playAnimation(float delta) {
        this.deltaTimer += delta;
        this.texture = (Texture)this.animation.getKeyFrame(this.deltaTimer, false);
    }

    /**
     * Vykreslovanie asteroidu.
     * @param batch
     */
    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(this.texture, (float)this.getX(), (float)this.getY(), this.getWidth(), this.getHeight());
    }
}
