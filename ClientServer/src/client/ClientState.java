package client;

import server.CountDownServer;
import server.ServerState;

public interface ClientState {

    ClientState execute(CountDownClient connection);

    boolean isEndingState();

    boolean isErrorState();

}
