package ball.decorator;

import ball.Ball;

public abstract class BallDecorator extends Ball {

    protected Ball decoratedBall;

    public BallDecorator(Ball decoratedBall) {
        this.decoratedBall = decoratedBall;
    }
}
