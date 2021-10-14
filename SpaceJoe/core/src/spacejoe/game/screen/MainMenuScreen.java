package spacejoe.game.screen;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import spacejoe.game.SpaceJoeGame;
import spacejoe.game.gameManager.InputManager;
import spacejoe.game.screen.adapter.SpaceJoeScreenAdapter;

/**
 * Obrazovka hlavneho menu.
 */
public class MainMenuScreen extends SpaceJoeScreenAdapter {

    private final InputManager inputManager;
    private AssetManager assetManager;
    private SpaceJoeGame game;

    public MainMenuScreen(SpaceJoeGame game, InputManager inputManager, AssetManager assetManager) {
        super(new SpriteBatch(), new BitmapFont(), 0);

        this.game = game;
        this.inputManager = inputManager;
        this.assetManager = assetManager;


        this.getFont().setColor(0, 0.5f, 0, 1);
        this.getFont().getData().setScale(3);
    }

    public void setShowMessages(boolean showMessages) {
        super.setShowMessages(showMessages);
    }

    public void setMessage(String message) {
        super.setMessage(message);
    }

    @Override
    public void render(float delta) {
        this.inputManager.manageInput(delta);

        this.getBatch().begin();

        this.getBatch().draw((Texture)this.assetManager.get("shop.png"), 0, 0);

        if (this.getShowMessages()) {
            this.flashMessage(delta, 3, 3, 20, 200);
        }

        this.getFont().draw(this.getBatch(), "[N]ew Game", 40, 500);
        this.getFont().draw(this.getBatch(), "[L]oad Game", 40, 400);
        this.getFont().draw(this.getBatch(), "[Q]uit", 40, 300);

        this.getBatch().end();
    }

}
