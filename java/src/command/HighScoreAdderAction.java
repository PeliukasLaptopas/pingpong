package command;

public class HighScoreAdderAction implements Action {

    private String name;

    public HighScoreAdderAction(String name) {
        this.name = name;
    }

    @Override
    public void execute() {
        System.out.println("Add + points");
    }

    @Override
    public void undo() {
        System.out.println("Remove one point");
    }

    @Override
    public String getName() {
        return this.name;
    }


}