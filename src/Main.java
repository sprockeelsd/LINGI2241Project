import java.io.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        //commande hostname     Damien : LAPTOP-TQFF0SRJ  Arnaud : LAPTOP-I9J1EU77
        int number_of_clients = 50; //Nombre max de threads clients.
        int number_of_requests = 3; //Nombre de requêtes par client.
        ExecutorService executor = Executors.newFixedThreadPool(number_of_clients);

        //Ici on fait les initialisations des requêtes qu'on veut que le client fasse.
        String[][] Requestss = new String[number_of_clients][number_of_requests];
        Requestss[0] = new String[]{"2;CONTENTS\n", "1;FORD OF CANADA\n", "1;CONTENTS\n"};
        //Requestss[1] = new String[]{"1;CANADA POST\n", "1;LA GENDARMERIE ROYALE DU CANADA\n", "1;LA LOI LECTORALE DU CANADA\n"};

        //Ici on va boucler sur tous les clients pour les lancer.
        for(int i = 0;i< number_of_clients;i++){
            System.out.println("client lance");
            Runnable worker = new ClientThread(i,Requestss[0]);
            executor.execute(worker);
        }
        executor.shutdown();
        Thread.sleep(20000);
        PrintWriter writer = new PrintWriter("delays.csv", "UTF-8");
        while(!MyServer.delays.isEmpty()){
            writer.println(Long.toString(MyServer.delays.remove(0)));
        }
        writer.close();
    }
}
