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

        int portNumber = 1234; //porta a cui il server si mettera' in ascolto
        
        //creo il gestore di eventi che serviranno ai vari workers di comunicare 
        Evento newMessaggio = new Evento();


        try{
            //metto il server in ascolto alla porta desiderata per clients che
            //richiedono connessione
            ServerSocket server = new ServerSocket(portNumber);
            System.out.println("Server chatTCP in esecuzione...");

            while(true){
                //Socket Worker e' l'oggetto che si occupa di servire il client
                //che si e' connesso, ne verra' generato un Worker per ogni
                //client e verra' eseguito in un suo Thread personale
                SocketWorker w;
                try {
                    //mi metto in attesa di una nuova richiesta di connessione
                    //server.accept() restituisce il nuovo socket
                    Socket newSocket = server.accept();
                    //una nuova connessione con un nuovo client e' stata creata
                    //creo un nuovo "servitore"(SocketWorker) che si prendera'
                    //cura del nuovo client
                    w = new SocketWorker(newSocket);

                    //registro il nuovo servitore come come Publisher cosi' che
                    //puo' generare un evento "newMessaggio" una volta ricevuto
                    //il nuovo messaggio dal proprio client
                    w.registraPublisher(newMessaggio);

                    //aggiungo il nuovo Worker nella lista dei Subscriber
                    //cosi' che riceverera' la notifica/comando che un nuovo
                    //messaggio e' stato ricevuto dal server e che deve inviarlo
                    //al proprio client
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