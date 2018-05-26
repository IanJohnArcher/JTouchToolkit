package com.uce.userlab.haptics.motion.interfaces;
/*
 * Ian John Archer
 * @author Ian John Archer (Software Engineer - User-lab)
 * @version 1.0
 */

import java.io.*;

/**
 * This Interface layout the default set of recorder methods and behavours that all
 * complete implementations of a good Recorder should have. All recorders that are
 * developed and delivered with this toolkit will implement this interface
 */
public interface Recorder
{
    /**
     * Sets the recorder to record a single dimension of the haptic device
     */
    public static final int COORDINATE_1D = 1;
    /**
     * Sets the recorder to record a two dimension of the haptic device
     */
    public static final int COORDINATE_2D = 2;
    /**
     * Sets the recorder to record a three dimension of the haptic device
     */
    public static final int COORDINATE_3D = 3;
    
    /**
     * Sets the co-ordinate to be record to be X
     */
    public static final int X = 1;
    /**
     * Sets the co-ordinate to be record to be Y
     */
    public static final int Y = 2;
    /**
     * Sets the co-ordinate to be record to be Z
     */
    public static final int Z = 3;
    /**
     * Sets the co-ordinate to be record to be X_Y
     */
    public static final int X_Y = 4;
    /**
     * Sets the co-ordinate to be record to be X_Z
     */
    public static final int X_Z = 5;
    /**
     * Sets the co-ordinate to be record to be Y_Z
     */
    public static final int Y_Z = 6;
    /**
     * Sets the co-ordinate to be record to be X_Y_Z
     */
    public static final int X_Y_Z = 7;
    
    /**
     * Starts the player recording
     */
    public void record();
    /**
     * Stops the player recording
     */
    public void stop();
    /**
     * Clears the recorded data
     */
    public void clear();
    
    /**
     * Saves the recorded data to specified file path
     * @param filePath The Path to the location where the recordings should be saved
     */
    public void save(String filePath) throws IOException;
    /**
     * Saves the recorded data to specified file
     * @param file The File to save the recordings to
     */
    public void save(File file) throws IOException;
    
    /**
     * Returns the Array of Co-odinates that have been recorded
     * @return Tha Array of co-ordinats that have been recorded
     */
    public double[][] getRecordData();
    /**
     * Returns the Array of Co-ordinates that have been recorded
     * @return The Array of co-ordinates that have been reocrded
     */
    public Object[] getRecordObjects();
    
    /**
     * Sets the Co-ordinate system.
     * @param parameter The Identifier provided by interface that indicates the Co-ordinate System you wish, see: <ul>
     *          <li><code><a href="#COORDINATE_1D">COORDINATE_1D</a></code></li>
     *          <li><code><a href="#COORDINATE_2D">COORDINATE_2D</a></code></li>
     *          <li><code><a href="#COORDINATE_3D">COORDINATE_3D</a></code></li></ul>
     */
    public void setCoOrdinateSystem(int parameter);
    /**
     * Sets the dimension to record in the system
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
     * Adds a recorder listener to recorder, this will provide notification of changes to recorder
     * @param listener The Listener to add to recorder
     */
    public void addRecordListener(RecorderListener listener);
    /**
     * Removes a recorder listener from recorder
     * @param listener The Listener to remove from recorder
     */
    public void removeRecordListener(RecorderListener listener);
}
