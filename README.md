# ChatTCP
Client e Server per una CHAT di tipo TCP

Studiate il codice e usate i commenti nel codice come guida al vostro studio

## Low Level Design (blocco: Socket Worker)

### Funzionalita' (da HLD)
* gestisce socket di connessione ad un client
* viene eseguito in un proprio thread

### Interazioni con altri blocchi  (da HLD)
* Riceve messaggio da socket
* Invia messaggi al “Gestore Messaggi”
* Riceve messaggi da “Gestore Messaggi” da inoltrare a socket
* inoltra al socket il messaggio da inviare al client

#### Costruttore
* riceve parametro socket da gestire

#### caratteristiche della classe che implementa il Socket Woeker
* dovra' estendere la classe Thread
* avra' un metodo **run()** che eseguira' il codice del thread

* implemento l'interfaccia "Ricevi Messaggio"
* implemento l'interfaccia 'Invia Messaggio"
