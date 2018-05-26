/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bcu.userlab.haptics.HDAL;

/**
 *
 * @author id108336
 */
public class HDLException extends java.lang.Exception {
    public int errorCode;
    
    public HDLException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
    
    public int getErrorCode() {
        return errorCode;
    }
}
