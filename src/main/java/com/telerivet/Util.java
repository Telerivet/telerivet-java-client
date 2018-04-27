package com.telerivet;

import org.json.JSONObject;
import java.util.Date;

/**
 *
 * @author youngj
 */
public class Util {
    public static JSONObject options(Object... params)
    {
        JSONObject options = new JSONObject();
        return addOptions(options, params);
    }
    
    public static JSONObject addOptions(JSONObject options, Object... params)
    {        
        int numParams = params.length;
        for (int i = 0; i < numParams - 1; i += 2)
        {
            String paramName = params[i].toString();
            Object paramValue = params[i + 1];            
            options.put(paramName, paramValue);            
        }
        return options;
    }
    
    public static Object convertNull(Object maybeJsonNull)
    {
        if (JSONObject.NULL.equals(maybeJsonNull))
        {
            return null;
        }
        return maybeJsonNull;
    }    
    
    public static Long toLong(Object obj)
    {
        if (obj == null)
        {
            return null;
        }
        else if (obj instanceof Long)
        {
            return (Long)obj;
        }
        else
        {
            return ((Number)obj).longValue();
        }
    }
    
    public static Double toDouble(Object obj)
    {
        if (obj == null)
        {
            return null;
        }
        else if (obj instanceof Double)
        {
            return (Double)obj;
        }
        else
        {
            return ((Number)obj).doubleValue();
        }
    }    

    public static Date timestampToDate(Long obj)
    {
        if (obj == null)
        {
            return null;
        }
        else
        {
            long timestamp = ((Number)obj).longValue();            
            return new Date(timestamp * 1000);
        }
    }
    
    public static Long dateToTimestamp(Date obj)
    {
        if (obj == null)
        {
            return null;
        }
        else
        {
            return ((Date)obj).getTime() / 1000;
        }
    }
}
