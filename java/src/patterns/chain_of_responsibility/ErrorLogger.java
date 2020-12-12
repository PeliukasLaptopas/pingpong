package patterns.chain_of_responsibility;

public class ErrorLogger extends Logger {

    ErrorLogger() {
        this.level = Logger.ERROR;
    }

    @Override
    protected void writeMessage(String message) {
        System.out.println("::Error:: " + message);
    }
}
