package pl.pikart.ami;

import pl.pikart.ami.contracts.ResponseListener;
import pl.pikart.ami.contracts.EventListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import pl.pikart.ami.resources.Event;
import pl.pikart.ami.resources.Response;

public class Main implements EventListener, ResponseListener {

    private Manager manager;

    public static void main(String[] args) throws IOException {

        Main dialer = new Main();
        dialer.run();

    }

    public void run() throws IOException {
        this.manager = new Manager("ASTERSIK_IP", "LOGIN", "PASSWORD");

        this.manager.addEventListener(this);
        this.manager.addResponseListener(this);

        this.manager.login();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void onEvent(Event e) {
        Util.prettyResourcePrint(e);
    }

    @Override
    public void onResponse(Response r) {
        Util.prettyResourcePrint(r);
    }
}
