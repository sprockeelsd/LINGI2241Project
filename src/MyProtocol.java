import java.net.*;
import java.io.*;

public class MyProtocol {
    public String processInput(String theInput) {
        String theOutputString = null; //return value
        if (theInput == null || theInput.equals("")) {//si l'argument est nul  || theInput.equals("\n") || theInput.equals("")
            theOutputString = "connection established or wrong argument";
        }
        else {
            String[] input = theInput.split(";"); //récupère les types et la phrase
            if(input[0].equals("Goodbye")){ //si le client veut se déconnecter
                return "Bye.\n";
            }
            if(input.length == 2) {
                int types[] = {1, 2, 3, 4, 5};  //cas par défaut
                if (!input[0].equals("")) {   //si les types sont précisés
                    String integers[] = input[0].split(",");    //récupérer les types
                    types = new int[integers.length];
                    for (int k = 0; k < integers.length; k++) {
                        types[k] = Integer.parseInt(integers[k]);
                    }
                }
                int i = 0;
                while (i < MyServer.dataSize) { //on parcours les données
                    String currLine[] = {MyServer.data[i][0], MyServer.data[i][1]}; //récupère la ligne du tableau
                    if (currLine[1].contains(input[1])) {   //si la phrase lue dans le fichier est celle demandée
                        for (int k = 0; k < types.length; k++) { //check si c'est le bon type
                            if (Integer.parseInt(currLine[0]) == types[k]) {    //si c'est le bon type
                                if (theOutputString == null) {
                                    theOutputString = currLine[0] + " " + currLine[1] + "\n";
                                } else {
                                    theOutputString = theOutputString + currLine[0] + " " + currLine[1] + "\n";
                                }
                            }
                        }
                    }
                    i++;
                }
            }
            theOutputString = theOutputString + "\n";
        }
        if(theOutputString == null){
            return "Couldn't find what you are looking for \n";
        }
        System.out.println(theOutputString);
        return theOutputString;
    }
}