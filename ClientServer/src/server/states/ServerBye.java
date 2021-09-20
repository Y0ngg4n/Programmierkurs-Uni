package server.states;

import server.CountDownServer;
import server.ServerState;

public class ServerBye implements ServerState {
    @Override
    public ServerState execute(CountDownServer connection) {
        connection.send(connection.getServerProtocol().getBuy());
        return new ServerEnd();
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
