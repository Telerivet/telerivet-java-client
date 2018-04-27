/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.telerivet.exception;

/**
 *
 * @author youngj
 */
public class TelerivetNotFoundException extends TelerivetAPIException {
    public TelerivetNotFoundException(String message, String code)
    {
        super(message,code);
    }
}
