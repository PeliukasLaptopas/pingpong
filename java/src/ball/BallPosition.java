package ball;

import com.google.gson.Gson;

public class BallPosition {
    private double x;
    private double y;

    public BallPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public String toJson() {
        return new Gson().toJson(this);
    }
}
