package server.states;

import server.CountDownServer;
import server.ServerState;

public class ServerFrom implements ServerState {
    @Override
    public ServerState execute(CountDownServer connection) {
        String request = connection.recieve();
        connection.printLeftArrow(request);
        if (!connection.getServerProtocol().isValidFrom(request)) {
            return new ServerError(new UnsupportedOperationException());
        }

        int c = connection.getServerProtocol().extractNumber(request);
        if (c <= 0) {
            return new ServerError(new UnsupportedOperationException());
        }

        return new ServerStart(c);
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
