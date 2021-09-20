package client.states;

import client.ClientState;
import client.CountDownClient;

public class ClientStart implements ClientState {

    @Override
    public ClientState execute(CountDownClient connection) {
        String message = connection.recieve();
        if (!connection.getClientProtocol().isStartMessage(message)) {
            new ClientError(new UnsupportedOperationException());
        }
        return new ClientCount();
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
