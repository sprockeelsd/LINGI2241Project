import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyServer {
    public static String[][] data;  //les données du fichier
    public static int dataSize;
    public static int portNumber = 1234;  //1234

    public static void main (String[] args) throws FileNotFoundException {
        String file = "dbdata.txt"; //nom du fichier
        //pour lire le fichier
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        BufferedReader counter = new BufferedReader(new FileReader(file));
        try{
            ServerSocket serverSocket = new ServerSocket(portNumber,100);   //crée un nouveau serveur
            System.out.println("serveur cree, ecoute sur le port "+portNumber);

            //charger le fichier et le mettre dans un tableau data[N][2] ou N = nb de lignes : une colonne pour type et une pour sentence
            //compte le nombre de lignes dans le fichier
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

            ExecutorService executor = Executors.newFixedThreadPool(100); // Le nombre maximum de threads
            while(true){
                //On attend qu'un client veuille se connecter
                Socket clientSocket = serverSocket.accept();//retourne un socket lié au port portNumber et avec le n. de port et l'adresse du client
                System.out.println("client connecte");
                Runnable worker = new TaskThread(clientSocket);
                executor.execute(worker);
            }

            //System.out.println("fin de try");
        }
        catch(IOException e){
            System.out.println("this port is already taken or the client disconnected");
        }
    }
}

