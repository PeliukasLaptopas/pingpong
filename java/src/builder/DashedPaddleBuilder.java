package builder;

import factory.Paddle;
import paddles.DashedPaddle;
import paddles.SimplePaddle;

public class DashedPaddleBuilder extends PaddleBuilder {

    private int dashCount = 2;

    public PaddleBuilder setDashCount(int dashCount) {
        this.dashCount = dashCount;
        return this;
    }

    @Override
    public Paddle build() {
        return new DashedPaddle(player, speed, dashCount);
    }
}
