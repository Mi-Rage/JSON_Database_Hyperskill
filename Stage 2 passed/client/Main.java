package database.client;

import database.util.InputReader;
import database.util.OutputWriter;

import java.net.InetAddress;
import java.net.Socket;

public class Main {

    private static Socket clientSocket;

    public static void main(final String[] args) {

        createSocket();

        InputReader inputReader = new InputReader(clientSocket);
        OutputWriter outputWriter = new OutputWriter(clientSocket);

        System.out.println("Client started!");

        // Sending a request to the server
        final String message = "Sent: Give me a record # 12";
        System.out.println(message);
        outputWriter.sentMessage(message);

        // Getting answer from server
        String rawMessage = inputReader.read().trim();
        String[] data = rawMessage.split(" ");
        String procMessage = "";
        for (int i = 1; i < data.length; i++) {
            procMessage = procMessage.concat(data[i] + " ");
        }
        System.out.println("Received: " + procMessage);

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
