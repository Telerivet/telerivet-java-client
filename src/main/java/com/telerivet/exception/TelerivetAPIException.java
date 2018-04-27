/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.telerivet.exception;

import java.io.IOException;

/**
 *
 * @author youngj
 */
public class TelerivetAPIException extends IOException {
    
    private String code;
    
    public TelerivetAPIException(String message, String code)
    {
        super(message);
        this.code = code;
    }
    
    public String getCode()
    {
        return code;
    }
}
