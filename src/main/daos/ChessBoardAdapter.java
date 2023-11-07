package daos;

import chess.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ChessBoardAdapter implements JsonDeserializer<chess.Board> {
    @Override
    public chess.Board deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonElement boardElement = jsonElement.getAsJsonObject().get("board");
        JsonArray boardArray = boardElement.getAsJsonArray();

        Board board = new Board();

        for (int row=0;row<boardArray.size();row++) {
            JsonArray currRow = boardArray.get(row).getAsJsonArray();
            for (int column=0;column<currRow.size();column++) {
                chess.Piece currPiece;
                if (!currRow.get(column).isJsonNull()) {
                    currPiece = jsonDeserializationContext.deserialize(currRow.get(column).getAsJsonObject(), chess.Piece.class);
                } else {
                    currPiece = null;
                }
                board.addPiece(new Position(row, column), currPiece);
            }
        }

        return board;
    }
}
