package spacejoe.game.gameManager.resourceManager;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;

import java.util.HashMap;

/**
 * Sprava a vytvorenie animacii.
 */
public class AnimationManager {
    private HashMap<String, Animation> animationHashMap;

    public AnimationManager() {
        this.animationHashMap = new HashMap<>();
    }

    public Animation getAnimation(String key) {
        return this.animationHashMap.get(key);
    }

    /**
     * Vytvorenie animacie a pridanie do HashMapu
     * @param key kluc pre vyhladavanie aniamcie v HM
     */
    public void createNewAnimation(String key, Texture[] keyFrames) {
        this.animationHashMap.put(key, new Animation(0.35f, keyFrames));
    }
}
