package spacejoe.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import spacejoe.game.SpaceJoeGame;
import spacejoe.game.gameManager.InputManager;
import spacejoe.game.gameobject.player.Player;
import spacejoe.game.screen.adapter.SpaceJoeScreenAdapter;

/**
 * Obrazovka zobrazena pri prehre.
 */
public class GameOverScreen extends SpaceJoeScreenAdapter {

    private final SpaceJoeGame game;
    private final Player player;
    private final AssetManager assetManager;
    private InputManager inputManager;

    public GameOverScreen(SpaceJoeGame game, Player player, InputManager inputManager, AssetManager assetManager) {
        super(new SpriteBatch(), new BitmapFont(), 0);
        this.game = game;

        this.player = player;
        this.inputManager = inputManager;
        this.assetManager = assetManager;

        this.getFont().setColor(0, 0.5f, 0, 1);
        this.getFont().getData().setScale(3);
    }

    public void setShowMessage(boolean showMessage) {
        super.setShowMessages(showMessage);
    }

    public void setMessage(String message) {
        super.setMessage(message);
    }

    @Override
    public void render(float delta) {

        this.inputManager.manageInput(delta);

        this.getBatch().begin();

        this.getBatch().draw((Texture)this.assetManager.get("game-over.png"), 0, 0);
        this.getFont().draw(this.getBatch(), "[ SIGNAL LOST ]", 20, Gdx.graphics.getHeight() / 2 + 180);
        this.getFont().draw(this.getBatch(), "[ PTS SCR: " + this.player.getScore() + " ]", 20, Gdx.graphics.getHeight() / 2 + 60);
        this.getFont().draw(this.getBatch(), "[ TTL PTS: " + this.player.getPoints() + " ]", 20, Gdx.graphics.getHeight() / 2);
        this.getFont().draw(this.getBatch(), "   [R]etry\n   [S]ave\n   [B]uy\n   [Q]uit", 10, Gdx.graphics.getHeight() / 2 - 60);

        if (this.getShowMessages()) {
            this.flashMessage(delta, 2, 3, 120, 660);
        }

        this.getBatch().end();
    }
}


