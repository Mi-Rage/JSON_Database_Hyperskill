package server;

import com.google.gson.Gson;
import database.DataBase;
import util.InputReader;
import util.OutputWriter;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedHashMap;
import java.util.Map;

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
    Map<String, String> answer = new LinkedHashMap<>();

    /**
     * Constructor of the message processing object
     *
     * @param socket       - Socket, connection with client
     * @param serverSocket - ServerSocket, connection or server
     * @param dataBase     - object, where data is stored
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
        final String rawMessage = inputReader.read().trim();

        GsonFromJson gsonFromJson = new GsonFromJson(rawMessage);
        gsonFromJson.getString();

        String command = gsonFromJson.getType();
        String key = gsonFromJson.getKey();
        String value = gsonFromJson.getValue();

        switch (command) {
            case "get":
                String result = dataBase.getData(key);
                if (result == null) {
                    answer.put("response", "ERROR");
                    answer.put("reason", "No such key");
                } else {
                    answer.put("response", "OK");
                    answer.put("value", result.trim());
                }
                sentAnswer(answer);
                break;
            case "set":
                if (dataBase.setData(key, value)) {
                    answer.put("response", "OK");
                } else {
                    answer.put("response", "ERROR");
                }
                sentAnswer(answer);
                break;
            case "delete":
                if (dataBase.deleteData(key)) {
                    answer.put("response", "OK");
                } else {
                    answer.put("response", "ERROR");
                    answer.put("reason", "No such key");
                }
                sentAnswer(answer);
                break;
            case "exit":
                answer.put("response", "OK");
                sentAnswer(answer);
                closeSocket();
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    /**
     * Creating and sending the server response in JSON format
     * @param answer Map<> , a set of keys and objects for generating a JSON message
     */
    private void sentAnswer(Map<String, String> answer) {
        Gson gson = new Gson();

        String output = gson.toJson(answer);

        outputWriter.sentMessage(output);
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
