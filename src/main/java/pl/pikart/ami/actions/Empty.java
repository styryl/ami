package pl.pikart.ami.actions;

import pl.pikart.ami.contracts.Action;

public class Empty extends Action {

    public Empty(String name) {
        this.setOption("Action", name);
    }

    public void setOption(String key, String value) {
        this.options.put(key, value);
    }
}
