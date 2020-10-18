package player;

import pong.Paddle;

public class Player {
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
}
