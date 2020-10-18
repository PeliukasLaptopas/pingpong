package factory;

import paddles.AngledPaddle;
import paddles.DashedPaddle;
import paddles.SimplePaddle;
import player.SelectedPlayer;

public class PaddleBuilder {
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

    public Paddle createPaddle(PaddleType paddleType, int dashCount, int defaultAngle) {
        switch (paddleType) {
            case SIMPLE:
                return new SimplePaddle(player, speed);
            case DASHED:
                return new DashedPaddle(player, speed, dashCount);
            case ANGLED:
                return new AngledPaddle(player, speed, defaultAngle);
            default:
                return null;
        }
    }
}
