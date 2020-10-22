package paddles.builder;

import paddles.Paddle;
import player.SelectedPlayer;

import java.awt.*;

public abstract class PaddleBuilder {
    public SelectedPlayer player;
    public int speed;
    public Color color = Color.WHITE;

    public PaddleBuilder setColor(Color color) {
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
