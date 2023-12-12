import adapters.*;
import chess.Board;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import webSocketMessages.serverMessages.LoadGameMessage;
import webSocketMessages.serverMessages.NotificationMessage;
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.userCommands.UserGameCommand;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

public class WSFacade extends Endpoint {

    private final Session session;

    private Map<UUID, CompletableFuture> responseMap = new ConcurrentHashMap<>();

    public WSFacade() throws Exception {

        try {
            URI socketURI = new URI("ws://localhost:8080/connect");

            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            this.session = container.connectToServer(this, socketURI);

            this.session.addMessageHandler(new MessageHandler.Whole<String>() {
                @Override
                public void onMessage(String message) {
                    GsonBuilder builder = createBuilder();
                    ServerMessage serverMessage = builder.create().fromJson(message, ServerMessage.class);
                    CompletableFuture response = null;
                    if (serverMessage.getRequestId() != null) {
                        response = responseMap.get(serverMessage.getRequestId());
                    }

                    switch (serverMessage.getServerMessageType()) {
                        case LOAD_GAME -> loadGame((LoadGameMessage) serverMessage, response);
                        case ERROR -> error();
                        case NOTIFICATION -> notification((NotificationMessage) serverMessage, response);
                    }
                }
            });
        } catch (DeploymentException | IOException | URISyntaxException ex) {
            // throw the correct exception here!
            throw new Exception(ex.getMessage());
        }
    }

    public CompletableFuture<String> sendCommandAndWaitForResponse(UserGameCommand command) throws IOException {
        CompletableFuture<String> response = new CompletableFuture<>();
        UUID requestId = UUID.randomUUID();
        responseMap.put(requestId, response);
        command.setRequestId(requestId);

        this.session.getBasicRemote().sendText(new Gson().toJson(command));

        return response;
    }

    private void loadGame(LoadGameMessage gameMessage, CompletableFuture<String> response) {
        client.setCurrBoard((Board) gameMessage.getGame().getGame().getBoard());
        Display.printChessBoard(client.getUserColor(), client.getCurrBoard());
        if (response != null) {
            response.complete(null);
            responseMap.remove(gameMessage.getRequestId());
        }
    }

    private void error() {

    }

    private void notification(NotificationMessage notificationMessage, CompletableFuture<String> response) {
        System.out.println('\n' + notificationMessage.getMessage());
        if (response != null) {
            response.complete(null);
            responseMap.remove(notificationMessage.getRequestId());
        }
    }

    private static GsonBuilder createBuilder() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(ServerMessage.class, new ServerMessageAdapter());
        builder.registerTypeAdapter(models.Game.class, new ChessGameAdapter());
        builder.registerTypeAdapter(chess.Board.class, new ChessBoardAdapter());
        builder.registerTypeAdapter(chess.Piece.class, new ChessPieceAdapter());

        return builder;
    }

    @Override
    public void onOpen(Session session, EndpointConfig endpointConfig) {
        // Don't do anything :)
    }
}
