package Protocol;

/**
 * Protocol class with constants for creating protocol messages.
 */
public final class Protocol {
    //The initial message sent by the client once a connection has been established
    public static final String HELLO = "HELLO";
    //Separator
    public static final String SEPARATOR = "~";
    //Sent by the client to claim a username on the server
    public static final String LOGIN = "LOGIN";
    //Sent by client to request the list of clients logged into the server
    public static final String LIST = "LIST";
    //Sent by client to indicate what move the client wants to make
    public static final String MOVE = "MOVE";
    public static final String ALREADYLOGGEDIN = "ALREADYLOGGEDIN";
    public static final String NEWGAME = "NEWGAME";
    public static final String GAMEOVER = "GAMEOVER";

    private Protocol(){
    }
}
