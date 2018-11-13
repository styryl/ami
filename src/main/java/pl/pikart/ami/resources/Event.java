
package pl.pikart.ami.resources;

import pl.pikart.ami.contracts.Resource;
import java.util.HashMap;
import java.util.Map;


public class Event implements Resource {

    private String type = "event";
    private String name;
    private Map<String, String> data = new HashMap<String, String>();

    public Event(Map<String, String> buffer) {
        this.name = buffer.get("event");
        this.data.putAll(buffer);
    }

    @Override
    public String getType() {
        return this.type;
    }

    @Override
    public String getKey(String key) {
        return ( this.data.containsKey( key ) ) ? this.data.get(key) : null;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Map<String, String> getData() {
        return this.data;
    }

}
