// 代码生成时间: 2025-10-05 17:15:44
import org.apache.struts2.dispatcher.SessionMap;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketError;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.eclipse.jetty.websocket.api.annotations.WebSocketHandler;
import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A simple WebSocket handler that handles communication.
 */
@WebSocket
public class WebSocketChat {

    // A set to store active WebSocket sessions.
    private static final Set<Session> activeSessions = Collections.synchronizedSet(new HashSet<>());

    @OnWebSocketConnect
    public void onConnect(Session session) {
        // Add the new session to the active sessions.
        activeSessions.add(session);
        System.out.println("New connection established: " + session);
    }

    @OnWebSocketMessage
    public void onMessage(Session session, String message) {
        // Broadcast the received message to all connected clients.
        for (Session s : activeSessions) {
            try {
                s.getRemote().sendString(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @OnWebSocketClose
    public void onClose(Session session, int statusCode, String reason) {
        // Remove the closed session from the active sessions.
        activeSessions.remove(session);
        System.out.println("Connection closed: " + session);
    }

    @OnWebSocketError
    public void onError(Session session, Throwable error) {
        // Handle any errors that occur during communication.
        error.printStackTrace();
    }
}

// Servlet configuration for WebSocketChat
public class WebSocketChatServlet extends WebSocketServlet {
    private static final long serialVersionUID = 1L;

    @Override
    public void configure(WebSocketServletFactory factory) {
        factory.register(WebSocketChat.class);
    }
}