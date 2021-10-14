package spacejoe.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import spacejoe.game.SpaceJoeGame;
import spacejoe.game.enumerator.GameState;
import spacejoe.game.gameManager.InputManager;
import spacejoe.game.gameobject.gameObjectManager.ProjectileManager;
import spacejoe.game.gameobject.Background;
import spacejoe.game.gameobject.player.Player;
import spacejoe.game.gameobject.gameObjectManager.AsteroidManager;
import spacejoe.game.gameManager.resourceManager.AnimationManager;
import spacejoe.game.item.IItem;

/**
 * Hlavna obrazovka hry, tu prebieha hlavna cast gameplayu.
 */
public class MainScreen extends ScreenAdapter {
    private static final int X_MARGIN = 20;

    private final SpaceJoeGame game;
    private Player player;
    private final ProjectileManager projectileManager;
    private AssetManager assetManager;
    private AnimationManager animationManager;
    private InputManager inputManager;
    private SpriteBatch batch;
    private BitmapFont font;
    private Background background;
    private AsteroidManager asteroidManager;

    public MainScreen(SpaceJoeGame game, Player player, InputManager inputManager, AssetManager assetManager, AnimationManager animationManager) {
        this.game = game;

        this.batch = new SpriteBatch();
        this.font = new BitmapFont();
        this.font.setColor(0, 0.5f, 0, 1);
        this.font.getData().setScale(2, 2);

        this.assetManager = assetManager;
        this.animationManager = animationManager;
        this.inputManager = inputManager;

        this.asteroidManager = new AsteroidManager(this.assetManager, this.animationManager);

        this.player = player;
        this.player.init();
        this.projectileManager = this.player.getProjectileManager();
        this.background = new Background(this.assetManager.get("bg-back.png"), this.assetManager.get("bg-front.png"));
    }

    @Override
    public void render(float delta) {
        this.asteroidManager.createAsteroidsAtRandom(delta);

        // MOVE
        this.manageMovement(delta);

        // COLLIDE
        this.manageCollisions();

        // PLAYER EFFECTS
        this.player.managePlayerEffects(delta);

        this.batch.begin();
        // DRAW
        this.drawObjects();
        this.drawHud();
        //ANIMATE
        this.playAnimations(delta);

        this.batch.end();

        // MANAGE COOLDOWNS
        this.manageCooldowns(delta);

        // DISPOSE
        this.disposeObjects();

        // CHECK GAME STATE
        if (this.game.getGameState() == GameState.GAME_OVER) {
            this.game.setScreen(this.game.getGameOverScreen());
        }
    }

    /**
     * Cooldown zbrane hraca.
     * @param delta
     */
    private void manageCooldowns(float delta) {
        this.player.getWeapon().coolDown(delta);
    }

    /**
     * Sprava pohyby roznych objektov na obrazovke.
     * @param delta
     */
    private void manageMovement(float delta) {
        this.inputManager.manageInput(delta);

        this.background.move(delta);
        this.projectileManager.moveProjectiles(delta);
        this.asteroidManager.moveAsteroids(delta);
    }

    /**
     * Likvidacia objektov mimo obrazovku + pridavanie skore hracovi.
     */
    private void disposeObjects() {
        int asteroidReward = 0;
        asteroidReward =  this.asteroidManager.disposeOfLostAsteroids();
        this.player.addScore(asteroidReward);
        this.player.addTotalScore(asteroidReward);
        this.player.addPoints(asteroidReward);

        this.projectileManager.disposeOfLostProjectiles();
    }

    /**
     * Kolizie medzi objektami.
     */
    private void manageCollisions() {
        this.player.checkForCollisions(this.asteroidManager.getAsteroidListCopy());
        this.projectileManager.manageCollisions(this.asteroidManager.getAsteroidListCopy());
        this.asteroidManager.manageCollisions(this.projectileManager.getProjectilesListCopy());
        this.asteroidManager.manageCollisions(this.player);
    }

    /**
     * Vykreslovanie objektov
     */
    private void drawObjects() {
        this.background.draw(this.batch);
        this.player.draw(this.batch);
        this.projectileManager.drawProjectiles(this.batch);
        this.asteroidManager.drawAsteroids(this.batch);
    }

    /**
     * Prehravanie animacii
     * @param delta
     */
    private void playAnimations(float delta) {
        this.asteroidManager.playAnimations(delta);
    }

    /**
     * Vykreslenie HUD, informacie pre hraca.
     */
    private void drawHud() {
        // POINTS, HULL, SHIELD
        this.font.draw(this.batch, "[ PTS SCR: " + this.player.getScore() + " ]", X_MARGIN, Gdx.graphics.getHeight() - 20);
        this.font.draw(this.batch, "[ HULL PTS: " + this.player.getHp() + " ]", Gdx.graphics.getWidth() / 2 + 30, Gdx.graphics.getHeight() - 20);
        this.font.draw(this.batch, "[ SHIELD: " + this.player.getShield() + " ]", Gdx.graphics.getWidth() / 2 + 30, Gdx.graphics.getHeight() - 60);

        // WEAPON HEAT
        this.font.draw(this.batch, "[ WPN HEAT ]", X_MARGIN, Gdx.graphics.getHeight() - 60);
        for (int i = 1; i <= this.player.getWeapon().getHeat(); i++) {
            this.font.getData().setScale(3, 3);
            this.font.draw(this.batch, "*", X_MARGIN + i * 15, Gdx.graphics.getHeight() - 90);
            this.font.getData().setScale(2, 2);
        }

        // OVERHEAT WARNING
        if (this.player.getWeapon().getHeat() >= this.player.getWeapon().getHeatLimit()) {
            this.font.draw(this.batch, "[ WEAPON OVERHEATING ]", 55, 400);
        }

        // PLAYER EFFECT
        if (this.player.getPlayerEffect() != null) {
            this.font.draw(this.batch, "[ " + this.player.getPlayerEffect().getName() + " ACTIVE ]", 100, 200);
        }

        // ITEM SLOTS
        for (int i = 1; i < 4; i++) {
            this.font.draw(this.batch, "[ " + i + " ]", X_MARGIN + i * 100, 80);
        }
        // ITEMS
        int i = 1;
        for (IItem item : this.player.getInventoryCopy()) {
            this.font.draw(this.batch, item.getShortId(), (X_MARGIN + i * 100) - 7, 35);
            i += 1;
        }

    }
}
