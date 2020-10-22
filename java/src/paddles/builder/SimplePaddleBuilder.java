package paddles.builder;

import paddles.Paddle;
import paddles.SimplePaddle;

public class SimplePaddleBuilder extends PaddleBuilder {

    @Override
    public Paddle build() {
        return new SimplePaddle(player, speed, color);
    }
}
