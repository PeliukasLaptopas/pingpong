package builder;

import factory.Paddle;
import paddles.AngledPaddle;
import paddles.SimplePaddle;

public class AngledPaddleBuilder extends PaddleBuilder {

    private int angle = 45;

    public AngledPaddleBuilder setAngle(int angle) {
        this.angle = angle;
        return this;
    }

    @Override
    public Paddle build() {
        return new AngledPaddle(player, speed, angle);
    }
}
