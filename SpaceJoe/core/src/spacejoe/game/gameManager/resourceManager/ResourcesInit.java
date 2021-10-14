package spacejoe.game.gameManager.resourceManager;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

/**
 * Inicializacia textur a animacii.
 */
public class ResourcesInit {


    public static void initTextures(AssetManager assetManager) {
        assetManager.load("ship.png", Texture.class);
        assetManager.load("bg-back.png", Texture.class);
        assetManager.load("bg-front.png", Texture.class);
        assetManager.load("projectile-default.png", Texture.class);
        assetManager.load("asteroid.png", Texture.class);
        assetManager.load("explosion-start.png", Texture.class);
        assetManager.load("explosion-mid.png", Texture.class);
        assetManager.load("explosion-end.png", Texture.class);
        assetManager.load("game-over.png", Texture.class);
        assetManager.load("shop.png", Texture.class);
        assetManager.load("shield.png", Texture.class);

        assetManager.finishLoading();
    }

    public static void initAnimations(AssetManager assetManager, AnimationManager animationManager) {
        animationManager.createNewAnimation("explosion-ani", new Texture[] {assetManager.get("explosion-start.png"),
                                                                                assetManager.get("explosion-mid.png"),
                                                                                assetManager.get("explosion-end.png")});

    }
}
