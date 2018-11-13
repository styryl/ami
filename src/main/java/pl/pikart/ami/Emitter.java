package pl.pikart.ami;

import pl.pikart.ami.contracts.ResponseListener;
import pl.pikart.ami.contracts.EventListener;
import pl.pikart.ami.contracts.Resource;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Logger;
import pl.pikart.ami.resources.Event;
import pl.pikart.ami.resources.Response;

public class Emitter implements Runnable {

    private final BlockingQueue<Resource> q;
    private final List<EventListener> eventListeners;
    private final List<ResponseListener> responseListeners;
    private static final Logger LOGGER = Logger.getLogger(Emitter.class.getName());

    public Emitter(BlockingQueue<Resource> q, List<EventListener> eventListeners, List<ResponseListener> responseListeners) {
        this.q = q;
        this.eventListeners = eventListeners;
        this.responseListeners = responseListeners;

        LOGGER.info("Emitter running.");
    }

    @Override
    public void run() {

        try {

            while (!Thread.currentThread().isInterrupted()) {

                Resource res = (Resource) q.take();

                //LOGGER.info("Pobieram zasób: " + res.getType() + " name:" + res.getName());
                if (res.getType().equals("event")) {
                    if (!eventListeners.isEmpty()) {
                        for (EventListener listener : eventListeners) {
                            try {
                                listener.onEvent((Event) res);
                            } catch (Exception e) {
                                LOGGER.severe("EVENT:" + e.getMessage() + " " + res.getData());
                            }

                        }
                    }

                } else {

                    if (!responseListeners.isEmpty()) {
                        for (ResponseListener listener : responseListeners) {
                            try {
                                listener.onResponse((Response) res);
                            } catch (Exception e) {
                                LOGGER.severe("RESPONSE:" + e.getMessage());
                            }

                        }
                    }

                }

            }

        } catch (InterruptedException e) {
            LOGGER.info("Emitter stopeed!");
        }
    }

}
