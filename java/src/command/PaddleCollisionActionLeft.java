package command;

import ball.Ball;
import paddles.Paddle;

public class PaddleCollisionActionLeft implements Action {

    public String name;
    public Paddle paddle;
    public Ball ball;

    public PaddleCollisionActionLeft(Paddle _paddle, Ball _ball, String _name) {
        name = _name;
        paddle = _paddle;
        ball = _ball;
    }

    @Override
    public void execute() {
//        System.out.println("Left collision");

        paddle.doCollision(ball);
    }

    @Override
    public void undo() {

    }

    @Override
    public String getName() {
        return this.name;
    }

}