package patterns.proxy;

import patterns.template.ColoredPaddleTemplate;
import player.Player;
import player.SelectedPlayer;

import java.util.concurrent.TimeUnit;

public class CreatePlayer1Proxy {
    public boolean delayCreation = false;

    public CreatePlayer1Proxy(boolean delay) {
        delayCreation = delay;
    }

    public Player newPlayer() throws InterruptedException {
        if(delayCreation) {
            TimeUnit.SECONDS.sleep(3);
        }

        return new Player(
                true,
                new ColoredPaddleTemplate().createPaddleTemplate(SelectedPlayer.PLAYER1),
                SelectedPlayer.PLAYER1
        );
    }
}
