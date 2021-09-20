package client.states;

import client.ClientState;
import client.CountDownClient;
import server.CountDownServer;
import server.ServerState;

public class ClientError implements ClientState {

    Exception exception;

    public ClientError(Exception exception) {
        this.exception = exception;
    }

    @Override
    public ClientState execute(CountDownClient connection) {
        exception.printStackTrace();
        return null;
    }

    @Override
    public boolean isEndingState() {
        return false;
    }

    @Override
    public boolean isErrorState() {
        return true;
    }
}
