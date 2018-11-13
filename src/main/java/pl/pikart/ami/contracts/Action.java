package pl.pikart.ami.contracts;

import java.util.HashMap;
import java.util.Map;

public abstract class Action {

    protected Map<String, String> options = new HashMap<String, String>();

    public Action() {
        String id = "" + System.currentTimeMillis();
        this.options.put("ActionID", id);
    }

    public String getActionID() {
        return this.options.get("ActionID");
    }

    public void setOptions(String key, String val) {
        this.options.put(key, val);
    }

    public String toString() {

        String buffer = "";
        for (Map.Entry<String, String> entry : this.options.entrySet()) {
            buffer += entry.getKey() + ": " + entry.getValue() + "\r\n";
        }

        return buffer + "\r\n";
    }
}
