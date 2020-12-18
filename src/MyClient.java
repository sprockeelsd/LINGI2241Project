/*
 * Copyright (c) 1995, 2013, Oracle and/or its affiliates. All rights reserved.
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

import java.io.*;
import java.net.*;

public class MyClient {
    public boolean transmitionStarted = false;
    public int id;
    public String Requests[];
    public boolean GoodbyeSent = false;
    public boolean requestSent = false;
    public boolean serverStartedResponding = false;

    //commande hostname     Damien : LAPTOP-TQFF0SRJ  Arnaud : LAPTOP-I9J1EU77
    public MyClient(int id, String[] Requests){
        this.id = id;
        this.Requests = Requests;
    }

    public void connect(String hostName, int portNumber) throws IOException {

        try (
                Socket kkSocket = new Socket(hostName, portNumber);
                PrintWriter out = new PrintWriter(kkSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(kkSocket.getInputStream()));
        ) {
            BufferedReader stdIn =
                    new BufferedReader(new InputStreamReader(System.in));
            String fromServer;
            String fromUser;
            int i = 0;
            while ((fromServer = in.readLine()) != null || i < this.Requests.length) {
                if(!fromServer.equals("")){
                    System.out.println("Server: " + fromServer);
                    serverStartedResponding = true;
                }
                if (fromServer.equals("Bye.")) {
                    break;
                }
                if(fromServer.equals("") && transmitionStarted && requestSent && serverStartedResponding){
                    System.out.println("Client " + this.id + " : answer received\n");
                    requestSent = false;
                    serverStartedResponding = false;
                }
                if(fromServer.equals("") || !transmitionStarted){
                    //le client entre une requête
                    if(GoodbyeSent){
                        fromUser = null;
                    }
                    else if(i >= this.Requests.length && !GoodbyeSent){
                        fromUser = "Goodbye";
                        GoodbyeSent = true;
                        requestSent = true;
                        serverStartedResponding = false;
                    }
                    else{
                        fromUser = this.Requests[i];
                        i++;
                        requestSent = true;
                    }
                    if (fromUser != null) {
                        //on a commencé à parler au serveur
                        transmitionStarted = true;
                        System.out.println("Client " + this.id + " :" + fromUser);
                        out.println(fromUser);
                    }
                }
            }
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                    hostName);
            System.exit(1);
        }
    }
}