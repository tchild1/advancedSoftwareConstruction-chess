package adapters;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import models.Game;
import webSocketMessages.userCommands.JoinPlayerCommand;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class JoinPlayerCommandAdapter implements JsonDeserializer<JoinPlayerCommand> {
    @Override
    public JoinPlayerCommand deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        JsonElement observersElement = jsonObject.get("commandType");
        JsonElement chessboardElement = jsonObject.getAsJsonObject("game").get("board");
        chess.Board chessboard = jsonDeserializationContext.deserialize(chessboardElement, chess.Board.class);
        int ID = jsonObject.get("gameID").getAsInt();

        java.lang.reflect.Type listType = new TypeToken<ArrayList<String>>() {}.getType();
        ArrayList<String> observerList = new Gson().fromJson(observersElement, listType);


        models.Game game = new Game(ID, null, null, null);
        game.getGame().setBoard(chessboard);
        game.setObservers(observerList);

        return null;
    }
}
