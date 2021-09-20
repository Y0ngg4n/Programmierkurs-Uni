package server.states;

import server.CountDownServer;
import server.ServerState;

public class ServerCount implements ServerState {
    int c;

    public ServerCount(int c) {
        this.c = c;
    }

    @Override
    public ServerState execute(CountDownServer connection) {
        connection.printRightArrow(String.valueOf(c));
        for (int i = c - 1; i >= 0; i--) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                return new ServerError(e);
            }
            connection.send(String.valueOf(i));
            connection.printRightArrow(String.valueOf(i));
        }
        return new ServerBye();
    }

    @Override
    public boolean isEndingState() {
        return false;
    }

    @Override
    public boolean isErrorState() {
        return false;
    }
}
