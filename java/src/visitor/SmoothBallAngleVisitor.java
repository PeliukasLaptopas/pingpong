package visitor;

import ball.Ball;

import java.util.concurrent.ThreadLocalRandom;

public class SmoothBallAngleVisitor implements BallAngleVisitor {

    @Override
    public double calculateAngle(Ball ball) {
        return ThreadLocalRandom.current().nextDouble(ball.angle - 10, ball.angle + 10);

    }
}
