package api;

import observer.Subject;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

public class PingPongSocketClient extends WebSocketClient {

    private Subject<String> subject = new Subject<>();

    public Subject<String> getSubject() {
        return subject;
    }

    public PingPongSocketClient(URI serverUri) {
        super(serverUri);
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        send("Connected");
    }

    @Override
    public void onMessage(String s) {
        System.out.println("Response: " + s);
        subject.setState(s);
    }

    @Override
    public void onClose(int i, String s, boolean b) {

    }

    @Override
    public void onError(Exception e) {

    }
}
