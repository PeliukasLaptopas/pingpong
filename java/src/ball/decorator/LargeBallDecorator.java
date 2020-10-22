package ball.decorator;

import ball.Ball;

public class LargeBallDecorator extends BallDecorator {

    private final int LARGE_SIZE = 20;
    private final int LARGE_SPEED = 1;

    public LargeBallDecorator(Ball decoratedBall) {
        super(decoratedBall);
    }

    @Override
    public int getSize() {
        return LARGE_SIZE;
    }

    @Override
    public int getSpeed() {
        return LARGE_SPEED;
    }
}
