package spacejoe.game.screen;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import spacejoe.game.SpaceJoeGame;
import spacejoe.game.gameManager.InputManager;
import spacejoe.game.gameobject.player.Player;
import spacejoe.game.item.IItem;
import spacejoe.game.item.ItemManager;
import spacejoe.game.item.UsableItem;
import spacejoe.game.item.Weapon;
import spacejoe.game.screen.adapter.SpaceJoeScreenAdapter;

import java.util.ArrayList;

/**
 * Obchod - kupovanie itemov, ich nacitanie zo spravcu itemov.
 */
public class ShopScreen extends SpaceJoeScreenAdapter {

    private static final int X_MARGIN = 40;

    private final SpaceJoeGame game;
    private Player player;
    private InputManager inputManager;
    private final AssetManager assetManager;

    private ArrayList<IItem> stock;

    public ShopScreen(SpaceJoeGame game, Player pLayer, InputManager inputManager, AssetManager assetManager, ItemManager itemManager) {
        super(new SpriteBatch(), new BitmapFont(), 0);
        this.game = game;


        this.player = pLayer;
        this.inputManager = inputManager;
        this.assetManager = assetManager;

        this.getFont().setColor(0, 0.5f, 0, 1);

        this.stock = itemManager.getStockCopy();
    }

    @Override
    public void render(float delta) {


        this.inputManager.manageInput(delta);


        this.getBatch().begin();

        this.getBatch().draw((Texture)this.assetManager.get("shop.png"), 0, 0);

        this.getFont().getData().setScale(2, 2);
        this.getFont().draw(this.getBatch(), "[ STS: DCK ]                        [E]xit", X_MARGIN, 700);
        this.getFont().draw(this.getBatch(), "[ CREDITS: " + this.player.getPoints() + " ]", X_MARGIN, 660);

        if (this.player.isInventoryFull()) {
            this.getFont().draw(this.getBatch(), "[ WARNING: C-BAY FULL ]", X_MARGIN, 620);
        } else {
            this.getFont().draw(this.getBatch(), "[ C-BAY: OPEN, AWT C ]", X_MARGIN, 620);

            this.getFont().getData().setScale(1.75f, 1.75f);
            int i = 1;
            for (IItem item : stock) {
                this.getFont().draw(this.getBatch(), "[ " + i + " ] " + item.getName() + "\n" + item.getDescription() + "\n Price: " + item.getPrice(), X_MARGIN, 600 - 100 * i);
                i += 1;
            }
        }

        if (this.getShowMessages()) {
            this.flashMessage(delta, 2, 3, X_MARGIN, 580);
        }
        this.getBatch().end();
    }

    /**
     * Kupovanie predmetov, vybera sa predmet z daneho slotu (index + 1).
     * Kontroluju sa rozne podmienky ako dostatok penazi, dostatok miesta v inventari, ci uz dany predmet nemame.
     * Nastavuje sa sprava ktora sa zobrazi pri rendrovani.
     * @param slot
     */
    public void buyItem(int slot) {
        slot -= 1;
        if (this.player.getPoints() >= this.stock.get(slot).getPrice()) {
            if (this.stock.get(slot) instanceof Weapon) {
                if (this.player.getWeapon() == this.stock.get(slot)) {
                    this.setMessage("[ WEAPON ALREADY EQUIPPED ]");
                    return;
                }
                this.player.setWeapon((Weapon)this.stock.get(slot));
                this.setMessage("[ WEAPON BOUGHT ]");
                this.player.subPoints(this.stock.get(slot).getPrice());
            } else {
                if (!this.player.isInventoryFull()) {
                    this.player.addItem((UsableItem)this.stock.get(slot));
                    this.setMessage("[ ITEM BOUGHT ]");
                    this.player.subPoints(this.stock.get(slot).getPrice());
                } else {
                    this.setMessage("[ CARGO BAY FULL ]");
                }
            }
        } else {
            this.setMessage("[ NOT ENOUGH CURRENCY ]");
        }

        this.setShowMessages(true);
    }
}
