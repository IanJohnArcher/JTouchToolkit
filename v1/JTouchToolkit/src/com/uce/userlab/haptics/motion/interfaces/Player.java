package com.uce.userlab.haptics.motion.interfaces;
/*
 * Ian John Archer
 * @author Ian John Archer (Software Engineer - User-lab)
 * @version 1.0
 */

import java.io.*;
import java.lang.*;

/**
 * This Interface layout the default set of players methods and behavours that all
 * complete implementations of a good Player should have. All players that are
 * developed and delivered with this toolkit will implement this interface
 */
public interface Player
{
    /**
     * Sets the player to play a single dimension of the haptic device
     */
    public static final int COORDINATE_1D = 1;
    /**
     * Sets the player to play two dimensions of the haptic device
     */
    public static final int COORDINATE_2D = 2;
    /**
     * Sets the player to play three dimensions of the haptic device
     */
    public static final int COORDINATE_3D = 3;
    /**
     * Sets the co-ordinate to be played to be X
     */
    public static final int X = 1;
    /**
     * Sets the co-ordinate to be played to be Y
     */
    public static final int Y = 2;
    /**
     * Sets the co-ordinate to be played to be Z
     */
    public static final int Z = 3;
    /**
     * Sets the co-ordinates to be played to be X and Y
     */
    public static final int X_Y = 4;
    /**
     * Sets the co-ordinates to be played to be X and Z
     */
    public static final int X_Z = 5;
    /**
     * Sets the co-ordinates to be played to be Y and Z
     */
    public static final int Y_Z = 6;
    /**
     * Sets the co-ordinates to be played to be X, Y and Z
     */
    public static final int X_Y_Z = 7;
    
    /**
     * Starts the player
     */
    public void start();
    /**
     * Stops the player
     */
    public void stop();
    /**
     * Pauses the player
     */
    public void pause();
    /**
     * Resets the player
     */
    public void reset();
    /**
     * Skips the player forwards
     */
    public void skipForward();
    /**
     * Skips the player backwards
     */
    public void skipBackward();
    
    /**
     * Loads a recorded file to play from a path specified by a String 
     * @param filePath The path to the file to load
     */
    public void loadRecord(String filePath) throws IOException;
    /**
     * Loads a recorded file to play from a file specified
     * @param file The File to load into the player
     */
    public void loadRecord(File file) throws IOException;
    /**
     * Sets the data to be played from an array of co-ordinates
     * @param data The Array of double co-ordinates to play
     */
    public void setPlayData(double[][] data) throws Exception;
    /**
     * Sets the data to be played from an array of objects
     * @param data The Array of Objects to play
     */
    public void setPlayData(Object[] data) throws Exception;
    
    /**
     * Sets the Co-ordinate system.
     * @param parameter The Identifier provided by interface that indicates the Co-ordinate System you wish, see: <ul>
     *          <li><code><a href="#COORDINATE_1D">COORDINATE_1D</a></code></li>
     *          <li><code><a href="#COORDINATE_2D">COORDINATE_2D</a></code></li>
     *          <li><code><a href="#COORDINATE_3D">COORDINATE_3D</a></code></li></ul>
     */
    public void setCoOrdinateSystem(int parameter);
    /**
     * Sets the dimension to play in the system
     * @param parameter The Identifier provided by interface that indicates the dimension you wish, see: <ul>
     *          <li><code><a href="#X">X</a></code></li>
     *          <li><code><a href="#Y">Y</a></code></li>
     *          <li><code><a href="#Z">Z</a></code></li>
     *          <li><code><a href="#X_Y">X_Y</a></code></li>
     *          <li><code><a href="#X_Z">X_Z</a></code></li>
     *          <li><code><a href="#Y_Z">Y_Z</a></code></li>
     *          <li><code><a href="#X_Y_Z">X_Y_Z</a></code></li></ul>
     */
    public void setCoOrdinateRecord(int parameter);
    
    /**
     * Sets the maxium force to use
     * @param force doubles that contain the maxium forces for X, Y, Z
     */
    public void setMaxForce(double force);
    /**
     * Sets the minium force to use
     * @param force doubles that contain the minium force for X, Y, Z
     */
    public void setMinForce(double force);
    
    /**
     * Returns the maxium force to use
     * @return A double value that is the maxium force for X, Y, Z
     */
    public double getMaxForce();
    /**
     * Returns the minium force to use
     * @return A double value that is the minium force for X, Y, Z
     */
    public double getMinForce();
    
    /**
     * Adds a player listener to player, this will provide notification of changes to player
     * @param listener The Listener to add to player
     */
    public void addPlayListener(PlayerListener listener);
    /**
     * Removes a player listener from player
     * @param listener The Listener to remove from player
     */
    public void removePlayListener(PlayerListener listener);
}
