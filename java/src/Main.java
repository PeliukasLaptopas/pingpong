import api.PingPongSocketClient;

import java.net.URI;
import java.util.Scanner;

public class Main {
    private static final String URL = "wss://f523e9310ac4.ngrok.io";
    private static PingPongSocketClient client = null;

    public static void main(String[] args){
//        new Game(); //create a new game object
        createConnection();
        // Read messages from the console and send them to the server
        Scanner scanner = new Scanner(System.in);
        while(true) {
            String input = scanner.nextLine();
            if(client != null && client.getConnection().isOpen()) {
                client.send(input);
                System.out.println("Sent: " + input);
            }
        }
    }

    private static void createConnection() {
        client = new PingPongSocketClient(URI.create(URL));
        client.connect();
    }
}
