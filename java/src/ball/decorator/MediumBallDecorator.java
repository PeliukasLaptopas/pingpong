package ball.decorator;

import ball.Ball;

public class MediumBallDecorator extends BallDecorator {

    private final int MEDIUM_SIZE = 10;
    private final int MEDIUM_SPEED = 2;

    public MediumBallDecorator(Ball decoratedBall) {
        super(decoratedBall);
    }

    @Override
    public int getSize() {
        return MEDIUM_SIZE;
    }

    @Override
    public int getSpeed() {
        return MEDIUM_SPEED;
    }

    @Override
    public void makeSound() {
        System.out.println("Created medium ball");
    }
}
