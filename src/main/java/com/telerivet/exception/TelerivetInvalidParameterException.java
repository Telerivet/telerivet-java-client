/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.telerivet.exception;

/**
 *
 * @author youngj
 */
public class TelerivetInvalidParameterException extends TelerivetAPIException {
    public TelerivetInvalidParameterException(String message, String code)
    {
        super(message,code);
    }
}
