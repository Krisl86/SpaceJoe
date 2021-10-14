package spacejoe.game.gameManager;

import spacejoe.game.enumerator.ProjectileType;
import spacejoe.game.gameobject.player.Player;
import spacejoe.game.item.IItem;
import spacejoe.game.item.ItemManager;
import spacejoe.game.item.UsableItem;
import spacejoe.game.item.Weapon;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class GameSaveManager {

    /**
     * Ukladanie hracovych statov a invetara.
     * @param player hrac
     * @return true ak sa ukladanie podarilo, inak false
     */
    public static boolean save(Player player) {
        try {
            File saveFile = new File("/home/kristian/MEGAsync/Informatika2-Semestralka/SpaceJoe/core/src/spacejoe/game/savegame.txt");
            FileWriter writer = new FileWriter(saveFile);

            try {
                saveFile.createNewFile();
                writer.write(player.getPoints() + "\n");
                writer.write(player.getTotalScore() + "\n");

                writer.write(player.getWeapon().getName() + "\n");
                writer.write(player.getWeapon().getDescription() + "\n");
                writer.write(player.getWeapon().getPrice() + "\n");
                writer.write(player.getWeapon().getHeatLimit() + "\n");
                writer.write(player.getWeapon().getCooltime() + "\n");
                writer.write(player.getWeapon().getProjectileType().toString() + "\n");

                for (IItem item : player.getInventoryCopy()) {
                    writer.write(item.getName() + "\n");
                }

                writer.close();
                return true;
            } catch (IOException e) {
                writer.close();
                return false;
            }

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Nacitanie hracovych parametrov a inventara.
     * @param player hrac
     * @param itemManager manazer predmetov
     * @return true ak sa nacitanie podarilo, inak false
     */
    public static boolean load(Player player, ItemManager itemManager) {
        try {
            File saveFile = new File("/home/kristian/MEGAsync/Informatika2-Semestralka/SpaceJoe/core/src/spacejoe/game/savegame.txt");
            Scanner scanner = new Scanner(saveFile);
            player.setPoints(Integer.parseInt(scanner.nextLine()));
            player.setTotalScore(Integer.parseInt(scanner.nextLine()));

            String weaponName = scanner.nextLine();
            String weaponDescription = scanner.nextLine();
            int price = Integer.parseInt(scanner.nextLine());
            int heatLimit = Integer.parseInt(scanner.nextLine());
            float cooltime = Float.parseFloat(scanner.nextLine());
            ProjectileType projectileType = ProjectileType.stringToType(scanner.nextLine());

            player.setWeapon(new Weapon(weaponName, weaponDescription, price, heatLimit, cooltime, projectileType, player.getProjectileManager()));

            while (scanner.hasNextLine()) {
                player.addItem((UsableItem)itemManager.stringToItem(scanner.nextLine()));
            }

            scanner.close();
            return true;
        } catch (FileNotFoundException e) {
            return false;
        }
    }
}
