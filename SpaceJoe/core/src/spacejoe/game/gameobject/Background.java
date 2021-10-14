package spacejoe.game.gameobject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Trieda zodpovedna za vykreslovanie pozadia hernej plochy.
 */
public class Background {
    private static final int BACK_MOVEMENT_SPEED = 200;
    private static final int FRONT_MOVEMENT_SPEED = 550;
    private static final double DEFAULT_X = 0;
    private static final double DEFAULT_Y = 0;

    private final Texture backTexture;
    private final Texture frontTexture;
    private double backY;
    private double frontY;

    /**
     * @param backTexture textura pozadia
     * @param frontTexture textura popredia
     */
    public Background(Texture backTexture, Texture frontTexture) {
        this.backTexture = backTexture;
        this.frontTexture = frontTexture;
    }

    /**
     * Pohyb oboch vrstiev pozadia.
     * @param delta
     */
    public void move(float delta) {
        this.backY = this.backY - BACK_MOVEMENT_SPEED * delta;
        this.frontY = this.frontY - FRONT_MOVEMENT_SPEED * delta;
    }

    /**
     * Vykreslovanie a reset vrstiev pozadia ked dojdu na koniec.
     * @param batch
     */
    public void draw(SpriteBatch batch) {
        batch.draw(this.backTexture, 0, (float)this.backY);
        batch.draw(this.frontTexture, 0, (float)this.frontY);

        // resetovanie y-suradnic textur ked sa dostaneme ku koncu
        if (this.backY <= -this.backTexture.getHeight() + Gdx.graphics.getHeight()) {
            this.backY = 0;
        }

        if (this.frontY <= -this.frontTexture.getHeight() + Gdx.graphics.getHeight()) {
            this.frontY = 0;
        }

    }
}
