package server.states;

import server.CountDownServer;
import server.ServerState;

public class ServerError implements ServerState {

    Exception exception;

    ServerError(Exception exception){
        this.exception = exception;
    }

    @Override
    public ServerState execute(CountDownServer connection) {
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
