package strategy;

//Context context = new Context(new OperationAdd());
//System.out.println("10 + 5 = " + context.executeStrategy(10, 5));
public class Context {
    private Strategy strategy;

    public Context(Strategy strategy){
        this.strategy = strategy;
    }

    public int executeStrategy(int num1, int num2){
        return strategy.doOperation(num1, num2);
    }
}