package spacejoe.game.gameManager;

import spacejoe.game.gameobject.GameObject;

/**
 * Zistovanie kolizii medzi 2 objektami (typ GameObject).
 */
public class CollisionManager {

    /**
     * Zistuje koliziu medzi dvoma objektmi
     * @param objectOne prvy objekt
     * @param objectTwo druhy objekt
     * @return vracia true ak doslo ku kolizii, false ak nedoslo
     */
    public static boolean isColliding(GameObject objectOne, GameObject objectTwo) {
        boolean xAxisCollision = false;
        boolean yAxisCollision = false;

        // zistovanie ktory z objektov je sirsi/vyssi
        GameObject wide = objectTwo.getWidth() > objectOne.getWidth() ? objectTwo : objectOne;
        GameObject narrow = objectTwo.getWidth() <= objectOne.getWidth() ? objectTwo : objectOne;
        GameObject tall = objectTwo.getHeight() > objectOne.getHeight() ? objectTwo : objectOne;
        GameObject low = objectTwo.getHeight() <= objectOne.getHeight() ? objectTwo : objectOne;

        // zistovanie kolizie na x osi
        if (narrow.getX() >= wide.getX() && narrow.getX() <= wide.getX() + wide.getWidth() ||
                narrow.getX() + narrow.getWidth() >= wide.getX() && narrow.getX() + narrow.getWidth() <= wide.getX() + wide.getWidth()) {
            xAxisCollision = true;
        }

        // zistovanie kolizie na y osi
        if (low.getY() >= tall.getY() && low.getY() <= tall.getY() + tall.getHeight() ||
                low.getY() + low.getHeight() >= tall.getY() && low.getY() + low.getHeight() <= tall.getY() + tall.getHeight()) {
            yAxisCollision = true;
        }

        return xAxisCollision && yAxisCollision;
    }
}
