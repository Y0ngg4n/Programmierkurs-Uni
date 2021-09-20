package client.states;

import client.ClientState;
import client.CountDownClient;
import server.CountDownServer;
import server.ServerState;

public class ClientFrom implements ClientState {

    int startNumber;

    public ClientFrom(int startNumber) {
        this.startNumber = startNumber;
    }

    @Override
    public ClientState execute(CountDownClient connection) {
        String fromString = connection.getClientProtocol().createFrom(startNumber);
        connection.printRightArrow(fromString);
        connection.send(fromString);
        return new ClientStart();
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
