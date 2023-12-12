import adapters.UserGameCommandAdapter;
import chess.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import daos.AuthDAO;
import daos.GameDAO;
import dataAccess.DataAccessException;
import models.Game;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import webSocketMessages.serverMessages.ErrorMessage;
import webSocketMessages.serverMessages.LoadGameMessage;
import webSocketMessages.serverMessages.NotificationMessage;
import webSocketMessages.userCommands.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Objects;

@WebSocket
public class WebSocketHandler {

    private final ConnectionManager connectionManager = new ConnectionManager();

    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws IOException, SQLException, DataAccessException, InvalidMoveException, exceptions.DataAccessException {
        GsonBuilder builder = createBuilder();
        UserGameCommand command = builder.create().fromJson(message, UserGameCommand.class);

        switch (command.getCommandType()) {
            case JOIN_PLAYER -> joinPlayerService((JoinPlayerCommand) command, session);
            case JOIN_OBSERVER -> joinObserverService((JoinObserverCommand) command, session);
            case MAKE_MOVE -> makeMoveService((MakeMoveCommand) command);
            case LEAVE -> leaveService((LeaveCommand) command);
            case RESIGN -> resignService((ResignCommand) command);
        }
    }

    private boolean colorIsAvailable(ChessGame.TeamColor color, Game game, UserGameCommand command) {
        if (color == ChessGame.TeamColor.WHITE && Objects.equals(game.getWhiteUsername(), command.getUserName())) {
            return true;
        } else if (color == ChessGame.TeamColor.BLACK && Objects.equals(game.getBlackUsername(), command.getUserName())) {
            return true;
        } else {
            return false;
        }
    }

    private void joinPlayerService(JoinPlayerCommand command, Session session) throws IOException, SQLException, DataAccessException, exceptions.DataAccessException {
        Game game = GameDAO.getGame(command.getGameID());
        connectionManager.add(command.getAuthString(), session);
        if (game == null) {
            ErrorMessage errorMessage = new ErrorMessage("This Game ID does not exist");
            errorMessage.setRequestId(command.getRequestId());
            connectionManager.connections.get(command.getAuthString()).send(new Gson().toJson(errorMessage));
        } else {
            command.setUserName(AuthDAO.GetUsername(command.getAuthString()));
            if (!colorIsAvailable(command.getPlayerColor(), game, command)) {
                ErrorMessage errorMessage = new ErrorMessage("A player has already claimed this spot.");
                errorMessage.setRequestId(command.getRequestId());
                connectionManager.connections.get(command.getAuthString()).send(new Gson().toJson(errorMessage));
            } else {

                String playerColor;
                if (command.getPlayerColor() == ChessGame.TeamColor.WHITE) {
                    playerColor = "White";
                } else if (command.getPlayerColor() == ChessGame.TeamColor.BLACK) {
                    playerColor = "Black";
                } else {
                    playerColor = "an observer";
                }

                NotificationMessage serverMessage = new NotificationMessage(String.format("%s has joined the game as %s.", command.getUserName(), playerColor));
                connectionManager.broadcast(command.getAuthString(), serverMessage);

                LoadGameMessage loadGameMessage = new LoadGameMessage(GameDAO.getGame(command.getGameID()));
                loadGameMessage.setRequestId(command.getRequestId());
                connectionManager.connections.get(command.getAuthString()).send(new Gson().toJson(loadGameMessage));
            }
        }
    }

    private void joinObserverService(JoinObserverCommand command, Session session) throws SQLException, DataAccessException, IOException, exceptions.DataAccessException {
        Game game = GameDAO.getGame(command.getGameID());
        connectionManager.add(command.getAuthString(), session);
        if (game == null) {
            ErrorMessage errorMessage = new ErrorMessage("This Game ID does not exist");
            errorMessage.setRequestId(command.getRequestId());
            connectionManager.connections.get(command.getAuthString()).send(new Gson().toJson(errorMessage));
        } else {
            connectionManager.add(command.getAuthString(), session);
            LoadGameMessage loadGameMessage = new LoadGameMessage(GameDAO.getGame(command.getGameID()));
            loadGameMessage.setRequestId(command.getRequestId());
            connectionManager.connections.get(command.getAuthString()).send(new Gson().toJson(loadGameMessage));

            String username = AuthDAO.GetUsername(command.getAuthString());

            NotificationMessage notificationMessage = new NotificationMessage(String.format("%s joined the game as an observer.", username));
            connectionManager.broadcast(command.getAuthString(), notificationMessage);
        }
    }

    private void makeMoveService(MakeMoveCommand command) throws SQLException, DataAccessException, InvalidMoveException, IOException, exceptions.DataAccessException {
        models.Game game = GameDAO.getGame(command.getGameID());
        ChessMove userMove = command.getMove();

        if (game.getGame().getWinner() != null) {
            ErrorMessage errorMessage = new ErrorMessage("Game is over. No more moving.");
            errorMessage.setRequestId(command.getRequestId());
            connectionManager.connections.get(command.getAuthString()).send(new Gson().toJson(errorMessage));
        } else {
            if (!Objects.equals(AuthDAO.GetUsername(command.getAuthString()), game.getWhiteUsername()) && !Objects.equals(AuthDAO.GetUsername(command.getAuthString()), game.getBlackUsername())) {
                ErrorMessage errorMessage = new ErrorMessage("Only players can move pieces!");
                errorMessage.setRequestId(command.getRequestId());
                connectionManager.connections.get(command.getAuthString()).send(new Gson().toJson(errorMessage));
            } else {
                ChessGame.TeamColor currTeam;
                if (Objects.equals(AuthDAO.GetUsername(command.getAuthString()), game.getWhiteUsername())) {
                    currTeam = ChessGame.TeamColor.WHITE;
                } else {
                    currTeam = ChessGame.TeamColor.BLACK;
                }
                ChessGame.TeamColor pieceColor = game.getGame().getBoard().getPiece(command.getMove().getStartPosition()).getTeamColor();

                if (currTeam != pieceColor) {
                    ErrorMessage errorMessage = new ErrorMessage("You cannot move this piece!");
                    errorMessage.setRequestId(command.getRequestId());
                    connectionManager.connections.get(command.getAuthString()).send(new Gson().toJson(errorMessage));
                } else {
                    if (game.getGame().getTeamTurn() != currTeam) {
                        ErrorMessage errorMessage = new ErrorMessage("It is not your Turn!");
                        errorMessage.setRequestId(command.getRequestId());
                        connectionManager.connections.get(command.getAuthString()).send(new Gson().toJson(errorMessage));
                    } else {
                        Collection<ChessMove> allValidMoves = game.getGame().validMoves(userMove.getStartPosition());

                        if (allValidMoves.contains(userMove)) {
                            try {
                                game.getGame().makeMove(userMove);

                                ChessGame.TeamColor newTeamTurn = null;
                                if (currTeam == ChessGame.TeamColor.WHITE){
                                    newTeamTurn = ChessGame.TeamColor.BLACK;
                                } else {
                                    newTeamTurn = ChessGame.TeamColor.WHITE;
                                }

                                game.getGame().setTeamTurn(newTeamTurn);
                                GameDAO.updateGame(command.getGameID(), game);


                                LoadGameMessage loadGameMessage = new LoadGameMessage(game);
                                loadGameMessage.setRequestId(command.getRequestId());
                                connectionManager.broadcastToAll(loadGameMessage);

                                NotificationMessage notificationMessage = new NotificationMessage(String.format("%s has moved %s%s to %s%s.", command.getUserName(), numberToColumn(command.getMove().getStartPosition().getColumn()), numberToRow(command.getMove().getStartPosition().getRow()), numberToColumn(command.getMove().getEndPosition().getColumn()), numberToRow(command.getMove().getEndPosition().getRow())));
                                connectionManager.broadcast(command.getAuthString(), notificationMessage);

                                if (game.getGame().isInCheckmate(newTeamTurn)) {
                                    NotificationMessage checkNotificationMessage = new NotificationMessage(String.format("%s is in checkmate! %s won!", newTeamTurn, currTeam));
                                    connectionManager.broadcastToAll(checkNotificationMessage);
                                    game.getGame().setWinner(currTeam.name());
                                } else if (game.getGame().isInCheck(newTeamTurn)) {
                                    NotificationMessage checkNotificationMessage = new NotificationMessage(String.format("%s is in check!", newTeamTurn));
                                    connectionManager.broadcastToAll(checkNotificationMessage);
                                }
                            } catch (InvalidMoveException e) {
                                ErrorMessage errorMessage = new ErrorMessage("It is not your turn!");
                                errorMessage.setRequestId(command.getRequestId());
                                connectionManager.connections.get(command.getAuthString()).send(new Gson().toJson(errorMessage));
                            }
                        } else {
                            ErrorMessage errorMessage = new ErrorMessage(String.format("%s%s to %s%s is an invalid move!", numberToColumn(command.getMove().getStartPosition().getColumn()), numberToRow(command.getMove().getStartPosition().getRow()), numberToColumn(command.getMove().getEndPosition().getColumn()), numberToRow(command.getMove().getEndPosition().getRow())));
                            errorMessage.setRequestId(command.getRequestId());
                            connectionManager.connections.get(command.getAuthString()).send(new Gson().toJson(errorMessage));
                        }
                    }
                }
            }
        }
    }

    private int numberToRow(int row) {
        switch (row) {
            case 0 -> {
                return 8;
            }
            case 1 -> {
                return 7;
            }
            case 2 -> {
                return 6;
            }
            case 3 -> {
                return 5;
            }
            case 4 -> {
                return 4;
            }
            case 5 -> {
                return 3;
            }
            case 6 -> {
                return 2;
            }
            case 7 -> {
                return 1;
            }
        }
        return 'a';
    }

    private char numberToColumn(int column) {
        switch (column) {
            case 0 -> {
                return 'a';
            }
            case 1 -> {
                return 'b';
            }
            case 2 -> {
                return 'c';
            }
            case 3 -> {
                return 'd';
            }
            case 4 -> {
                return 'e';
            }
            case 5 -> {
                return 'f';
            }
            case 6 -> {
                return 'g';
            }
            case 7 -> {
                return 'h';
            }
        }
        return 'a';
    }

    private void leaveService(LeaveCommand command) throws SQLException, DataAccessException, IOException, exceptions.DataAccessException {
        String userName = AuthDAO.GetUsername(command.getAuthString());
        Game game = GameDAO.getGame(command.getGameId());
        if (!Objects.equals(userName, game.getWhiteUsername()) && !Objects.equals(userName, game.getBlackUsername())) {
            GameDAO.removeObserverFromGame(command.getGameId(), userName);
        } else {
            if (Objects.equals(userName, game.getWhiteUsername())) {
                GameDAO.removePlayer(command.getGameId(), ChessGame.TeamColor.WHITE);
            } else {
                GameDAO.removePlayer(command.getGameId(), ChessGame.TeamColor.BLACK);
            }
        }

        NotificationMessage notificationMessage = new NotificationMessage(String.format("%s has left the game.", AuthDAO.GetUsername(command.getAuthString())));
        connectionManager.broadcast(command.getAuthString(), notificationMessage);

        NotificationMessage userNotificationMessage = new NotificationMessage("You have left the game.");
        userNotificationMessage.setRequestId(command.getRequestId());
        connectionManager.connections.get(command.getAuthString()).send(new Gson().toJson(userNotificationMessage));

        connectionManager.remove(command.getAuthString());
    }

    private void resignService(ResignCommand command) throws SQLException, DataAccessException, IOException, exceptions.DataAccessException {
        Game game = GameDAO.getGame(command.getGameId());
        String username = AuthDAO.GetUsername(command.getAuthString());

        String loserName;
        String winnerName;
        if (Objects.equals(username, game.getWhiteUsername())) {
            loserName = game.getWhiteUsername();
            winnerName = game.getBlackUsername();
        } else {
            loserName = game.getBlackUsername();
            winnerName = game.getWhiteUsername();
        }

        if (game.getGame().getWinner() != null) {
            ErrorMessage errorMessage = new ErrorMessage("Cannot resign. game is over");
            errorMessage.setRequestId(command.getRequestId());
            connectionManager.connections.get(command.getAuthString()).send(new Gson().toJson(errorMessage));
        } else {
            if (!Objects.equals(username, game.getWhiteUsername()) && !Objects.equals(username, game.getBlackUsername())) {
                ErrorMessage errorMessage = new ErrorMessage("Observers cannot resign");
                errorMessage.setRequestId(command.getRequestId());
                connectionManager.connections.get(command.getAuthString()).send(new Gson().toJson(errorMessage));
            } else {
                game.getGame().setWinner(winnerName);
                GameDAO.updateGame(command.getGameId(), game);
                NotificationMessage notificationMessage = new NotificationMessage(String.format("%s has resigned. %s won the game! ", loserName, winnerName));
                connectionManager.broadcast(command.getAuthString(), notificationMessage);
                NotificationMessage notificationMessageToResignee = new NotificationMessage(String.format("You have resigned. %s won the game. ", winnerName));
                notificationMessageToResignee.setRequestId(command.getRequestId());
                connectionManager.connections.get(command.getAuthString()).send(new Gson().toJson(notificationMessageToResignee));
            }
        }
    }

    private static GsonBuilder createBuilder() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(UserGameCommand.class, new UserGameCommandAdapter());

        return builder;
    }
}
