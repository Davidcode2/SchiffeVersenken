# SchiffeVersenken
***


## Network

### Testen:

**tester laufen lassen:**
**mit IntelliJ:**
 
startet im ersten Schritt den Host und im zweiten den Client.

1. `ALT+SHIFT+F10` dann `tester` und pfeil nach rechts, `edit` und in `program arguments` `host` eintragen.
   Im selben Fenster Bei ``Modify options`` `allow multiple instances` auswählen.
   ``Enter``
2. das selbe nochmal und ``host`` wieder rauslöschen `Enter`

**alternativ command line**

Code irgendwie compilieren (out ordner fehlt bei mir) und in Powershell `tester` einmal mit parameter ``host`` und einmal ohne aufrufen.
> java tester host

> java tester


## Docs

Starten Serververbindung:
```java
network.Server server = new network.Server()
server.startConnection(int port);
```
Starten Clientverbindung: *nach starten von Server* 
```java
network.Client client = new network.Client()
client.startConnection(String ip, int port); // ip beim lokalen testen: 127.0.0.1
```

``network.Connection.sendMessage(String)`` versendet Nachricht an Mitspieler, sofern aktuller Spieler am Zug.

``network.Connection.getMessage()`` gibt zuletzt erhaltene Nachricht zurück (String).

**TODO:**
- Verbindung über Gui herstellen
    - Anzeigen verfügbarer IP Adressen
    - Auswahl IP Adresse
- Test Verbindung an zwei unterschiedlichen PCs

***