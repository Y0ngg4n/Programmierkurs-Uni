package client;

import client.states.ClientFrom;
import client.states.ClientWelcome;

import java.io.*;
import java.net.Socket;

public class CountDownClient implements Runnable {
    String host;
    int port;
    Socket client;
    InputStream inputStream;
    OutputStream outputStream;
    Thread talk;
    ClientState zustand;
    ClientProtocol clientProtocol = new ClientProtocol();
    BufferedReader inputBufferedReader;
    PrintWriter outputPrintWriter;

    public Thread getTalk() {
        return talk;
    }

    public ClientProtocol getClientProtocol() {
        return clientProtocol;
    }

    public CountDownClient(String host, int port, int startNumber) {
        this.host = host;
        this.port = port;
        this.zustand = new ClientWelcome(startNumber);
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
            client = new Socket(host, port);
            start();
            System.out.println("Client: trying to connect ...");
        } catch (IOException e) {
            System.err.println("Could not start Server");
            e.printStackTrace();
        }
    }

    @Override
    public synchronized void run() {
        try {
            outputStream = client.getOutputStream();
            inputStream = client.getInputStream();
            inputBufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            outputPrintWriter = new PrintWriter(outputStream, true);

            do {
                zustand = zustand.execute(this);
            } while (zustand != null && (!zustand.isEndingState() || !zustand.isErrorState()));

            client.close();
            finish();
        } catch (IOException e) {
            e.printStackTrace();
        }
        notify();
    }

    public void finish() {
        try {
            if (client != null)
                client.close();
            System.out.println("Client socket closed");
        } catch (IOException e) {
            System.out.println("Could not close Client socket");
            e.printStackTrace();
        }
    }

    public void printRightArrow(String value) {
        System.out.println("Client -> " + value);
    }


}
