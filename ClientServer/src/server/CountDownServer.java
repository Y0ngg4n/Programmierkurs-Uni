package server;

import server.states.ServerWelcome;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class CountDownServer implements Runnable {

    int port;
    ServerSocket server;
    InputStream inputStream;
    OutputStream outputStream;
    Thread talk;
    ServerState zustand;
    ServerProtocol serverProtocol = new ServerProtocol();
    BufferedReader inputBufferedReader;
    PrintWriter outputPrintWriter;

    public Thread getTalk() {
        return talk;
    }

    public ServerProtocol getServerProtocol() {
        return serverProtocol;
    }

    public CountDownServer(int port) {
        this.port = port;
        zustand = new ServerWelcome();
        init();
    }

    public void send(String s) {
        outputPrintWriter.println(s);
    }

    public String recieve() {
        try {
            String request = inputBufferedReader.readLine();
            System.out.println(request);
            return request;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    void start() {
        talk = new Thread(this);
        talk.start();
    }

    void init() {
        try {
            server = new ServerSocket(port);
            start();
            System.out.println("Server started and waiting");
        } catch (IOException e) {
            System.err.println("Could not start Server");
            e.printStackTrace();
        }
    }

    @Override
    public synchronized void run() {
        try {
            Socket socket = server.accept();
            System.out.println("Server: client connected");
            outputStream = socket.getOutputStream();
            inputStream = socket.getInputStream();
            inputBufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            outputPrintWriter = new PrintWriter(outputStream, true);

            do {
                zustand = zustand.execute(this);
            } while (zustand != null && (!zustand.isEndingState() || !zustand.isErrorState()));

            socket.close();
            finish();
        } catch (IOException e) {
            e.printStackTrace();
        }
        notify();
    }

    public void finish() {
        try {
            if (server != null)
                server.close();
            System.out.println("ServerSocket closed");
        } catch (IOException e) {
            System.out.println("Could not close ServerSocket");
            e.printStackTrace();
        }
    }

    public void printRightArrow(String value) {
        System.out.println("Server -> " + value);
    }

    public void printLeftArrow(String value) {
        System.out.println("Server <- " + value);
    }
}
