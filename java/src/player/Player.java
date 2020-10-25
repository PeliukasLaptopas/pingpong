package player;

import paddles.Paddle;

public class Player implements Cloneable {
    private boolean isHost;
    private Paddle paddle;

    public Player(boolean isHost, Paddle paddle) {
        this.isHost = isHost;
        this.paddle = paddle;
    }

    public Paddle getPaddle() {
        return paddle;
    }

    public void setPaddle(Paddle paddle) {
        this.paddle = paddle;
    }

    public boolean isHost() {
        return isHost;
    }

    public void setHost(boolean host) {
        isHost = host;
    }

    public Player makeCopy() {
        try {
            return (Player) this.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return this;
        }
    }
}
