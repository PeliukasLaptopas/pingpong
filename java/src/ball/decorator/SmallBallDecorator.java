package ball.decorator;

import ball.Ball;

public class SmallBallDecorator extends BallDecorator {

    private final int SMALL_SIZE = 6;
    private final int SMALL_SPEED = 3;

    public SmallBallDecorator(Ball decoratedBall) {
        super(decoratedBall);
    }

    @Override
    public int getSize() {
        return SMALL_SIZE;
    }

    @Override
    public int getSpeed() {
        return SMALL_SPEED;
    }

    @Override
    public void makeSound() {
        System.out.println("Created small ball");
    }
}
