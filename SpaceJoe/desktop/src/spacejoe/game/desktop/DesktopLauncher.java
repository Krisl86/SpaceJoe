package spacejoe.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import spacejoe.game.SpaceJoeGame;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "Space Joe";
        config.width = 480;
        config.height = 720;
        config.resizable = false;
        new LwjglApplication(new SpaceJoeGame(), config);

    }
}
