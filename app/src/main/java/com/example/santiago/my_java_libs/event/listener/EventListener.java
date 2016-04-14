package com.example.santiago.my_java_libs.event.listener;

import com.example.santiago.my_java_libs.event.Event;

/**
 * Created by santiaguilera@theamalgama.com on 01/03/16.
 */
public interface EventListener {

    /**
     * Broadcast the event to all the ones listening
     * @param event
     */
    void broadcastEvent(Event event);

}
