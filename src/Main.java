import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


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

    public void  run(){
        try {
            MyClient client = new MyClient(this.id, this.Requests);
            client.connect("LAPTOP-I9J1EU77", 1234);
        }
        catch (IOException e){
            System.err.println("I/O Exception error");
        }
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        //commande hostname     Damien : LAPTOP-TQFF0SRJ  Arnaud : LAPTOP-I9J1EU77
        int number_of_clients = 2; //Nombre max de threads clients.
        int number_of_requests = 3; //Nombre de requêtes par client.
        ExecutorService executor = Executors.newFixedThreadPool(number_of_clients);

        //Ici on fait les initialisations des requêtes qu'on veut que le client fasse.
        String[][] Requestss = new String[number_of_clients][number_of_requests];
        Requestss[0] = new String[]{"2;CONTENTS\n", "1;FORD OF CANADA\n", "1;CONTENTS\n"};
        Requestss[1] = new String[]{"1;CANADA POST\n", "1;LA GENDARMERIE ROYALE DU CANADA\n", "1;LA LOI LECTORALE DU CANADA\n"};

        //Ici on va boucler sur tous les clients pour les lancer.
        for(int i = 0;i< number_of_clients;i++){
            System.out.println("client lance");
            Runnable worker = new ClientThread(i,Requestss[i]);
            executor.execute(worker);
        }
    }
}
