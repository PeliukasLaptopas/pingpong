package player;

import paddles.Paddle;

public class Player implements Cloneable {
    private boolean isHost;
    private Paddle paddle;
    private SelectedPlayer selectedPlayer;

    public Player(boolean isHost, Paddle paddle, SelectedPlayer selectedPlayer) {
        this.isHost = isHost;
        this.paddle = paddle;
        this.selectedPlayer = selectedPlayer;
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

    public SelectedPlayer getSelectedPlayer() {
        return selectedPlayer;
    }

    public void setSelectedPlayer(SelectedPlayer selectedPlayer) {
        this.selectedPlayer = selectedPlayer;
    }
}
