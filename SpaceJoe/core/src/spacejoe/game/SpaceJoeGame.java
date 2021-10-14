package spacejoe.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import spacejoe.game.enumerator.GameState;
import spacejoe.game.gameManager.InputManager;
import spacejoe.game.gameobject.gameObjectManager.ProjectileManager;
import spacejoe.game.gameobject.player.Player;
import spacejoe.game.item.ItemManager;
import spacejoe.game.gameManager.resourceManager.ResourcesInit;
import spacejoe.game.gameManager.resourceManager.AnimationManager;
import spacejoe.game.screen.GameOverScreen;
import spacejoe.game.screen.MainMenuScreen;
import spacejoe.game.screen.MainScreen;
import spacejoe.game.screen.ShopScreen;

/**
 * Hlavna trieda celej aplikacie, spaja rozne prvky dokopy, vytvara obrazovky.
 */
public class SpaceJoeGame extends Game {

    private Player player;
    private AssetManager assetManager;
    private InputManager inputManager;
    private ItemManager itemManager;
    private AnimationManager animationManager;
    private ShopScreen shopScreen;
    private GameOverScreen gameOverScreen;
    private MainMenuScreen mainMenuScreen;

    private GameState gameState;


    @Override
    public void create() {
        this.assetManager = new AssetManager();
        this.animationManager = new AnimationManager();

        ResourcesInit.initTextures(this.assetManager);
        ResourcesInit.initAnimations(this.assetManager, this.animationManager);

        this.player = new Player(this, this.assetManager.get("ship.png"), new ProjectileManager(this.assetManager));

        this.itemManager = new ItemManager(this);
        this.inputManager = new InputManager(this, this.player, this.itemManager);

        this.shopScreen = new ShopScreen(this, this.player, this.inputManager, this.assetManager, this.itemManager);
        this.gameOverScreen = new GameOverScreen(this, this.player, this.inputManager, this.assetManager);
        this.mainMenuScreen = new MainMenuScreen(this, this.inputManager, this.assetManager);

        setScreen(this.mainMenuScreen);
        this.gameState = GameState.MAIN_MENU;
    }

    public GameState getGameState() {
        return gameState;
    }

    public ShopScreen getShopScreen() {
        return shopScreen;
    }

    public GameOverScreen getGameOverScreen() {
        return gameOverScreen;
    }

    public MainMenuScreen getMainMenuScreen() {
        return mainMenuScreen;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    /**
     * Vytvara novu hraciu obrazovku a vracia ju.
     * @return nova hracia obrazovka MainScreen
     */
    public MainScreen createNewMainScreen () {
        return new MainScreen(this, this.player, this.inputManager, this.assetManager, this.animationManager);
    }

    @Override
    public void setScreen(Screen screen) {
        super.setScreen(screen);
    }
}
