/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

import java.net.*;
import java.io.*;

public class MyProtocol {
    /*private static final int WAITING = 0;
    private static final int SENTKNOCKKNOCK = 1;
    private static final int SENTCLUE = 2;
    private static final int ANOTHER = 3;*/
    private static final int WAITINGFORREQUEST = 4;
    private static final int CLOSING = 5;
    //private static final int NUMJOKES = 5;

    private int state = WAITINGFORREQUEST;
    //private int currentJoke = 0;

    /*private String[] clues = { "Turnip", "Little Old Lady", "Atch", "Who", "Who" };
    private String[] answers = { "Turnip the heat, it's cold in here!",
            "I didn't know you could yodel!",
            "Bless you!",
            "Is there an owl in here?",
            "Is there an echo in here?" };*/
    public String processInput(String theInput) {
        String theOutputString = null;

        if (state == WAITINGFORREQUEST) { //si tout va bien et qu'on attend une requête
            if (theInput == null || theInput.equals("\n") || theInput.equals("")) {//si l'argument est nul
                theOutputString = "connection established or wrong argument";
            }
            else {
                //récupère les types et la phrase
                String[] input = theInput.split(";");
                //si le client veut se déconnecter
                if(input[0].equals("Goodbye")){
                    return "Bye.\n";
                }
                if(input.length != 2){
                    return "Invalid request\n";
                }
                int types[] = {1,2,3,4,5};//cas par défaut
                //si les types sont précisés
                if(!input[0].equals("")){
                    //récupérer les types
                    String integers[] = input[0].split(",");
                    types = new int[integers.length];
                    for(int k = 0; k < integers.length; k++){
                        types[k] = Integer.parseInt(integers[k]);
                        //System.out.println("types = " + types[k]);
                    }
                }

                int i = 0;
                //on parcours les données
                while (i < MyServer.dataSize) {
                    //récupère la ligne du tableau
                    String currLine[] = {MyServer.data[i][0], MyServer.data[i][1]};
                    //System.out.println(i + " " + currLine[0] + currLine[1] + "\n");
                    //si la phrase lue dans le fichier est celle demandée
                    if (currLine[1].equals(input[1])) {
                        //check si c'est le bon type
                        for(int k = 0; k < types.length; k++){
                            //si c'est le bon type
                            if(Integer.parseInt(currLine[0]) == types[k]){
                                //System.out.println("ligne trouvée : " + currLine[0] + " " + currLine[1] + "\n" + "i = " + i);
                                if(theOutputString == null){
                                    theOutputString = currLine[0] + " " + currLine[1] + "\n";
                                }
                                else {
                                    theOutputString = theOutputString + currLine[0] + " " + currLine[1] + "\n";
                                }
                            }
                        }
                    }
                    i++;
                }
                //theOutputString = theOutputString + "\n";
            }
        }
        if(theOutputString == null){
            return "Couldn't find what you are looking for \n";
        }
        System.out.println(theOutputString);
        return theOutputString;
    }
}
/*String theOutput = null;
        if (state == WAITING) {
            theOutput = "Knock! Knock!";
            state = SENTKNOCKKNOCK;
        } else if (state == SENTKNOCKKNOCK) {
            if (theInput.equalsIgnoreCase("Who's there?")) {
                theOutput = clues[currentJoke];
                state = SENTCLUE;
            } else {
                theOutput = "You're supposed to say \"Who's there?\"! " +
                        "Try again. Knock! Knock!";
            }
        } else if (state == SENTCLUE) {
            if (theInput.equalsIgnoreCase(clues[currentJoke] + " who?")) {
                theOutput = answers[currentJoke] + " Want another? (y/n)";
                state = ANOTHER;
            } else {
                theOutput = "You're supposed to say \"" +
                        clues[currentJoke] +
                        " who?\"" +
                        "! Try again. Knock! Knock!";
                state = SENTKNOCKKNOCK;
            }
        } else if (state == ANOTHER) {
            if (theInput.equalsIgnoreCase("y")) {
                theOutput = "Knock! Knock!";
                if (currentJoke == (NUMJOKES - 1))
                    currentJoke = 0;
                else
                    currentJoke++;
                state = SENTKNOCKKNOCK;
            } else {
                theOutput = "Bye.";
                state = WAITING;
            }
        }
        return theOutput;*/
