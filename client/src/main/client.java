import chess.Board;
import chess.ChessGame;
import chess.ChessPiece;

import java.io.*;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

public class client {

    private static String tokenString = null;
    private static Scanner Scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException, InterruptedException {

        System.out.print("Welcome to Chess! Type Help to see commands.\n\n");

        boolean cont = true;
        while (cont) {
            if (!isAuthenticated()) {
                System.out.print("[LOGGED OUT] Enter a command or 'help' to see command options >>> ");
            } else {
                System.out.print("[LOGGED IN] Enter a command or 'help' to see command options >>> ");
            }

            String userInput = getUserInput().nextLine();
            cont = handleUserInput(userInput);
        }
    }

    private static boolean handleUserInput(String i) throws IOException, InterruptedException {
        var tokens = i.toLowerCase().split(" ");
        var input = (tokens.length > 0) ? tokens[0] : "help";
        var params = Arrays.copyOfRange(tokens, 1, tokens.length);

        if (Objects.equals(input, "register")) {
            if (!isAuthenticated()) {
                if (checkParameters(params, 3)) {
                    System.out.println(ServerFacade.userEnteredRegister(params[0], params[1], params[2]));
                }
            } else {
                System.out.println("Cannot register a user while logged in.");
            }
        } else if (Objects.equals(input, "login")) {
            if (isAuthenticated()) {
                System.out.println("Cannot log in while logged in.");
            } else {
                if (checkParameters(params, 2)) {
                    System.out.println(ServerFacade.userEnteredLogin(params[0], params[1]));
                }
            }
        } else if (Objects.equals(input, "quit")) {
            System.out.println("Thanks for playing!");
            return false;
        } else if (Objects.equals(input, "help")) {
            System.out.println(ServerFacade.userEnteredHelp());
        } else if (Objects.equals(input, "logout")) {
            if (isAuthenticated()) {
                System.out.println(ServerFacade.userEnteredLogout());
            } else {
                System.out.println("Cannot log out without being logged in.");
            }
        } else if (Objects.equals(input, "create")) {
            if (isAuthenticated()) {
                if (checkParameters(params, 1)) {
                    System.out.println(ServerFacade.userEnteredCreateGame(params[0]));
                }
            } else {
                System.out.println("Must be logged in to create a game.");
            }
        } else if (Objects.equals(input, "list")) {
            if (isAuthenticated()) {
                System.out.println(ServerFacade.userEnteredListGames());
            } else {
                System.out.println("Must be logged in to list games.");
            }
        } else if (Objects.equals(input, "join")) {
            if (isAuthenticated()) {
                if (checkParameters(params, 2)) {
                    System.out.println(ServerFacade.userEnteredJoin(params[0], params[1]));
                }
            } else {
                System.out.println("Must be logged in to join a game.");
            }
        } else if (Objects.equals(input, "clear")) {
            ServerFacade.userEnteredClear();
        } else {
            System.out.println("Command not recognized. Please enter a valid command.");
        }
        return true;
    }

    private static boolean checkParameters(String[] params, int numNeeded) {
        if (params.length < numNeeded) {
            System.out.println("Missing Parameters! ");
            return false;
        } else if (params.length > numNeeded) {
            System.out.println("Too many parameters! ");
            return false;
        }
        return true;
    }

    static Scanner getUserInput() {
        return Scanner;
    }

    static boolean isAuthenticated() {
        return tokenString != null;
    }

    public static String getTokenString() {
        return tokenString;
    }

    public static void setTokenString(String tokenString) {
        client.tokenString = tokenString;
    }
}
