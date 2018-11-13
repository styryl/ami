package pl.pikart.ami;

import pl.pikart.ami.contracts.ResponseListener;
import pl.pikart.ami.contracts.EventListener;
import pl.pikart.ami.contracts.Action;
import pl.pikart.ami.contracts.Resource;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Logger;

public class Manager {

    private final String host;
    private final String secret;
    private final String username;
    private final int port;
    private DataOutputStream writer;
    private BufferedReader bufferReader;
    private Socket socket;
    private Thread readerThread;
    private Thread emitterThread;
    private final BlockingQueue<Resource> resourceQueue = new LinkedBlockingQueue<>();
    private final List<EventListener> eventListeners = new ArrayList<>();
    private final List<ResponseListener> responseListeners = new ArrayList<>();
    private boolean connected = false;
    private final static Logger LOGGER = Logger.getLogger(Manager.class.getName());

    public Manager(String host, String username, String secret) {
        this.host = host;
        this.username = username;
        this.secret = secret;
        this.port = 5038;
    }

    public Manager(String host, String username, String secret, int port) {
        this.host = host;
        this.username = username;
        this.secret = secret;
        this.port = port;
    }

    public void disconnect() throws IOException {
        this.readerThread.interrupt();
        this.emitterThread.interrupt();
        this.connected = false;
        this.socket.close();
    }

    /**
     * Creates a connection to the asterisk server
     */
    public void connect() {
        try {

            this.socket = new Socket(this.host, this.port);

            this.writer = new DataOutputStream(socket.getOutputStream());
            this.bufferReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            this.connected = true;

            this.readerThread = new Thread(new Reader(this.bufferReader, this.resourceQueue));
            this.readerThread.start();

            this.emitterThread = new Thread(new Emitter(this.resourceQueue, this.eventListeners, this.responseListeners));
            this.emitterThread.start();

        } catch (UnknownHostException e) {

            LOGGER.severe(e.getMessage());

        } catch (IOException e) {// Jeżeli nie ma połaczenia

            this.connected = false;

            LOGGER.severe(e.getMessage());
            LOGGER.info("There is no connection, I try to re-establish in 5 seconds.");

            try {

                Thread.sleep(5000);
                this.login();

            } catch (InterruptedException | IOException e1) {
                LOGGER.severe(e1.getMessage());
            }
        }

    }

    /**
     * Asterisk login
     *
     * @throws IOException
     */
    public void login() throws IOException {
        this.connect();
        String loginCommand = "Action: Login\r\nusername: " + this.username + "\r\nsecret:" + this.secret + "\r\n\r\n";
        this.send(loginCommand);
    }

    /**
     * Send message
     *
     * @param request
     * @throws IOException
     */
    public void send(String request) throws IOException {
        if (this.connected) {
            writer.writeBytes(request);
        }
    }

    /**
     * Send action
     *
     * @param action
     * @throws IOException
     */
    public void action(Action action) throws IOException {
        this.send(action.toString());
    }

    /**
     * Add event listener
     *
     * @param listener
     */
    public void addEventListener(EventListener listener) {
        synchronized (this.eventListeners) {
            // only add it if its not already there
            if (!this.eventListeners.contains(listener)) {
                this.eventListeners.add(listener);
            }
        }
    }

    /**
     * Remove event listener
     *
     * @param listener
     */
    public void removeEventListener(EventListener listener) {
        synchronized (this.eventListeners) {
            // only add it if its not already there
            if (!this.eventListeners.contains(listener)) {
                this.eventListeners.remove(listener);
            }
        }
    }

    /**
     * Add response listener
     *
     * @param listener
     */
    public void addResponseListener(ResponseListener listener) {
        synchronized (this.responseListeners) {
            // only add it if its not already there
            if (!this.responseListeners.contains(listener)) {
                this.responseListeners.add(listener);
            }
        }
    }

    /**
     * Remove response listener
     *
     * @param listener
     */
    public void removeResponseListener(ResponseListener listener) {
        synchronized (this.responseListeners) {
            // only add it if its not already there
            if (!this.responseListeners.contains(listener)) {
                this.responseListeners.remove(listener);
            }
        }
    }

    /**
     * Check the connection
     *
     * @return
     */
    public boolean isConnected() {
        return connected;
    }
}
