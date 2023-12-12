package adapters;

import chess.ChessGame;
import chess.Move;
import chess.Position;
import com.google.gson.*;
import webSocketMessages.userCommands.*;

import java.lang.reflect.Type;
import java.util.Objects;
import java.util.UUID;

public class UserGameCommandAdapter implements JsonDeserializer<UserGameCommand> {
    @Override
    public UserGameCommand deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        JsonElement commandType = jsonObject.get("commandType");
        UUID requestId = null;
        if (jsonObject.get("requestId") != null && jsonObject.get("requestId").isJsonPrimitive()) {
            requestId = UUID.fromString(jsonObject.getAsJsonPrimitive("requestId").getAsString());
        }

        UserGameCommand returnCommand = null;
        switch (commandType.getAsString()) {
            case "JOIN_PLAYER" -> returnCommand = createJoinPlayerCommand(jsonObject);
            case "MAKE_MOVE" -> returnCommand = createMakeMoveCommand(jsonObject);
            case "LEAVE" -> returnCommand = createLeaveCommand(jsonObject);
            case "RESIGN" -> returnCommand = createResignCommand(jsonObject);
            case "JOIN_OBSERVER" -> returnCommand = createJoinObserverCommand(jsonObject);
        }

        assert returnCommand != null;
        returnCommand.setRequestId(requestId);
        return returnCommand;
    }

    private JoinObserverCommand createJoinObserverCommand(JsonObject jsonObject) {

        JoinObserverCommand joinObserverCommand = new JoinObserverCommand(jsonObject.getAsJsonPrimitive("authToken").getAsString());
        joinObserverCommand.setGameID(jsonObject.getAsJsonPrimitive("gameID").getAsString());
        return joinObserverCommand;
    }

    private ResignCommand createResignCommand(JsonObject jsonObject) {
//        ChessGame.TeamColor color;
//        if (Objects.equals(jsonObject.getAsJsonPrimitive("color").getAsString(), "WHITE")) {
//            color = ChessGame.TeamColor.WHITE;
//        } else {
//            color = ChessGame.TeamColor.BLACK;
//        }

        return new ResignCommand(jsonObject.getAsJsonPrimitive("authToken").getAsString(), jsonObject.getAsJsonPrimitive("gameID").getAsString(), ChessGame.TeamColor.WHITE);
    }

    private LeaveCommand createLeaveCommand(JsonObject jsonObject) {
//        ChessGame.TeamColor color;
//        if (Objects.equals(jsonObject.getAsJsonPrimitive("color").getAsString(), "WHITE")) {
//            color = ChessGame.TeamColor.WHITE;
//        } else if (Objects.equals(jsonObject.getAsJsonPrimitive("color").getAsString(), "BLACK")) {
//            color = ChessGame.TeamColor.BLACK;
//        } else {
//            color = ChessGame.TeamColor.OBSERVER;
//        }

        return new LeaveCommand(jsonObject.getAsJsonPrimitive("authToken").getAsString(), jsonObject.getAsJsonPrimitive("gameID").getAsString());
    }

    private JoinPlayerCommand createJoinPlayerCommand(JsonObject jsonObject) {

        ChessGame.TeamColor teamColor;
        if (Objects.equals(jsonObject.getAsJsonPrimitive("playerColor").getAsString(), "WHITE")) {
            teamColor = ChessGame.TeamColor.WHITE;
        } else if (Objects.equals(jsonObject.getAsJsonPrimitive("playerColor").getAsString(), "BLACK")) {
            teamColor = ChessGame.TeamColor.BLACK;
        } else {
            teamColor = ChessGame.TeamColor.OBSERVER;
        }
        String userName = null;
        if (jsonObject.get("userName") != null && jsonObject.get("userName").isJsonPrimitive()) {
            userName = jsonObject.getAsJsonPrimitive("userName").getAsString();
        }

        JoinPlayerCommand joinPlayerCommand = new JoinPlayerCommand(jsonObject.getAsJsonPrimitive("authToken").getAsString(), jsonObject.getAsJsonPrimitive("gameID").getAsString(), teamColor);
        joinPlayerCommand.setUserName(userName);

        return joinPlayerCommand;
    }

    private MakeMoveCommand createMakeMoveCommand(JsonObject jsonObject) {

        String authToken = jsonObject.getAsJsonPrimitive("authToken").getAsString();

        String gameID = jsonObject.getAsJsonPrimitive("gameID").getAsString();


        Position startPosition = new Position(Integer.parseInt(jsonObject.getAsJsonObject("move").getAsJsonObject("startPosition").getAsJsonPrimitive("row").getAsString()), Integer.parseInt(jsonObject.getAsJsonObject("move").getAsJsonObject("startPosition").getAsJsonPrimitive("column").getAsString()));
        Position endPosition = new Position(Integer.parseInt(jsonObject.getAsJsonObject("move").getAsJsonObject("endPosition").getAsJsonPrimitive("row").getAsString()), Integer.parseInt(jsonObject.getAsJsonObject("move").getAsJsonObject("endPosition").getAsJsonPrimitive("column").getAsString()));
        Move move = new Move(startPosition, endPosition,  null);


        String userName = null;
        if (jsonObject.get("userName") != null && jsonObject.get("userName").isJsonPrimitive()) {
            userName = jsonObject.getAsJsonPrimitive("userName").getAsString();
        }

        return new MakeMoveCommand(authToken, gameID, move, userName);
    }
}
