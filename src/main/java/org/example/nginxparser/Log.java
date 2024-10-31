import exceptions.PropertyNotFoundException;

import java.util.*;

public class Log {
    private final Map<String, String> values = new LinkedHashMap<>();

    public void setProperty(String name, String value) {
        this.values.put(name, value);
    }

    public String getProperty(String name) {
        if (!this.values.containsKey(name)) {
            throw new PropertyNotFoundException();
        }
        return this.values.get(name);
    }

    @Override
    public String toString() {
        List<String> parts = new ArrayList<>();
        for (Map.Entry<String, String> entry : this.values.entrySet()) {
            parts.add(String.format("%s=%s", entry.getKey(), entry.getValue()));
        }

        return String.format("Log[%s]", String.join(", ", parts));
    }

    @Override
    public boolean equals(Object o) {
        return (o instanceof Log) && this.values.equals(((Log) o).values);
    }
}
