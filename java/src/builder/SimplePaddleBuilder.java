package builder;

import factory.Paddle;
import paddles.SimplePaddle;

public class SimplePaddleBuilder extends PaddleBuilder {

    @Override
    public Paddle build() {
        return new SimplePaddle(player, speed);
    }
}
