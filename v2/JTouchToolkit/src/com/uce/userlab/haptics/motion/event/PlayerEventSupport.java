package com.uce.userlab.haptics.motion.event;
/**
 * Ian John Archer
 * @author Ian John Archer (Software Engineer - User-lab)
 * @version 1.0
 */
import java.util.HashSet;
import java.util.Iterator;
import com.uce.userlab.haptics.motion.interfaces.PlayerListener;

/**
 * This supports the Player implementations with manageing PlayerListeners and
 * event calls to those PlayerListeners. It provides methods for firing all 
 * events for a standard PlayerListener to detect and number of variables that can
 * be povided with the event fire.
 */
public class PlayerEventSupport
{
    /**
     * A ADT of the PlayerListeners to aid in adding, removing, retrieving
     */
    private HashSet <PlayerListener> listeners;
    /**
     * The source object that will fire all the events
     */
    private Object source;
    
    /**
     * Creates a new PlayerEventSupport object
     * @param source The source object that will fire all the events
     */
    public PlayerEventSupport(Object source)
    {
        this.source = source;
        listeners = new HashSet <PlayerListener> ();
    }
    
    /**
     * Adds a new PlayerListener to the notify list for all event firing
     * @param listener The PlayerListener to add to the notify list
     */
    public void addPlayerListener(PlayerListener listener)
    {
        this.listeners.add(listener);
    }
    
    /**
     * Removes a PlayerListener from the notify list for all event firing
     * @param listener The PlayerListener to remove from the notify list
     */
    public void removePlayerListener(PlayerListener listener)
    {
        this.listeners.remove(listener);
    }
    
    /**
     * Returns the list of PlayerListeners in an array of PlayerListeners
     * @return The list of PlayerListeners in an array of PlayerListeners
     */
    public synchronized PlayerListener[] getPlayerListeners()
    {
        return this.listeners.toArray(new PlayerListener[listeners.size()]);
    }
    
    /**
     * Returns the number of PlayerListeners that it has to notify
     * @return The number of PlayerListeners that it has to notify
     */
    public int getNumOfListeners()
    {
        return this.listeners.size();
    }
    
    /**
     * Fires a file loaded event for when the player has loaded a file to play
     * @param message The message describing the event
     * @param eventCode The event code to identify the type of event
     */
    public void fireFileLoaded(String message, double eventCode)
    {
        MotionEvent event = new MotionEvent(source, message, eventCode);
        this.fireFileLoaded(event);
    }
    /**
     * Fires a player started event for when the player starts playing the data it has back
     * @param message The message describing the event
     * @param eventCode The event code to identify the type of event
     */
    public void firePlayingStarted(String message, double eventCode)
    {
        MotionEvent event = new MotionEvent(source, message, eventCode);
        this.firePlayingStarted(event);
    }
    /**
     * Fires a player stoped event for when the player has stoped playing the data it has
     * @param message The message describing the event
     * @param eventCode The event code to identify the type of event
     */
    public void firePlayingStoped(String message, double eventCode)
    {
        MotionEvent event = new MotionEvent(source, message, eventCode);
        this.firePlayingStoped(event);
    }
    /**
     * Fires a player reset event for when the player has reset the data it has
     * @param message The message describing the event
     * @param eventCode The event code to identify the type of event
     */
    public void firePlayerReset(String message, double eventCode)
    {
        MotionEvent event = new MotionEvent(source, message, eventCode);
        this.firePlayerReset(event);
    }
    /**
     * Fires a player skip forward event for when the player has been skipped forwards
     * @param message The message describing the event
     * @param eventCode The event code to identify the type of event
     */
    public void firePlaySkipForward(String message, double eventCode)
    {
        MotionEvent event = new MotionEvent(source, message, eventCode);
        this.firePlaySkipForward(event);
    }
    /**
     * Fires a player skip backward event for when the player has been skipped backwards
     * @param message The message describing the event
     * @param eventCode The event code to identify the type of event
     */
    public void firePlaySkipBackward(String message, double eventCode)
    {
        MotionEvent event = new MotionEvent(source, message, eventCode);
        this.firePlaySkipBackward(event);
    }
    /**
     * Fires a player update for when the player state or properties have been changed
     * @param message The message describing the event
     * @param eventCode The event code to identify the type of event
     */
    public void firePlayerUpdate(String message, double eventCode)
    {
        MotionEvent event = new MotionEvent(source, message, eventCode);
        this.firePlayerUpdate(event);
    }
    
    
    /**
     * Fires a file loaded event for when the player has loaded a file to play
     * @param message The message describing the event
     * @param eventCode The event code to identify the type of event
     * @param pos The position the player is set to when the event is fired, if any
     * @param step The step number the player is on when the event is fired, if any
     * @param state A description of the state that the player is in when the event is fired
     */
    public void fireFileLoaded(String message, double eventCode,
                                double[] pos, int step, String state)
    {
        MotionEvent event = new MotionEvent(source, message, eventCode,
                                            pos, step, state);
        this.fireFileLoaded(event);
    }
    /**
     * Fires a player started event for when the player starts playing the data it has back
     * @param message The message describing the event
     * @param eventCode The event code to identify the type of event
     * @param pos The position the player is set to when the event is fired, if any
     * @param step The step number the player is on when the event is fired, if any
     * @param state A description of the state that the player is in when the event is fired
     */
    public void firePlayingStarted(String message, double eventCode,
                                    double[] pos, int step, String state)
    {
        MotionEvent event = new MotionEvent(source, message, eventCode,
                                            pos, step, state);
        this.firePlayingStarted(event);
    }
    /**
     * Fires a player stoped event for when the player has stoped playing the data it has
     * @param message The message describing the event
     * @param eventCode The event code to identify the type of event
     * @param pos The position the player is set to when the event is fired, if any
     * @param step The step number the player is on when the event is fired, if any
     * @param state A description of the state that the player is in when the event is fired
     */
    public void firePlayingStoped(String message, double eventCode,
                                    double[] pos, int step, String state)
    {
        MotionEvent event = new MotionEvent(source, message, eventCode,
                                            pos, step, state);
        this.firePlayingStoped(event);
    }
    /**
     * Fires a player reset event for when the player has reset the data it has
     * @param message The message describing the event
     * @param eventCode The event code to identify the type of event
     * @param pos The position the player is set to when the event is fired, if any
     * @param step The step number the player is on when the event is fired, if any
     * @param state A description of the state that the player is in when the event is fired
     */
    public void firePlayerReset(String message, double eventCode,
                                    double[] pos, int step, String state)
    {
        MotionEvent event = new MotionEvent(source, message, eventCode,
                                            pos, step, state);
        this.firePlayerReset(event);
    }
    /**
     * Fires a player skip forward event for when the player has been skipped forwards
     * @param message The message describing the event
     * @param eventCode The event code to identify the type of event
     * @param pos The position the player is set to when the event is fired, if any
     * @param step The step number the player is on when the event is fired, if any
     * @param state A description of the state that the player is in when the event is fired
     */
    public void firePlaySkipForward(String message, double eventCode,
                                        double[] pos, int step, String state)
    {
        MotionEvent event = new MotionEvent(source, message, eventCode,
                                            pos, step, state);
        this.firePlaySkipForward(event);
    }
    /**
     * Fires a player skip backward event for when the player has been skipped backwards
     * @param message The message describing the event
     * @param eventCode The event code to identify the type of event
     * @param pos The position the player is set to when the event is fired, if any
     * @param step The step number the player is on when the event is fired, if any
     * @param state A description of the state that the player is in when the event is fired
     */
    public void firePlaySkipBackward(String message, double eventCode,
                                        double[] pos, int step, String state)
    {
        MotionEvent event = new MotionEvent(source, message, eventCode,
                                            pos, step, state);
        this.firePlaySkipBackward(event);
    }
    /**
     * Fires a player update for when the player state or properties have been changed
     * @param message The message describing the event
     * @param eventCode The event code to identify the type of event
     * @param pos The position the player is set to when the event is fired, if any
     * @param step The step number the player is on when the event is fired, if any
     * @param state A description of the state that the player is in when the event is fired
     */
    public void firePlayerUpdate(String message, double eventCode,
                                    double[] pos, int step, String state)
    {
        MotionEvent event = new MotionEvent(source, message, eventCode,
                                            pos, step, state);
        this.firePlayerUpdate(event);
    }
    
    
    /**
     * Fires a file loaded event for when the player has loaded a file to play
     * @param event The MotionEvent to pass to all PlayerListeners
     */
    public void fireFileLoaded(MotionEvent event)
    {
        Iterator<PlayerListener> ite = listeners.iterator();
        
        while(ite.hasNext())
            ite.next().fileLoaded(event);
    }
    /**
     * Fires a player started event for when the player starts playing the data it has back
     * @param event The MotionEvent to pass to all PlayerListeners
     */
    public void firePlayingStarted(MotionEvent event)
    {
        Iterator<PlayerListener> ite = listeners.iterator();
        
        while(ite.hasNext())
            ite.next().playingStarted(event);
    }
    /**
     * Fires a player stoped event for when the player has stoped playing the data it has
     * @param event The MotionEvent to pass to all PlayerListeners
     */
    public void firePlayingStoped(MotionEvent event)
    {
        Iterator<PlayerListener> ite = listeners.iterator();
        
        while(ite.hasNext())
            ite.next().playingStoped(event);
    }
    /**
     * Fires a player reset event for when the player has reset the data it has
     * @param event The MotionEvent to pass to all PlayerListeners
     */
    public void firePlayerReset(MotionEvent event)
    {
        Iterator<PlayerListener> ite = listeners.iterator();
        
        while(ite.hasNext())
            ite.next().playerReset(event);
    }
    /**
     * Fires a player skip forward event for when the player has been skipped forwards
     * @param event The MotionEvent to pass to all PlayerListeners
     */
    public void firePlaySkipForward(MotionEvent event)
    {
        Iterator<PlayerListener> ite = listeners.iterator();
        
        while(ite.hasNext())
            ite.next().playSkipForward(event);
    }
    /**
     * Fires a player skip backward event for when the player has been skipped backwards
     * @param event The MotionEvent to pass to all PlayerListeners
     */
    public void firePlaySkipBackward(MotionEvent event)
    {
        Iterator<PlayerListener> ite = listeners.iterator();
        
        while(ite.hasNext())
            ite.next().playSkipBackward(event);
    }
    /**
     * Fires a player update for when the player state or properties have been changed
     * @param event The MotionEvent to pass to all PlayerListeners
     */
    public void firePlayerUpdate(MotionEvent event)
    {
        Iterator<PlayerListener> ite = listeners.iterator();
        
        while(ite.hasNext())
            ite.next().playerUpdate(event);
    }
    
}
