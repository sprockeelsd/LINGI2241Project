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
import java.util.ArrayList;

public class MyProtocolOpti {
    public String processInput(String theInput) {
        String theOutputString = null; //return value
        if (theInput == null || theInput.equals("")) {//si l'argument est nul  || theInput.equals("\n") || theInput.equals("")
            theOutputString = "connection established or wrong argument";
        }
        else {
            String[] input = theInput.split(";",2); //récupère les types et la phrase
            if(input[0].equals("Goodbye")){ //si le client veut se déconnecter
                return "Bye.\n";
            }
            if(input.length == 2) {
                String[] integers = new String[]{"0", "1", "2", "3", "4", "5"};  //cas par défaut
                if (!input[0].equals("")) {   //si les types sont précisés
                    integers = input[0].split(",");    //récupérer les types
                }
                System.out.println(integers);
                for (int j = 0; j<integers.length;j++){
                    ArrayList<String> data_in_db = MyServerOpti.data_opti.get(integers[j]);
                    for (int z = 0;z<data_in_db.size();z++){
                        if(data_in_db.get(z).contains(input[1])){
                            if (theOutputString == null) {
                                theOutputString = integers[j] + " " + data_in_db.get(z) + "\n";
                            } else {
                                theOutputString = theOutputString + integers[j] + " " + data_in_db.get(z) + "\n";
                            }
                        }
                    }
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
