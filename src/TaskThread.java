import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;

class TaskThread implements Runnable
{
    private Socket clientSocket;

    public TaskThread(Socket clientSocket){
        this.clientSocket = clientSocket;
    }

    public void run()
    {
        try
        {
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
            String inputLine, outputLine;

            // Initiate conversation with client
            MyProtocol protocol = new MyProtocol();
            outputLine = protocol.processInput(null);
            out.println(outputLine);
            // Connection with the client has been established
            while ((inputLine = in.readLine()) != null) {
                if(inputLine.equals("")){
                    continue;
                }
                outputLine = protocol.processInput(inputLine);
                out.println(outputLine);
                if (outputLine.equals("Bye."))
                    break;
            }
        }

        catch(IOException e)
        {
            System.out.println("this port is already taken or a client disconnected");
        }
    }
}