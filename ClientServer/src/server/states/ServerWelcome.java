package server.states;

import server.CountDownServer;
import server.ServerState;

public class ServerWelcome implements ServerState {
    @Override
    public ServerState execute(CountDownServer connection) {
        connection.send(connection.getServerProtocol().getWelcome());
        return new ServerFrom();
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
