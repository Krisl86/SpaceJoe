package spacejoe.game.gameobject.gameObjectManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import spacejoe.game.enumerator.ProjectileType;
import spacejoe.game.gameManager.CollisionManager;
import spacejoe.game.gameobject.GameObject;
import spacejoe.game.gameobject.Projectile;

import java.util.LinkedList;

/**
 * Zodpoveda za sledovanie projektilov na obrazovke, ich pohyb, kolizie a ostranenie.
 */
public class ProjectileManager {

    private final LinkedList<Projectile> projectilesList;
    private AssetManager assetManager;

    /**
     * @param assetManager asset manazer pre ziskanie textur
     */
    public ProjectileManager(AssetManager assetManager) {
        this.assetManager = assetManager;
        this.projectilesList = new LinkedList<>();
    }

    public LinkedList<Projectile> getProjectilesListCopy() {
        return new LinkedList<Projectile>(this.projectilesList);
    }

    /**
     * Sprava kolizii projektilov s danym listom (asteroidy).
     * @param list zoznam objektov
     * @param <T> typ GameObject
     */
    public <T extends GameObject> void manageCollisions(LinkedList<T> list) {
        for (Projectile projectile : this.projectilesList) {
            for (T item : list) {
                if (CollisionManager.isColliding(projectile, item) && !item.getHasCollided()) {
                    projectile.setHasCollided(true);
                }
            }
        }
    }

    /**
     * Vytvorenie noveho projektilu a pridanie do zoznamu.
     * @param x x pozicia
     * @param y y pozicia
     * @param projectileType typ projektilu - zavisi od zbrane
     */
    public void createNewProjectile(double x, double y, ProjectileType projectileType) {
        this.projectilesList.add(new Projectile(this.assetManager.get("projectile-default.png"), x, y, projectileType));
    }

    /**
     * Pohyb vsetkych projektilov.
     * @param delta
     */
    public void moveProjectiles(float delta) {
        for (Projectile projectile : projectilesList) {
            projectile.move(delta);
        }
    }

    /**
     * Vykreslenie vsetkych spravovanych projektilov.
     * @param batch
     */
    public void drawProjectiles(SpriteBatch batch) {
        for (Projectile projectile : projectilesList) {
            projectile.draw(batch);
        }
    }

    /**
     *Odstranuje projektily kt. uz nie su na obrazovke/kolidovali.
     */
    public void disposeOfLostProjectiles() {
        for (int i = 0; i < this.projectilesList.size(); i++) {
            if (!this.isProjectileOnScreen(this.projectilesList.get(i)) || this.projectilesList.get(i).getHasCollided()) {
                this.projectilesList.remove(i);
            }
        }
    }

    /**
     * Overenie ci sa dany projektil nachadza na obrazovke.
     * @param projectile dany projektil
     * @return true ak je na obrazovke, inak false
     */
    private boolean isProjectileOnScreen(Projectile projectile) {
        if (projectile.getX() + projectile.getWidth() >= Gdx.graphics.getWidth() || projectile.getX() <= -projectile.getWidth()) {
            return false;
        }

        if (projectile.getY() + projectile.getHeight() >= Gdx.graphics.getHeight() || projectile.getY() <= -projectile.getHeight()) {
            return false;
        }
        return true;
    }

}
