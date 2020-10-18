package factory;

import player.SelectedPlayer;
import paddles.SimplePaddle;

public class PaddleFactory {

    private int defaultSpeed = 10;

    public Paddle createPaddle(PaddleType paddleType, SelectedPlayer player) {
        switch (paddleType) {
            case SIMPLE:
                return new SimplePaddle(player, defaultSpeed);
            default:
                return null;
        }
    }
}
