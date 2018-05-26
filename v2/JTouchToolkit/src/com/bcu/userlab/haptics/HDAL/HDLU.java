/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bcu.userlab.haptics.HDAL;

/**
 *
 * @author id108336
 */
public class HDLU {
    public static native double[] hdluGenerateHapticToAppWorkspaceTransform(
            double[] hapticWorkspace, double[] gameWorkspace, boolean useUniformScale);
    public static native double hdluGetSystemTime();
    
        //code that loads DLL/library file containing the C implementation of these methods
    	static
	{
            try{
                System.loadLibrary("JHDAL");
            }catch(Exception e){}
	}
}
