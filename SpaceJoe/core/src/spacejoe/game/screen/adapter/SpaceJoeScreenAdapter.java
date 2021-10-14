package spacejoe.game.screen.adapter;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Pozmenena implementacia ScreenAdaptera - pridane pop-up spravy.
 */
public class SpaceJoeScreenAdapter extends ScreenAdapter {
    private final SpriteBatch batch;
    private final BitmapFont font;
    private float deltaTimer;
    private boolean showMessages;
    private String message;

    public SpaceJoeScreenAdapter(SpriteBatch batch, BitmapFont font, float deltaTimer) {
        super();
        this.batch = batch;
        this.font = font;
        this.deltaTimer = deltaTimer;
        this.showMessages = false;
        this.message = "";
    }

    public SpriteBatch getBatch() {
        return batch;
    }

    public BitmapFont getFont() {
        return font;
    }


    public boolean getShowMessages() {
        return showMessages;
    }

    protected void setShowMessages(boolean showMessages) {
        this.showMessages = showMessages;
    }


    protected void setMessage(String message) {
        this.message = message;
    }

    /**
     * Vyskakovacie spravy ktore po case zmiznu.
     * @param delta
     * @param scale velkost fontu sprav
     * @param duration dlzka trvania zobrazenia spravy
     * @param x x pozicia
     * @param y y pozicia
     */
    public void flashMessage(float delta, float scale, float duration, int x, int y) {
        float originalScaleX = this.font.getScaleX();
        float originalScaleY = this.font.getScaleY();
        this.font.getData().setScale(scale);
        this.deltaTimer += delta;
        if (this.deltaTimer < duration) {
            this.font.draw(this.batch, this.message, x, y);
        } else {
            this.deltaTimer = 0;
            this.showMessages = false;
        }

        this.font.getData().setScale(originalScaleX, originalScaleY);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void hide() {
        super.hide();
    }

    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void resume() {
        super.resume();
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
