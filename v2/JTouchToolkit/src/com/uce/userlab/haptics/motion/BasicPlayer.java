package com.uce.userlab.haptics.motion;
/**
 * Ian John Archer
 * @author Ian John Archer (Software Engineer - User-lab)
 * @version 1.0
 */
import com.uce.userlab.haptics.motion.event.PlayerEventSupport;
import com.uce.userlab.haptics.motion.interfaces.Player;
import com.uce.userlab.haptics.motion.interfaces.PlayerListener;
import com.uce.userlab.haptics.*;
import com.uce.userlab.haptics.motion.interfaces.Recorder;
import java.io.*;
import java.util.*;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.*;

/**
 * A Basic player for the haptic devices, it plays recording from the BasicRecorder.
 * This is a basic implimentation of the Player interface, it allows basic changing
 * of play back speed and coOrdinates to playback but this is an example implementation
 * of Player. If more detailed or comprehensive players are needed it is recommended
 * for developers to implement their own implementation of Player.
 */
public class BasicPlayer implements Player, Runnable
{
    /**
     * The Device to play the motion recording through
     */
    private Device device;
    /**
     * Aids in managing the PlayerListeners and event firing
     */
    private PlayerEventSupport support;
    /**
     * Store the type of coOrdinate System that is being used
     */
    private int coOrdinateSystem;
    /**
     * Stored the axis values to playback, e.g. {true,true,false} = x,y Not z
     */
    private boolean[] coOrdinateRecord;
    /**
     * Stores the locations that are to be played through the device
     */
    private ArrayList<PointerLocation> coOrdinates;
    /**
     * Counts the position of the player in the data to be played
     */
    private int count;
    /**
     * The thread that will run the method that will manage the play back of the data
     */
    private Thread thread;
    /**
     * The rate that the player will play back the data
     */
    private long rate;
    /**
     * The location that the device is currently being sent to
     */
    private double[] position;
    /**
     * The PointerRelocator that manages the force need to move the device to the given position in the data
     */
    private PointerRelocator rel;
         
    /**
     * Creates a BasicPlayer for a given device
     * @param device The device to play the motion through
     */       
    public BasicPlayer(Device device)
    {
        this.device = device;
        this.rel = new PointerRelocator(this.device);
        try{
            rel.setMaxForce(device.getMaxForce());
            rel.setMinForce(device.getMinForce());
        }catch(Exception e){}
        
        this.rate = 1;
        this.support = new PlayerEventSupport(this);
        this.coOrdinateRecord = new boolean[]{true,true,true};
        this.coOrdinateSystem = BasicPlayer.COORDINATE_3D;
        
        this.coOrdinates = new ArrayList<PointerLocation>();
        this.count = -1;
        
        this.thread = new Thread(this);
    }
    
    /**
     * Adds a PlayerListener to the notify list of any events
     * @param listener The PlayerListener to add to the notify list
     */
    public void addPlayListener(PlayerListener listener){support.addPlayerListener(listener);}
    /**
     * Removes a PlayerListener from the notify list of any events
     * @param listener The PlayerListener to remove from the notify list
     */
    public void removePlayListener(PlayerListener listener){support.removePlayerListener(listener);}
    
    /**
     * Starts the playback of any data that is currently loaded in the player
     */
    public void start()
    {
        if(!this.thread.isAlive())
        {
            this.count++;
            if(this.coOrdinates.size() > 0)
            {
                this.position = this.coOrdinates.get(count).getLocation();
                this.checkLocation();
                this.rel.setLocation(position);
                this.rel.start();
                this.thread.start();
                support.firePlayingStarted("BasicPlayer started",BasicPlayer.EVENT_PLAYING_STARTED,this.position,this.count,"Playing");
            }
        }
    }
    /**
     * Stops and resets the player back to the start of the data loaded
     */
    public void stop()
    {
        if(this.thread.isAlive())
        {
            this.thread.interrupt();
            support.firePlayingStoped("BasicPlayer stopped",BasicPlayer.EVENT_PLAYING_STOPPED,this.position,this.count,"Stopped");
            this.reset();
        }
    }
    /**
     * Stops the data but retains the place reached in the playback of the loaded data
     */
    public void pause()
    {
        if(this.thread.isAlive())
            this.thread.interrupt();
    }
    /**
     * Resets the player back to the start of the loaded data
     */
    public void reset()
    {
        if(!this.thread.isAlive())
        {
            this.rel.stop();
            this.count = -1;
            this.position = null;
            support.firePlayerReset("BasicPlayer reset",BasicPlayer.EVENT_PLAYER_RESET,this.position,this.count,"Reset");
        }
    }
    /**
     * Skips the player forward by one step within the data loaded
     */
    public void skipForward()
    {
        if(this.count < this.coOrdinates.size())
            this.count++;
        this.position = this.coOrdinates.get(count).getLocation();
        this.rel.setLocation(this.position);
        support.firePlaySkipForward("BasicPlayer Skipped Forwards",BasicPlayer.EVENT_PLAYER_SKIPFORWARD,this.position,this.count,"Playing");
    }
    /**
     * Skips the player backward by one step within the data loaded
     */
    public void skipBackward()
    {
        if(this.count > 0)
            this.count--;
        this.position = this.coOrdinates.get(count).getLocation();
        this.rel.setLocation(this.position);
        support.firePlaySkipBackward("BasicPlayer Skipped Backwards",BasicPlayer.EVENT_PLAYER_SKIPBACKWARD,this.position,this.count,"Playing");
    }
    
    /**
     * Loads the recording from the file path specified ready for playback
     * @param filePath The path of the file you wish to load ready for playback
     * @throws IOException If the path or file is invalid and is unable to be loaded
     */
    public void loadRecord(String filePath) throws IOException{this.loadRecord(new File(filePath));}
    /**
     * Loads the recording from the file specified ready for playback
     * @param file The file you wish to load ready for playback
     * @throws IOException If the path or file is invalid and is unable to be loaded
     */
    public void loadRecord(File file) throws IOException
    {
        try{
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file);
            
            doc.getDocumentElement().normalize();
            
            if(doc.getDocumentElement().getNodeName().compareTo("MotionRecord") == 0)
            {
                NodeList r = doc.getDocumentElement().getElementsByTagName("RecordRate");
                Node rat = r.item(0);
                
                NodeList c = doc.getDocumentElement().getElementsByTagName("CoOrdinateSystem");
                Node coSys = c.item(0);
                
                NodeList o = doc.getDocumentElement().getElementsByTagName("CoOrdinateRecorded");
                Node coRat = o.item(0);
                
                this.rate = (new Long(rat.getFirstChild().getNodeValue())).longValue();
                String value = coSys.getFirstChild().getNodeValue();
                
                if(value.compareTo("1D") == 0)
                    this.coOrdinateSystem = Recorder.COORDINATE_1D;
                else if(value.compareTo("2D") == 0)
                    this.coOrdinateSystem = Recorder.COORDINATE_2D;
                else if(value.compareTo("3D") == 0)
                    this.coOrdinateSystem = Recorder.COORDINATE_3D;
                else
                    this.coOrdinateSystem = Recorder.COORDINATE_3D;
                
                this.coOrdinateRecord = new boolean[]{(new Boolean(coRat.getAttributes().getNamedItem("X").getNodeValue())).booleanValue(),
                                                    (new Boolean(coRat.getAttributes().getNamedItem("Y").getNodeValue())).booleanValue(),
                                                    (new Boolean(coRat.getAttributes().getNamedItem("Z").getNodeValue())).booleanValue()};
                
                NodeList list = doc.getDocumentElement().getElementsByTagName("Location");
                
                this.coOrdinates.clear();
                for(int i=0; i<list.getLength(); i++)
                {
                    double x = (new Double(list.item(i).getAttributes().getNamedItem("X").getNodeValue())).doubleValue();
                    double y = (new Double(list.item(i).getAttributes().getNamedItem("Y").getNodeValue())).doubleValue();
                    double z = (new Double(list.item(i).getAttributes().getNamedItem("Z").getNodeValue())).doubleValue();
                    
                    this.coOrdinates.add(new PointerLocation(x,y,z));
                }
                support.fireFileLoaded("BasicPlayer has loaded file",BasicPlayer.EVENT_FILE_LOADED,this.position,this.count,"Stopped");
            }
            
        }catch(Exception e)
        {
            if(e instanceof IOException)
                throw (IOException)e;
            else
                e.printStackTrace();
        }
    }
    
    /**
     * Sets the data to be played back, takes in a an array of arrays of length 3
     * @param data Array of Arrays of length of 3 holding coOrdinates to playback
     * @throws Exception If the data passed is invalid or data is missing preventing playback
     */
    public void setPlayData(double[][] data) throws Exception
    {
        if(this.thread.isAlive())
            this.stop();
        this.coOrdinates.clear();
        for(int i=0; i<data.length; i++)
        {
            double x = data[i][0];
            double y = data[i][1];
            double z = data[i][2];
            
            this.coOrdinates.add(new PointerLocation(x,y,z));
        }
        support.firePlayerUpdate("BasicPlayer new data loaded",BasicPlayer.EVENT_PLAYER_UPDATE,this.position,this.count,"Stopped");
    }
    /**
     * Sets the data to be played back, takes in a an array objects, e.g. PointerLocation
     * @param data Array of objects holding coOrdinates to playback, e.g. PointerLocation
     * @throws Exception If the data passed is invalid or data is missing preventing playback
     */
    public void setPlayData(Object[] data) throws Exception
    {
        if(this.thread.isAlive())
            this.stop();
        this.coOrdinates.clear();
        for(int i=0; i<data.length; i++)
        {
            if(data[i] instanceof PointerLocation)
                this.coOrdinates.add((PointerLocation)data[i]);
        }
        support.firePlayerUpdate("BasicPlayer new data loaded",BasicPlayer.EVENT_PLAYER_UPDATE,this.position,this.count,"Stopped");
    }
    
    /**
     * Sets the coOrdinate system to use on the playback of the loaded data
     * @param parameter The coOrdinate system identifier, see following:
     * <ul>
     *  <li><a href="interfaces/Player.html#COORDINATE_1D">COORDINATE_1D</a></li>
     *  <li><a href="interfaces/Player.html#COORDINATE_2D">COORDINATE_2D</a></li>
     *  <li><a href="interfaces/Player.html#COORDINATE_3D">COORDINATE_3D</a></li>
     * <ul>
     */
    public void setCoOrdinateSystem(int parameter)
    {
        if(this.thread.isAlive())
            this.stop();
        this.coOrdinateSystem = BasicPlayer.COORDINATE_3D;
        switch(parameter)
        {
            case Recorder.COORDINATE_1D:
            case Recorder.COORDINATE_2D:
            case Recorder.COORDINATE_3D:
                this.coOrdinateSystem = parameter;
                support.firePlayerUpdate("BasicPlayer new coOrdinate System",BasicPlayer.EVENT_PLAYER_UPDATE,this.position,this.count,"Stopped");
                break;
        }
    }
    /**
     * Set the coOrdinates to playback from the recording of the device
     * @param parameter The coOrdinate(s) that you wish to playback from the recording of the device, see following:
     * <ul>
     *  <li><a href="interfaces/Player.html#X">X</a></li>
     *  <li><a href="interfaces/Player.html#Y">Y</a></li>
     *  <li><a href="interfaces/Player.html#Z">Z</a></li>
     *  <li><a href="interfaces/Player.html#X_Y">X_Y</a></li>
     *  <li><a href="interfaces/Player.html#X_Z">X_Z</a></li>
     *  <li><a href="interfaces/Player.html#Y_Z">Y_Z</a></li>
     *  <li><a href="interfaces/Player.html#X_Y_Z">X_Y_Z</a></li>
     * <ul>
     */
    public void setCoOrdinateRecord(int parameter)
    {
        if(this.thread.isAlive())
            this.stop();
        this.coOrdinateRecord = new boolean[]{false,false,false};
        switch(parameter)
        {
            case Recorder.X:
                this.coOrdinateRecord = new boolean[]{true,false,false};
                break;
            case Recorder.Y:
                this.coOrdinateRecord = new boolean[]{false,true,false};
                break;
            case Recorder.Z:
                this.coOrdinateRecord = new boolean[]{false,false,true};
                break;
            case Recorder.X_Y:
                this.coOrdinateRecord = new boolean[]{true,true,false};
                break;
            case Recorder.X_Z:
                this.coOrdinateRecord = new boolean[]{true,false,true};
                break;
            case Recorder.Y_Z:
                this.coOrdinateRecord = new boolean[]{false,true,true};
                break;
            case Recorder.X_Y_Z:
                this.coOrdinateRecord = new boolean[]{true,true,true};
                break;
        }
        support.firePlayerUpdate("BasicPlayer new coOrdinate Record",BasicPlayer.EVENT_PLAYER_UPDATE,this.position,this.count,"Stopped");
    }
    
    /**
     * Sets the Maximum force to use when playing back the recording, e.g. Positive force (push)
     * @param force The Maximum force that should be applied to device when playing recording back
     */
    public void setMaxForce(double force) {rel.setMaxForce(force);}
    /**
     * Sets the Minimum force to use when playing back the recording, e.g. Negative force (pull)
     * @param force The Maximum force that should be applied to device when playing recording back
     */
    public void setMinForce(double force) {rel.setMinForce(force);}
    
    /**
     * Gets the Maximum force set for playbacking data
     * @return The Maximum force set for playbacking data
     */
    public double getMaxForce(){return rel.getMaxForce();}
    /**
     * Gets the Minimum force set for playbacking data
     * @return The Minimum force set for playbacking data
     */
    public double getMinForce(){return rel.getMinForce();}
    
    /**
     * This method will run within a thread and control the change in location and the playback of the data loaded
     */
    public void run()
    {
        try{
            while(position != null && count != -1)
            {
                Thread.sleep(this.rate+1);
                if(this.rel.getDistanceFromBoundary() < 2)
                {
                    if(count<this.coOrdinates.size())
                    {
                        this.position = this.coOrdinates.get(count).getLocation();
                        this.checkLocation();
                        this.rel.setLocation(this.position);
                        this.count++;
                    }
                    else
                    {
                        this.rel.stop();
                        this.position = null;
                    }
                }
            }
        }catch(Exception e){}
    }
    
    /**
     * Checks the locations against the location to playback, if location is not to be played back
     * it is defaulted to 0 on the given axis
     */
    private void checkLocation()
    {
        if(!this.coOrdinateRecord[0])
            this.position[0] = 0.0;
        if(!this.coOrdinateRecord[1])
            this.position[1] = 0.0;
        if(!this.coOrdinateRecord[2])
            this.position[2] = 0.0;
    }
    
    /**
     * Identifies file loaded events
     */
    public static final double EVENT_FILE_LOADED        =   1000;
    /**
     * Identifies playing started events
     */
    public static final double EVENT_PLAYING_STARTED    =   1100;
    /**
     * Identifies playing stopped events
     */
    public static final double EVENT_PLAYING_STOPPED    =   1010;
    /**
     * Identifies playing reset events
     */
    public static final double EVENT_PLAYER_RESET       =   0;
    /**
     * Identifies player skip forward events
     */
    public static final double EVENT_PLAYER_SKIPFORWARD =   1001;
    /**
     * Identifies player skip backward events
     */
    public static final double EVENT_PLAYER_SKIPBACKWARD=   1011;
    /**
     * Identifies player update events
     */
    public static final double EVENT_PLAYER_UPDATE      =   1111;
}
