package pl.pikart.ami.actions;

import pl.pikart.ami.contracts.Action;

public class Originate extends Action {

    public Originate() {
        this.options.put("Action", "Originate");
    }

    public void setChannel(String option) {
        this.options.put("Channel", option);
    }

    public void setContext(String option) {
        this.options.put("Context", option);
    }

    public void setExten(String option) {
        this.options.put("Exten", option);
    }

    public void setPriority(String option) {
        this.options.put("Priority", option);
    }

    public void setCallerid(String option) {
        this.options.put("Callerid", option);
    }

    public void setTimeout(String option) {
        this.options.put("Timeout", option);
    }

    public void setVariable(String option) {
        this.options.put("Variable", option);
    }

    public void setActionID(String option) {
        this.options.put("ActionID", option);
    }

    public void setChannelId(String id) {
        this.options.put("ChannelId", id);
    }

    public void setOtherChannelId(String id) {
        this.options.put("OtherChannelId", id);
    }

    public void setAsync(Boolean option) {
        if (option) {
            this.options.put("Async", "True");
        } else {
            this.options.put("Async", "False");
        }

    }
}
