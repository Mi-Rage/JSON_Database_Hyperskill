package client;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import database.util.InputReader;
import database.util.OutputWriter;

import java.net.InetAddress;
import java.net.Socket;

public class Main {

    private static Socket clientSocket;

    @Parameter(names = {"--type", "-t"})
    String request;
    @Parameter(names = {"--index", "-i"})
    String index;
    @Parameter(names = {"--modify", "-m"})
    String data;

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

        // Sending a request to the server
        final String message = request + " " + (index!=null ? index : "")
                + " " + (data!=null ? data : "");
        System.out.println("Sent: " + message.trim());
        outputWriter.sentMessage(message);

        // Getting answer from server
        String receivedMessage = inputReader.read().trim();
        System.out.println("Received: " + receivedMessage);

        closeSocket();
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
