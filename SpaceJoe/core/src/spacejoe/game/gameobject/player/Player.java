package spacejoe.game.gameobject.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import spacejoe.game.SpaceJoeGame;
import spacejoe.game.enumerator.GameState;
import spacejoe.game.enumerator.PlayerEffect;
import spacejoe.game.gameobject.GameObject;
import spacejoe.game.gameManager.CollisionManager;
import spacejoe.game.gameobject.gameObjectManager.ProjectileManager;
import spacejoe.game.enumerator.ProjectileType;
import spacejoe.game.item.IItem;
import spacejoe.game.item.UsableItem;
import spacejoe.game.item.Weapon;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Trieda hraca - jeho ovladanie, vykreslovanie, pohyb, koliezie a dalsie operacie.
 */
public final class Player extends GameObject {
    private static final int MOVEMENT_SPEED = 550;
    private static final int WIDTH = 64;
    private static final int HEIGHT = 96;
    private static final double DEFAULT_X = Gdx.graphics.getWidth() / 2 - WIDTH / 2;
    private static final double DEFAULT_Y = 25;
    private static final int DEFAULT_MAX_HP = 3;
    private static final int INV_SIZE = 3;

    private final SpaceJoeGame game;
    private final Texture texture;
    private ProjectileManager projectileManager;
    private int totalScore;
    private int points;
    private int score;
    private int hp;
    private int shield;
    private Weapon weapon;

    private ArrayList<UsableItem> inventory;
    private PlayerEffect playerEffect;
    private int effectAmount;

    /**
     * @param game SpaceJoeGame
     * @param texture textura hraca
     * @param projectileManager manazer pre projektily
     */
    public Player(SpaceJoeGame game, Texture texture, ProjectileManager projectileManager) {
        super(DEFAULT_X, DEFAULT_Y, WIDTH, HEIGHT);
        this.game = game;
        this.texture = texture;
        this.projectileManager = projectileManager;
        this.totalScore = 0;
        this.points = 300;
        this.score = 0;
        this.hp = DEFAULT_MAX_HP;
        this.shield = 0;
        this.weapon = new Weapon("Default laser", "Is alright", 10, 8, 1, ProjectileType.DEFAULT, this.projectileManager);

        this.inventory = new ArrayList<>();
        this.playerEffect = null;
        this.effectAmount = 0;
    }

    public static int getDefaultMaxHp() {
        return DEFAULT_MAX_HP;
    }

    public int getPoints() {
        return points;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public int getScore() {
        return score;
    }

    public int getHp() {
        return hp;
    }

    public PlayerEffect getPlayerEffect() {
        return playerEffect;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public ArrayList<IItem> getInventoryCopy() {
        return new ArrayList<>(this.inventory);
    }

    public int getShield() {
        return shield;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
        this.updateWeaponProjectileManager();
    }

    public ProjectileManager getProjectileManager() {
        return this.projectileManager;
    }

    /**
     * Pridanie predmetov do inventara.
     * @param item predmet ktory chceme pridat.
     * @return true ak sa podarilo pridat, inak false;
     */
    public boolean addItem(UsableItem item) {
        if (!this.isInventoryFull()) {
            this.inventory.add(item);
            return true;
        }
        return false;
    }

    /**
     * Pridava hracovy body (herna mena).
     * @param amount mnozstvo k pridaniu
     */
    public void addPoints(int amount) {
        this.points += amount;
    }

    /**
     * Pridava hracovi celkove skore - skore ktore sa nemaze a neresetuje - skore za vsetly hry.
     * @param amount mnozstvo k pridaniu
     */
    public void addTotalScore(int amount) {
        this.totalScore += amount;
    }

    /**
     *Pridava hracovi docasne skore (v ramci jednej hry) - pri novej hre je zresetovane.
     * @param amount mnozstvo k pridaniu
     */
    public void addScore(int amount) {
        this.score += amount;
    }

    /**
     * Pridanie Hull pointov - hracove zdravie.
     * @param amount mnozstvo k pridaniu
     */
    public void addHp(int amount) {
        this.hp += amount;
    }

    /**
     * Pridanie bodov stitu hracovi - chrani ho pred zasahmi.
     * @param amount mnozstvo k pridaniu
     */
    public void addShield(int amount) {
        this.shield += amount;
    }

    /**
     * Odobratie HP hracovi o jeden bod (-1).
     */
    public void subHp(int amount) {
        this.hp -= amount;
    }

    /**
     * Odobratie STITU hracovi o 1 bod (-1).
     */
    public void subShield(int amount) {
        this.shield -= amount;
    }

    /**
     * Odobratie pointov - penazi v danom mnozstve.
     * @param amount mnozstvo k odobratiu
     */
    public void subPoints(int amount) {
        this.points -= amount;
    }

    /**
     * Check ci je inventar hraca plny.
     * @return true ak je plny, inak false
     */
    public boolean isInventoryFull() {
        return this.inventory.size() == INV_SIZE;
    }

    /**
     * Kontrola kolizii hraca s objektmi zo zoznamu, reakcie na kolizie (znizenie hp, shield).
     * @param list zoznam objektov pre kontrolu
     * @param <T> typ GameObject
     */
    public <T extends GameObject> void checkForCollisions(LinkedList<T> list) {

        for (T item : list) {
            if (CollisionManager.isColliding(this, item) && !item.getHasCollided()) {
                if (this.shield > 0) {
                    this.subShield(1);
                } else {
                    this.subHp(1);
                }
                if (this.hp <= 0) {
                    this.game.setGameState(GameState.GAME_OVER);
                }
            }
        }
    }

    /**
     * Pohyb hore.
     * @param delta cas delta
     */
    public void moveUp(float delta) {
        if (this.getY() + this.getHeight() >= Gdx.graphics.getHeight()) {
            return;
        }
        this.setY(this.getY() + MOVEMENT_SPEED * delta);
    }

    /**
     * Pohyb dole.
     * @param delta cas delta
     */
    public void moveDown(float delta) {
        if (this.getY() <= 0) {
            return;
        }
        this.setY(this.getY() - MOVEMENT_SPEED * delta);
    }

    /**
     * Pohyb vlavo.
     * @param delta cas delta
     */
    public void moveLeft(float delta) {
        if (this.getX() <= 0) {
            return;
        }
        this.setX(this.getX() - MOVEMENT_SPEED * delta);
    }

    /**
     * Pohyb vpravo.
     * @param delta cas delta
     */
    public void moveRight(float delta) {
        if (this.getX() + this.getWidth() >= Gdx.graphics.getWidth()) {
            return;
        }
        this.setX(this.getX() + MOVEMENT_SPEED * delta);
    }

    /**
     * Strelba - vytvorenie projektilu hracovou zbranou.
     * Zbran vyuziva hracove koordinaty pre urcenie pozicie projektilu.
     */
    public void shoot() {
        this.weapon.shoot(this.getX(), this.getY(), this.getWidth(), this.getHeight());
    }

    /**
     * Pouzitie predmetu z inventara na danom slote (cislovanie od 1).
     * Odstrani predchadzajuci efekt ak je pritomny.
     * @param slot slot predmetu v inventary -> index + 1
     */
    public void useItem(int slot) {
        if (slot > this.inventory.size()) {
            return;
        }

        slot -= 1;

        if (playerEffect != null) {
            this.playerEffect.removeEffect(this);
        }
        this.inventory.get(slot).applyEffect(this);

        this.playerEffect = this.inventory.get(slot).getPlayerEffect();
        this.effectAmount = this.inventory.get(slot).getEffectAmount();
        this.inventory.remove(slot);
        System.out.println(this.playerEffect);
    }

    /**
     * Sprava hracovych efektov - posielame spravu PlayerEffect.manageEffect - tato odpoveda true/false ci efekt stale
     *  pretrvava.
     * @param delta
     */
    public void managePlayerEffects(float delta) {
        if (this.playerEffect != null) {
            if (!this.playerEffect.manageEffect(this, delta)) {
                this.playerEffect = null;
            }
        }

        if (this.playerEffect == PlayerEffect.SHIELD && this.shield == 0) {
            this.playerEffect = null;
        }
    }

    /**
     * Kupovanie itemov cez shop.
     * @param slot slot index predmetu + 1 -> index 0 = slot 1
     */
    public void buyItem(int slot) {
        this.game.getShopScreen().buyItem(slot);
    }

    /**
     * Inicializacia hraca, pre zacatie novej hry.
     */
    public void init() {
        this.hp = DEFAULT_MAX_HP;
        this.setX(DEFAULT_X);
        this.setY(DEFAULT_Y);
        this.score = 0;
        this.weapon.setHeat(0);
        this.playerEffect = null;
        this.effectAmount = 0;
    }

    /**
     * Vykreslovanie hraca.
     * @param batch
     */
    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(this.texture, (float)this.getX(), (float)this.getY(), (float)this.getWidth(), (float)this.getHeight());
    }

    private void updateWeaponProjectileManager() {
        if (this.weapon != null) {
            this.weapon.setProjectileManager(this.projectileManager);
        }
    }
}

