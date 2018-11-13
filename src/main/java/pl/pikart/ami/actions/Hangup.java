package pl.pikart.ami.actions;

import pl.pikart.ami.contracts.Action;

public class Hangup extends Action {

    public Hangup() {
        this.options.put("Action", "Hangup");
        this.options.put("Couse", "1");
    }

    public void setChannel(String option) {
        this.options.put("Channel", option);
    }

}
