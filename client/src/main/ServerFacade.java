import adapters.ChessBoardAdapter;
import adapters.ChessGameAdapter;
import adapters.ChessPieceAdapter;
import adapters.ListGamesAdapter;
import chess.ChessGame;
import chess.Move;
import chess.Position;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import models.Game;
import requests.*;
import responses.CreateGameResponse;
import responses.ListGamesResponse;
import responses.LoginResponse;
import responses.RegisterUserResponse;
import webSocketMessages.userCommands.*;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class ServerFacade {

    public static void userEnteredClear() throws IOException, InterruptedException {
        HttpResponse<String> response = makeRequest("/db", "DELETE", new ClearApplicationRequest());

        if (response.statusCode() == 200) {
            System.out.println("Database cleared.");
        } else {
            System.out.println("Error occurred clearing the database. ");
        }
    }

    public static String userEnteredJoin(String gameToJoin, String colorToPlay) throws Exception {

        ChessGame.TeamColor requestColor;
        if (Objects.equals(colorToPlay, "w")) {
            requestColor = ChessGame.TeamColor.WHITE;
        } else if (Objects.equals(colorToPlay, "b")) {
            requestColor = ChessGame.TeamColor.BLACK;
        } else {
            requestColor = ChessGame.TeamColor.OBSERVER;
        }

        HttpResponse<String> response = makeRequest("/game", "PUT", new JoinGameRequest(client.getTokenString(), requestColor, gameToJoin));

        if (response.statusCode() == 200) {
            if (requestColor == ChessGame.TeamColor.OBSERVER) {
                client.setUserColor(requestColor);
                client.setGameID(gameToJoin);
                client.setGameConnection(new WSFacade());
                JoinObserverCommand joinObserverCommand = new JoinObserverCommand(client.getTokenString());
                joinObserverCommand.setUserName(client.getUserName());
                joinObserverCommand.setGameID(client.getGameID());
                CompletableFuture<String> WSResponse = client.getGameConnection().sendCommandAndWaitForResponse(joinObserverCommand);
                WSResponse.get();
            } else {
                client.setUserColor(requestColor);
                client.setGameID(gameToJoin);
                client.setGameConnection(new WSFacade());
                JoinPlayerCommand joinPlayerCommand = new JoinPlayerCommand(client.getTokenString(), gameToJoin, requestColor);
                joinPlayerCommand.setUserName(client.getUserName());
                CompletableFuture<String> WSResponse = client.getGameConnection().sendCommandAndWaitForResponse(joinPlayerCommand);
                WSResponse.get();
            }


            return "Added to game " + gameToJoin + " Successfully";
        } else {
            return "Failed to add user to game.";
        }
    }

    public static String userEnteredListGames() throws IOException, InterruptedException {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(models.Game.class, new ChessGameAdapter());
        builder.registerTypeAdapter(chess.Board.class, new ChessBoardAdapter());
        builder.registerTypeAdapter(chess.Piece.class, new ChessPieceAdapter());
        builder.registerTypeAdapter(responses.ListGamesResponse.class, new ListGamesAdapter());

        HttpResponse<String> response = makeRequest("/game", "GET", new ListGamesRequest(client.getTokenString()));

        if (response.statusCode() != 200) {
            return "Problem occurred when listing games.";
        } else {
            ListGamesResponse games = builder.create().fromJson(response.body(), ListGamesResponse.class);
            ArrayList<Game> allGames = games.getGames();
            StringBuilder gamesList = new StringBuilder();
            gamesList.append("All Games: \n"); 
            for (Game currGame : allGames) {
                gamesList.append("    Game ID: ").append(currGame.getGameID()).append("\n");
                gamesList.append("    Game Name: ").append(currGame.getGameName()).append("\n");
                if (currGame.getWhiteUsername() != null) {
                    gamesList.append("    White Player: ").append(currGame.getWhiteUsername()).append("\n");
                } else {
                    gamesList.append("    White Player: [NONE]" + "\n");
                }
                if (currGame.getBlackUsername() != null) {
                    gamesList.append("    Black Player: ").append(currGame.getBlackUsername()).append("\n");
                } else {
                    gamesList.append("    Black Player: [NONE]" + "\n");
                }
                gamesList.append("\n");
            }

            gamesList.append("\n");
            return gamesList.toString();
        }
    }

    public static String userEnteredCreateGame(String gameName) throws IOException, InterruptedException {
        HttpResponse<String> response = makeRequest("/game", "POST", new CreateGameRequest(client.getTokenString(), gameName));

        CreateGameResponse createGameResponse = new Gson().fromJson(response.body(), CreateGameResponse.class);

        if (response.statusCode() != 200) {
            return "Error occurred when creating game.";
        } else {
            return gameName + " " + createGameResponse.getGameID() + " created.";
        }
    }

    public static String userEnteredLogout() throws IOException, InterruptedException {
        HttpResponse<String> response = makeRequest("/session", "DELETE", null);

        if (response.statusCode() == 200) {
            client.setTokenString(null);
            return "Logout successful.";
        } else {
            return "There was an error logging out.";
        }
    }

    public static String userEnteredRegister(String username, String password, String email) throws IOException, InterruptedException {
        HttpResponse<String> response = makeRequest("/user", "POST", new RegisterUserRequest(username, password, email));

        if (response.statusCode() == 200) {
            RegisterUserResponse registerUserResponse = new Gson().fromJson(response.body(), RegisterUserResponse.class);
            client.setTokenString(registerUserResponse.getAuthToken());
            client.setUsername(username);
            return "New User " + username + " was created.";
        } else {
            return "There was an error registering user.";
        }
    }

    public static String userEnteredLogin(String username, String password) throws IOException, InterruptedException {
        HttpResponse<String> response = makeRequest("/session", "POST", new LoginRequest(username, password));

        if (response.statusCode() == 200) {
            LoginResponse loginResponse = new Gson().fromJson(response.body(), LoginResponse.class);
            client.setTokenString(loginResponse.getToken());
            client.setUsername(username);
            return username + " is signed in.";
        } else {
            return "There was an error logging in.";
        }
    }

    public static String userEnteredMakeMove(String moveFrom, String moveTo) throws ExecutionException, InterruptedException, IOException {
        Position from = inputToPosition(moveFrom);
        Position to = inputToPosition(moveTo);
        CompletableFuture<String> WSResponse = client.getGameConnection().sendCommandAndWaitForResponse(new MakeMoveCommand(client.getTokenString(), client.getGameID(), new Move(from, to, null), client.getUserName()));
        WSResponse.get();

        return "You have moved to " + moveTo + " successfully";
    }

    public static String userEnteredLeave() throws ExecutionException, InterruptedException, IOException {
        CompletableFuture<String> WSResponse = client.getGameConnection().sendCommandAndWaitForResponse(new LeaveCommand(client.getTokenString(), client.getGameID()));
        WSResponse.get();

        client.setCurrBoard(null);
        client.setUserColor(null);
        client.setGameID(null);
        client.setGameConnection(null);
        return "";
    }

    public static String userEnteredResign() throws IOException, ExecutionException, InterruptedException {
        CompletableFuture<String> WSResponse = client.getGameConnection().sendCommandAndWaitForResponse(new ResignCommand(client.getTokenString(), client.getGameID(), client.getUserColor()));
        WSResponse.get();

        return "";
    }

    private static Position inputToPosition(String coordinate) {
        int row = numberToIndex(coordinate.substring(1, 2));
        int col = letterToIndex(coordinate.substring(0, 1));

        return new Position(row, col);
    }

    private static int numberToIndex(String number) {
        return switch (number.charAt(0)) {
            case '8' -> 0;
            case '7' -> 1;
            case '6' -> 2;
            case '5' -> 3;
            case '4' -> 4;
            case '3' -> 5;
            case '2' -> 6;
            case '1' -> 7;
            default -> 0;
        };
    }
    private static int letterToIndex(String letter) {
        return switch (letter.charAt(0)) {
            case 'a' -> 0;
            case 'b' -> 1;
            case 'c' -> 2;
            case 'd' -> 3;
            case 'e' -> 4;
            case 'f' -> 5;
            case 'g' -> 6;
            case 'h' -> 7;
            default -> 0;
        };
    }

    public static String userEnteredHelp() {
        StringBuilder returnString = new StringBuilder();
        if (!client.isAuthenticated()) {
            returnString.append("\nOptions:\n");
            returnString.append("register <username> <password> <email> - to create an account\n");
            returnString.append("login <username> <password> - to play chess\n");
            returnString.append("quit - stop playing chess\n");
            returnString.append("help - with possible commands\n");
            returnString.append("\n");
        } else {
            if (client.isInGame()) {
                returnString.append("\nOptions:\n");
                returnString.append("redraw chess board\n");
                returnString.append("leave - the game\n");
                returnString.append("make move\n");
                returnString.append("resign - from game\n");
                returnString.append("highlight legal moves\n");
                returnString.append("help - with possible commands\n");
                returnString.append("\n");
            } else {
                returnString.append("\nOptions:\n");
                returnString.append("create <game name> - a game\n");
                returnString.append("list - games\n");
                returnString.append("join <game ID> <W/B/O> - a game\n");
                returnString.append("logout - when you are done\n");
                returnString.append("quit - playing chess\n");
                returnString.append("help - with possible commands\n");
                returnString.append("\n");
            }
        }
        return returnString.toString();
    }

    public void userEnteredHighlight() {

    }

    private static HttpResponse<String> makeRequest(String route, String method, Request body) throws IOException, InterruptedException {

        HttpClient httpClient = HttpClient.newHttpClient();

        HttpRequest request = null;
        if (Objects.equals(method, "POST")) {
            if (!client.isAuthenticated()) {
                request = HttpRequest.newBuilder()
                        .uri(URI.create("http://localhost:8080"+route))
                        .POST(HttpRequest.BodyPublishers.ofString(new Gson().toJson(body)))
                        .build();
            } else {
                request = HttpRequest.newBuilder()
                        .uri(URI.create("http://localhost:8080"+route))
                        .POST(HttpRequest.BodyPublishers.ofString(new Gson().toJson(body)))
                        .header("Authorization", client.getTokenString())
                        .build();
            }
        } else if (Objects.equals(method, "DELETE")) {
            if (client.isAuthenticated()) {
                request = HttpRequest.newBuilder()
                        .uri(URI.create("http://localhost:8080"+route))
                        .DELETE()
                        .header("Authorization", client.getTokenString())
                        .build();
            } else {
                request = HttpRequest.newBuilder()
                        .uri(URI.create("http://localhost:8080"+route))
                        .DELETE()
                        .build();
            }

        } else if (Objects.equals(method, "GET")) {
            request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080"+route))
                    .GET()
                    .header("Authorization", client.getTokenString())
                    .build();
        } else if (Objects.equals(method, "PUT")) {
            request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080"+route))
                    .method("PUT", HttpRequest.BodyPublishers.ofString(new Gson().toJson(body)))
                    .header("Authorization", client.getTokenString())
                    .build();
        }

        return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    }
}
