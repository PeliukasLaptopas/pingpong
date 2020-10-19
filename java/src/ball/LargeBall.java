package ball;


public class LargeBall extends Ball {

    private int size = 20;

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public int getSpeed() {
        return 1;
    }

    public LargeBall() {
        super();
    }
}
