package abstract_factory;

import factory.paddle.Paddle;
import factory.paddle.PaddleType;
import player.SelectedPlayer;

public abstract class AbstractPaddleFactory {
    public abstract Paddle createPaddle(PaddleType paddleType, SelectedPlayer player);
}
