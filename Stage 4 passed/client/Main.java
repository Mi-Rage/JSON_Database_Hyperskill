package client;

import com.google.gson.Gson;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import util.InputReader;
import util.OutputWriter;

import java.net.InetAddress;
import java.net.Socket;
import java.util.LinkedHashMap;
import java.util.Map;

public class Main {

    private static Socket clientSocket;

    @Parameter(names = {"--type", "-t"})
    String type;
    @Parameter(names = {"--key", "-k"})
    String key;
    @Parameter(names = {"--value", "-v"})
    String value;

    Map<String, String> request = new LinkedHashMap<>();

    public static void main(final String[] args) {

        Main main = new Main();
        JCommander.newBuilder()
                .addObject(main)
                .build()
                .parse(args);
        main.run();
    }

    public void run() {

        createSocket();

        InputReader inputReader = new InputReader(clientSocket);
        OutputWriter outputWriter = new OutputWriter(clientSocket);

        System.out.println("Client started!");

        createRequest(type, key, value);

        Gson gson = new Gson();

        String output = gson.toJson(request);

        System.out.println("Sent: " + output);
        outputWriter.sentMessage(output);

        String receivedMessage = inputReader.read().trim();
        System.out.println("Received: " + receivedMessage);

        closeSocket();
    }

    /**
     * Forming a request Map based on the available parameters
     * @param type - String, command
     * @param key - String, key of Map
     * @param value - String, value of key
     */
    private void createRequest(String type, String key, String value) {
        request.put("type", type);
        if (key != null) {
            request.put("key", key);
        }
        if (value != null) {
            request.put("value", value);
        }
    }


    /**
     * Close the connection
     */
    private static void closeSocket() {
        try {
            clientSocket.close();
        } catch (Exception ignored) {
        }
    }


    /**
     * Creating a socket to connect to the server
     */
    private static void createSocket() {
        final String address = "127.0.0.1";
        final int port = 23456;
        while (true) {
            try {
                clientSocket = new Socket(InetAddress.getByName(address), port);
                return;
            } catch (Exception e) {
                System.out.println("\n" + e + "\n[CLIENT] Can't connect to the server");
            }

        }
    }
}
