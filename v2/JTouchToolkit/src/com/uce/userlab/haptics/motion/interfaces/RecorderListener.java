package com.uce.userlab.haptics.motion.interfaces;
/*
 * Ian John Archer
 * @author Ian John Archer (Software Engineer - User-lab)
 * @version 1.0
 */

import com.uce.userlab.haptics.motion.event.MotionEvent;

/**
 * This interface layout the method that must be provided to be a Recorder Listener,
 * this interface must be implemented by all objects that wish to listener to recorder
 * events. For further details of when events are fired please see the implementation 
 * of recorder that you are using.
 */
public interface RecorderListener
{
    /**
     * This event is fired when the recorder starts recording motion of the haptic device
     * @param event The event object containing information about the event
     */
    public void recorderStarted(MotionEvent event);
    /**
     * This event is fired when the recorder stops recording motion of the haptic device
     * @param event The event object containing information about the event
     */
    public void recorderStoped(MotionEvent event);
    /**
     * This event is fired when an update is performed on the recorder
     * @param event The event object containing information about the event
     */
    public void recorderUpdate(MotionEvent event);
}
