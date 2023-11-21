import chess.Board;
import chess.ChessGame;
import chess.ChessPiece;

import java.io.*;
import java.util.Objects;
import java.util.Scanner;

public class client {

    private static String tokenString = null;

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

    private static boolean handleUserInput(String input) throws IOException, InterruptedException {
        if (Objects.equals(input, "register")) {
            if (!isAuthenticated()) {
                ServerFacade.userEnteredRegister();
            } else {
                System.out.println("Cannot register a user while logged in.");
            }
        } else if (Objects.equals(input, "login")) {
            if (isAuthenticated()) {
                System.out.println("Cannot log in while logged in.");
            } else {
                ServerFacade.userEnteredLogin();
            }
        } else if (Objects.equals(input, "quit")) {
            System.out.println("Thanks for playing!");
            return false;
        } else if (Objects.equals(input, "help")) {
            ServerFacade.userEnteredHelp();
        } else if (Objects.equals(input, "logout")) {
            if (isAuthenticated()) {
                ServerFacade.userEnteredLogout();
            } else {
                System.out.println("Cannot log out without being logged in.");
            }
        } else if (Objects.equals(input, "create")) {
            if (isAuthenticated()) {
                ServerFacade.userEnteredCreateGame();
            } else {
                System.out.println("Must be logged in to create a game.");
            }
        } else if (Objects.equals(input, "list")) {
            if (isAuthenticated()) {
                ServerFacade.userEnteredListGames();
            } else {
                System.out.println("Must be logged in to list games.");
            }
        } else if (Objects.equals(input, "join")) {
            if (isAuthenticated()) {
                ServerFacade.userEnteredJoin();
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

    static Scanner getUserInput() {
        return new Scanner(System.in);
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
