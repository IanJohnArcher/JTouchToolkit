package com.uce.userlab.haptics.motion.interfaces;
/*
 * Ian John Archer
 * @author Ian John Archer (Software Engineer - User-lab)
 * @version 1.0
 */

import com.uce.userlab.haptics.motion.event.MotionEvent;

/**
 * This interface layout the method that must be provided to be a Player Listener,
 * this interface must be implemented by all objects that wish to listener to player
 * events. For further details of when events are fired please see the implementation 
 * of player that you are using.
 */
public interface PlayerListener 
{
    /**
     * This event is fired if the player loads a new motion record file
     * @param event The event object containing information about the event
     */
    public void fileLoaded(MotionEvent event);
    /**
     * This event is fired if the player starts playing a motion recording
     * @param event The event object containing information about the event
     */
    public void playingStarted(MotionEvent event);
    /**
     * This event is fired if the player stops playing a motion recording
     * @param event The event object containing information about the event
     */
    public void playingStoped(MotionEvent event);
    /**
     * This event is fired if the player is reset
     * @param event The event object containing information about the event
     */
    public void playerReset(MotionEvent event);
    /**
     * This event is fired if the player is skiped forwards in the motion playing
     * @param event The event object containing information about the event
     */
    public void playSkipForward(MotionEvent event);
    /**
     * This event is fired if the player is skiped backwards in the motion playing
     * @param event The event object containing information about the event
     */
    public void playSkipBackward(MotionEvent event);
    /**
     * This event is fired if the player is updated at any point in its life
     * @param event The event object containing information about the event
     */
    public void playerUpdate(MotionEvent event);
}
