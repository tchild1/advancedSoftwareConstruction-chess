import handlers.*;
import spark.Spark;

/**
 * Server class
 */
public class Server {

    /**
     * Starts the server
     *
     * @param args
     */
    public static void main(String[] args) {
        new Server().run();
    }

    /**
     * Runs servers and defines handlers for all requests
     */
    private void run() {
        int PORT = 8080;
        Spark.port(PORT);

        Spark.externalStaticFileLocation("web");

        Spark.delete("/db", (req, res) -> new ClearApplicationHandler().handler(req, res));

        Spark.post("/user", (req, res) -> new RegisterUserHandler().handler(req, res));

        Spark.post("/session", (req, res) -> new LoginHandler().handler(req, res));

        Spark.delete("/session", (req, res) -> new LogoutHandler().handler(req, res));

        Spark.get("/game", (req, res) -> new ListGamesHandler().handler(req, res));

        Spark.post("/game", (req, res) -> new CreateGameHandler().handler(req, res));

        Spark.put("/game", (req, res) -> new JoinGameHandler().handler(req, res));

        Spark.init();

        System.out.println("Server is running on port: " + PORT);
    }
}
