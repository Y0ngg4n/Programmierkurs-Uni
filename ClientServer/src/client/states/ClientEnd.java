package client.states;

import client.ClientState;
import client.CountDownClient;
import server.CountDownServer;
import server.ServerState;

public class ClientEnd implements ClientState {
    @Override
    public ClientState execute(CountDownClient connection) {
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
