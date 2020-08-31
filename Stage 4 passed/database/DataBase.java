package database;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class DataBase {

    Map<String,String> storage;

    /**
     * Constructor of an object with the passed parameters
     */
    public DataBase() {
        this.storage = new LinkedHashMap<>();
    }

    /**
     * Set data into data base
     * @param key - String, key of the database cell where the data should be placed
     * @param value - Strind, data to be placed
     * @return - boolean, result of operation
     */
    public boolean setData(String key, String value) {
        storage.put(key, value);
        return true;
    }

    /**
     * Getting data from the database
     * @param key - String, key of the database cell
     * @return - String, receiving data
     */
    public String getData(String key) {
        String result = storage.get(key);
        return Objects.requireNonNullElse(result, "");
    }

    /**
     * Delete data from database
     * @param key - String, key of the database cell
     * @return - boolean result of operation
     */
    public boolean deleteData(String key) {
        if (storage.containsKey(key)) {
            storage.remove(key);
            return true;
        } else {
            return false;
        }
    }
}