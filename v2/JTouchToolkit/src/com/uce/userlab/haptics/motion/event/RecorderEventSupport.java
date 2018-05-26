package com.uce.userlab.haptics.motion.event;
/**
 * Ian John Archer
 * @author Ian John Archer (Software Engineer - User-lab)
 * @version 1.0
 */
import java.util.HashSet;
import java.util.Iterator;
import com.uce.userlab.haptics.motion.interfaces.RecorderListener;

/**
 * This supports the Recorder implementations with manageing RecorderListeners and
 * event calls to those RecorderListeners. It provides methods for firing all 
 * events for a standard RecorderListeners to detect and number of variables that can
 * be povided with the event fire.
 */
public class RecorderEventSupport
{
    /**
     * A ADT of the RecorderListeners to aid in adding, removing, retrieving
     */
    private HashSet <RecorderListener> listeners;
    /**
     * The source object that will fire all the events
     */
    private Object source;
    
    /**
     * Creates a new RecorderEventSupport object
     * @param source The source object that will fire all the events
     */
    public RecorderEventSupport(Object source)
    {
        this.source = source;
        listeners = new HashSet <RecorderListener> ();
    }
    
    /**
     * Adds a new RecorderListeners to the notify list for all event firing
     * @param listener The RecorderListeners to add to the notify list
     */
    public void addRecorderListener(RecorderListener listener)
    {
        listeners.add(listener);
    }
    
    /**
     * Removes a RecorderListeners from the notify list for all event firing
     * @param listener The RecorderListeners to remove from the notify list
     */
    public void removeRecorderListener(RecorderListener listener)
    {
        listeners.remove(listener);
    }
    
    /**
     * Returns the list of RecorderListeners in an array of RecorderListeners
     * @return The list of RecorderListeners in an array of RecorderListeners
     */
    public synchronized RecorderListener[] getRecorderListeners()
    {
        return this.listeners.toArray(new RecorderListener[listeners.size()]);
    }
    
    /**
     * Returns the number of RecorderListeners that it has to notify
     * @return The number of RecorderListeners that it has to notify
     */
    public int getNumOfListeners()
    {
        return this.listeners.size();
    }
    
    
    /**
     * Fires a recorder started event to all listeners on the notify list
     * @param message The message describing the event
     * @param eventCode The event code to identify the type of event
     */
    public void fireRecorderStarted(String message, double eventCode)
    {
        MotionEvent event = new MotionEvent(source, message, eventCode);
        this.fireRecorderStarted(event);
    }
    /**
     * Fires a recorder stoped event to all listeners on the notify list
     * @param message The message describing the event
     * @param eventCode The event code to identify the type of event
     */
    public void fireRecorderStoped(String message, double eventCode)
    {
        MotionEvent event = new MotionEvent(source, message, eventCode);
        this.fireRecorderStoped(event);
    }
    /**
     * Fires a recorder update event to all listeners on the notify list
     * @param message The message describing the event
     * @param eventCode The event code to identify the type of event
     */
    public void fireRecorderUpdate(String message, double eventCode)
    {
        MotionEvent event = new MotionEvent(source, message, eventCode);
        this.fireRecorderUpdate(event);
    }
    
    
    /**
     * Fires a recorder started event to all listeners on the notify list
     * @param message The message describing the event
     * @param eventCode The event code to identify the type of event
     * @param pos The position the recorder is set to when the event is fired, if any
     * @param step The step number the recorder is on when the event is fired, if any
     * @param state A description of the state that the recorder is in when the event is fired
     */
    public void fireRecorderStarted(String message, double eventCode,
                                    double[] pos, int step, String state)
    {
        MotionEvent event = new MotionEvent(source, message, eventCode,
                                            pos, step, state);
        this.fireRecorderStarted(event);
    }
    /**
     * Fires a recorder stoped event to all listeners on the notify list
     * @param message The message describing the event
     * @param eventCode The event code to identify the type of event
     * @param pos The position the recorder is set to when the event is fired, if any
     * @param step The step number the recorder is on when the event is fired, if any
     * @param state A description of the state that the recorder is in when the event is fired
     */
    public void fireRecorderStoped(String message, double eventCode,
                                    double[] pos, int step, String state)
    {
        MotionEvent event = new MotionEvent(source, message, eventCode,
                                            pos, step, state);
        this.fireRecorderStoped(event);
    }
    /**
     * Fires a recorder update event to all listeners on the notify list
     * @param message The message describing the event
     * @param eventCode The event code to identify the type of event
     * @param pos The position the recorder is set to when the event is fired, if any
     * @param step The step number the recorder is on when the event is fired, if any
     * @param state A description of the state that the recorder is in when the event is fired
     */
    public void fireRecorderUpdate(String message, double eventCode,
                                    double[] pos, int step, String state)
    {
        MotionEvent event = new MotionEvent(source, message, eventCode,
                                            pos, step, state);
        this.fireRecorderUpdate(event);
    }
    
    
    /**
     * Fires a recorder started event to all listeners on the notify list
     * @param event The MotionEvent to pass to all RecorderListeners
     */
    public void fireRecorderStarted(MotionEvent event)
    {
        Iterator <RecorderListener> ite = listeners.iterator();
        
        while(ite.hasNext())
            ite.next().recorderStarted(event);
    }
    /**
     * Fires a recorder stoped event to all listeners on the notify list
     * @param event The MotionEvent to pass to all RecorderListeners
     */
    public void fireRecorderStoped(MotionEvent event)
    {
        Iterator <RecorderListener> ite = listeners.iterator();
        
        while(ite.hasNext())
            ite.next().recorderStoped(event);
    }
    /**
     * Fires a recorder update event to all listeners on the notify list
     * @param event The MotionEvent to pass to all RecorderListeners
     */
    public void fireRecorderUpdate(MotionEvent event)
    {
        Iterator <RecorderListener> ite = listeners.iterator();
        
        while(ite.hasNext())
            ite.next().recorderUpdate(event);
    }
}
