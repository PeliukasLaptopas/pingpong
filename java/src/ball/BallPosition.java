package ball;

import com.google.gson.Gson;

public class BallPosition {
    private int x;
    private int y;

    public BallPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String toJson() {
        return new Gson().toJson(this);
    }
}
