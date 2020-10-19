package ball;


public class MediumBall extends Ball {

    private int size = 10;

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public int getSpeed() {
        return 2;
    }

    public MediumBall() {
        super();
    }
}
