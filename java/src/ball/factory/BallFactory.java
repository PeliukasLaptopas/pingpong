package ball.factory;

import ball.Ball;
import ball.decorator.LargeBallDecorator;
import ball.decorator.MediumBallDecorator;
import ball.decorator.SmallBallDecorator;

public class BallFactory {

    public Ball createBall(BallType ballType) {
        Ball ball = new Ball() {
            @Override
            public int getSize() {
                return 0;
            }

            @Override
            public int getSpeed() {
                return 0;
            }
        };
        switch (ballType) {
            case SMALL:
                return new SmallBallDecorator(ball);
            case MEDIUM:
                return new MediumBallDecorator(ball);
            case LARGE:
                return new LargeBallDecorator(ball);
            default:
                return null;
        }
    }
}
