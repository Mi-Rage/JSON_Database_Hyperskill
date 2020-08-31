package database;

import java.util.Objects;
import java.util.Scanner;

public class Main {
    private static final int SIZE = 100;
    private static String data;
    private static int index = 0;

    public static void main(String[] args) {

        DataBase dataBase = new DataBase(SIZE);

        String command;

        do {
            command = getCommand();
            switch (command) {
                case "get":
                    String result = dataBase.getData(index);
                    System.out.println(Objects.requireNonNullElse(result, "ERROR"));
                    break;
                case "set":
                    System.out.println(dataBase.setData(index, data) ? "OK" : "ERROR");
                    break;
                case "delete":
                    System.out.println(dataBase.deleteData(index) ? "OK" : "ERROR");
                    break;
                case "exit":
                    break;
                default:
                    System.out.println("ERROR wrong command");
                    break;
            }
        } while (!command.equals("exit"));
    }

    /**
     * Getting command from console and converting command to variables
     * @return - String value main command
     */
    public static String getCommand() {
        Scanner scanner = new Scanner(System.in);
        String[] input = scanner.nextLine().split(" ");
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
}