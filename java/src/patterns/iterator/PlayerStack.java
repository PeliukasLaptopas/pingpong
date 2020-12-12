package patterns.iterator;

import player.Player;

import java.util.Stack;

public class PlayerStack implements Aggregator<Player> {

    private Stack<Player> players;

    public PlayerStack(Stack<Player> players) {
        this.players = players;
    }

    @Override
    public Iterator<Player> getIterator() {
        return new StackIterator<>(players);
    }
}
