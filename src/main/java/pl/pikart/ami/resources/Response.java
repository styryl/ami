package pl.pikart.ami.resources;

import pl.pikart.ami.contracts.Resource;
import java.util.HashMap;
import java.util.Map;

public class Response implements Resource {

    private String type = "response";
    private String name;
    private Map<String, String> data = new HashMap<>();

    public Response(Map<String, String> buffer) {
        this.name = "Response";
        this.data.putAll(buffer);
    }

    @Override
    public String getType() {
        return this.type;
    }

    @Override
    public String getKey(String key) {
        return (this.data.containsKey(key)) ? this.data.get(key) : null;
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
