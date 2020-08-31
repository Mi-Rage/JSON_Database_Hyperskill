package server;

/**
 * This is an auxiliary class for generating JSON message arguments
 */
public class Arguments {
    private final String type;
    private final String key;
    private final String value;

    public Arguments(String type, String key, String value) {
        this.type = type;
        this.key = key;
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
