import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyServer {
    public static String[][] data;  // The data is stored in a double array of String.
    public static int dataSize;
    public static int portNumber = 1234;  //1234
    public static int nbThreads = 30;

    public static void main (String[] args) throws FileNotFoundException {
        String file = "dbdata.txt"; // Name of the file
        // To read the file
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        BufferedReader counter = new BufferedReader(new FileReader(file));
        try{
            ServerSocket serverSocket = new ServerSocket(portNumber,100);   // Here we define the ServerSocket and the backlog we want.
            System.out.println("serveur cree, ecoute sur le port "+portNumber);

            // Load the file and put it in an array data[N][2] where N = number of line and the second column contains the type.
            dataSize = 0;
            while (counter.readLine() != null) dataSize++;
            counter.close();
            data = new String[dataSize][2];
            String curLine;
            int i = 0;
            while ((curLine = bufferedReader.readLine()) != null){
                //process the line as required
                String [] splitString = curLine.split("@@@");
                data[i][0] = splitString[0];
                data[i][1] = splitString[1];
                i++;
            }
            bufferedReader.close();

            // We use an ExecutorService to specify the number of threads running in parallel.
            ExecutorService executor = Executors.newFixedThreadPool(nbThreads); // The number of threads.
            while(true){
                //We wait for the clients to connect and accept them.
                Socket clientSocket = serverSocket.accept();
                System.out.println("client connecte");
                Runnable worker = new TaskThread(clientSocket);
                executor.execute(worker);
            }
        }
        catch(IOException e){
            System.out.println("this port is already taken or the client disconnected");
        }
    }
}

