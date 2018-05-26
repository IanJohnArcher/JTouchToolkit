package com.uce.userlab.haptics.motion;
/**
 * Ian John Archer
 * @author Ian John Archer (Software Engineer - User-lab)
 * @version 1.0
 */
import com.uce.userlab.haptics.motion.event.RecorderEventSupport;
import com.uce.userlab.haptics.motion.interfaces.Recorder;
import com.uce.userlab.haptics.motion.interfaces.RecorderListener;
import com.uce.userlab.haptics.Device;
import com.uce.userlab.haptics.DeviceException;
import java.io.*;
import java.util.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileOutputStream;

import org.w3c.dom.*;

/**
 * A Basic recorder for the haptic devices, it records from the given device.
 * This is a basic implimentation of the Recorder interface, it allows basic changing
 * of recording speed and coOrdinates to record but this is an example implementation
 * of Recorder. If more detailed or comprehensive recorders are needed it is recommended
 * for developers to implement their own implementation of Recorder.
 */
public class BasicRecorder implements Recorder, Runnable
{
    /**
     * The Device to record the motion from
     */
    private Device device;
    /**
     * Aids in managing the RecorderListeners and event firing
     */
    private RecorderEventSupport support;
    /**
     * Store the type of coOrdinate System that is being used
     */
    private int coOrdinateSystem;
    /**
     * Stored the axis values to store and record, e.g. {true,true,false} = x,y Not z
     */
    private boolean[] coOrdinateRecord;
    /**
     * Stores the locations that are being recorded from the device
     */
    private ArrayList<PointerLocation> coOrdinates;
    /**
     * The rate in which to record from the device
     */
    private long rate;
    /**
     * The thread that will run the method that will read of and store the location of the device at the given rate
     */
    private Thread thread;
    /**
     * The last location that was stored, this allows filtering of repeating locations
     */
    private PointerLocation reLoc;
    
    /**
     * Creates a BasicRecorder for a given device
     * @param device The device to record the motion from
     */
    public BasicRecorder(Device device)
    {
        this.device = device;
        this.support = new RecorderEventSupport(this);
        this.coOrdinateSystem = this.COORDINATE_3D;
        this.coOrdinateRecord =  new boolean[]{true,true,true};
        this.coOrdinates = new ArrayList<PointerLocation>();
        this.thread = new Thread(this);
    }
    
    /**
     * Adds a RecorderListener to the notify list of any events
     * @param listener The RecorderListener to add to the notify list
     */
    public void addRecordListener(RecorderListener listener){this.support.addRecorderListener(listener);}
    /**
     * Removes a RecorderListener from the notify list of any events
     * @param listener The RecorderListener to remove from the notify list
     */
    public void removeRecordListener(RecorderListener listener){this.support.removeRecorderListener(listener);}
    
    /**
     * Starts the recording of positions and motion of the device
     */
    public void record()
    {
        if(!this.thread.isAlive())
        {
            this.thread.start();
            support.fireRecorderStarted("BasicRecorder recording",this.EVENT_RECORDER_STARTED,new double[3],-1,"Recording");
        }
    }
    /**
     * Stops the recording of the position and motion of the device
     */
    public void stop()
    {
        if(this.thread.isAlive())
        {
            this.thread.interrupt();
            support.fireRecorderStoped("BasicRecorder stopped",this.EVENT_RECORDER_STOPPED,new double[3],-1,"Stopped");
        }
    }
    /**
     * Clears the recorded data ready for new recording
     */
    public void clear()
    {
        if(!this.thread.isAlive())
            this.coOrdinates.clear();
    }
    
    /**
     * Saves the recorded data in an xml file to a give location and file
     * @param filePath The path and file you wish to save the recorded data to
     * @throws IOException If the path or file is invalid and is unable to be writen to
     */
    public void save(String filePath) throws IOException{this.save(new File(filePath));}
    /**
     * Saves the recorded data in an xml file to a give location and file
     * @param file The file you wish to save the recorded data to
     * @throws IOException If the path or file is invalid and is unable to be writen to
     */
    public void save(File file) throws IOException
    {
        try{
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            
            Element root = doc.createElement("MotionRecord");
            doc.appendChild(root);
            
            //information about recording and device
            Element info = doc.createElement("Info");
                Element about = doc.createElement("About");
                    about.setAttribute("class", "com.uce.userlab.haptics.motion.BasicRecorder");
                    about.setAttribute("version","1.1");
                    about.setAttribute("author", "Ian John Archer (Software Engineer)");
                info.appendChild(about);
                
                Element dev = doc.createElement("Device");
                    dev.setAttribute("version", this.device.getVersion());
                    dev.setAttribute("serial", this.device.getDeviceID());
                    dev.setAttribute("vendor", this.device.getVendor());
                    dev.appendChild(doc.createTextNode(this.device.getDevice()));
                info.appendChild(dev);
                
                Element sett =  doc.createElement("Settings");
                    Element recR = doc.createElement("RecordRate");
                        recR.appendChild(doc.createTextNode(""+this.rate));
                    sett.appendChild(recR);
                    Element coS = doc.createElement("CoOrdinateSystem");
                        switch(this.coOrdinateSystem)
                        {
                            case Recorder.COORDINATE_1D:
                                coS.appendChild(doc.createTextNode("1D"));
                                break;
                            case Recorder.COORDINATE_2D:
                                coS.appendChild(doc.createTextNode("2D"));
                                break;
                            case Recorder.COORDINATE_3D:
                                coS.appendChild(doc.createTextNode("3D"));
                                break;
                        }
                    sett.appendChild(coS);
                    Element coR = doc.createElement("CoOrdinateRecorded");
                        coR.setAttribute("X",""+this.coOrdinateRecord[0]);
                        coR.setAttribute("Y",""+this.coOrdinateRecord[1]);
                        coR.setAttribute("Z",""+this.coOrdinateRecord[2]);
                    sett.appendChild(coR);
                info.appendChild(sett);
            root.appendChild(info);
            
            //the data from the recording
            Element data = doc.createElement("Data");
                PointerLocation[] locs = this.coOrdinates.toArray(new PointerLocation[this.coOrdinates.size()]);
                for(int i=0; i<locs.length; i++)
                {
                    Element loc = doc.createElement("Location");
                        loc.setAttribute("X", ""+locs[i].getX());
                        loc.setAttribute("Y", ""+locs[i].getY());
                        loc.setAttribute("Z", ""+locs[i].getZ());
                    data.appendChild(loc);
                }
            root.appendChild(data);
            
            Transformer trans = TransformerFactory.newInstance().newTransformer();
            trans.transform(new DOMSource(doc),new StreamResult(new FileOutputStream(file)));
            
        }catch(Exception e)
        {
            if(e instanceof IOException)
                throw (IOException)e;
        }
    }
    
    /**
     * The rate in which to record the position of the device, it the delay or 
     * sleep parameter for thread. Small value the more recordings (high quality),
     * Larger value the less recordings (low quality)
     * @param rate The rate in which to record position, (value used for sleep time between recordings)
     */
    public void setRate(long rate)
    {
        if(this.thread.isAlive())
            this.stop();
        this.rate = rate;
        support.fireRecorderUpdate("BasicRecorder record delay rate updated",this.EVENT_RECORDER_UPDATE,new double[3],-1,"Stopped");
    }
    /**
     * Returns the recording rate that is currently set for the recorder
     * @return The recording rate that is currently set for the recorder
     */
    public long getRate(){return this.rate;}
    
    /**
     * Returns the recorded data from the recorder as a 2D array
     * @return The recorded data from the recorder as a 2D array
     */
    public double[][] getRecordData()
    {
        Iterator it = this.coOrdinates.iterator();
        double[][] data = new double[this.coOrdinates.size()][3];
        int count = 0;
        
        while(it.hasNext())
        {
            PointerLocation point = (PointerLocation)it.next();
                data[count][0] = point.getX();
                data[count][1] = point.getY();
                data[count][2] = point.getZ();
            count++;
        }
        
        return data;
    }
    /**
     * Returns the recorded data from the recorder as a array of Objects
     * @return The recorded data from the recorder as a array of Objects (PointerLocations)
     */
    public Object[] getRecordObjects()
    {
        return this.coOrdinates.toArray();
    }
    
    /**
     * Sets the coOrdinate system to use on the recording of the motion
     * @param parameter The coOrdinate system identifier, see following:
     * <ul>
     *  <li><a href="interfaces/Recorder.html#COORDINATE_1D">COORDINATE_1D</a></li>
     *  <li><a href="interfaces/Recorder.html#COORDINATE_2D">COORDINATE_2D</a></li>
     *  <li><a href="interfaces/Recorder.html#COORDINATE_3D">COORDINATE_3D</a></li>
     * <ul>
     */
    public void setCoOrdinateSystem(int parameter)
    {
        if(this.thread.isAlive())
            this.stop();
        this.coOrdinateSystem = this.COORDINATE_3D;
        switch(parameter)
        {
            case Recorder.COORDINATE_1D:
            case Recorder.COORDINATE_2D:
            case Recorder.COORDINATE_3D:
                this.coOrdinateSystem = parameter;
                support.fireRecorderUpdate("BasicRecorder coOrdinate System updated",this.EVENT_RECORDER_UPDATE,new double[3],-1,"Stopped");
                break;
        }
    }
    /**
     * Set the coOrdinates to record from the device
     * @param parameter The coOrdinate(s) that you wish to record from the device, see following:
     * <ul>
     *  <li><a href="interfaces/Recorder.html#X">X</a></li>
     *  <li><a href="interfaces/Recorder.html#Y">Y</a></li>
     *  <li><a href="interfaces/Recorder.html#Z">Z</a></li>
     *  <li><a href="interfaces/Recorder.html#X_Y">X_Y</a></li>
     *  <li><a href="interfaces/Recorder.html#X_Z">X_Z</a></li>
     *  <li><a href="interfaces/Recorder.html#Y_Z">Y_Z</a></li>
     *  <li><a href="interfaces/Recorder.html#X_Y_Z">X_Y_Z</a></li>
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
        support.fireRecorderUpdate("BasicRecorder coOrdinate Record updated",this.EVENT_RECORDER_UPDATE,new double[3],-1,"Stopped");
    }
    
    /**
     * This methos will run within a thread and control the recording of the motion of the device
     */
    public void run()
    {
        try
        {
            while(true)
            {
                try{
                    double[] pos = device.getCurrentPosition();
                    if(reLoc != null)
                    {
                        if(!reLoc.equals(new PointerLocation(pos[0],pos[1],pos[2])))
                            this.recordPos(pos);
                    }
                    else
                        this.recordPos(pos);
                }catch(DeviceException e)
                {
                    e.printStackTrace();
                }
                this.thread.sleep(rate);
            }
        }
        catch(Exception e){}
    }
    
    /**
     * Checks the recorded location against the allowed recording fields for the recorder
     */
    private void recordPos(double[] pos)
    {
        double[] data =  new double[]{0,0,0};
        
        if(this.coOrdinateRecord[0])
            data[0] = pos[0];
        if(this.coOrdinateRecord[1])
            data[1] = pos[1];
        if(this.coOrdinateRecord[2])
            data[2] = pos[2];
        
        PointerLocation newLoc = new PointerLocation(data[0],data[1],data[2]);
        
        this.reLoc = new PointerLocation(pos[0],pos[1],pos[2]);
        this.coOrdinates.add(newLoc);
    }
    
    /**
     * Identifies recorder started events
     */
    public static final double EVENT_RECORDER_STARTED           =   10;
    /**
     * Identifies recorder stopped events
     */
    public static final double EVENT_RECORDER_STOPPED           =   1;
    /**
     * Identifies recorder update events
     */
    public static final double EVENT_RECORDER_UPDATE            =   11;
}
