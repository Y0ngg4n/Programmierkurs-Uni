import client.CountDownClient;
import server.CountDownServer;

public class Main {
    public static void main(String[] args) {
        for (int i = 0; i < 3; i++) {
            CountDownServer countDownServer = new CountDownServer(4444);
            CountDownClient countDownClient = new CountDownClient("127.0.0.1", 4444, 10);
            try {
                synchronized (countDownServer.getTalk()){
                    countDownServer.getTalk().wait();
                }
                synchronized (countDownClient.getTalk()){
                    countDownClient.getTalk().wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
