package paddles.builder;

import paddles.Paddle;
import paddles.DashedPaddle;

public class DashedPaddleBuilder extends PaddleBuilder {

    private int dashCount = 2;

    public DashedPaddleBuilder setDashCount(int dashCount) {
        this.dashCount = dashCount;
        return this;
    }

    @Override
    public Paddle build() {
        return new DashedPaddle(player, speed, dashCount, color);
    }
}
