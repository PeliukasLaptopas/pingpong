package patterns.visitor;

import ball.Ball;

public class SameBallAngleVisitor implements BallAngleVisitor{

    @Override
    public double calculateAngle(Ball ball) {
        return ball.angle;
    }
}
