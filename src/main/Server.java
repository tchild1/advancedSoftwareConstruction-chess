import handlers.*;
import spark.Spark;

public class Server {

    public static void main(String[] args) {
        new Server().run();
    }

    private void run() {
        Spark.port(8080);

        Spark.externalStaticFileLocation("web");

        Spark.delete("/db", (req, res) -> new ClearApplicationHandler().handler(req, res));

        Spark.post("/user", (req, res) -> new RegisterUserHandler().handler(req, res));

        Spark.post("/session", (req, res) -> new LoginHandler().handler(req, res));

        Spark.delete("/session", (req, res) -> new LogoutHandler().handler(req, res));

        Spark.get("/game", (req, res) -> new ListGamesHandler().handler(req, res));

        Spark.post("/game", (req, res) -> new CreateGameHandler().handler(req, res));

        Spark.put("/game", (req, res) -> new JoinGameHandler().handler(req, res));

        Spark.init();
    }
}
