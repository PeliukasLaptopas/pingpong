package visitor;

import ball.Ball;

import java.util.concurrent.ThreadLocalRandom;

public class RandomBallAngleVisitor implements BallAngleVisitor {

    @Override
    public double calculateAngle(Ball ball) {
        return ThreadLocalRandom.current().nextDouble(1, 180);
    }
}
