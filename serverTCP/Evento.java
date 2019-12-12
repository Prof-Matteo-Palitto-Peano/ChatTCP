/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverTCP;

import java.util.ArrayList;

/**
 *
 * @author matteo
 */
//L'ultimo messaggio ricevuto e' la risorsa comune condivisa tra i vari Threads
// Con questa Classe ricevo l'ultimo messaggio inviato dai Clients
//e richiedo l'invio a tutti i workers di inviare il messaggio al proprio client
class Evento {

    //ultimo messaggio inviato dai Clients
    private String messaggio;
    //lista dei workers che viengono creati, uno per ogni Client connesso
    private ArrayList<SocketWorker> workers = new ArrayList<>();
    
    //aggiungo il client alla lista
    void addListener(SocketWorker worker) {
        this.workers.add(worker);
    }
    
    //rimuovo il client dalla lista
    void removeListener(SocketWorker worker) {
        this.workers.remove(worker);
    }
    
    //chiamata dai vari Threads quando ricevono un messaggio da client
    //questo metodo e' sycronized per evitare conflitti tra workers
    //che desiderano accedere alla stessa risorsa (cioe' nel caso in cui
    // vengono ricevuti simultaneamente i messaggi da piu' clients)
    synchronized void sendNewMessaggio(String m) {
        //aggiorna l'ultimo messaggio
        this.messaggio = m;
        //chiedi ad ogni worker di inviare il messaggio ricevuto
        for (SocketWorker worker: this.workers) {
            worker.sendMessaggio(this.messaggio);
        }
    }
    
}

//questa interfaccia deve essere implementata da tutti i threads che vogliono
//inviare il nuovo messaggio
interface EventoListener {
    public void sendMessaggio(String m);
}

//questa interffacci deve essere implementata da tutti i threads che vogliono
//ricevere un messaggio per poi poterlo inviare agli altri threads
interface EventoPublisher {
    public void registraPublisher(Evento newMessaggio);
    public void messaggioReceived(String m);
}