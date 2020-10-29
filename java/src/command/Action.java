package command;

public interface Action {

    void execute();

    void undo();

    String getName();
}