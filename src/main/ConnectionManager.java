import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.Session;
import webSocketMessages.serverMessages.ServerMessage;

public class ConnectionManager {
    public final ConcurrentHashMap<String, Connection> connections = new ConcurrentHashMap<>();

    public void add(String authToken, Session session) {
        Connection connection = new Connection(authToken, session);
        connections.put(authToken, connection);
    }

    public void remove(String authToken) {
        connections.remove(authToken);
    }

    public void broadcast(String doNotSendAuthToken, ServerMessage message) throws IOException {
        ArrayList<Connection> removeList = new ArrayList<Connection>();
        for (Connection connection : connections.values()) {
            if (connection.session.isOpen()) {
                if (!connection.authToken.equals(doNotSendAuthToken)) {
                        connection.send(new Gson().toJson(message));
                }
            } else {
                removeList.add(connection);
            }
        }

        for (Connection connection : removeList) {
            connections.remove(connection.authToken);
        }
    }

    public void broadcastToAll(ServerMessage message) throws IOException {
        ArrayList<Connection> removeList = new ArrayList<Connection>();
        for (Connection connection : connections.values()) {
            if (connection.session.isOpen()) {
                connection.send(new Gson().toJson(message));
            } else {
                removeList.add(connection);
            }
        }

        for (Connection connection : removeList) {
            connections.remove(connection.authToken);
        }
    }
}
