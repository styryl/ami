package pl.pikart.ami;

import pl.pikart.ami.contracts.Resource;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Logger;
import pl.pikart.ami.resources.Event;
import pl.pikart.ami.resources.Response;

public class Reader implements Runnable {

    private final BlockingQueue<Resource> q;
    private final BufferedReader reader;
    private final static Logger LOGGER = Logger.getLogger(Reader.class.getName());

    public Reader(BufferedReader reader, BlockingQueue<Resource> q) {
        LOGGER.info("Reader running.");
        this.q = q;
        this.reader = reader;
    }

    @Override
    public void run() {

        final Map<String, String> buffer = new HashMap<>();

        String line;
        Boolean run = true;

        try {

            while (run) {

                try {
                    line = reader.readLine();
                } catch (SocketException e) {
                    LOGGER.info("Reader stopped");
                    break;
                }

                if (line == null) {
                    LOGGER.info("Connection to the server is broken.");
                    buffer.put("event", "ConnectionError");
                    q.put(new Event(buffer));
                    buffer.clear();
                    break;
                }

                if (line.length() > 0) {

                    // Splitter key: value
                    int delimiterIndex = line.indexOf(": ");
                    // Splitter width ":";
                    int delimiterLength = 2;

                    // If the splitter index exists and the line length is greater than 4
                    if (delimiterIndex > 0 && line.length() > delimiterIndex + delimiterLength) {
                        String name;
                        String value;

                        name = line.substring(0, delimiterIndex).toLowerCase();
                        value = line.substring(delimiterIndex + delimiterLength);

                        buffer.put(name, value);
                    }
                }

                if (line.length() == 0) {

                    if (buffer.containsKey("event")) {
                        Event res = new Event(buffer);
                        q.put(res);
                    }

                    if (buffer.containsKey("response")) {
                        q.put(new Response(buffer));
                    }

                    buffer.clear();
                }

            }

        } catch (IOException | InterruptedException e) {
            LOGGER.info("Reader stopped.");
        }

    }

}
