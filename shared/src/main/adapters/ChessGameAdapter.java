package adapters;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import models.Game;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ChessGameAdapter implements JsonDeserializer<models.Game> {
    @Override
    public models.Game deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        JsonElement observersElement = jsonObject.get("observers");
        JsonElement chessboardElement = jsonObject.getAsJsonObject("game").get("board");
        chess.Board chessboard = jsonDeserializationContext.deserialize(chessboardElement, chess.Board.class);
        int ID = jsonObject.get("gameID").getAsInt();

        java.lang.reflect.Type listType = new TypeToken<ArrayList<String>>() {}.getType();
        ArrayList<String> observerList = new Gson().fromJson(observersElement, listType);

        String whiteUsername = null;
        String blackUsername = null;
        String gameName = null;
        String winner = null;

        if (jsonObject.get("whiteUsername") != null) {
            whiteUsername = jsonObject.get("whiteUsername").getAsString();
        }
        if (jsonObject.get("blackUsername") != null) {
            blackUsername = jsonObject.get("blackUsername").getAsString();
        }
        if (jsonObject.get("gameName") != null) {
            gameName = jsonObject.get("gameName").getAsString();
        }
        if (jsonObject.getAsJsonObject("game").get("winner") != null) {
            winner = jsonObject.getAsJsonObject("game").getAsJsonPrimitive("winner").getAsString();
        }

        models.Game game = new Game(ID, whiteUsername, blackUsername, gameName);
        game.getGame().setBoard(chessboard);
        game.getGame().setWinner(winner);
        game.setObservers(observerList);

        return game;
    }
}
