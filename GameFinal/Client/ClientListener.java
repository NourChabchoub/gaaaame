package Client;
import java.io.IOException;

public interface ClientListener {
    public void connectionLost() throws IOException;
}
