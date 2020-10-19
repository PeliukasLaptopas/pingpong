package factory.ball;

import ball.Ball;
import ball.LargeBall;
import ball.MediumBall;
import ball.SmallBall;

public class BallFactory {

    public Ball createBall(BallType ballType) {
        switch (ballType) {
            case SMALL:
                return new SmallBall();
            case MEDIUM:
                return new MediumBall();
            case LARGE:
                return new LargeBall();
            default:
                return null;
        }
    }
}
