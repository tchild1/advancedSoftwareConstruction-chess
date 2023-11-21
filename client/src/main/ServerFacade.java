import adapters.ChessBoardAdapter;
import adapters.ChessGameAdapter;
import adapters.ChessPieceAdapter;
import adapters.ListGamesAdapter;
import chess.Board;
import chess.ChessGame;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import models.Game;
import requests.*;
import responses.ListGamesResponse;
import responses.LoginResponse;
import responses.RegisterUserResponse;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Objects;

public class ServerFacade {

    public static void userEnteredClear() throws IOException, InterruptedException {
        HttpResponse<String> response = makeRequest("/db", "DELETE", new ClearApplicationRequest());

        if (response.statusCode() == 200) {
            System.out.println("Database cleared.");
        } else {
            System.out.println("Error occurred clearing the database. ");
        }
    }

    public static void userEnteredJoin() throws IOException, InterruptedException {
        userEnteredListGames();

        System.out.println("Please enter the ID of the game you would like to join: ");
        String gameToJoin = client.getUserInput().next();

        System.out.println("What color would you like to play? [W/B/O])");
        String colorToPlay = client.getUserInput().next();

        ChessGame.TeamColor requestColor;
        if (Objects.equals(colorToPlay, "W")) {
            requestColor = ChessGame.TeamColor.WHITE;
        } else if (Objects.equals(colorToPlay, "B")) {
            requestColor = ChessGame.TeamColor.BLACK;
        } else {
            requestColor = null;
        }

        HttpResponse<String> response = makeRequest("/game", "PUT", new JoinGameRequest(client.getTokenString(), requestColor, gameToJoin));

        if (response.statusCode() == 200) {
            System.out.println("Added to game " + gameToJoin + " Successfully");
            System.out.print("\n");

            Board board = new Board();
            board.resetBoard();
            Display.printChessboardForBlack(board);
            System.out.print("\n");
            System.out.print("\n");
            Display.printChessboardForWhite(board);
        } else {
            System.out.println("Failed to add user to game.");
        }
    }

    public static void userEnteredListGames() throws IOException, InterruptedException {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(models.Game.class, new ChessGameAdapter());
        builder.registerTypeAdapter(chess.Board.class, new ChessBoardAdapter());
        builder.registerTypeAdapter(chess.Piece.class, new ChessPieceAdapter());
        builder.registerTypeAdapter(responses.ListGamesResponse.class, new ListGamesAdapter());

        HttpResponse<String> response = makeRequest("/game", "GET", new ListGamesRequest(client.getTokenString()));

        if (response.statusCode() != 200) {
            System.out.println("Problem occurred when listing games.");
        } else {
            ListGamesResponse games = builder.create().fromJson(response.body(), ListGamesResponse.class);
            ArrayList<Game> allGames = games.getGames();

            System.out.println("All Games: ");
            for (Game currGame : allGames) {
                System.out.println("    Game ID: " + currGame.getGameID());
                System.out.println("    Game Name: " + currGame.getGameName());
                if (currGame.getWhiteUsername() != null) {
                    System.out.println("    White Player: " + currGame.getWhiteUsername());
                } else {
                    System.out.println("    White Player: [NONE]");
                }
                if (currGame.getBlackUsername() != null) {
                    System.out.println("    Black Player: " + currGame.getBlackUsername());
                } else {
                    System.out.println("    Black Player: [NONE]");
                }
                System.out.println();
            }
            System.out.println();
        }
    }

    public static void userEnteredCreateGame() throws IOException, InterruptedException {
        System.out.println("Please enter the game's name: ");
        String gameName = client.getUserInput().next();
        HttpResponse<String> response = makeRequest("/game", "POST", new CreateGameRequest(client.getTokenString(), gameName));

        if (response.statusCode() != 200) {
            System.out.println("Error occurred when creating game. ");
        } else {
            System.out.println("Game created. ");
        }
    }

    public static void userEnteredLogout() throws IOException, InterruptedException {
        HttpResponse<String> response = makeRequest("/session", "DELETE", null);

        if (response.statusCode() == 200) {
            client.setTokenString(null);
        } else {
            System.out.println("There was an error logging out.");
        }
    }

    public static void userEnteredRegister() throws IOException, InterruptedException {
        System.out.println("Please enter your username: ");
        String username = client.getUserInput().nextLine();

        System.out.println("Please enter your password: ");
        String password = client.getUserInput().nextLine();

        System.out.println("Please enter your email: ");
        String email = client.getUserInput().nextLine();

        HttpResponse<String> response = makeRequest("/user", "POST", new RegisterUserRequest(username, password, email));

        if (response.statusCode() == 200) {
            RegisterUserResponse registerUserResponse = new Gson().fromJson(response.body(), RegisterUserResponse.class);
            client.setTokenString(registerUserResponse.getAuthToken());
        } else {
            System.out.println("There was an error registering user.");
        }
    }

    public static void userEnteredLogin() throws IOException, InterruptedException {
        System.out.println("Please enter your username: ");
        String username = client.getUserInput().nextLine();

        System.out.println("Please enter your password: ");
        String password = client.getUserInput().nextLine();

        HttpResponse<String> response = makeRequest("/session", "POST", new LoginRequest(username, password));

        if (response.statusCode() == 200) {
            LoginResponse loginResponse = new Gson().fromJson(response.body(), LoginResponse.class);
            client.setTokenString(loginResponse.getToken());
        } else {
            System.out.println("There was an error logging in. ");
        }
    }

    public static void userEnteredHelp() {
        if (!client.isAuthenticated()) {
            System.out.println("\nOptions:");
            System.out.println("register - to create an account");
            System.out.println("login - to play chess");
            System.out.println("quit - stop playing chess");
            System.out.println("help - with possible commands");
            System.out.println();
        } else {
            System.out.println("\nOptions:");
            System.out.println("create - a game");
            System.out.println("list - games");
            System.out.println("join - a game");
            System.out.println("logout - when you are done");
            System.out.println("quit - playing chess");
            System.out.println("help - with possible commands");
            System.out.println();
        }
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
