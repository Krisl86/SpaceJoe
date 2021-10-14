package spacejoe.game.item;

import spacejoe.game.SpaceJoeGame;
import spacejoe.game.enumerator.PlayerEffect;
import spacejoe.game.enumerator.ProjectileType;

import java.util.ArrayList;

/**
 * Sprava itemov - ich vytvorenie - pre jednoduchy pristup a pridavanie/odoberanie
 */
public class ItemManager {

    private SpaceJoeGame game;
    private ArrayList<IItem> stock;

    /**
     * @param game SpaceJoeGame
     */
    public ItemManager(SpaceJoeGame game) {
        this.game = game;

        this.stock = new ArrayList<>();
        this.generateItems();
    }

    public ArrayList<IItem> getStockCopy() {
        return new ArrayList<>(this.stock);
    }

    public IItem stringToItem(String string) {
        for (IItem item : stock) {
            if (item.getName().equals(string)) {
                return item;
            }
        }
        return null;
    }

    /**
     * Vytvaranie predmetov prebieha tu.
     */
    private void generateItems() {
        this.stock.add(new UsableItem("Shield module", "Shields from 2 hits, cannot be repaired", 20, PlayerEffect.SHIELD, 2));
        this.stock.add(new UsableItem("Repair bot", "Repairs for 1 hull point", 15, PlayerEffect.REPAIR, 1));
        this.stock.add(new UsableItem("Weapon cooler", "Cools your gun", 50, PlayerEffect.COOLER, 1));
        // projectile manager musi byt nastaveny na projectile manager hraca pri kupeni
        this.stock.add(new Weapon("Cool laser", "Warning:\nthe actual projectiles are not cool\nbut hot", 10, 20, 1.75f, ProjectileType.SLOW, null));
    }
}
