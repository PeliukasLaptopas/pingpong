package chain_of_responsibility;

public class DebugLogger extends Logger {

    DebugLogger() {
        this.level = Logger.DEBUG;
    }

    @Override
    protected void writeMessage(String message) {
        System.out.println("::Debug:: " + message);
    }
}
