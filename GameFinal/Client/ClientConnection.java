package Client;

import Protocol.Protocol;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ClientConnection extends SocketConnection {
    private Client client;
    private String username;
    ClientTUI clientTUI = new ClientTUI();
    private List<ClientConnection> connectedClients = new ArrayList<>();


    /**
     * Create a new SocketConnection. This is not meant to be used directly.
     * Instead, the SocketServer and SocketClient classes should be used.
     *
     * @param address the address of the server for this connection.
     * @param portnumber the portnumber of the server for this connection.
     * @throws IOException if there is an I/O exception while initializing the Reader/Writer objects
     */
    protected ClientConnection(String address, int portnumber) throws IOException {
        super(address, portnumber);
    }

    public void start(){
        super.start();
    }

    public void setClient (Client client){
        this.client = client;
    }

    // method to add a new client
    public void registerClient (ClientConnection clientConnection){
        connectedClients.add(clientConnection);
    }

    /**
     * Handles a message received from the connection.
     *
     * @param message the message received from the connection
     */
    @Override
    protected void handleMessage(String message) throws IOException {
    if (message != null){
        String[] list = message.split(Protocol.SEPARATOR);
            String firstpart = list[0];
            String secondpart = list[1];
        switch (list[0]){
            case Protocol.HELLO -> {
                System.out.println(Protocol.HELLO + Protocol.SEPARATOR + "Successfully connected to the server!");
            }
            case Protocol.LOGIN -> {
                System.out.println(clientTUI.getUsername(username) + " is successfully logged in! ");
            }
            case Protocol.LIST -> {
                for (int i=1; i<list.length; i++) {
                    System.out.println("The list of all the clients connected to the server: \n" + list[i]);
                }
            }
            case Protocol.NEWGAME -> {
                System.out.println(Protocol.NEWGAME + Protocol.SEPARATOR + " Player 1: " + list[1] + Protocol.SEPARATOR + " Player 2 " + list[2]);
            }
            case Protocol.ALREADYLOGGEDIN -> {
                System.out.println("username is already used, please enter another one");
            }
            case Protocol.GAMEOVER -> {
                System.out.println(Protocol.GAMEOVER + Protocol.SEPARATOR + list[1] + list[2]);
            }
//            case Protocol.MOVE -> {
//                System.out.println(username + "made the move: " + );
//            }


        }
        }
    }

    /**
     * Handles a disconnect from the connection, i.e., when the connection is closed.
     */
    @Override
    protected void handleDisconnect() throws IOException {
        this.client.handleDisconnected();
    }
    public void close(){
        super.close();
    }

    public void sendChatMessage(String message){
        super.sendMessage(Protocol.HELLO + Protocol.SEPARATOR + username);
    }
}