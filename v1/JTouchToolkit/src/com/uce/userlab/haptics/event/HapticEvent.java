package com.uce.userlab.haptics.event;
/**
 * Ian John Archer
 * @author Ian John Archer (Software Engineer - User-lab)
 * @version 1.0
 */
import javax.swing.text.html.HTML;

/**
 * This representes Events within a haptic device, it is passed when listener methods are 
 * called. It contains information for you to identify the event and the help with dealing
 * with the event of that event.
 * @see com.uce.userlab.haptics.event.HapticListener
 */
public class HapticEvent
{
    /**
     * The source of the event
     */
    private Object source;
    /**
     * The message describing the event
     */
    private String message;
    /**
     * The eventCode that identifies the event type
     */
    private int eventCode;
    /**
     * The position of the haptic device when the event was fired, if possiable
     */
    private double[] currentPos;
    /**
     * The value of any input to the haptic device, for example buttons pressed or rotations
     */
    private int input;
    
    /**
     * Creates a new HapticEvent object to be passed in event calls
     * @param source The source Object that has fired the event
     * @param message The message decribing the event
     * @param eventCode The identifier for the event, so its type can be determined by listener
     */
    public HapticEvent(Object source, String message, int eventCode)
    {
        this.source = source;
        this.message = message;
        this.eventCode = eventCode;
    }
    
    /**
     * Creates a new HapticEvent object to be passed in event calls
     * @param source The source Object that has fired the event
     * @param message The message decribing the event
     * @param eventCode The identifier for the event, so its type can be determined by listener
     * @param currentPos The position of the haptic device when the event was fired
     * @param input The value of any input that the haptic device has, e.g. button pressed or rotations made
     */
    public HapticEvent(Object source, String message, int eventCode,
                            double[] currentPos, int input)
    {
        this.source = source;
        this.message = message;
        this.eventCode = eventCode;
        this.currentPos = currentPos;
        this.input = input;
    }
    
    /**
     * Returns the source of the event
     * @return The source of the event
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
     * Returns the position of the device when the event is fired
     * @return The position of the device when the event is fired
     */
    public double[] getPosition()
    {
        return this.currentPos;
    }
    /**
     * Returns the event code that identifies the type of event it is
     * @return The event code that identifies the type of event it is
     */
    public int getEventCode()
    {
        return this.eventCode;
    }
    /**
     * Returns the input that the device has received or has when the event is fired
     * @return The input that the device has received or has when the event is fired
     */
    public int getInput()
    {
        return this.input;
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
