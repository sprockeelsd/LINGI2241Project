import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;

public class MyServer {
    public static void main(String[] args) {
        int portNumber = 1234; //Integer.parseInt(args[0]);
        try{
            //seulement pour un client
            //crée un nouveau serveur
            ServerSocket serverSocket = new ServerSocket(portNumber);
            System.out.println("port créé");
            //attend qu'un client essaie de se connecter
            Socket clientSocket = serverSocket.accept();//retourne un socket lié au port portNumber et avec le n. de port et l'adresse du client
            System.out.println("client connecté");
            PrintWriter out =
                        new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream()));
            String inputLine, outputLine;

            // Initiate conversation with client
            MyProtocol protocol = new MyProtocol();
            outputLine = protocol.processInput(null);
            out.println(outputLine);

            while ((inputLine = in.readLine()) != null) {
                outputLine = protocol.processInput(inputLine);
                out.println(outputLine);
                if (outputLine.equals("Bye."))
                    break;
            }
            System.out.println("fin de try");
        }
        catch(IOException e){
            System.out.println("error");
        }
    }
}

