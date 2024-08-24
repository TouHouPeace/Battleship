package main.java;

import java.io.*;
import java.net.*;
import java.util.logging.Logger;
import java.nio.charset.StandardCharsets;

public class Server implements Connectable {
    private int port;
    private Socket socket;
    private ServerSocket servSocket;
    private InputStream inStream;
    private OutputStream outStream;
    private BufferedReader in;
    private PrintWriter out;
    private Logger log;

    public Server() throws IOException {
        this(DEFAULT_PORT);
    }

    public Server(int port) throws IOException {
        this.log = Logger.getLogger("global");
        this.port = port;
        this.servSocket = new ServerSocket(this.port);
        log.info(String.format("Server socket was created on port %d.\n", port));
    }

    public void connect() throws IOException {
        try {
            this.socket = this.servSocket.accept();
            log.info(String.format("Incoming connection from a client at %s accepted.\n", this.socket.getRemoteSocketAddress().toString()));
            this.inStream = this.socket.getInputStream();
            this.outStream = this.socket.getOutputStream();
            this.in = new BufferedReader(new InputStreamReader(this.inStream));
            this.out = new PrintWriter(new OutputStreamWriter(this.outStream, StandardCharsets.UTF_8), true);
        } catch (IOException e) {
            log.severe("Error connecting to client: " + e.getMessage());
            throw e;
        }
    }

    public void send(String message) {
        this.out.println(message);
        log.info(String.format("Message %s sent.\n", message));
    }

    public String receive() {
        try {
            String message = this.in.readLine();
            log.info(String.format("Message %s received.\n", message));
            return message;
        } catch (IOException e) {
            log.severe("Error receiving message: " + e.getMessage());
            return null;
        }
    }

    public int getPort() {
        return this.port;
    }

    public boolean isConnectionClosed() {
        return this.socket.isClosed();
    }
}
