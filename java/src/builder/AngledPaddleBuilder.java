package builder;

import factory.paddle.Paddle;
import paddles.AngledPaddle;

public class AngledPaddleBuilder extends PaddleBuilder {

    private int angle = 45;

    public AngledPaddleBuilder setAngle(int angle) {
        this.angle = angle;
        return this;
    }

    @Override
    public Paddle build() {
        return new AngledPaddle(player, speed, angle, color);
    }
}
