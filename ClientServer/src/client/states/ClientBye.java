package client.states;

import client.ClientState;
import client.CountDownClient;

public class ClientBye implements ClientState {
    @Override
    public ClientState execute(CountDownClient connection) {
        String message = connection.recieve();
        if(!connection.getClientProtocol().isByeMessage(message)){
            new ClientError(new UnsupportedOperationException());
        }
        return new ClientEnd();
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
