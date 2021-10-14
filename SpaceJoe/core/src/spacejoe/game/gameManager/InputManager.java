package spacejoe.game.gameManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import spacejoe.game.SpaceJoeGame;
import spacejoe.game.enumerator.GameState;
import spacejoe.game.gameobject.player.Player;
import spacejoe.game.item.ItemManager;

/**
 * Sprava inputu od pouzivatela.
 */
public class InputManager {

    private SpaceJoeGame game;
    private Player player;
    private ItemManager itemManager;

    /**
     * @param game SpaceJoeGame hlavna trieda hry
     */
    public InputManager(SpaceJoeGame game, Player player, ItemManager itemManager) {
        this.game = game;
        this.player = player;
        this.itemManager = itemManager;
    }

    /**
     * Sprava inputu - pouziva sa GameState.
     * @param delta cas delta
     */
    public void manageInput(float delta) {

        // MAIN MENU
        if (this.game.getGameState() == GameState.MAIN_MENU) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.N)) {
                this.game.setGameState(GameState.RUNNING);
                this.game.setScreen(this.game.createNewMainScreen());
            }

            if (Gdx.input.isKeyJustPressed(Input.Keys.L)) {
                if (GameSaveManager.load(this.player, this.itemManager)) {
                    this.game.setGameState(GameState.RUNNING);
                    this.game.setScreen(this.game.createNewMainScreen());
                } else {
                    this.game.getMainMenuScreen().setMessage("[ LOADING FAILED ]");
                    this.game.getMainMenuScreen().setShowMessages(true);
                }
            }

            if (Gdx.input.isKeyJustPressed(Input.Keys.Q)) {
                System.exit(0);
            }

        }

        // MANAZMENT INPUTU AK HRA BEZI (Obrazovka MainScreen)
        if (this.game.getGameState() == GameState.RUNNING) {

            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                this.player.shoot();
            }

            if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
                this.player.moveUp(delta);
            }

            if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                this.player.moveDown(delta);
            }

            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                this.player.moveLeft(delta);
            }

            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                this.player.moveRight(delta);
            }

            if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
                this.player.useItem(1);
            }

            if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) {
                this.player.useItem(2);
            }

            if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_3)) {
                this.player.useItem(3);
            }

        }

        // MANAZMENT INPUTU AK SME PREHRALI - GAME OVER(Obrazovka GameOverScreen)
        if (this.game.getGameState() == GameState.GAME_OVER) {

            if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
                this.game.setGameState(GameState.RUNNING);
                this.game.setScreen(this.game.createNewMainScreen());
            }

            if (Gdx.input.isKeyJustPressed(Input.Keys.B)) {
                this.game.setGameState(GameState.SHOPPING);
                this.game.setScreen(this.game.getShopScreen());
            }

            if (Gdx.input.isKeyJustPressed(Input.Keys.S)) {
                if (GameSaveManager.save(this.player)) {
                    this.game.getGameOverScreen().setMessage("[ GAME SAVED ]");
                } else {
                    this.game.getGameOverScreen().setMessage("[ SAVING FAILED ]");
                }
                this.game.getGameOverScreen().setShowMessage(true);
            }

            if (Gdx.input.isKeyJustPressed(Input.Keys.Q)) {
                System.exit(0);
            }

        }

        // MANAZMENT INPUTU AK SME V SHOPE - obrazovka ShopScreen
        if (this.game.getGameState() == GameState.SHOPPING) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
                this.player.buyItem(1);
            }

            if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) {
                this.player.buyItem(2);
            }

            if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_3)) {
                this.player.buyItem(3);
            }

            if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_4)) {
                this.player.buyItem(4);
            }

            if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
                this.game.setGameState(GameState.GAME_OVER);
                this.game.setScreen(this.game.getGameOverScreen());
            }
        }


    }
}
