import client.CountDownClient;
import server.CountDownServer;

public class Main {
    public static void main(String[] args) {
        new CountDownServer(4444);
        new CountDownClient("127.0.0.1", 4444, 10);
    }
}
