package server.states;

import server.CountDownServer;
import server.ServerState;

public class ServerEnd implements ServerState {
    @Override
    public ServerState execute(CountDownServer connection) {
        connection.printRightArrow("Countdown finished");
        return null;
    }

    @Override
    public boolean isEndingState() {
        return true;
    }

    @Override
    public boolean isErrorState() {
        return false;
    }
}
