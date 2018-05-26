package com.uce.userlab.haptics.motion.event;
/**
 * Ian John Archer
 * @author Ian John Archer (Software Engineer - User-lab)
 * @version 1.0
 */

/**
 * This represents motion events that are fired by the Players and Recorders, it
 * decribes the type of event and the state in which the player/recorder is in as
 * well the device state.
 * @see com.uce.userlab.haptics.Device
 * @see com.uce.userlab.haptics.motion.interfaces.PlayerListener
 * @see com.uce.userlab.haptics.motion.interfaces.RecorderListener
 */
public class MotionEvent
{
    /**
     * The source Object that has fired the event
     */
    private Object source = null;
    /**
     * The message describing the event and the description of the state of the player/recorder
     */
    private String message = "", state = "";
    /**
     * The event code that identifies the event type
     */
    private double eventCode;
    /**
     * The position the player/recorder's device is set to at the time the event is fired
     */
    private double[] pos;
    /**
     * The steps made by the player/recorder at the time of the event is fired
     */
    private int step;
    
    /**
     * Creates a new MotionEvent object to describe an event fired by a player/recorder
     * @param source The source object that has fired this event
     * @param message The message describing the event
     * @param eventCode The event code to identify the type of event
     */
    public MotionEvent(Object source, String message, double eventCode)
    {
        this.source = source;
        this.message = message;
        this.eventCode = eventCode;
        this.pos = new double[0];
        this.step = -1;
        this.state = "Unknown";
    }
    
    /**
     * Creates a new MotionEvent object to describe an event fired by a player/recorder
     * @param source The source object that has fired this event
     * @param message The message describing the event
     * @param eventCode The event code to identify the type of event
     * @param pos The position the source objects's device is set to at the time the event is fired
     * @param step The steps made by the source object at the time of the event is fired
     * @param state The description of the state that the source object is in
     */
    public MotionEvent(Object source, String message, double eventCode, 
                        double[] pos, int step, String state)
    {
        this.source = source;
        this.message = message;
        this.eventCode = eventCode;
        this.pos = pos;
        this.step = step;
        this.state = state;
    }
    
    /**
     * Returns the source Object that fired this event
     * @return The source Object that fired this event
     */
    public Object getSource()
    {
        return this.source;
    }
    
    /**
     * Returns the message describing the event
     * @return The message describing the event
     */
    public String getMessage()
    {
        return this.message;
    }
    
    /**
     * Returns the event code that identifies the event type
     * @return The event code that identifies the event type
     */
    public double getEventCode()
    {
        return this.eventCode;
    }
    
    /**
     * Returns the location of the device at the time of the event is fired
     * @return The location of the device at the time of the event is fired
     */
    public double[] getLocation()
    {
        return this.pos;
    }
    
    /**
     * Returns the state that the source object is in at the time of the event is fired
     * @return The state that the source object is in at the time of the event is fired
     */
    public String getState()
    {
        return this.state;
    }
    
    /**
     * Returns the step number the source object is on at the time of the event is fired
     * @return The step number the source object is on at the time of the event is fired
     */
    public int getStepNumber()
    {
        return this.step;
    }
    
    /**
     * Returns a String describing the event and its attributes
     * @return A String describing the event and its attributes
     */
    public String toString()
    {
        return this.getClass().getName() + "[source = " + source + "]\n" +
                    "[message = " + message + "]" + 
                    "[eventCode = " + eventCode + "]";
    }
}
