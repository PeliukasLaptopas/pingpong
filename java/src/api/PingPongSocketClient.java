package api;

import patterns.chain_of_responsibility.Logger;
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
        Logger.log(Logger.INFO, "Connected server");
        send("Connected");
    }

    @Override
    public void onMessage(String s) {
        Logger.log(Logger.INFO, "Response: " + s);
        subject.setState(s);
    }

    @Override
    public void onClose(int i, String s, boolean b) {
        Logger.log(Logger.WARN, "Server closed");
    }

    @Override
    public void onError(Exception e) {
        Logger.log(Logger.ERROR, "Server error: " + e.getLocalizedMessage());
    }
}
