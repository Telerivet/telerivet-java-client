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

    private String param;

    public TelerivetInvalidParameterException(String message, String code)
    {
        super(message, code);
    }

    public TelerivetInvalidParameterException(String message, String code, String param)
    {
        super(message, code);
        this.param = param;
    }

    public String getParam()
    {
        return param;
    }
}
