package spacejoe.game.item;

/**
 * Predmety ktore mozno kupovat v obchode.
 */
public interface IItem {
    String getName();
    String getShortId();
    String getDescription();
    int getPrice();
}
