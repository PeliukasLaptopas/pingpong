package ball;


public class SmallBall extends Ball {

    private int size = 5;

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public int getSpeed() {
        return 3;
    }

    public SmallBall() {
        super();
    }
}
