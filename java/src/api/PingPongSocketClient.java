package api;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.util.function.Consumer;

public class PingPongSocketClient extends WebSocketClient {

    private Consumer<String> onMessageReceived;

    public PingPongSocketClient(URI serverUri, Consumer<String> onMessageReceived) {
        super(serverUri);
        this.onMessageReceived = onMessageReceived;
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        send("Connected");
    }

    @Override
    public void onMessage(String s) {
        System.out.println("Response: " + s);
        onMessageReceived.accept(s);
    }

    @Override
    public void onClose(int i, String s, boolean b) {

    }

    @Override
    public void onError(Exception e) {

    }
}
