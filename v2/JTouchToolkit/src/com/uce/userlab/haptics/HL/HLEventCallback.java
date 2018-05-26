package com.uce.userlab.haptics.HL;
/**
 * Ian John Archer
 * @author Ian John Archer (Software Engineer - User-lab)
 * @version 1.0
 */

/**
 * This Interface should be implemented by Objects that wish to listen to events from
 * the HL toolkit. You will be forced by the compiler to implement all the methods but
 * some may not be of use to you, ignore these.
 */
public interface HLEventCallback
{
    /**
     * This method is called when a motion event is fired
     * @param shapeId The Identifier for the shape that the event has been fired for
     * @param thread The thread that the event has been fired from
     * @param cache The Cache object that store parameter values from when the event fired
     * @param data An array of objects that have been set to be passed at event callback scheduling
     */
    public void HL_EVENT_MOTION(int shapeId, int thread, HLCache cache, Object[] data);
    /**
     * This method is called when the button 1 down event is fired
     * @param shapeId The Identifier for the shape that the event has been fired for
     * @param thread The thread that the event has been fired from
     * @param cache The Cache object that store parameter values from when the event fired
     * @param data An array of objects that have been set to be passed at event callback scheduling
     */
    public void HL_EVENT_1BUTTONDOWN(int shapeId, int thread, HLCache cache, Object[] data);
    /**
     * This method is called when the button 1 up event is fired
     * @param shapeId The Identifier for the shape that the event has been fired for
     * @param thread The thread that the event has been fired from
     * @param cache The Cache object that store parameter values from when the event fired
     * @param data An array of objects that have been set to be passed at event callback scheduling
     */
    public void HL_EVENT_1BUTTONUP(int shapeId, int thread, HLCache cache, Object[] data);
    /**
     * This method is called when the button 2 down event is fired
     * @param shapeId The Identifier for the shape that the event has been fired for
     * @param thread The thread that the event has been fired from
     * @param cache The Cache object that store parameter values from when the event fired
     * @param data An array of objects that have been set to be passed at event callback scheduling
     */
    public void HL_EVENT_2BUTTONDOWN(int shapeId, int thread, HLCache cache, Object[] data);
    /**
     * This method is called when the button 2 up event is fired
     * @param shapeId The Identifier for the shape that the event has been fired for
     * @param thread The thread that the event has been fired from
     * @param cache The Cache object that store parameter values from when the event fired
     * @param data An array of objects that have been set to be passed at event callback scheduling
     */
    public void HL_EVENT_2BUTTONUP(int shapeId, int thread, HLCache cache, Object[] data);
    /**
     * This method is called when the touch event is fired
     * @param shapeId The Identifier for the shape that the event has been fired for
     * @param thread The thread that the event has been fired from
     * @param cache The Cache object that store parameter values from when the event fired
     * @param data An array of objects that have been set to be passed at event callback scheduling
     */
    public void HL_EVENT_TOUCH(int shapeId, int thread, HLCache cache, Object[] data);
    /**
     * This method is called when the untouch event is fired
     * @param shapeId The Identifier for the shape that the event has been fired for
     * @param thread The thread that the event has been fired from
     * @param cache The Cache object that store parameter values from when the event fired
     * @param data An array of objects that have been set to be passed at event callback scheduling
     */
    public void HL_EVENT_UNTOUCH(int shapeId, int thread, HLCache cache, Object[] data);
}
