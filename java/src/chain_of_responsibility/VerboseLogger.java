package chain_of_responsibility;

public class VerboseLogger extends Logger {

    VerboseLogger() {
        this.level = Logger.VERBOSE;
    }

    @Override
    protected void writeMessage(String message) {
        System.out.println("::Verbose:: " + message);
    }
}
