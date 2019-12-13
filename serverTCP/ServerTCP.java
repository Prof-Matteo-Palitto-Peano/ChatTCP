/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverTCP;



/**
 * ServerTCP MultiThreaded java Server che attende per richieste di connessioni 
 * da Clients e li gestisce in modo contemporaneo generando un socket "worker" 
 * per ogni connessione.
 * 
 * @author Prof. Matteo Palitto
 */
import java.net.*;
import java.io.*;

public class ServerTCP {
    

    public static void main(String[] args) {

        int portNumber = 1234;
        Evento newMessaggio = new Evento();


        try{
            //metto il server in ascolto alla porta desiderata
            ServerSocket server = new ServerSocket(portNumber);
            System.out.println("Server di Testo in esecuzione...  (CTRL-C quits)\n");

            while(true){
                //Socket Worker e' l'oggetto che si occupa di servire il client
                //che si e' connesso, ne verra' generato un Worker per ogni
                //client e verra' eseguito in un suo Thread personale
                SocketWorker w;
                try {
                    //server.accept returns a client connection
                    w = new SocketWorker(server.accept());
                    w.registraPublisher(newMessaggio);
                    //aggiungo il nuovo Worker nella lista dei Workers
                    //cosi' che puo' ricevere la notifica che un nuovo
                    //messaggio e' stato ricevuto
                    newMessaggio.addSubscriber(w);
                    //genero il Thread per l'esecuzione del nuovo Worker
                    Thread t = new Thread(w);
                    //Avvio l'esecuzione del nuovo worker nel Thread
                    t.start();
                } catch (IOException e) {
                    System.out.println("Connessione NON riuscita con client: ");
                    System.exit(-1);
                }
            }
        } catch (IOException e) {
            System.out.println("Error! Porta: " + portNumber + " non disponibile");
            System.exit(-1);
        }

        
    }
}