package chain_of_responsibility;

public abstract class Logger {
    public static int VERBOSE = 1;
    public static int DEBUG = 2;
    public static int INFO = 3;
    public static int WARN = 4;
    public static int ERROR = 5;

    private Logger nextLogger;
    protected int level;

    public void setNextChain(Logger logger) {
        this.nextLogger = logger;
    }

    public static void log(int level, String message) {
        getLoggers().logMessage(level, message);
    }

    protected void logMessage(int level, String message) {
        if (this.level <= level) {
            writeMessage(message);
        }
        if (nextLogger != null) {
            nextLogger.logMessage(level, message);
        }
    }

    protected abstract void writeMessage(String message);

    private static Logger getLoggers() {
        Logger errorLogger = new ErrorLogger();
        Logger warnLogger = new WarnLogger();
        Logger infoLogger = new InfoLogger();
        Logger debugLogger = new DebugLogger();
        Logger verboseLogger = new VerboseLogger();

        errorLogger.setNextChain(warnLogger);
        warnLogger.setNextChain(infoLogger);
        infoLogger.setNextChain(debugLogger);
        debugLogger.setNextChain(verboseLogger);

        return errorLogger;
    }
}
