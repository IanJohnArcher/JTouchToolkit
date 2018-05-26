package com.uce.userlab.haptics.HL;
/**
 * Ian John Archer
 * @author Ian John Archer (Software Engineer - User-lab)
 * @version 1.0
 */

/**
 * This Class is the Java Implementation of OpenHaptics API HLAPI, this class is
 * includes all the methods needed to bind to OpenGL (JOGL). Many of the methods
 * and functions have been updated and changed, and in some cases removed to make
 * this more robust and stable. It must be stressed that this is the first generation
 * of this implementation.
 */
public class HLAPI
{
    /**
     * Allows stiffness to be controlled, this will effect how hard a surface will feel. 
     * <I>Value must be between 0 and 1, where 1 is the hardest and 0 the softest</I>
     */
    public static final int HL_STIFFNESS = 1;
    /**
     * Allows damping to be controlled, this will reduce the springiness of surfaces. 
     * <I>Value must be between 0 and 1, where 1 is the least springiness and 0 the highly springy</I>
     */
    public static final int HL_DAMPING = 2;
    /**
     * Allows static friction to be controlled, this will effect the resistance a surface gives to move along the surface.
     * <I>Value must be between 0 and 1, where 1 is the maximum friction for a surface and 0 is a completely frictionless surface</I>
     */
    public static final int HL_STATIC_FRICTION = 3;
    /**
     * Allows dynamic friction to be controlled, this will effect the resistance a surface gives once movement is has started along the surface.
     *<I>Value must be between 0 and 1, where 1 is the maximum friction for a suface and 0 is a completely frictionless surface</I>
     */
    public static final int HL_DYNAMIC_FRICTION = 4;
    /**
     * Allows the touchable surface to be specified
     */
    public static final int HL_TOUCHABLE_FACE = 5;
    /**
     * Applys material properties only to the front of the shape
     */
    public static final int HL_FRONT = 6;
    /**
     * Applys material properties only to the back of the shape
     */
    public static final int HL_BACK = 7;
    /**
     * Applys material properties to the front and back of the shape
     */
    public static final int HL_FRONT_AND_BACK = 8;
    
    /**
     * Gets the type of Touch Model that is being used, e.g: HL_CONTACT
     */
    public static final int HL_TOUCH_MODEL = 9;
    /**
     * Set a surface (geometric primitives) not to allow the proxy to pass though it, it may move off the surface but must stay one side of it
     */
    public static final int HL_CONTACT = 10;
    /**
     * Set a surface (geometric primitives) not to allow the proxy off the surface, it will only be allowed to move off the surface if device position from surface is greater than snap distance
     */
    public static final int HL_CONSTRAINT = 11;
    /**
     * Allows the Snap Distance for the HL_CONSTRAINT to be set, the value must be a float value representing the distance in millimeters
     */
    public static final int HL_SNAP_DISTANCE = 12;
    /**
     * Allows a single boolean value to specify weather the proxy position is update automatically, this will be valid for all shapes in frame
     */
    public static final int HL_PROXY_RESOLUTION = 13;
    /**
     * Allows the enhanced shape render to be used, this will allow depth features to be felt by the user that are normally occluded by basic rending. <I>It is Disabled by default</I>
     */
    public static final int HL_HAPTIC_CAMERA_VIEW = 14;
    /**
     * Allows the optimization of single pass rendering for depth buffer, this is incompatible it HL_HAPTIC_CAMERA_VIEW. <I>It is Disabled by default</I>
     */
    public static final int HL_ADAPTIVE_VIEWPORT = 15;
    /**
     * Allows the use of a Optimally allocated buffer for OpenGL (JOGL) buffer reading
     */
    public static final int HL_SHAPE_FEEDBACK_BUFFER = 16;
    /**
     * Allows depth commands for the OpenGL (JOGL) to be read and used with the haptic render
     */
    public static final int HL_SHAPE_DEPTH_BUFFER = 17;
    /**
     * Allows the creation of custom shaped that are not supported by other commands, OpenGL commands are ignored for this shape type
     */
    public static final int HL_SHAPE_CALLBACK = 18;
    /**
     * Gets a singel boolean value indicating weather the proxy is in contact with a shape
     */
    public static final int HL_PROXY_IS_TOUCHING = 19;
    /**
     * Gets a Vector of 3 doubles representing the point of contact, only valid if HL_PROXY_IS_TOUCHING is <code>true</code>
     */
    public static final int HL_PROXY_TOUCH_NORMAL = 20;
    /**
     * Gets a Vector of 3 doubles representing the shapes overall reaction force sent to the haptic device for the last frame
     */
    public static final int HL_REACTION_FORCE = 21;
    /**
     * Allows you to specify to the render approximately how many vertices will be in the next feedback so it can reserve the correct amount of memory
     */
    public static final int HL_SHAPE_FEEDBACK_BUFFER_VERTICES = 22;
    /**
     * Allows you to pass a single boolean value to indicate weather the following shape should be treated as a dynamically changing surface.
     */
    public static final int HL_SHAPE_DYNAMIC_SURFACE_CHANGE = 23;
    /**
     * All matrix manipulation commands will target the transform from view co-ordinates to touch co-ordinates
     */
    public static final int HL_VIEWTOUCH = 24;
    /**
     * All matrix manipulation commands will target the transform from touch co-ordinates to the local co-ordinates of the haptic device
     */
    public static final int HL_TOUCHWORKSPACE = 25;
    /**
     * Gets array of 16 doubles representing 4x4 transform matrix in column major order for co-ordinates from view to touch
     */
    public static final int HL_VIEWTOUCH_MATRIX = 26;
    /**
     * Gets array of 16 doubles representing 4x4 transform matrix in column major order for co-ordinates from touch to workspace
     */
    public static final int HL_TOUCHWORKSPACE_MATRIX = 27;
    /**
     * Gets the details of the Workspace that is being used
     */
    public static final int HL_WORKSPACE = 28;
    /**
     * Gets the Workspace Maximum Dimensions that can be used
     */
    public static final int HL_MAX_WORKSPACE_DIMS = 29;
    /**
     * Gets a Vector of 3 doubles representing the position of proxy in world co-ordinates
     */
    public static final int HL_PROXY_POSITION = 30;
    /**
     * Gets a Vector of 4 doubles representing a quaternion that specifies the rotation of the proxy world co-ordinates
     */
    public static final int HL_PROXY_ROTATION = 31;
    /**
     * Gets a Vector of 16 doubles representing a 4x4 transform matrix in column major order that specifies the transform
     * of proxy relative to world co-ordinates
     */
    public static final int HL_PROXY_TRANSFORM = 32;
    /**
     * Gets a Vector of 3 doubles representing the position of the haptics device in world co-ordinates
     */
    public static final int HL_DEVICE_POSITION = 33;
    /**
     * Gets a Vector of 4 doubles representing a quaternion that specifies the rotation of the haptic device in world co-ordinates
     */
    public static final int HL_DEVICE_ROTATION = 34;
    /**
     * Gets Vector 16 doubles representing a 4x4 transform matrix in column major order that specifies the transform of the haptic
     * device relative to world co-ordinates
     */
    public static final int HL_DEVICE_TRANSFORM = 35;
    /**
     * Gets Vector of 3 doubles representing the last force, in world co-ordinates, sent to the haptic device
     */
    public static final int HL_DEVICE_FORCE = 36;
    /**
     * Gets Vector of 3 doubles representing the last torque, in world co-ordinates, sent to the haptic device
     */
    public static final int HL_DEVICE_TORQUE = 37;
    /**
     * Gets the Button1 state, value of <code>true</code> means the button is depressed
     */
    public static final int HL_BUTTON1_STATE = 38;
    /**
     * Gets the Button2 state, value of <code>true</code> means the button is depressed
     */
    public static final int HL_BUTTON2_STATE = 39;
    /**
     * An event type that is fired when the proxy has moved or rotated more than the tolerance set
     */
    public static final int HL_EVENT_MOTION = 40;
    /**
     * An event type that is fired when button 1 is depressed
     */
    public static final int HL_EVENT_1BUTTONDOWN = 41;
    /**
     * An event type that is fired when button 1 is released
     */
    public static final int HL_EVENT_1BUTTONUP = 42;
    /**
     * An event type that is fired when button 2 is depressed
     */
    public static final int HL_EVENT_2BUTTONDOWN = 43;
    /**
     * An event type that is fired when button 2 is released
     */
    public static final int HL_EVENT_2BUTTONUP = 44;
    /**
     * An event type that is fired when a shape within a scene is touched
     */
    public static final int HL_EVENT_TOUCH = 45;
    /**
     * An event type that is fired when a shape within a scene is no longer being touched
     */
    public static final int HL_EVENT_UNTOUCH = 46;
    /**
     * An event type that is fired when a Calibration update has been made
     */
    public static final int HL_EVENT_CALIBRATION_UPDATE = 47;
    /**
     * An event type that is fired when a Calibration update is required and needs user input. e.g: return to dock position
     */
    public static final int HL_EVENT_CALIBRATION_INPUT = 48;
    /**
     * Gets a double value for the precision of the device, the minimum distance the device needs to move in the workspace before a motion event is fired
     */
    public static final int HL_EVENT_MOTION_LINEAR_TOLERANCE = 49;
    /**
     * Gets a double value for the precision of the device, the minimum rotation the device needs to move before a motion event is fired
     */
    public static final int HL_EVENT_MOTION_ANGULAR_TOLERANCE = 50;
    /**
     * Sets the Event Callback to occure for any object within a scene
     */
    public static final int HL_OBJECT_ANY = 0;
    /**
     * This is used to set a Callback to a intersect line of a user defined custom shape
     */
    public static final int HL_SHAPE_INTERSECT_LS = 52;
    /**
     * This is used to set a Callback to find closest point on the surface to an input point as well as one or more local features
     */
    public static final int HL_SHAPE_CLOSEST_FEATURES = 53;
    /**
     * The Local point is a single point whose position is given by a Vector
     */
    public static final int HL_LOCAL_FEATURE_POINT = 54;
    /**
     * The Local line is give by a start point (Vector) and a end point (Vector)
     */
    public static final int HL_LOCAL_FEATURE_LINE = 55;
    /**
     * The Local plan is a whose normal is given by a Vectors and what passes though it
     */
    public static final int HL_LOCAL_FEATURE_PLANE = 56;
    /**
     * This will indicate that the Callback method will be called from client thread or when the client calls hlCheckEvents
     */
    public static final int HL_CLIENT_THREAD = 57;
    /**
     * This will indicate that the Callback method will be called from the internal collision thread
     */
    public static final int HL_COLLISION_THREAD = 58;
    /**
     * Allows the user to create a custom effect by setting a effect callback from the renderer
     */
    public static final int HL_EFFECT_CALLBACK = 59;
    /**
     * Adds a constant force vector to the total force sent to the haptic device
     */
    public static final int HL_EFFECT_CONSTANT = 60;
    /**
     * Adds a spring force to the total force sent to the haptic device, this force will pull the proxy towards event point
     */
    public static final int HL_EFFECT_SPRING = 61;
    /**
     * Adds a viscous force to the total force sent to the haptic device, this force will apply resistance according to the velocity of the haptic device
     */
    public static final int HL_EFFECT_VISCOUS = 62;
    /**
     * Adds a friction force to the total force sent to the hatic device, this friction is for both when touching objects and when in free space
     */
    public static final int HL_EFFECT_FRICTION = 63;
    /**
     * Gets the effect property type that is current selected
     */
    public static final int HL_EFFECT_PROPERTY_TYPE = 64;
    /**
     * This property is used by spring, friction and viscous effect types. The higher the gains will mean the greater the force that will be generated
     */
    public static final int HL_EFFECT_PROPERTY_GAIN = 65;
    /**
     * This property is used by constant, spring, friction and viscous effect types. This will be the cap on the maximum force that will be generated
     */
    public static final int HL_EFFECT_PROPERTY_MAGNITUDE = 66;
    /**
     * Reserved for use by future effect types and callback effects
     */
    public static final int HL_EFFECT_PROPERTY_FREQUENCY = 67;
    /**
     * Used by all effects started with a call to <code>hlTriggerEffect</code>. The effect will be automatically be terminated when the specified duration has elapsed
     */
    public static final int HL_EFFECT_PROPERTY_DURATION = 68;
    /**
     * This property is used by spring effect. The parameter passed will be the anchor of the spring
     */
    public static final int HL_EFFECT_PROPERTY_POSITION = 69;
    /**
     * This property is used by constant effect. The parameter passed will represent the direction of the constant force vector
     */
    public static final int HL_EFFECT_PROPERTY_DIRECTION = 70;
    /**
     * Gets the computed force for the effect
     */
    public static final int HL_EFFECT_COMPUTE_FORCE = 71;
    /**
     * Indicates the start of an effect definition or excution
     */
    public static final int HL_EFFECT_START = 72;
    /**
     * Indicates the end of a effect excution or definition
     */
    public static final int HL_EFFECT_STOP = 73;
    /**
     * Gets the version of the haptic library that this API is binding to
     */
    public static final int HL_VERSION = 74;
    /**
     * Gets the name of the company responsible for the renderer library that this API is binding to
     */
    public static final int HL_VENDOR = 75;
    /**
     * 
     */
    //public static final int HL_VERSION_MAJOR_NUMBER = 1;
    /**
     *  
     */
    //public static final int HL_VERSION_MINOR_NUMBER = 0;
    
    
    /**
     * Begins a haptic frame, starts render to send data to haptic device
     * @throws HLException - if nested within another hlBeginFrame statement
     */
    public static native void hlBeginFrame() throws HLException;
    /**
     * Sets the haptic device for the current rendering context
     * @param hHD the device ID that you wish set
     * @throws HLException If nested within a hlBeginFrame or if no haptic rendering context is active
     */
    public static native void hlContextDevice(int hHD) throws HLException;
    /**
     * Creates a new haptic rendering context for the given haptic device
     * @param hHD the device ID that you wish to create a context for
     * @return and integer to represent or indentify the HHLRC that has been created
     */
    public static native int hlCreateContext(int hHD);
    /**
     * Deletes a haptic device rendering context
     * @param hHLRC the rendering context ID that you wish to delete
     */
    public static native void hlDeleteContext(int hHLRC);
    /**
     * Ends a haptic frame, the block now send the code to the haptic device
     * @throws HLException If the hlBeginFrame is not set, or if there is no renderer context current
     */
    public static native void hlEndFrame() throws HLException;
    /**
     * Returns the ID for the current haptic device rendering context
     * @return Integer that is used to identify/refer to the context
     */
    public static native int hlGetCurrentContext();
    /**
     * Returns the current device in use for the present context
     * @return Integer that is used to identify/refer to the device
     */
    public static native int hlGetCurrentDevice();
    /**
     * Makes a current context current, a current context is used to send force to its device
     * @param hHLRC the context ID that you wish to make current
     */
    public static native void hlMakeCurrent(int hHLRC);
    
    /**
     * Enables a capability for the current rendering context
     * @param cap The capability you wish to enable, see:
     * <code><ul>
     * <li><a href="#HL_PROXY_RESOLUTION">HL_PROXY_RESOLUTION</a></li>
     * <li><a href="#HL_HAPTIC_CAMERA_VIEW">HL_HAPTIC_CAMERA_VIEW</a></li>
     * <li><a href="#HL_ADAPTIVE_VIEWPORT">HL_ADAPTIVE_VIEWPORT</a></li>
     * </ul></code>
     * @throws HLException If the capability is not known or cannot be enabled
     */
    public static native void hlEnable(int cap) throws HLException;
    /**
     * Disabled a capability for the current rendering context
     * @param cap The capability you wish to disable, see:
     * <code><ul>
     * <li><a href="#HL_PROXY_RESOLUTION">HL_PROXY_RESOLUTION</a></li>
     * <li><a href="#HL_HAPTIC_CAMERA_VIEW">HL_HAPTIC_CAMERA_VIEW</a></li>
     * <li><a href="#HL_ADAPTIVE_VIEWPORT">HL_ADAPTIVE_VIEWPORT</a></li>
     * </ul></code>
     * @throws HLException If the capability is not known or cannot be disabled
     */
    public static native void hlDisable(int cap) throws HLException;
    /**
     * Allows queries of different state values that have boolean data types
     * @param pname The property you wish to query and return the state of, see:
     * <code><ul>
     * <li><a href="#HL_BUTTON1_STATE">HL_BUTTON1_STATE</a></li>
     * <li><a href="#HL_BUTTON2_STATE">HL_BUTTON2_STATE</a></li>
     * <li><a href="#HL_PROXY_IS_TOUCHING">HL_PROXY_IS_TOUCHING</a></li>
     * </ul></code>
     * @return An array of boolean values representing the state of the property you are querying
     * @throws HLException If the property is un-known
     */
    public static native boolean[] hlGetBooleanv(int pname) throws HLException;
    /**
     * Allows queries of different state values that have double data types
     * @param pname The property you wish to query and return the state of, see:
     * <code><ul>
     * <li><a href="#HL_PROXY_TOUCH_NORMAL">HL_PROXY_TOUCH_NORMAL</a></li>
     * <li><a href="#HL_PROXY_POSITION">HL_PROXY_POSITION</a></li>
     * <li><a href="#HL_PROXY_ROTATION">HL_PROXY_ROTATION</a></li>
     * <li><a href="#HL_PROXY_TRANSFORM">HL_PROXY_TRANSFORM</a></li>
     * <li><a href="#HL_DEVICE_POSITION">HL_DEVICE_POSITION</a></li>
     * <li><a href="#HL_DEVICE_ROTATION">HL_DEVICE_ROTATION</a></li>
     * <li><a href="#HL_DEVICE_TRANSFORM">HL_DEVICE_TRANSFORM</a></li>
     * <li><a href="#HL_DEVICE_FORCE">HL_DEVICE_FORCE</a></li>
     * <li><a href="#HL_DEVICE_TORQUE">HL_DEVICE_TORQUE</a></li>
     * <li><a href="#HL_EVENT_MOTION_LINEAR_TOLERANCE">HL_EVENT_MOTION_LINEAR_TOLERANCE</a></li>
     * <li><a href="#HL_EVENT_MOTION_ANGULAR_TOLERANCE">HL_EVENT_MOTION_ANGULAR_TOLERANCE</a></li>
     * <li><a href="#HL_VIEWTOUCH_MATRIX">HL_VIEWTOUCH_MATRIX</a></li>
     * <li><a href="#HL_TOUCHWORKSPACE_MATRIX">HL_TOUCHWORKSPACE_MATRIX</a></li>
     * </ul></code>
     * @return An array of double values representing the state of the property you are querying
     * @throws HLException If the property is un-known
     */
    public static native double[] hlGetDoublev(int pname) throws HLException;
    /**
     * Allows queries of different state values that have integer data types
     * @param pname The property you wish to query and return the state of
     * @return An array of integer values representing the state of the property you are querying
     * @throws HLException If the property is un-known
     */
    public static native int[] hlGetIntegerv(int pname) throws HLException;
    /**
     * Returns a string description of the haptic renderer implementation
     * @param name The property of current render you wish to get a description of, see:
     * <code><ul>
     * <li><a href="#HL_VENDOR">HL_VENDOR</a></li>
     * <li><a href="#HL_VERSION">HL_VERSION</a></li>
     * </ul></code>
     * @return A String describing the current render implementation
     * @throws HLException If the property is un-known
     */
    public static native String hlGetString(int name) throws HLException;
    /**
     * Sets parameters that allow the haptic render to optimise its performance
     * @param target The property to set, see:
     * <code><ul>
     * <li><a href="#HL_SHAPE_FEEDBACK_BUFFER_VERTICES">HL_SHAPE_FEEDBACK_BUFFER_VERTICES</a></li>
     * </ul></code>
     * @param value The value to set the specified property to, <I>integer</I>
     * @throws HLException If the property is un-known or if the value is invalid, also if there is not haptic render context current
     */
    public static native void hlHinti(int target, int value) throws HLException;
    /**
     * Sets parameters that allow the haptic render to optimise its performance
     * @param target The property to set, see:
     * <code><ul>
     * <li><a href="#HL_SHAPE_DYNAMIC_SURFACE_CHANGE">HL_SHAPE_DYNAMIC_SURFACE_CHANGE</a></li>
     * </ul></code>
     * @param value The value to set the specified property to, <I>boolean</I>
     * @throws HLException If the property is un-known or if the value is invalid, also if there is not haptic render context current
     */
    public static native void hlHintb(int target, boolean value) throws HLException;
    /**
     * Checks weather a capability is enabled for the current renderer context
     * @param cap The Capability to check, see:
     * <code><ul>
     * <li><a href="#HL_PROXY_RESOLUTION">HL_PROXY_RESOLUTION</a></li>
     * <li><a href="#HL_HAPTIC_CAMERA_VIEW">HL_HAPTIC_CAMERA_VIEW</a></li>
     * <li><a href="#HL_ADAPTIVE_VIEWPORT">HL_ADAPTIVE_VIEWPORT</a></li>
     * </ul></code>
     * @return A boolean value to indicate weather a capability is enabled or disabled, <code>true</code> if enabled, <code>false</code> if disabled
     * @throws HLException If the capability is un-known or if there is not current renderer context
     */
    public static native boolean hlIsEnabled(int cap) throws HLException;
    
    /**
     * Indicates the begin of a shape, the geometric primitive will be read and sent at hlEndShape
     * @param type The type of shape to be specified, see:
     * <code><ul>
     * <li><a href="#HL_SHAPE_FEEDBACK_BUFFER">HL_SHAPE_FEEDBACK_BUFFER</a></li>
     * <li><a href="#HL_SHAPE_DEPTH_BUFFER">HL_SHAPE_DEPTH_BUFFER</a></li>
     * <li><a href="#HL_SHAPE_CALLBACK">HL_SHAPE_CALLBACK</a></li>
     * </ul></code>
     * @param shape The ID to be used to identify the following shape definition
     * @throws HLException If the type is not known, or if the operation is not within a <code>hlBeginFrame</code> or already in a <code>hlBeginShape</code>
     */
    public static native void hlBeginShape(int type, int shape) throws HLException;
    /**
     * Deallocates an ID created by <code>hlGenShape</code>
     * @param shape The first shape identifier to deallocate
     * @param range The range length to deallocate from the first ID
     * @throws HLException If the IDs and range was not already allocated, or if there is not haptic renderer context
     */
    public static native void hlDeleteShapes(int shape, int range) throws HLException;
    /**
     * Closes the shape specified by <code>hlBeginShape</code>, the geometry is sent ot the haptic renderer
     * @throws HLException If <code>hlBeginShape</code> is already set, or not within a <code>hlBeginFrame</code>
     */
    public static native void hlEndShape() throws HLException;
    /**
     * Generates a unique ID for a shape
     * @return Integer that can be used to uniquelly identify a shape
     * @throws HLException If no haptic rendering context is active
     */
    public static native int hlGenShape() throws HLException;
    /**
     * Generates unique IDs for a range of shapes
     * @return Integer that is the first in a range of IDs that can be used to identify shapes
     * @throws HLException If no haptic rendering context is active
     */
    public static native int hlGenShapes(int range) throws HLException;
    
    /**
     * Specifies a local geometry feature to the haptic renderer
     * @param type The type of local feature being specified
     * @param v An array of floats that specify the location of a local feature
     * @throws HLException If the type is not valid
     */
    public static native void hlLocalFeature1fv(int type, float[] v) throws HLException;
    /**
     * Specifies a local geometry feature to the haptic renderer
     * @param type The type of local feature being specified
     * @param v An array of doubles that specify the location of a local feature
     * @throws HLException If the type is not valid
     */
    public static native void hlLocalFeature1dv(int type, double[] v) throws HLException;
    /**
     * Specifies a local geometry feature to the haptic renderer
     * @param type The type of local feature being specified
     * @param v1 An array of floats that specify the location of the start of a local feature
     * @param v2 An array of floats that specify the location of the end of a local feature
     * @throws HLException If the type is not valid
     */
    public static native void hlLocalFeature2fv(int type, float[] v1, float[] v2) throws HLException;
    /**
     * Specifies a local geometry feature to the haptic renderer
     * @param type The type of local feature being specified
     * @param v1 An array of doubles that specify the location of the start of a local feature
     * @param v2 An array of doubles that specify the location of the end of a local feature
     * @throws HLException If the type is not valid
     */
    public static native void hlLocalFeature2dv(int type, double[] v1, double[] v2) throws HLException;
    
    /**
     * Checks weather the identifier passed is an unique identifier for a shape
     * @param shape The unique identifier to check
     * @return A boolean value indicating weather the integer is a ID used as an ID for a Shape
     * @throws HLException If called within a <code>hlBeginShape</code> and <code>hlEndShape</code>
     */
    public static native boolean hlIsShape(int shape) throws HLException;
    
    /**
     * Allows querying of the state of a perticular shape
     * @param shapeId Identifier of the shape you wish to query
     * @param pname Name of the property you wish to recieve information about, see:
     * <code><ul>
     * <li><a href="#HL_PROXY_IS_TOUCHING">HL_PROXY_IS_TOUCHING</a></li>
     * </ul></code>
     * @return An array of boolean values indicating the state of that property for that shape
     * @throws HLException If the property is not known, or no haptic rendering context is active
     */
    public static native boolean[] hlGetShapeBooleanv(int shapeId, int pname) throws HLException;
    /**
     * Allows querying of the state of a perticular shape
     * @param shapeId Identifier of the shape you wish to query
     * @param pname Name of the property you wish to recieve information about, see:
     * <code><ul>
     * <li><a href="#HL_REACTION_FORCE">HL_REACTION_FORCE</a></li>
     * </ul></code>
     * @return An array of double values indicating the state of that property for that shape
     * @throws HLException If the property is not known, or no haptic rendering context is active
     */
    public static native double[] hlGetShapeDoublev(int shapeId, int pname) throws HLException;
    
    /**
     * Gets the material property for the haptic shapes and constraints
     * @param face The face to the material is applied to, see:
     * <code><ul>
     * <li><a href="#HL_FRONT">HL_FRONT</a></li>
     * <li><a href="#HL_BACK">HL_BACK</a></li>
     * </ul></code>
     * @param pname The property name to return the value of for the give face(s), see:
     * <code><ul>
     * <li><a href="#HL_STIFFNESS">HL_STIFFNESS</a></li>
     * <li><a href="#HL_DAMPING">HL_DAMPING</a></li>
     * <li><a href="#HL_STATIC_FRICTION">HL_STATIC_FRICTION</a></li>
     * <li><a href="#HL_DYNAMIC_FRICTION">HL_DYNAMIC_FRICTION</a></li>
     * </ul></code>
     * @return An array of floats of the value of the material property
     * @throws HLException If face or pname arguments are not known values or properties, or if there is no haptic rendering context
     */
    public static native float[] hlGetMaterialfv(int face, int pname) throws HLException;
    /**
     * Sets the haptic material property for the shape or constraint
     * @param face The face(s) to apply the property to, see:
     * <code><ul>
     * <li><a href="#HL_FRONT">HL_FRONT</a></li>
     * <li><a href="#HL_BACK">HL_BACK</a></li>
     * <li><a href="#HL_FRONT_AND_BACK">HL_FRONT_AND_BACK</a></li>
     * </ul></code>
     * @param pname The property to apply to the material of the give face(s), see:
     * <code><ul>
     * <li><a href="#HL_STIFFNESS">HL_STIFFNESS</a></li>
     * <li><a href="#HL_DAMPING">HL_DAMPING</a></li>
     * <li><a href="#HL_STATIC_FRICTION">HL_STATIC_FRICTION</a></li>
     * <li><a href="#HL_DYNAMIC_FRICTION">HL_DYNAMIC_FRICTION</a></li>
     * </ul></code>
     * @param param The Value to set the property to for the give face(s)
     * @throws HLException If the face or property is unknown, or if there is no haptic renderer context active. <i>The value to set must be between 0 and 1</I>
     */
    public static native void hlMaterialf(int face, int pname, float param) throws HLException;
    /**
     * Sets the face of the shapes that will be touchable by the haptic device
     * @param mode The face(s)/mode that will be set to touchable
     * @throws HLException If the mode argument is not a known value, or if there is no haptic context active
     */
    public static native void hlTouchableFace(int mode) throws HLException;
    /**
     * Sets the touch model to specify shpaes as contact shapes or constraints
     * @param mode The contact or constraint model, see:
     * <code><ul>
     * <li><a href="#HL_FRONT">HL_FRONT</a></li>
     * <li><a href="#HL_BACK">HL_BACK</a></li>
     * <li><a href="#HL_FRONT_AND_BACK">HL_FRONT_AND_BACK</a></li>
     * </ul></code>
     * @throws HLException If the mode is not known, or there is no haptic renderer context active
     */
    public static native void hlTouchModel(int mode) throws HLException;
    
    /**
     * Sets properties of the touch model
     * @param mode The touch parameter to alter, see:
     * <code><ul>
     * <li><a href="#HL_SNAP_DISTANCE">HL_SNAP_DISTANCE</a></li>
     * </ul></code>
     * @param param The new value to set to the touch parameter
     * @throws HLException If the parameter is unknown, or if there is no haptic renderer context current
     */
    public static native void hlTouchModelf(int mode, float param) throws HLException;
    
    /**
     * Deallocates unique identifiers for effects
     * @param effect The first effect identifier to deallocate
     * @param range The range from the first effect to deallocate
     * @throws HLException If the the effect or any in range where not allocated, or if there is no haptic renderer context
     */
    public static native void hlDeleteEffects(int effect, int range) throws HLException;
    
    /**
     * Sets the current value of an effect property
     * @param pname The property to set the value of
     * @param param The double value to set to the specified property
     * @throws HLException If the property is not known, or if there is no haptic renderer context current
     */
    public static native void hlEffectd(int pname, double param) throws HLException;
    /**
     * Sets the current value of an effect property
     * @param pname The property to set the value of
     * @param param The integer value to set to the specified property
     * @throws HLException If the property is not known, or if there is no haptic renderer context current
     */
    public static native void hlEffecti(int pname, int param) throws HLException;
    /**
     * Sets the current value of an effect property
     * @param pname The property to set the value of
     * @param params The array of double values to set to the specified property
     * @throws HLException If the property is not known, or if there is no haptic renderer context current
     */
    public static native void hlEffectdv(int pname, double[] params) throws HLException;
    /**
     * Sets the current value of an effect property
     * @param pname The property to set the value of
     * @param params The array of integer values to set to the specified property
     * @throws HLException If the property is not known, or if there is no haptic renderer context current
     */
    public static native void hlEffectiv(int pname, int[] params) throws HLException;
    
    /**
     * Generates unique identifiers for the use of assigning to effects
     * @return An unique integer to assign to an effect
     * @throws HLException If there is no haptic renderer context active
     */
    public static native int hlGenEffect() throws HLException;
    /**
     * Generates unique identifiers for the use of assigning to effects
     * @param range The range to allocate and reserve to be set to effects
     * @return The first unique integer in the range to assign to an effect
     * @throws HLException If there is no haptic renderer context active
     */
    public static native int hlGenEffects(int range) throws HLException;
    
    /**
     * Queries the current value of an effect property
     * @param effect The identifier of the effect to query
     * @param pname The name of there property to query on the given effect
     * @return The value of the property in the form of an array of doubles
     * @throws HLException If the property is unknown, or if there is no haptic renderer context active
     */
    public static native double[] hlGetEffectdv(int effect, int pname) throws HLException;
    /**
     * Queries the current value of an effect property
     * @param effect The identifier of the effect to query
     * @param pname The name of there property to query on the given effect
     * @return The value of the property in the form of an array of integers
     * @throws HLException If the property is unknown, or if there is no haptic renderer context active
     */
    public static native double[] hlGetEffectiv(int effect, int pname) throws HLException;
    
    /**
     * Checks if the give identifier is an effect
     * @param effect The Identifier to check
     * @return A boolean value to indicate weather the give identifier is allocated to an effect
     * @throws HLException If there is no haptic renderer context active
     */
    public static native boolean hlIsEffect(int effect) throws HLException;
    
    /**
     * Starts an effect which will continue to run until it is terminated by <code>hlStopEffect</code>
     * @param type The type of effect to start, see:
     * <code><ul>
     * <li><a href="#HL_EFFECT_CONSTANT">HL_EFFECT_CONSTANT</a></li>
     * <li><a href="#HL_EFFECT_SPRING">HL_EFFECT_SPRING</a></li>
     * <li><a href="#HL_EFFECT_VISCOUS">HL_EFFECT_VISCOUS</a></li>
     * <li><a href="#HL_EFFECT_FRICTION">HL_EFFECT_FRICTION</a></li>
     * <li><a href="#HL_EFFECT_CALLBACK">HL_EFFECT_CALLBACK</a></li>
     * </ul></code>
     * @param effect The unique identifier of the effect to start
     * @throws HLException If type is not known, or if not within a <code>hlBeginFrame</code>/<code>hlEndFrame</code> or if inside <code>hlBeginShape</code>/<code>hlEndShape</code>
     */
    public static native void hlStartEffect(int type, int effect) throws HLException;
    /**
     * Stops an effect which has been started by <code>hlStartEffect</code>
     * @param effect The unique identifier of the effect to stop
     * @throws HLException If type is not known, or if not within a <code>hlBeginFrame</code>/<code>hlEndFrame</code> or if inside <code>hlBeginShape</code>/<code>hlEndShape</code>
     */
    public static native void hlStopEffect(int effect) throws HLException;
    /**
     * Starts an effect which will continue to run for a specified duration
     * @param type The type of effect to start running for the specified duration, see:
     * <code><ul>
     * <li><a href="#HL_EFFECT_CONSTANT">HL_EFFECT_CONSTANT</a></li>
     * <li><a href="#HL_EFFECT_SPRING">HL_EFFECT_SPRING</a></li>
     * <li><a href="#HL_EFFECT_VISCOUS">HL_EFFECT_VISCOUS</a></li>
     * <li><a href="#HL_EFFECT_FRICTION">HL_EFFECT_FRICTION</a></li>
     * <li><a href="#HL_EFFECT_CALLBACK">HL_EFFECT_CALLBACK</a></li>
     * </ul></code>
     * @throws HLException If the type is an unknown type, or if not within a <code>hlBeginFrame</code>/<code>hlEndFrame</code> or if inside <code>hlBeginShape</code>/<code>hlEndShape</code>
     */
    public static native void hlTriggerEffect(int type) throws HLException;
    /**
     * Sets properties of the proxy
     * @param pname The name of the property to set the value of
     * @param params The array of double values to set to the given property of the proxy
     * @throws HLException If the property is unknown, or if there is no haptic rendering context active or not within <code>hlBeginFrame</code>/<code>hlEndFrame</code>
     */
    public static native void hlProxydv(int pname, double[] params) throws HLException;
    /**
     * Replaces the current matrix on top of the current matrix stack with the identity matrix
     * @throws HLException If there no haptic renderer context active
     */
    public static native void hlLoadIdentity() throws HLException;
    
    /**
     * Replaces the current matrix with the 4x4 matrix specified
     * @param m The 4x4 matrix of double values to place on top of the current one <I>Array length 16</I>
     * @throws HLException If no haptic renderer context is active
     */
    public static native void hlLoadMatrixd(double[] m) throws HLException;
    /**
     * Replaces the current matrix with the 4x4 matrix specified
     * @param m The 4x4 matrix of float values to place on top of the current one <I>Array length 16</I>
     * @throws HLException If no haptic renderer context is active
     */
    public static native void hlLoadMatrixf(float[] m) throws HLException;
    /**
     * Multiplies the current matrix on top of the current stack with the 4x4 matrix specified
     * @param m The 4x4 matrix of double values to multiply the current matrix on top of the stack <I>Array length 16</I>
     * @throws HLException If no haptic renderer context is active
     */
    public static native void hlMultMatrixd(double[] m) throws HLException;
    /**
     * Multiplies the current matrix on top of the current stack with the 4x4 matrix specified
     * @param m The 4x4 matrix of float values to multiply the current matrix on top of the stack <I>Array length 16</I>
     * @throws HLException If no haptic renderer context is active
     */
    public static native void hlMultMatrixf(float[] m) throws HLException;
    /**
     * Set which stack is the target for the future calls to matrix manipulation commands
     * @param mode The mode/stack to use for furure calls to matrix manipulation commands, see:
     * <code><ul>
     * <li><a href="#HL_VIEWTOUCH">HL_VIEWTOUCH</a></li>
     * <li><a href="#HL_TOUCHWORKSPACE">HL_TOUCHWORKSPACE</a></li>
     * </ul></code>
     * @throws HLException If the mode is unknown or if there is no haptic rendering context is active
     */
    public static native void hlMatrixMode(int mode) throws HLException;
    /**
     * Sets up the haptic view volume which determines how determines the mapping between workspace to graphical view volume
     * @param left A double value that specifies the left boundaries
     * @param right A double value that specifies the right boundaries
     * @param bottom A double value that specifies the bottom boundaries
     * @param top A double value that specifies the top boundaries
     * @param zNear A double value that specifies the front boundaries
     * @param zFar A double value that specifies the back boundaries
     * @throws HLException If no haptic renderer context is active
     */
    public static native void hlOrtho(double left, double right, 
                               double bottom, double top, 
                               double zNear, double zFar) throws HLException;
    /**
     * Pushes a new matrix onto the top of the current stack
     * @throws HLException If no haptic renderer context is active or if the matrix stack already full
     */
    public static native void hlPushMatrix() throws HLException;
    /**
     * Removes the top matrix from the top of the current stack
     * @throws HLException If no haptic renderer context is active or if the matrix stack is only one deep
     */
    public static native void hlPopMatrix() throws HLException;
    /**
     * Multiplies the current matrix on top of the current stack by the 4x4 rotation matrix
     * @param angle A double value specifing the degrees to rotate
     * @param x A double value specifies to rotate on x axis
     * @param y A double value specifies to rotate on y axis
     * @param z A double value specifies to rotate on the z axis
     * @throws HLException If there is no haptic renderer context is active
     */
    public static native void hlRotated(double angle, double x, double y, double z) throws HLException;
    /**
     * Multiplies the current matrix on top of the current stack by the 4x4 rotation matrix
     * @param angle A float value specifing the degrees to rotate
     * @param x A float value specifies to rotate on x axis
     * @param y A float value specifies to rotate on y axis
     * @param z A float value specifies to rotate on the z axis
     * @throws HLException If there is no haptic renderer context is active
     */
    public static native void hlRotatef(float angle, float x, float y, float z) throws HLException;
    /**
     * Multiplies the current matrix on top of the current stack by the 4x4 scale matrix
     * @param x A double value to specifie scale factor for the x axis
     * @param y A double value to specifie scale factor for the y axis
     * @param z A double value to specifie scale factor for the z axis
     * @throws HLException If there is no haptic renderer context is active
     */
    public static native void hlScaled(double x, double y, double z) throws HLException;
    /**
     * Multiplies the current matrix on top of the current stack by the 4x4 scale matrix
     * @param x A float value to specifie scale factor for the x axis
     * @param y A float value to specifie scale factor for the y axis
     * @param z A float value to specifie scale factor for the z axis
     * @throws HLException If there is no haptic renderer context is active
     */
    public static native void hlScalef(float x, float y, float z) throws HLException;
    /**
     * Multiplies the current matrix on top of the current stack by the 4x4 translation matrix
     * @param x A double value to specifie the translation vector for the x axis
     * @param y A double value to specifie the translation vector for the y axis
     * @param z A double value to specifie the translation vector for the z axis
     * @throws HLException If no haptic renderer context is active
     */
    public static native void hlTranslated(double x, double y, double z) throws HLException;
    /**
     * Multiplies the current matrix on top of the current stack by the 4x4 translation matrix
     * @param x A flaot value to specifie the translation vector for the x axis
     * @param y A float value to specifie the translation vector for the y axis
     * @param z A float value to specifie the translation vector for the z axis
     * @throws HLException If no haptic renderer context is active
     */
    public static native void hlTranslatef(float x, float y, float z) throws HLException;
    /**
     * Defines the extents of the workspace of the haptic device that will be used for mapping between graphics and physical coordinates
     * @param left A double value for the left boundaries of the haptic device workspace
     * @param bottom A double value for the bottom boundaries of the haptic device workspace
     * @param back A double value for the back boundaries of the haptic device workspace
     * @param right A double value for the right boundaries of the haptic device workspace
     * @param top A double value for the top boundaries of the haptic device workspace
     * @param front A double value for the front boundaries of the haptic device workspace
     * @throws HLException If no haptic renderer context is active
     */
    public static native void hlWorkspace(double left, double bottom, 
                                          double back, double right, 
                                          double top, double front) throws HLException;
    /**
     * Causes the haptic device to re calibrate
     * @throws HLException if no haptic renderer context is available
     */
    public static native void hlUpdateCalibration() throws HLException;
    /**
     * Sets a user callback function
     * @param type The type of callback to set, see:
     * <code><ul>
     * <li><a href="#HL_SHAPE_INTERSECT_LS">HL_SHAPE_INTERSECT_LS</a></li>
     * <li><a href="#HL_SHAPE_CLOSEST_FEATURE">HL_SHAPE_CLOSEST_FEATURE</a></li>
     * </ul></code>
     * @param obj The Object to callback to, must implement <a href="HLCallback.html">HLCallback</a>
     * @param userdata An array of user data to pass back to the user Callback function on call
     * @throws HLException if the type is unknown, or no haptic renderer context is active
     */
    public static native void hlCallback(int type, HLCallback obj, Object[] userdata) throws HLException;
    /**
     * Adds a user event callback function to a shape
     * @param shape The shape to callback event for
     * @param thread The thread to callback in for events, see:
     * <code><ul>
     * <li><a href="#HL_COLLISION_THREAD">HL_COLLISION_THREAD</a></li>
     * <li><a href="#HL_CLIENT_THREAD">HL_CLIENT_THREAD</a></li>
     * </ul></code>
     * @param obj The Object to listen for events, must implement <a href="HLEventCallback.html">HLEventCallback</a>
     * @param userdata An array of user data to pass back to the user Event function on call
     * throws HLException If event thread is not known, or if no haptic renderer context is active
     */
    public static native void hlAddEventCallback(int shape, int thread, 
                                          HLEventCallback obj, 
                                          Object[] userdata) throws HLException;
    /**
     * Removes an existing user defined event handler function from the list of callback functions
     * @param shape The ID for the shape to remove the event callback from
     * @param thread The thread to the event callback belongs to, see:
     * <code><ul>
     * <li><a href="#HL_COLLISION_THREAD">HL_COLLISION_THREAD</a></li>
     * <li><a href="#HL_CLIENT_THREAD">HL_CLIENT_THREAD</a></li>
     * </ul></code>
     * @param obj The listener to remove from the event callback for the given shape and thread
     */
    public static native void hlRemoveEventCallback(int shape, int thread,
                                             HLEventCallback obj);
    
    /**
     * Calls callback function for all events that are subscribed to and that have occured since the last call to hlCheckEvents
     * @throws HLException If there is no haptic renderer context active
     */
    public static native void hlCheckEvents() throws HLException;
    /**
     * Set parameters that influence how and when event callbacks occure
     * @param pname The name of the event parameter to set, see:
     * <code><ul>
     * <li><a href="#HL_EVENT_MOTION_LINEAR_TOLERANCE">HL_EVENT_MOTION_LINEAR_TOLERANCE</a></li>
     * <li><a href="#HL_EVENT_MOTION_ANGULAR_TOLERANCE">HL_EVENT_MOTION_ANGULAR_TOLERANCE</a></li>
     * </ul></code>
     * @param param The doubel value to set the event parameter to
     * @throws HLException If pname is unknown or param is out of range, or if there is no haptic renderer context active
     */
    public static native void hlEventd(int pname, double param) throws HLException;
    
        static
	{
            try{
                System.loadLibrary("JHLAPI");
            }catch(Exception e){}
	}
}
