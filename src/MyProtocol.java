import java.net.*;
import java.io.*;

public class MyProtocol {
    public String processInput(String theInput) {

        String theOutputString = null; //return value

        // When we establish connection otherwise a wrong argument has been sent to the server.
        if (theInput == null || theInput.equals("")) {
            theOutputString = "connection established or wrong argument";
        }
        else {
            String[] input = theInput.split(";",2); // We split the input.

            if(input[0].equals("Goodbye")){  // If the client has finished to send it's request we can send him Bye.
                return "Bye.\n";
            }
            if(input.length == 2) {
                int types[] = {0, 1, 2, 3, 4, 5};  // By default we have 5 types.
                if (!input[0].equals("")) {   // If the type are specified by the input we only take them into account
                    String integers[] = input[0].split(",",2);
                    types = new int[integers.length];
                    for (int k = 0; k < integers.length; k++) {
                        types[k] = Integer.parseInt(integers[k]);
                    }
                }
                int i = 0;
                while (i < MyServer.dataSize) { // We loop through the data
                    String currLine[] = {MyServer.data[i][0], MyServer.data[i][1]};
                    if (currLine[1].contains(input[1])) {   // If the sentence written contains the input.
                        for (int k = 0; k < types.length; k++) { // If it has the right type.
                            if (Integer.parseInt(currLine[0]) == types[k]) {    // If it is the right type.
                                if (theOutputString == null) {
                                    theOutputString = currLine[0] + " " + currLine[1] + "\n";
                                } else {
                                    theOutputString = theOutputString + currLine[0] + " " + currLine[1] + "\n";
                                    //System.out.println(theOutputString);
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