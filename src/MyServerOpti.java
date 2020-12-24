import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyServerOpti {
    public static HashMap<String, ArrayList<String>> data_opti; // The data will be stored in a Hashmap with the key being a String containing the type.
    public static int portNumber = 1234;  //1234
    public static int nbThreads = 30;

    public static void main (String[] args) throws FileNotFoundException {
        String file = "dbdata.txt"; //Name of the file
        // To read the file
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        try{
            ServerSocket serverSocket = new ServerSocket(portNumber);   //crée un nouveau serveur
            System.out.println("serveur cree, ecoute sur le port "+portNumber);

            // We load the file and we start by putting it in a ArrayList of ArrayList<String> we then convert it to the HashMap we wanted.
            data_opti = new HashMap<String, ArrayList<String>>();
            String curLine2;

            // We create the ArrayList  of ArrayList then initialise the 6 ArrayList that are inside it.
            ArrayList<ArrayList<String>> List_of_strings = new ArrayList<ArrayList<String>>();
            for(int i =0;i<=5;i++){
                List_of_strings.add(new ArrayList<String>());
            }
            // Now we can fill the corresponding ArrayList (the one with the right type) .
            while ((curLine2 = bufferedReader.readLine()) != null){
                String [] splitString = curLine2.split("@@@");
                int type = Integer.parseInt(splitString[0]);
                List_of_strings.get(type).add(splitString[1]);
            }
            // Finally we associate the 6 ArrayList to the HashMap.
            for(int i =0;i<=5;i++){
                data_opti.put(((Integer)i).toString(),List_of_strings.get(i));
            }

            bufferedReader.close();

            ExecutorService executor = Executors.newFixedThreadPool(nbThreads); // The maximum number of threads.
            while(true){
                // We wait for the clients to connect and accept them
                Socket clientSocket = serverSocket.accept();
                System.out.println("client connecte");
                Runnable worker = new TaskThreadO(clientSocket);
                executor.execute(worker);
            }

            //System.out.println("fin de try");
        }
        catch(IOException e){
            System.out.println("this port is already taken or the client disconnected");
        }
    }
}
