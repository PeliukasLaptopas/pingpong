package chain_of_responsibility;

public class InfoLogger extends Logger {

    InfoLogger() {
        this.level = Logger.INFO;
    }

    @Override
    protected void writeMessage(String message) {
        System.out.println("::Info:: " + message);
    }
}
