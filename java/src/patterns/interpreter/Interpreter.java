package patterns.interpreter;

public class Interpreter {

    // Expressions
    // Ball expressions
    private Expression ballExpression = new TerminalExpression("ball");
    private Expression largeExpression = new TerminalExpression("large");
    private Expression smallExpression = new TerminalExpression("small");
    private Expression mediumExpression = new TerminalExpression("medium");
    private Expression largeBallExpression = new AndExpression(ballExpression, largeExpression);
    private Expression mediumBallExpression = new AndExpression(ballExpression, mediumExpression);
    private Expression smallBallExpression = new AndExpression(ballExpression, smallExpression);
    // Paddle expressions
    private Expression paddleExpression = new TerminalExpression("paddle");
    private Expression angledExpression = new TerminalExpression("angled");
    private Expression simpleExpression = new TerminalExpression("simple");
    private Expression paddleAngledExpression = new AndExpression(paddleExpression, angledExpression);
    private Expression paddleSimpleExpression = new AndExpression(paddleExpression, simpleExpression);

    public InterpretedAction interpret(String context) {
        if(smallBallExpression.interpret(context)) {
            return InterpretedAction.BALL_SMALL;
        } else if (mediumBallExpression.interpret(context)) {
            return InterpretedAction.BALL_MEDIUM;
        } else if (largeBallExpression.interpret(context)) {
            return InterpretedAction.BALL_LARGE;
        } else if (paddleAngledExpression.interpret(context)) {
            return InterpretedAction.PADDLE_ANGLED;
        } else if (paddleSimpleExpression.interpret(context)) {
            return InterpretedAction.PADDLE_SIMPLE;
        }
        return InterpretedAction.UNDEFINED;
    }
}
