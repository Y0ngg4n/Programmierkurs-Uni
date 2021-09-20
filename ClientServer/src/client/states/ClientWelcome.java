package client.states;

import client.ClientState;
import client.CountDownClient;

public class ClientWelcome implements ClientState {

    int startNumber;

    public ClientWelcome(int startNumber) {
        this.startNumber = startNumber;
    }

    @Override
    public ClientState execute(CountDownClient connection) {
        String message = connection.recieve();
        if(!connection.getClientProtocol().isWelcomeMessage(message)){
            new ClientError(new UnsupportedOperationException());
        }
        return new ClientFrom(startNumber);
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
