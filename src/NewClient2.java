import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class NewClient2 {
    public boolean GoodbyeSent = false;
    public boolean requestSent = false;
    //attributs
    public int id;
    public String requests[];
    public long timersSent[];
    public long timersReceived[];

    //commande hostname     Damien : LAPTOP-TQFF0SRJ  Arnaud : LAPTOP-I9J1EU77
    public NewClient2(int id, String[] Requests){
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
            int numberofnewline = 0;
            int i = 0;
            int j = 0;


            fromUser = this.requests[i];
            timersSent[i] = System.currentTimeMillis();
            i++;
            out.println(fromUser);

            long previoustime = timersSent[i-1];
            long actualtime;

            while ((fromServer = in.readLine())!=null) {
                //Print What the Server sent
                System.out.println("fromServer :" + " to Client " + this.id + " "  +  fromServer);


                // We exit the connect if the server sends Bye.
                if (fromServer.equals("Bye.")) {
                    timersReceived[j] = System.currentTimeMillis();
                    for(int k = 0; k < timersSent.length; k++){
                        long delay = timersReceived[k] - timersSent[k];
                        //System.out.println("time sent request " + k +"  " + timersSent[k] + " for Client " + id );
                        //System.out.println("time received request " + k +"  "+ timersReceived[k] + " for Client " + id);
                        System.out.println("time to receive answer for request " +  k + " for Client " + id +
                                " : " + delay);
                    }
                    break;
                }

                double random = Math.random();
                actualtime = System.currentTimeMillis();

                //Si la probabilité est plus grande et qu'on a attendu le temps suffisant on envoie la nouvelle requête
                if(random>0.2 && ((actualtime-previoustime)>= 100) && (i<this.requests.length)){
                    timersSent[i] = System.currentTimeMillis();
                    previoustime = timersSent[i];

                    fromUser = this.requests[i];
                    i++;
                    out.println(fromUser);
                }

                //Increment numberofnewline to check if it's the end of the request,back to 0 if it is a line of the request
                if(fromServer.equals("")){
                    numberofnewline++;
                }
                else{
                    numberofnewline = 0;
                }

                //If the number of new line is 2 it means we are at the end of the request
                if(fromServer.equals("") && numberofnewline >= 2){
                    if(GoodbyeSent){
                        continue;
                    }
                    timersReceived[j] = System.currentTimeMillis();
                    //System.out.println("Client " + this.id + " : answer received\n");
                    j++;
                    requestSent = false;

                    // If we received all requests we send Goodbye
                    if(j>= this.requests.length){
                        timersSent[i] = System.currentTimeMillis();
                        fromUser = "Goodbye";
                        GoodbyeSent = true;
                        i++;
                        out.println(fromUser);
                    }

                    continue;
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
