
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;


import static org.junit.jupiter.api.Assertions.*;

class ServerFacadeTest {

    @BeforeEach
    void clear() throws IOException, InterruptedException {
        ServerFacade.userEnteredClear();
        client.setTokenString(null);
    }

    @Test
    void clearPositive() throws IOException, InterruptedException {
        ServerFacade.userEnteredClear();
    }

    @Test
    void joinPositive() throws IOException, InterruptedException {
        ServerFacade.userEnteredRegister("testUser", "pass", "email");
        String gameID = ServerFacade.userEnteredCreateGame("testGame").split(" ")[1];

        assertEquals("Added to game " + gameID +  " Successfully", ServerFacade.userEnteredJoin(gameID, "W"));
    }

    @Test
    void joinNegative() throws IOException, InterruptedException {
        ServerFacade.userEnteredRegister("testUser", "pass", "email");

        assertEquals("Failed to add user to game.", ServerFacade.userEnteredJoin("0000", "W"), "Game does not exist, you should not be able to join it.");
    }

    @Test
    void listGamesPositive() throws IOException, InterruptedException {
        ServerFacade.userEnteredRegister("testUser", "pass", "email");

        ServerFacade.userEnteredCreateGame("testGame");

        assertTrue(ServerFacade.userEnteredListGames().matches("All Games:\\s*" +
                "Game ID: \\d{4}\\s*" +
                "Game Name: testGame\\s*" +
                "White Player: \\[NONE]\\s*" +
                "Black Player: \\[NONE]\\s*"));
    }

    @Test
    void listGamesNegative() throws IOException, InterruptedException {
        ServerFacade.userEnteredRegister("testUser", "pass", "email");

        assertFalse(ServerFacade.userEnteredListGames().matches("All Games:\\s*" +
                "Game ID: \\d{4}\\s*" +
                "Game Name: .*\\s*" +
                "White Player: \\[NONE]\\s*" +
                "Black Player: \\[NONE]\\s*"), "There should not be any games listed. ");
    }

    @Test
    void createGamePositive() throws IOException, InterruptedException {
        ServerFacade.userEnteredRegister("testUser", "pass", "email");

        // logout user
        assertTrue(ServerFacade.userEnteredCreateGame("testGame").matches("testGame \\d{4} created."), "Unable to create game");
    }

    @Test
    void createGameNegative() throws IOException, InterruptedException {
        // logout user
        assertEquals("Error occurred when creating game.", ServerFacade.userEnteredCreateGame("testGame"), "Must be signed in to create a game. ");
    }

    @Test
    void logoutPositive() throws IOException, InterruptedException {
        // create user
        ServerFacade.userEnteredRegister("testUser", "test", "email");

        // logout user
        assertEquals("Logout successful.", ServerFacade.userEnteredLogout(), "Unable to log out user. ");
    }

    @Test
    void logoutNegative() throws IOException, InterruptedException {
        // logout user
        assertEquals("There was an error logging out.", ServerFacade.userEnteredLogout(), "Shouldn't be able to log out a user when not logged in. ");
    }

    @Test
    void registerPositive() throws IOException, InterruptedException {
        // login new user
        assertEquals("New User testUser was created.", ServerFacade.userEnteredRegister("testUser", "test", "email"), "Unable to register a new user. ");
    }

    @Test
    void registerNegative() throws IOException, InterruptedException {
        ServerFacade.userEnteredRegister("testUser", "test", "email");

        // login new user
        assertEquals("There was an error registering user.", ServerFacade.userEnteredRegister("testUser", "test", "email"), "Should not have been able to register a duplicate user. ");
    }

    @Test
    void loginPositive() throws IOException, InterruptedException {
        // register new user
        ServerFacade.userEnteredRegister("testUser", "test", "email");

        // login new user
        assertEquals("testUser is signed in.", ServerFacade.userEnteredLogin("testUser", "test"));
    }

    @Test
    void loginNegative() throws IOException, InterruptedException {
        // register new user
        ServerFacade.userEnteredRegister("testUser", "test", "email");

        // login new user
        assertEquals("There was an error logging in.", ServerFacade.userEnteredLogin("testUser", "wrongPassword"));
    }

    @Test
    void helpPositive() throws IOException, InterruptedException {
        assertEquals("\n" +
                "Options:\n" +
                "register <username> <password> <email> - to create an account\n" +
                "login <username> <password> - to play chess\n" +
                "quit - stop playing chess\n" +
                "help - with possible commands\n" +
                "\n", ServerFacade.userEnteredHelp(), "Should show options when user enters help. ");
    }

    @Test
    void helpNegative() throws IOException, InterruptedException {
        String inputString = "testUser\ntest\nemail";
        System.setIn(new ByteArrayInputStream(inputString.getBytes()));

        ServerFacade.userEnteredClear();
        ServerFacade.userEnteredRegister("testUser", "test", "email");

        assertNotEquals("\n" +
                "Options:\n" +
                "register <username> <password> <email> - to create an account\n" +
                "login <username> <password> - to play chess\n" +
                "quit - stop playing chess\n" +
                "help - with possible commands\n" +
                "\n", ServerFacade.userEnteredHelp(), "Should show logged in options when user enters help after logging in. ");
    }
}