package paddles.abstractfactory;

import paddles.Paddle;
import paddles.factory.PaddleType;
import player.SelectedPlayer;

public abstract class AbstractPaddleFactory {
    public abstract Paddle createPaddle(PaddleType paddleType, SelectedPlayer player);
}
