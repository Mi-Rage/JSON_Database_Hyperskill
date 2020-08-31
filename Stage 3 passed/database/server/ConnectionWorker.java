package database.server;

import database.DataBase;
import database.util.InputReader;
import database.util.OutputWriter;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;

/**
 * In this class we provide processing of messages received by the server
 */
public class ConnectionWorker implements Runnable {
    private final InputReader inputReader;
    private final OutputWriter outputWriter;
    private final Socket socket;
    private final ServerSocket serverSocket;
    private static final boolean DEBUG_MODE = false;
    private final DataBase dataBase;
    private int index;
    private String data;

    /**
     * Constructor of the message processing object
     * @param socket - Socket, connection with client
     * @param serverSocket - ServerSocket, connection or server
     * @param dataBase - object, where data is stored
     */
    public ConnectionWorker(final Socket socket, ServerSocket serverSocket, DataBase dataBase) {
        if (DEBUG_MODE) {
            System.out.println("Client connected!");
        }
        this.socket = socket;
        this.serverSocket = serverSocket;
        this.dataBase = dataBase;
        this.inputReader = new InputReader(socket);
        this.outputWriter = new OutputWriter(socket);
    }

    /**
     * Get the query string and process it
     * according to the condition.
     */
    @Override
    public void run() {
        String command;
        do {
            final String rawMessage = inputReader.read().trim();

            command = getCommand(rawMessage);

            switch (command) {
                case "get":
                    String result = dataBase.getData(index);
                    outputWriter.sentMessage(Objects.requireNonNullElse(result, "ERROR"));
                    break;
                case "set":
                    outputWriter.sentMessage(dataBase.setData(index, data) ? "OK" : "ERROR");
                    break;
                case "delete":
                    outputWriter.sentMessage(dataBase.deleteData(index) ? "OK" : "ERROR");
                    break;
                case "exit":
                    outputWriter.sentMessage("OK");
                    closeSocket();
                    try {
                        serverSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        } while (!command.equals("exit"));
    }

    /**
     * Getting the command and data from raw request received from the client
     * @param rawMessage - String, rew request from client
     * @return - String, main command for database
     */
    public String getCommand(String rawMessage) {
        String[] input = rawMessage.split(" ");
        if (input.length > 1) {
            index = Integer.parseInt(input[1]) - 1;
        }
        if (input.length > 2) {
            data = input[2];
        }
        if (input.length > 3) {
            for (int i = 3; i < input.length; i++) {
                data = data.concat(" ").concat(input[i]);
            }
        }
        return input[0];
    }

    /**
     * Break connection
     */
    private void closeSocket() {
        try {
            socket.close();
        } catch (Exception ignored) {
        }
    }

}
