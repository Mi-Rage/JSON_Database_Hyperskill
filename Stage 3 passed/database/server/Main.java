package database.server;

import database.DataBase;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    private static final int SIZE = 100;
    private static ServerSocket serverSocket;

    public static void main(final String[] args) {

        DataBase dataBase = new DataBase(SIZE);
        greeting();
        createServerSocket();
        createClientSocket(dataBase);
        closeSocket();
    }


    /**
     * Say hello!
     */
    private static void greeting() {
        System.out.println("Server started!");
    }


    /**
     * Break connection.
     */
    private static void closeSocket() {
        try {
            serverSocket.close();
        } catch (Exception ignored) {
        }
    }

    /**
     * Creating clients socket
     * and provide connectivity
     */
    private static void createClientSocket(DataBase dataBase) {

        //while (!isExit) {
        while (!serverSocket.isClosed()) {
            final Socket clientSocket = getConnection();
            if (clientSocket != null)
                new Thread(new ConnectionWorker(clientSocket, serverSocket, dataBase)).start();

        }
    }

    /**
     * Getting the socket connection to the client
     *
     * @return - Socket connection
     */
    private static Socket getConnection() {
        try {
            return serverSocket.accept();
        } catch (Exception ignored) {
        }
        return null;
    }


    /**
     * Creating a ServerSocket object to connect clients to it
     */
    private static void createServerSocket() {
        final String address = "127.0.0.1";
        final int port = 23456;
        while (true) {
            try {
                serverSocket = new ServerSocket(port, 50, InetAddress.getByName(address));
                return;
            } catch (Exception ignored) {
                System.out.println("[SERVER] Can't create a socket!");
            }
        }
    }
}
