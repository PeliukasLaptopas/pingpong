import api.PingPongSocketClient;
import pong.Game;

import java.net.URI;

public class Main {
    private static final String URL = "wss://211cb73d95bd.ngrok.io";

    public static void main(String[] args){
        new Game(); //create a new game object
        createConnection();
    }

    private static void createConnection() {
        PingPongSocketClient pingPongSocketClient = new PingPongSocketClient(URI.create(URL));
        pingPongSocketClient.connect();
    }
}
