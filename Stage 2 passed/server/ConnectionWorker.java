package database.server;

import database.util.InputReader;
import database.util.OutputWriter;

import java.net.Socket;

/**
 * In this class we provide processing of messages received by the server
 */
public class ConnectionWorker implements Runnable {
    private final InputReader inputReader;
    private final OutputWriter outputWriter;
    private final Socket socket;
    private static final boolean DEBUG_MODE = false;

    /**
     * Constructor of the message processing object
     *
     * @param socket - Socket, connection with client
     */
    public ConnectionWorker(final Socket socket) {
        if (DEBUG_MODE) {
            System.out.println("Client connected!");
        }
        this.socket = socket;
        this.inputReader = new InputReader(socket);
        this.outputWriter = new OutputWriter(socket);
    }

    /**
     * Get the query string and process it
     * according to the condition.
     */
    @Override
    public void run() {
        final String rawMessage = inputReader.read().trim();
        String[] data = rawMessage.split(" ");
        String procMessage = "Received: ";
        for (int i = 1; i < data.length; i++) {
            procMessage = procMessage.concat(data[i] + " ");
        }
        System.out.println(procMessage.trim());
        final String outputMessage = "Sent: A record # " + data[data.length - 1] + " was sent!";
        System.out.println(outputMessage);
        outputWriter.sentMessage(outputMessage);

    }

}
