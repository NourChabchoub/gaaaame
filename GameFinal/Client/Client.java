package Client;

import Protocol.Protocol;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class Client {
    private String address;
    private int portnumber;
    private ClientConnection clientConnection;
    private String name;
    Set<ClientListener> listener = new HashSet<>();

    public Client(String address, int portnumber, String name) throws IOException {
        this.address = address;
        this.portnumber = portnumber;
        this.name = name;
        this.clientConnection = new ClientConnection(address, portnumber);
        clientConnection.setClient(this);
        clientConnection.start();
    }

    public void close(){
        clientConnection.close();
    }

    public void sendHelloMessage(String message){
        clientConnection.sendChatMessage(Protocol.HELLO + Protocol.SEPARATOR + name);
    }
    public void sendLoginMessage(String username){
        clientConnection.sendChatMessage(Protocol.LOGIN + Protocol.SEPARATOR+ username);
    }

    public void handleDisconnected() throws IOException{
        for (var clientListener: listener){
            listener.removeAll(listener);
            clientListener.connectionLost();
        }
    }

    public void addListener (ClientListener clientListener){
        listener.add(clientListener);
    }
}