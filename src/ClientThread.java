//import pour la classe
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
class ClientThread implements Runnable{
    private String[] Requests;
    private int id;

    public ClientThread(int id,String[] Requests){
        this.Requests = Requests;
        this.id = id;
    }

    // Arnaud Desktop : DESKTOP-G189FND , LAPTOP :  LAPTOP-I9J1EU77
    public void run(){
        try {
            NewClient client = new NewClient(this.id, this.Requests);
            client.connect("DESKTOP-G189FND", 1234);
        }
        catch (IOException e){
            System.err.println("I/O Exception error");
        }
    }
}