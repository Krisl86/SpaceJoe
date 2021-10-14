package spacejoe.game.gameobject;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import spacejoe.game.enumerator.ProjectileType;


/**
 * Projektily zbran striela.
 */
public class Projectile extends GameObject {

    private final ProjectileType projectileType;
    private final Texture texture;

    /**
     * @param texture textura projektilu
     * @param x x suradnica
     * @param y y suradnica
     * @param projectileType typ projektilu
     */
    public Projectile(Texture texture, double x, double y, ProjectileType projectileType) {
        super(x, y, projectileType.getWidth(), projectileType.getHeight());
        this.projectileType = projectileType;
        this.texture = texture;
    }

    public boolean getHasCollided() {
        return super.getHasCollided();
    }

    public void setHasCollided(boolean hasCollided) {
        super.setHasCollided(hasCollided);
    }

    /**
     * Pohyb projektilu.
     * @param delta
     */
    public void move(float delta) {
        this.setY(this.getY() + delta * this.projectileType.getSpeed());
    }

    /**
     * Vykreslovanie projektilu na obrazovke.
     * @param batch
     */
    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(this.texture, (float)this.getX(), (float)this.getY());
    }


}
