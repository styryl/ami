package pl.pikart.ami;

import java.util.Map.Entry;
import pl.pikart.ami.contracts.Resource;

public class Util {

    public static void dd(Object o) {
        System.out.println(Dumper.dump(o));
    }

    public static void dd(Object o, boolean exit) {
        System.out.println(Dumper.dump(o));

        if (exit) {
            System.exit(0);
        }
    }

    public static synchronized void prettyResourcePrint(Resource r) {
        System.out.println("--------------" + r.getType().toUpperCase() + ": " + r.getName() + " START--------------");
        for (Entry<String, String> entry : r.getData().entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
        System.out.println("--------------" + r.getType().toUpperCase() + ": " + r.getName() + " END--------------\r\n");
    }
}
