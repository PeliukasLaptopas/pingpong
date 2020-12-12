package chain_of_responsibility;

public class WarnLogger extends Logger {

    WarnLogger() {
        this.level = Logger.WARN;
    }

    @Override
    protected void writeMessage(String message) {
        System.out.println("::Warn:: " + message);
    }
}
