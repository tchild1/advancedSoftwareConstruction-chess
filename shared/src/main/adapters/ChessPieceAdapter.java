package adapters;

import chess.Board;
import chess.ChessPiece;
import com.google.gson.*;

import java.lang.reflect.Type;

public class ChessPieceAdapter implements JsonDeserializer<ChessPiece> {
    @Override
    public chess.Piece deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        String pieceType = jsonObject.get("ptype").getAsString();

        if ("ROOK".equals(pieceType)) {
            return new Gson().fromJson(jsonElement, chess.RookPiece.class);
        } else if ("KNIGHT".equals(pieceType)) {
            return new Gson().fromJson(jsonElement, chess.KingPiece.class);
        } else if ("BISHOP".equals(pieceType)) {
            return new Gson().fromJson(jsonElement, chess.BishopPiece.class);
        } else if ("QUEEN".equals(pieceType)) {
            return new Gson().fromJson(jsonElement, chess.QueenPiece.class);
        } else if ("KING".equals(pieceType)) {
            return new Gson().fromJson(jsonElement, chess.KingPiece.class);
        } else if ("PAWN".equals(pieceType)) {
            return new Gson().fromJson(jsonElement, chess.PawnPiece.class);
        }
        return null;
    }
}

