package spacejoe.game.gameobject.gameObjectManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import spacejoe.game.enumerator.AsteroidType;
import spacejoe.game.gameManager.CollisionManager;
import spacejoe.game.gameobject.Asteroid;
import spacejoe.game.gameobject.GameObject;
import spacejoe.game.gameManager.resourceManager.AnimationManager;

import java.util.LinkedList;
import java.util.Random;

/**
 * Spravca asteroidov - nahodne ich vytvara a pridava do listu,
 * odobera z listu ked sa dostanu mimo obrazovku.
 */
public class AsteroidManager {

    private float deltaTimer;
    private final LinkedList<Asteroid> asteroidList;
    private AssetManager assetManager;
    private AnimationManager animationManager;

    /**
     * @param assetManager assetManager pre ziskavanie textur
     * @param animationManager ziskavanie animacii
     */
    public AsteroidManager(AssetManager assetManager, AnimationManager animationManager) {
        this.assetManager = assetManager;
        this.animationManager = animationManager;
        this.deltaTimer = 0;
        this.asteroidList = new LinkedList<>();
    }

    public LinkedList<Asteroid> getAsteroidListCopy() {
        return new LinkedList<Asteroid>(this.asteroidList);
    }

    /**
     * Zistovanie kolizii asteroidov ktore tento manazer spravuje s danym listom objektov (projektily).
     * @param list list s ktorym zistujeme kolizie
     * @param <T> typ GameObject
     */
    public <T extends GameObject> void manageCollisions(LinkedList<T> list) {
        for (Asteroid asteroid : this.asteroidList) {
            for (T item : list) {
                if (CollisionManager.isColliding(asteroid, item)) {
                    asteroid.subHp(1);
                    if (asteroid.getHp() <= 0) {
                        asteroid.setHasCollided(true);
                    }
                }
            }
        }
    }

    /**
     * Zistovanie kolizii s jedinym objektom.
     * @param item objekt s ktorym zistujeme koliziu.
     * @param <T> typ GameObject
     */
    public <T extends GameObject> void manageCollisions(T item) {
        for (Asteroid asteroid : this.asteroidList) {
            if (CollisionManager.isColliding(asteroid, item) && !asteroid.getHasCollided()) {
                asteroid.setHasCollided(true);
                asteroid.subHp(asteroid.getAsteroidType().getHp());
            }
        }
    }

    /**
     * Vytvaranie asteroidov nahodnych typov a nahodnym casovym rozostupom.
     * @param delta
     */
    public void createAsteroidsAtRandom(float delta) {
        Random r = new Random();

        this.deltaTimer += delta;
        if (this.deltaTimer >= r.nextFloat() * 2 + 1) {
            this.createNewAsteroid();
            this.deltaTimer = 0;
        }
    }

    /**
     * Pohyb vsetkych spravovanych asteroidov.
     * @param delta
     */
    public void moveAsteroids(float delta) {
        for (Asteroid asteroid : asteroidList) {
            asteroid.move(delta);
        }
    }

    /**
     * Vykreslovanie vsetkych spravovanych asteroidov.
     * @param batch
     */
    public void drawAsteroids(SpriteBatch batch) {
        for (Asteroid asteroid : asteroidList) {
            asteroid.draw(batch);
        }
    }

    /**
     * Prehravanie animacii pre asteroidy (animacia vybuchu)
     * @param delta
     */
    public void playAnimations(float delta) {
        for (int i = 0; i < this.asteroidList.size(); i++) {
            if (this.asteroidList.get(i).getHp() <= 0) {
                this.asteroidList.get(i).playAnimation(delta);
            }
        }
    }

    /**
     * Odstranenie asteroidov ked uz nie su na obrazovke a sprava odmien hracovi.
     * Ak ma asteroid hp <= 0 a odmena este nebola udelena, odmena sa prida do countera,
     * na konci sa counter so score vrati.
     * @return odmena za znicenie asteroidov
     */
    public int disposeOfLostAsteroids() {
        int scoreCounter = 0;
        for (int i = 0; i < this.asteroidList.size(); i++) {
            if (this.asteroidList.get(i).getHp() <= 0 && !this.asteroidList.get(i).getIsRewarded()) {
                scoreCounter += this.asteroidList.get(i).getAsteroidType().getReward();
                this.asteroidList.get(i).setIsRewarded(true);
            }

            if (!this.isAsteroidOnScreen(this.asteroidList.get(i))) {
                this.asteroidList.remove(i);
            }
        }
        return scoreCounter;
    }

    /**
     *Vytvorenie noveho asteroidu daneho typu a priradenie do listu.
     */
    private void createNewAsteroid() {
        Random r = new Random();
        Asteroid asteroid = new Asteroid(this.assetManager.get("asteroid.png"),
                this.animationManager.getAnimation("explosion-ani"),
                AsteroidType.randomAsteroidType(),
                r.nextInt(Gdx.graphics.getWidth() - AsteroidType.MEDIUM.getWidth()) + 1,
                Gdx.graphics.getHeight() + 100);

        this.asteroidList.add(asteroid);
    }

    /**
     * Zistenie ci je asteroid na obrazovke.
     * @param asteroid asteroid
     * @return true ak je na obrazovke, inak false
     */
    private boolean isAsteroidOnScreen(Asteroid asteroid) {
        if (asteroid.getY() <= -180) {
            return false;
        }
        return true;
    }

}
