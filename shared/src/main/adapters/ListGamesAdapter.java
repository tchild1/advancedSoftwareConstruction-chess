package adapters;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import models.Game;
import responses.ListGamesResponse;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ListGamesAdapter implements JsonDeserializer<ListGamesResponse> {
    @Override
    public ListGamesResponse deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        JsonElement gamesElement = jsonObject.get("games");
        JsonArray gamesArray = gamesElement.getAsJsonArray();

        ArrayList<Game> gamesList = new ArrayList<>();

        for (int game=0;game<gamesArray.size();game++) {
            gamesList.add(jsonDeserializationContext.deserialize(gamesArray.get(game).getAsJsonObject(), Game.class));
        }

        return new ListGamesResponse(gamesList);
    }
}
