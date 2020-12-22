import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class NewClient {
    public boolean GoodbyeSent = false;
    public boolean requestSent = false;
    //attributs
    public int id;
    public String requests[];
    public long timersSent[];
    public long timersReceived[];

    //commande hostname     Damien : LAPTOP-TQFF0SRJ  Arnaud : LAPTOP-I9J1EU77
    public NewClient(int id, String[] Requests){
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

            while ((fromServer = in.readLine())!=null) {
                //Print What the Server sent
                System.out.println("fromServer :" + " to Client " + this.id + " "  +  fromServer);

                // We exit the connect if the server sends Bye.
                if (fromServer.equals("Bye.")) {
                    timersReceived[j] = System.currentTimeMillis();
                    for(int k = 0; k < timersSent.length; k++){
                        long delay = timersReceived[k] - timersSent[k];
                        MyServer.delays.add(delay);
                        //System.out.println("time sent request " + k +"  " + timersSent[k] + " for Client " + id );
                        //System.out.println("time received request " + k +"  "+ timersReceived[k] + " for Client " + id);
                        System.out.println("time to receive answer for request " +  k + " for Client " + id +
                                " : " + delay);
                    }
                    break;
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
                    System.out.println("Client " + this.id + " : answer received\n");
                    j++;
                    requestSent = false;

                    //we can therefore send the next request
                    Thread.sleep(1000);
                    timersSent[i] = System.currentTimeMillis();
                    // If we are at the end of the requests we send goodbye
                    if(i>= this.requests.length){
                        fromUser = "Goodbye";
                        GoodbyeSent = true;
                    }
                    // If not we send the next request
                    else{
                        fromUser = this.requests[i];
                    }
                    i++;
                    out.println(fromUser);
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
        } catch (InterruptedException e) {
            System.err.println("error");
        }
    }
}
