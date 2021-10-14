package spacejoe.game.enumerator;

/**
 * Rozne stavy v ktorych sa hra moze nachadzat.
 * RUNNING - hlavna obrazovka
 * GAME_OVER - ked sme prehrali
 * SHOPPING - ked sme v obchode
 *
 * Input manager pouziva tieto stavy na branie/zamietnutie inputu.
 */
public enum GameState {
    RUNNING,
    GAME_OVER,
    SHOPPING,
    MAIN_MENU;
}
