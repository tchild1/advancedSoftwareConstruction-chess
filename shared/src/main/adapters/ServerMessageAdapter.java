package adapters;

import com.google.gson.*;
import models.Game;
import webSocketMessages.serverMessages.ErrorMessage;
import webSocketMessages.serverMessages.LoadGameMessage;
import webSocketMessages.serverMessages.NotificationMessage;
import webSocketMessages.serverMessages.ServerMessage;

import java.lang.reflect.Type;
import java.util.UUID;

public class ServerMessageAdapter implements JsonDeserializer<ServerMessage> {
    @Override
    public ServerMessage deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        JsonElement commandType = jsonObject.get("serverMessageType");

        UUID requestId = null;
        if (jsonObject.get("requestId") != null) {
            requestId = UUID.fromString(jsonObject.getAsJsonPrimitive("requestId").getAsString());
        }

        ServerMessage returnMessage = null;
        switch (commandType.getAsString()) {
            case "LOAD_GAME" -> returnMessage = createLoadGameMessage(jsonObject, jsonDeserializationContext);
            case "ERROR" -> returnMessage = createErrorMessage(jsonObject);
            case "NOTIFICATION" -> returnMessage = createNotificationMessage(jsonObject);
        }

        assert returnMessage != null;
        returnMessage.setRequestId(requestId);
        return returnMessage;
    }

    private LoadGameMessage createLoadGameMessage(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext) {
        Game game = jsonDeserializationContext.deserialize(jsonObject.getAsJsonObject("game"), Game.class);

        return new LoadGameMessage(game);
    }

    private ErrorMessage createErrorMessage(JsonObject jsonObject) {
        return new ErrorMessage(jsonObject.getAsJsonPrimitive("errorMessage").getAsString());
    }

    private NotificationMessage createNotificationMessage(JsonObject jsonObject) {
        return new NotificationMessage(jsonObject.getAsJsonPrimitive("message").getAsString());
    }
}
