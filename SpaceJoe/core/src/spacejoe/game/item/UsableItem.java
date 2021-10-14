package spacejoe.game.item;

import spacejoe.game.enumerator.PlayerEffect;
import spacejoe.game.gameobject.player.Player;

/**
 * Pouzitelny predmet - predmet s ucinkom na hraca a ktory zmizne po pouziti.
 */
public class UsableItem implements IItem {

    private String name;
    private String description;
    private PlayerEffect playerEffect;
    private int effectAmount;
    private int price;

    /**
     * @param name nazov predmetu
     * @param description popis predmetu
     * @param price cena predmetu
     * @param playerEffect efekt na hraca - PLayerEffect enum
     * @param effectAmount mnozstvo efektu - o kolko sa zmeni dana hodnota pri pouziti predmetu
     */
    public UsableItem(String name, String description,  int price, PlayerEffect playerEffect, int effectAmount) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.playerEffect = playerEffect;
        this.effectAmount = effectAmount;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getShortId() {
        return this.playerEffect.getShortIdentifier();
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public int getPrice() {
        return this.price;
    }

    public PlayerEffect getPlayerEffect() {
        return playerEffect;
    }

    public int getEffectAmount() {
        return effectAmount;
    }

    /**
     * Aplikacia efektu na hraca.
     * @param player hrac
     */
    public void applyEffect(Player player) {
        this.playerEffect.applyEffect(player, this.effectAmount);
    }
}
