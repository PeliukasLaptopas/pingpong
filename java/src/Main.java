import api.PingPongSocketClient;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import pong.Game;

import java.net.URI;
import java.util.Scanner;

public class Main {
    private static final String URL = "wss://f523e9310ac4.ngrok.io";
    private static PingPongSocketClient client = null;

    public static void main(String[] args){
        JSONObject obj = new JSONObject();
        obj.put("x", 100);
        obj.put("y", 100);

//        JSONArray list = new JSONArray();
//        list.add("msg 1");
//        list.add("msg 2");
//        list.add("msg 3");
//
//        obj.put("messages", list);


        System.out.println(obj);
        System.out.println(obj.toJSONString());


        new Game(); //create a new game object

    }
}
