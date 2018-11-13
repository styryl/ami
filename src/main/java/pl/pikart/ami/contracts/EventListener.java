
package pl.pikart.ami.contracts;

import pl.pikart.ami.resources.Event;

public interface EventListener {

    public void onEvent(Event e);
}
