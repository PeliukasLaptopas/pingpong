package patterns.iterator;

import player.Player;

public class PlayerArray implements Aggregator<Player> {

    private Player[] players;

    public PlayerArray(Player[] players) {
        this.players = players;
    }

    @Override
    public Iterator<Player> getIterator() {
        return new ArrayIterator<>(players);
    }
}
