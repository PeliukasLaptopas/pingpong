package factory;

import paddles.DashedPaddle;
import player.SelectedPlayer;
import paddles.SimplePaddle;

public class PaddleFactory {

    private int defaultSpeed = 10;

    public Paddle createPaddle(PaddleType paddleType, SelectedPlayer player) {
        switch (paddleType) {
            case SIMPLE:
                return new SimplePaddle(player, defaultSpeed);
            case DASHED:
                return new DashedPaddle(player, defaultSpeed, 2);
            default:
                return null;
        }
    }
}
