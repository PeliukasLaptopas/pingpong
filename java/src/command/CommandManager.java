package command;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CommandManager {
    private static CommandManager instance = null;
    private QueueStack<List<Action>> queueStackNormal;
    private QueueStack<List<Action>> queueStackReverse;

    private List<String> actionHistory;

    public static CommandManager getInstance(){
        if(instance != null)
            return instance;
        return new CommandManager();
    }

    private CommandManager() {
        queueStackNormal = new QueueStack<>();
        queueStackReverse = new QueueStack<>();
        actionHistory = new ArrayList<>();
    }

    public void execute(List<Action> actionList){

        actionList.forEach(Action::execute);
        queueStackNormal.push(actionList);
        actionList.forEach(a -> actionHistory.add(a.getName()));
    }

    void undo() {
        Optional<List<Action>> optionalActions = queueStackNormal.pop();
        optionalActions.ifPresent(aList -> {
            aList.forEach(Action::undo);
            queueStackReverse.push(aList);
            aList.forEach(a -> actionHistory.add(a.getName() + " - undo"));
        });
    }

    void redo() {
        Optional<List<Action>> optionalActions = queueStackReverse.pop();
        optionalActions.ifPresent(aList -> {
            aList.forEach(Action::execute);
            queueStackNormal.push(aList);
            aList.forEach(a -> actionHistory.add(a.getName() + " - redo"));
        });
    }

    void clearNormal() {
        queueStackNormal.clear();
    }

    void clearReverse() {
        queueStackReverse.clear();
    }

    List<String> getActionHistory() {
        return actionHistory;
    }

    //How to use something like this pattern:
    /*
    CommandManager manager = CommandManager.getInstance();
    List<Action> actionList = new ArrayList<>();
    actionList.add(new HighScoreAdderAction("Action 1"));

    print("actions")
    manager.execute(actionList);

    manager.undo();
    manager.redo();

    manager.clearNormal();
    manager.undo();
    manager.redo();

    print("history:")
    print(manager.getActionHistory().toString());
    */
}