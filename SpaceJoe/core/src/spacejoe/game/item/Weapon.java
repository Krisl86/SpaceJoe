package spacejoe.game.item;

import spacejoe.game.enumerator.ProjectileType;
import spacejoe.game.gameobject.gameObjectManager.ProjectileManager;

/**
 * Zbrane ktore moze hrac kupovat - na rozdiel od UsableItem nemaju player efekt a nemiznu pri pouziti.
 */
public class Weapon implements IItem {

    private final String name;
    private final String description;
    private final int price;
    private final int heatLimit;
    private int heat;
    private float deltaTimer;
    private ProjectileType projectileType;
    private ProjectileManager projectileManager;
    private float cooltime;

    /**
     * @param name nazov predmetu
     * @param description popis
     * @param price cena
     * @param heatLimit pocet vystrelov kym je potrebne cakat na cooldown
     * @param cooltime pri "prehriati" zbrane je nutne cakat tento cas kym je strelba opat mozna
     * @param projectileType typ projektilu zbrane
     * @param projectileManager manazer projektilov pre zbran
     */
    public Weapon(String name, String description, int price, int heatLimit, float cooltime, ProjectileType projectileType, ProjectileManager projectileManager) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.heatLimit = heatLimit;
        this.heat = 0;
        this.projectileType = projectileType;
        this.projectileManager = projectileManager;
        this.deltaTimer = 0;
        this.cooltime = cooltime;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getShortId() {
        return "WPN";
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public int getPrice() {
        return this.price;
    }

    public int getHeatLimit() {
        return this.heatLimit;
    }

    public int getHeat() {
        return heat;
    }

    public float getCooltime() {
        return cooltime;
    }

    public ProjectileType getProjectileType() {
        return this.projectileType;
    }

    public void setHeat(int heat) {
        this.heat = heat;
    }

    public void setProjectileManager(ProjectileManager projectileManager) {
        this.projectileManager = projectileManager;
    }

    /**
     * Strelba - vytvorenie noveho projektilu na danych suradniciach. Zvysenie teploty zbrane.
     * @param x suradnica x
     * @param y suradnica y
     * @param width sirka strielajuceho - hraca - pre urcenie presnej polohy projektilu
     * @param height vyska strielajuceho - hraca
     */
    public void shoot(double x, double y, int width, int height) {
        if (this.heat < this.heatLimit) {
            this.projectileManager.createNewProjectile(x + (width / 2) - (this.projectileType.getWidth() / 2), y + height, this.getProjectileType());
            this.heat += 1;
        }
    }

    /**
     * "Ochladenie" zbrane - pri neustalej strelbe sa hran prehrieva az kym nedosiahne svoj heat limit,
     * potom sa musi ochladit kym je strelba opat mozna.
     * @param delta
     */
    public void coolDown(float delta) {
        this.deltaTimer += delta;
        if (this.deltaTimer >= this.cooltime) {
            if (this.heat >= 1) {
                this.heat -= 1;
            }
            this.deltaTimer = 0;
        }
    }
}
