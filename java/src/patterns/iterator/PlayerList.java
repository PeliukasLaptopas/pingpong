package patterns.iterator;

import player.Player;

import java.util.List;

public class PlayerList implements Aggregator<Player> {

    private List<Player> players;

    public PlayerList(List<Player> players) {
        this.players = players;
    }

    @Override
    public Iterator<Player> getIterator() {
        return new ListIterator<>(players);
    }
}
