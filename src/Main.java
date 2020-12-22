import java.io.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        //commande hostname     Damien : LAPTOP-TQFF0SRJ  Arnaud : LAPTOP-I9J1EU77
        int number_of_clients = 1; //Nombre max de threads clients.
        int number_of_requests = 3; //Nombre de requêtes par client.
        ExecutorService executor = Executors.newFixedThreadPool(number_of_clients);
        //Ici on fait les initialisations des requêtes qu'on veut que le client fasse.
                String[][] easyRequests = {
                {
                    "1;critique : great premise is executed with enough style and " +
                            "thrills to keep the piece interesting throughout its close to two-hour " +
                            "runtime \n",
                    "3;Mr. Speaker, this bill will enable children's organizations and parents to " +
                            "access the criminal record of persons convicted of sexual offences " +
                            "against children, even if later the sexual offender has received a " +
                            "pardon.\n",
                    "4;You wrote, sir, knowing what answer you would receive\n",
                        "1;critique : great premise is executed with enough style and " +
                                "thrills to keep the piece interesting throughout its close to two-hour " +
                                "runtime \n"
                },
                {
                    "0;There is en excellent example right now in Quebec.\n",
                    "5;Like many people, we have a very hard time making the difference " +
                                "between the Liberals and the Conservatives and we have a very good " +
                                "illustration of that in this case\n",
                    "0;And wilt encounter with my wrath, say so\n" ,
                        "0;There is en excellent example right now in Quebec.\n"
                },
                {
                    "3;To that I call!  [To LAVINIA]  What, would'st thou kneel with me?\n",
                    "5;BOY. Good grandsire, leave these bitter deep laments\n",
                    "5;Which made me down to throw my books, and fly-\n",
                        "3;To that I call!  [To LAVINIA]  What, would'st thou kneel with me?\n"
                }
        };
        String[][] averageRequests = {
                {
                        "2;CONTENTS\n",
                        "1;FORD OF CANADA\n",
                        ";CONTENTS\n"
                },
                {
                        "1;CANADA POST\n",
                        "1;LA GENDARMERIE ROYALE DU CANADA\n",
                        "1;LA LOI LECTORALE DU CANADA\n"
                },
                {
                        "4;AARON\n",
                        "4;SUPPLY\n",
                        "5;GOVERNMENT\n"
                }
        };
        String[][] hardRequests = {
                {
                    ";C\n",
                        ";a\n",
                        ";or\n"
                },
                {
                    ";O\n",
                        ";u\n",
                        ";an\n"
                },
                {
                    ";A\n",
                        ";f\n",
                        ";o\n"
                }
        };


        String[] myrequests4 = {
                "2;CONTENTS\n",
                "1;FORD OF CANADA\n",
                ";CONTENTS\n",
                "2;CONTENTS\n"
        };
        String[] myrequests5 = {
                "2;CONTENTS\n",
                "1;FORD OF CANADA\n",
                ";CONTENTS\n",
                "2;CONTENTS\n",
                "1;FORD OF CANADA\n"
        };
        String[] myrequests1 = {
                "2;CONTENTS\n"};
        //Ici on va boucler sur tous les clients pour les lancer.
        for(int i = 0;i< number_of_clients;i++){
            System.out.println("client lance");
            //changer ici pour la difficulté des requests
            //int rand = (int) (number_of_requests * Math.random());
            //String[] theseRequests = easyRequests[rand];
            String[] theseRequests = myrequests5;
            Runnable worker = new ClientThread(i,theseRequests);
            executor.execute(worker);
        }
        executor.shutdown();
        Thread.sleep(10000);
        PrintWriter writer = new PrintWriter("Delays.csv", "UTF-8");
        while(!MyServer.delays.isEmpty()){
            writer.append(Long.toString(MyServer.delays.remove(0)) + "\n");
        }
        writer.close();
    }
}
