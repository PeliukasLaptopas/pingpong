package api;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

public class PingPongSocketClient extends WebSocketClient {

    public PingPongSocketClient(URI serverUri) {
        super(serverUri);
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        send("Gustas duchas");
    }

    @Override
    public void onMessage(String s) {
        System.out.println("Response: " + s);
    }

    @Override
    public void onClose(int i, String s, boolean b) {

    }

    @Override
    public void onError(Exception e) {

    }
}
