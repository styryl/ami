package pl.pikart.ami.actions;

import pl.pikart.ami.contracts.Action;

public class UpdateConfig extends Action {

    private int counter = 0;

    public UpdateConfig(String src, String dst) {
        this.options.put("Action", "UpdateConfig");
        this.options.put("SrcFilename", src);
        this.options.put("DstFilename", dst);
    }

    public void reload() {
        this.setOption("reload", "yes");
    }

    public void setOption(String key, String value) {
        this.options.put(key, value);
    }

    public void setLine(String action, String cat, String var, String value) {

        String key = String.format("%06d", this.counter);
        this.setOption("Action-" + key, action);
        this.setOption("Cat-" + key, cat);
        this.setOption("Var-" + key, var);
        this.setOption("Value-" + key, value);

        this.counter++;
    }

}
