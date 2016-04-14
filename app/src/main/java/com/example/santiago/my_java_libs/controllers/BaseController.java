package com.example.santiago.my_java_libs.controllers;

import android.content.Context;
import android.support.annotation.Nullable;

import com.example.santiago.my_java_libs.event.Event;
import com.example.santiago.my_java_libs.event.EventManager;
import com.example.santiago.my_java_libs.event.listener.EventListener;

/**
 * Base controller which implements the sending/receiving of events.
 * You can broadcast a particular event to the EventManager
 * For receiving and handling events in a controller you will need override the getEventNotifierListener() and implmement an instance of EventNotifierListener
 *
 * Created by santiaguilera@theamalgama.com on 08/01/16.
 */
public abstract class BaseController<T> {

    private Context context;

    private T t;

    private EventListener eventListener;

    public BaseController(Context context) {
        this.context = context;
    }

    //------------------CONTEXT---------------------//

    protected Context getContext() {
        if(context==null)
            throw new NullPointerException("No context found");

        return context;
    }

    protected void setContext(Context context){
        this.context = context;
    }

    //-------------------ELEMENT--------------------//

    /**
     * Attach an element to the controller
     * @param t
     */
    public void attachElement(@Nullable T t) {
        this.t = t;

        if (t != null)
            onElementAttached(t);
    }

    /**
     * Called after attaching an element, subclass
     * should implement it
     * @param t
     */
    protected abstract void onElementAttached(T t);

    /**
     * Get the element attached to the view
     * @return
     */
    public T getElement() {
        return t;
    }

    //-------------------SETTERS------------------//

    /**
     * Set an EventListener which will broadcast events.
     *
     * @param eventListener used for sending events to the EventManager and the listening classes
     */
    public void setEventListener(EventListener eventListener){
        this.eventListener = eventListener;
    }

    /**
     * @return eventListener instance
     */
    public EventListener getEventListener() {
        return eventListener;
    }

    /**
     * Short version of everything you need for the events. With this you will be able to:
     *  - Broadcast events to the eventmanager and the listening classes.
     *  - Receive and handle other events.
     *
     * @param eventManager the EventManager in particular. Must implement EventListener (obviously)
     */
    public void setEventHandlerListener(@Nullable EventManager eventManager) {
        setEventListener(eventManager);

        if(eventManager!=null) {
            //avoid duplicates
            eventManager.removeListener(this);

            eventManager.addListener(this);
        }
    }

    //--------------------Handling of events---------------//

    /**
     * Send an event, process it (optional) in the eventmanager, and
     * dispatch it to all the listeners
     *
     * @param event
     */
    protected void broadcastEvent(@Nullable Event event) {
        if(eventListener==null)
            throw new NullPointerException(EventListener.class.getSimpleName() + " instance in " + this.getClass().getSimpleName() + " can't be null");

        if(event == null)
            throw new NullPointerException(Event.class.getSimpleName() + " is null in method");

        eventListener.broadcastEvent(event);
    }

}
