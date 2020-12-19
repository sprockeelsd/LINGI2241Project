import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;
import java.util.HashMap;

import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyServerOpti {
    public static String[][] data;  //les données du fichier
    public static HashMap<String, String[]> data_opti;
    public static int portNumber = 1234;  //1234

    public static void main (String[] args) throws FileNotFoundException {
        String file = "dbdata.txt"; //nom du fichier
        //pour lire le fichier
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        try{
            ServerSocket serverSocket = new ServerSocket(portNumber);   //crée un nouveau serveur
            System.out.println("serveur cree, ecoute sur le port "+portNumber);

            //charger le fichier et le mettre dans un tableau data[N][2] ou N = nb de lignes : une colonne pour type et une pour sentence
            //compte le nombre de lignes dans le fichier
            data_opti = new HashMap<String, String[]>();
            String curLine2;
            int[] indices= new int[6];
            while ((curLine2 = bufferedReader.readLine()) != null){
                String [] splitString = curLine2.split("@@@");
                int type = Integer.parseInt(splitString[0]);
                String[] previous_value = data_opti.get(splitString[0]);
                if(previous_value == null)  {
                    previous_value =  new String[100000];
                    previous_value[0] = splitString[1];
                }
                else{
                    previous_value[indices[type]] = splitString[1];
                }
                data_opti.put(splitString[0],previous_value);
                indices[type] = indices[type] + 1;
            }

            bufferedReader.close();

            ExecutorService executor = Executors.newFixedThreadPool(5); // Le nombre maximum de threads
            while(true){
                //On attend qu'un client veuille se connecter
                Socket clientSocket = serverSocket.accept();//retourne un socket lié au port portNumber et avec le n. de port et l'adresse du client
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