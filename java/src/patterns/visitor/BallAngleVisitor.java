package patterns.visitor;

import ball.Ball;

public interface BallAngleVisitor {
    public double calculateAngle(Ball ball);
}
