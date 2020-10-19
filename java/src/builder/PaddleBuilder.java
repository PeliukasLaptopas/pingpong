package builder;

import factory.Paddle;
import player.SelectedPlayer;

public abstract class PaddleBuilder {
    public SelectedPlayer player;
    public int speed;

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
