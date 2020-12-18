import java.io.*;
import java.net.*;
import java.util.List;

public class MyClient {
    public boolean GoodbyeSent = false;
    public boolean requestSent = false;
    public boolean serverStartedResponding = false;
    public boolean transmitionStarted = false;
    //attributs
    public int id;
    public String requests[];
    public long timersSent[];
    public long timersReceived[];


    //commande hostname     Damien : LAPTOP-TQFF0SRJ  Arnaud : LAPTOP-I9J1EU77
    public MyClient(int id, String[] Requests){
        this.id = id;
        this.requests = Requests;
        this.timersSent = new long[requests.length + 1]; //+1 pour le goodbye
        this.timersReceived = new long[requests.length + 1];
    }

    public void connect(String hostName, int portNumber) throws IOException {

        try (
                Socket kkSocket = new Socket(hostName, portNumber);
                PrintWriter out = new PrintWriter(kkSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(kkSocket.getInputStream()));
        ) {
            //BufferedReader stdIn =
                    //new BufferedReader(new InputStreamReader(System.in));
            String fromServer;
            String fromUser;
            int i = 0;
            int j = 0;
            while ((fromServer = in.readLine()) != null || i < this.requests.length) {
                if(!fromServer.equals("")){
                    //System.out.println("Server: " + fromServer);
                    serverStartedResponding = true;
                }
                if (fromServer.equals("Bye.")) {
                    timersReceived[i] = System.currentTimeMillis();
                    for(int k = 0; k < timersSent.length; k++){
                        long delay = timersReceived[k] - timersSent[k];
                        System.out.println("time to receive answer for request " +  k + " for Client " + id +
                                " : " + delay);
                    }
                    break;
                }
                if(fromServer.equals("") && transmitionStarted && requestSent && serverStartedResponding){
                    timersReceived[j] = System.currentTimeMillis() - timersReceived[j];
                    j++;
                    //System.out.println("Client " + this.id + " : answer received\n");
                    requestSent = false;
                    serverStartedResponding = false;
                }
                if(fromServer.equals("") || !transmitionStarted){
                    //le client entre une requête
                    if(GoodbyeSent){
                        fromUser = null;
                    }
                    else if(i >= this.requests.length && !GoodbyeSent){
                        fromUser = "Goodbye";
                        GoodbyeSent = true;
                        requestSent = true;
                        serverStartedResponding = false;
                        timersSent[i] = System.currentTimeMillis();
                    }
                    else{
                        fromUser = this.requests[i];
                        i++;
                        requestSent = true;
                        timersSent[i-1] = System.currentTimeMillis();
                    }
                    if (fromUser != null) {
                        //on a commencé à parler au serveur
                        transmitionStarted = true;
                        //System.out.println("Client " + this.id + " :" + fromUser);
                        out.println(fromUser);
                    }
                }
            }
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                    hostName);
            System.exit(1);
        }
    }
}