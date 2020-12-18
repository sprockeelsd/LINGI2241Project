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
            //theOutputString = theOutputString + "\n";
        }
        if(theOutputString == null){
            return "Couldn't find what you are looking for \n";
        }
        System.out.println(theOutputString);
        return theOutputString;
    }
}