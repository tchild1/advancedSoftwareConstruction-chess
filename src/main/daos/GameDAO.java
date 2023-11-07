package daos;

import chess.ChessGame;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import exceptions.DataAccessException;
import exceptions.ForbiddenException;
import models.Game;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;

/**
 * This table provides access to game database tables
 */
public class GameDAO {

    /**
     * Adds a game to the Database
     *
     * @param game object to be added
     * @throws DataAccessException if there is an error accessing database, exception is thrown
     */
    public static int CreateGame(Game game) throws DataAccessException, dataAccess.DataAccessException, SQLException {
        String sql = "INSERT INTO chess.game (game, game_name)" +
                     "VALUES (?, ?)";
        Connection connection = new Database().getConnection();

        PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, new Gson().toJson(game));
        statement.setString(2, game.getGameName());
        int rowsInserted = statement.executeUpdate();

        int newId=0;
        if (rowsInserted > 0) {
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                newId = (int) generatedKeys.getLong(1);
            }
        }

        String updateSql = "SELECT game FROM chess.game WHERE game_id=?";
        PreparedStatement updateStatement = connection.prepareStatement(updateSql);
        updateStatement.setString(1, String.valueOf(newId));
        ResultSet updateResult = updateStatement.executeQuery();

        if (updateResult.next()) {
            String json = updateResult.getString(1);
            GsonBuilder builder = createBuilder();

            models.Game updateGame = builder.create().fromJson(json, models.Game.class);

            updateDatabaseGame(connection, String.valueOf(newId), updateGame);
        }
        new Database().closeConnection(connection);
        return newId;
    }

    /**
     * Deletes all games from the database
     *
     * @throws DataAccessException if there is an error accessing the database, exception is thrown
     */
    public static void DeleteAllGames() throws DataAccessException, dataAccess.DataAccessException, SQLException {
        String sql = "DELETE FROM chess.game;";
        Connection connection = new Database().getConnection();

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.executeUpdate();

        new Database().closeConnection(connection);
    }

    /**
     * Adds a user to a current game
     *
     * @param gameID being played
     * @throws DataAccessException if there is an error accessing the database, exception is thrown
     */
    public static void AddPlayerToGame(String gameID, ChessGame.TeamColor color, String username) throws DataAccessException, ForbiddenException, dataAccess.DataAccessException, SQLException {
        String sql = "SELECT * FROM chess.game WHERE game_id=?";
        Connection connection = new Database().getConnection();

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, gameID);
        ResultSet result = statement.executeQuery();

        if (result.next()) {
            if (color == ChessGame.TeamColor.BLACK) {
                if (result.getString(4) == null) {
                    updatePlayer(color, connection, username, gameID);
                } else {
                    throw new ForbiddenException("Error: already taken");
                }
            } else if (color == ChessGame.TeamColor.WHITE) {
                if (result.getString(5) == null) {
                    updatePlayer(color, connection, username, gameID);
                } else {
                    throw new ForbiddenException("Error: already taken");
                }
            }
        } else {
            throw new ForbiddenException("Error: Game with this id does not exist");
        }
        new Database().closeConnection(connection);
    }

    /**
     * Adds a user as an observer to a game
     *
     * @param gameID ID of Game that the user wants to observe
     * @param username of the Observer
     * @throws DataAccessException if there is an error, exception is thrown
     */
    public static void AddObserverToGame(String gameID, String username) throws DataAccessException, dataAccess.DataAccessException, SQLException {
        String sql = "SELECT game FROM chess.game WHERE game_id=?";
        Connection connection = new Database().getConnection();

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, gameID);
        ResultSet result = statement.executeQuery();

        String json;
        if (result.next()) {
            json = result.getString(1);

            GsonBuilder builder = createBuilder();

            models.Game game = builder.create().fromJson(json, models.Game.class);
            game.addObservers(username);

            updateDatabaseGame(connection, gameID, game);
        }
        new Database().closeConnection(connection);
    }

    /**
     * Gets all current games from the database
     *
     * @return a list of all games being played
     * @throws DataAccessException if there is an error, exception is thrown
     */
    public static ArrayList<Game> GetAllGames() throws DataAccessException, dataAccess.DataAccessException, SQLException {
        String sql = "SELECT game, game_id " +
                     "FROM chess.game ";
        Connection connection = new Database().getConnection();

        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet result = statement.executeQuery();

        ArrayList<models.Game> allGames = new ArrayList<Game>();

        while (result.next()) {
            String json = result.getString(1);
            int ID = result.getInt(2);
            GsonBuilder builder = new GsonBuilder();
            builder.registerTypeAdapter(models.Game.class, new ChessGameAdapter());
            builder.registerTypeAdapter(chess.Board.class, new ChessBoardAdapter());
            builder.registerTypeAdapter(chess.Piece.class, new ChessPieceAdapter());

            models.Game game = builder.create().fromJson(json, models.Game.class);
            game.setGameID(ID);
            allGames.add(game);
        }
        Collections.reverse(allGames);
        return allGames;
    }

    /**
     * Returns True if a game with this ID exists, false otherwise
     *
     * @param gameID ID of the game to check if it exists
     * @return Boolean value
     */
    public static Boolean GameExists(String gameID) throws dataAccess.DataAccessException, SQLException {
        String sql = "SELECT * FROM chess.game WHERE game_id=?";
        Connection connection = new Database().getConnection();

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, gameID);
        ResultSet result = statement.executeQuery();

        boolean response = result.next();
        new Database().closeConnection(connection);

        return response;
    }

    private static void updatePlayer(ChessGame.TeamColor color, Connection connection, String username, String gameID) throws SQLException {
        String sql;
        if (color == ChessGame.TeamColor.BLACK) {
            sql = "UPDATE chess.game SET black_username=? WHERE game_id=?;";
        } else {
            sql = "UPDATE chess.game SET white_username=? WHERE game_id=?;";
        }
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, gameID);
        preparedStatement.executeUpdate();

        String sqlUpdateGame = "SELECT game FROM chess.game WHERE game_id=?";
        PreparedStatement preparedUpdateGame = connection.prepareStatement(sqlUpdateGame);
        preparedUpdateGame.setString(1, gameID);
        ResultSet updateResult = preparedUpdateGame.executeQuery();

        if (updateResult.next()) {
            String json = updateResult.getString(1);
            GsonBuilder builder = createBuilder();

            models.Game game = builder.create().fromJson(json, models.Game.class);
            if (color == ChessGame.TeamColor.BLACK) {
                game.setBlackUsername(username);
            } else {
                game.setWhiteUsername(username);
            }

            updateDatabaseGame(connection, gameID, game);
        }
    }

    static GsonBuilder createBuilder() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(models.Game.class, new ChessGameAdapter());
        builder.registerTypeAdapter(chess.Board.class, new ChessBoardAdapter());
        builder.registerTypeAdapter(chess.Piece.class, new ChessPieceAdapter());

        return builder;
    }

    private static void updateDatabaseGame(Connection connection, String gameID, Game game) throws SQLException {
        String sqlUpdate = "UPDATE chess.game " +
                "SET game=? " +
                "WHERE game_id=? ";

        PreparedStatement updateStatement = connection.prepareStatement(sqlUpdate);
        updateStatement.setString(1, new Gson().toJson(game));
        updateStatement.setString(2, gameID);
        updateStatement.executeUpdate();
    }

}
