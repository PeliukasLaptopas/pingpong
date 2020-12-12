package paddles.builder;

import bridge.ColorBridge;
import bridge.White;
import paddles.Paddle;
import player.SelectedPlayer;

public abstract class PaddleBuilder {
    public SelectedPlayer player;
    public int speed;
    public ColorBridge color = new White();

    public PaddleBuilder setColor(ColorBridge color) {
        this.color = color;
        return this;
    }

    public PaddleBuilder setSelectedPlayer(SelectedPlayer sp) {
        player = sp;
        return this;
    }

    public PaddleBuilder setSpeed(int newSpeed) {
        speed = newSpeed;
        return this;
    }

    public abstract Paddle build();
}
