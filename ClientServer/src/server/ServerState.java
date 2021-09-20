package server;

public interface ServerState {

    ServerState execute(CountDownServer connection);
    boolean isEndingState();
    boolean isErrorState();

}
