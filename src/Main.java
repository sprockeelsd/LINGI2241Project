import java.io.*;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static int number_of_clients = 20; //Nombre max de threads clients.
    public static int number_of_requests = 5; //Nombre de requêtes par client.
    public static void main(String[] args) throws IOException, InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(number_of_clients);
        // Here we initialise the different requests that the clients will make.
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


        String[] testaveragetime = {
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
                        "runtime \n",
                "1;CANADA POST\n",
                "1;LA GENDARMERIE ROYALE DU CANADA\n",
                "1;LA LOI LECTORALE DU CANADA\n",
                ";CON\n",
                ";CAN\n",
                ";GOV\n"

        };
        // Here we make a loop to initialise every Client.
        for(int i = 0;i< number_of_clients;i++){
            //System.out.println("client lance");
            //changer ici pour la difficulté des requests
            //int rand = (int) (number_of_requests * Math.random());
            //String[] theseRequests = easyRequests[rand];
            String[] theseRequests = myrequests4;
            Runnable worker = new ClientThread(i,theseRequests);
            executor.execute(worker);
        }
        executor.shutdown();
    }
}
