package server.states;

import server.CountDownServer;
import server.ServerState;

public class ServerStart implements ServerState {

    int c;

    public ServerStart(int c) {
        this.c = c;
    }

    @Override
    public ServerState execute(CountDownServer connection) {
        connection.send(connection.getServerProtocol().getStart() + c);
        connection.printRightArrow("Starting Countdown");
        connection.send(String.valueOf(c));

        return new ServerCount(c);
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
