package database;

public class DataBase {

    String[] array;

    /**
     * Constructor of an object with the passed parameters
     * @param size - int, size of the data base
     */
    public DataBase(int size) {
        this.array = new String[size];
    }

    /**
     * Set data into data base
     * @param index - int, index of the database cell where the data should be placed
     * @param data - Strind, data to be placed
     * @return - boolean, result of operation
     */
    public boolean setData(int index, String data) {
        if (index >= this.array.length) {
            System.out.println("ERROR");
            return false;
        } else {
            this.array[index] = data;
            return true;
        }
    }

    /**
     * Getting data from the database
     * @param index - int, index of the database cell
     * @return - String, receiving data
     */
    public String getData(int index) {
        System.out.println("got index:" + index);
        if (index < 0 || index >= this.array.length) {
            return "";
        } else {
            return this.array[index];
        }
    }

    /**
     * Delete data from database
     * @param index - int, index of the database cell
     * @return - boolean result of operation
     */
    public boolean deleteData(int index) {
        if (index < 0 || index >= this.array.length) {
            return false;
        } else {
            this.array[index] = null;
            return true;
        }
    }
}