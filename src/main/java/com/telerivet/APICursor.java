package com.telerivet;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author youngj
 */
public class APICursor<T extends Entity> implements Iterator<T>, Iterable<T> {
    
    private TelerivetAPI api;
    private String path;
    private JSONObject params;
    
    public static int NO_LIMIT = -1;
    
    private int count = -1;
    private int pos = -1;
    private JSONArray data;
    private boolean truncated = false;
    private String nextMarker = null;
    private int limit = NO_LIMIT;
    private int offset = 0;
    private Constructor ctor;
    
    public APICursor(TelerivetAPI api, Class<T> clazz, String path, JSONObject params)
    {
        if (params == null)
        {
            params = new JSONObject();
        }
        
        if (params.has("count"))
        {
            throw new InvalidParameterException("Cannot construct APICursor with 'count' parameter. Call the count() method instead.");
        }
        
        this.ctor = getConstructor(clazz);               
        this.api = api;
        this.path = path;
        this.params = params;        
    }    
    
    private Constructor getConstructor(Class<T> clazz)
    {
        Constructor[] ctors = clazz.getDeclaredConstructors();
        for (int i = 0; i < ctors.length; i++) 
        {
            if (ctors[i].getParameterTypes().length == 3)
            {
                return ctors[i];
            }
	}       
        throw new InvalidParameterException("Class " +clazz.getName()+ " does not have 3 argument constructor.");
    }
    
    public APICursor limit(int limit)
    {
        this.limit = limit;
        return this;
    }
    
    public int count() throws IOException
    {
        if (count == -1)
        {
            JSONObject requestParams = copyParams();            
            requestParams.put("count", 1);
            JSONObject res = (JSONObject) api.doRequest("GET", path, requestParams);
            count = res.getInt("count");            
        }
        return count;
    }
    
    public List<T> all()
    {
        List<T> res = new ArrayList<T>();
        for (T item : this)
        {
            res.add(item);
        }
        return res;
    }
    
    @Override
    public void remove()
    {
        throw new UnsupportedOperationException("APICursor does not implement remove()");
    }
        
    @Override
    public boolean hasNext()
    {        
        if (limit != NO_LIMIT && offset >= limit)
        {
            return false;
        }
        
        if (data == null)
        {        
            loadNextPage();
        }
            
        if (pos < data.length())
        {
            return true;
        }

        if (!truncated)
        {
            return false;
        }
            
        loadNextPage();
        return pos < data.length();
    }
    
    @Override
    public T next()
    {    
        if (limit != NO_LIMIT && offset >= limit)
        {
            throw new NoSuchElementException();
        }
        
        if (data == null || (pos >= data.length() && truncated))
        {                
            loadNextPage();
        }
        
        if (pos < data.length())
        {
            JSONObject itemData = data.getJSONObject(pos);
            pos += 1;
            offset += 1;
            
            try
            {
                return (T)ctor.newInstance(api, itemData, true);    
            }
            catch (Exception ex)
            {
                throw new RuntimeException(ex);
            }
        }
        else
        {    
            throw new NoSuchElementException();
        }
    }

    private JSONObject copyParams()
    {
        JSONObject requestParams = new JSONObject();
        
        if (params != null)
        {
            Iterator keysIter = params.keys();
            while (keysIter.hasNext()) 
            {
                String key = (String)keysIter.next();
                requestParams.put(key, params.get(key));
            }
        }
        return requestParams;
    }
    
    private void loadNextPage()
    {     
        JSONObject requestParams = copyParams();
        
        if (nextMarker != null)
        {
            requestParams.put("marker", nextMarker);
        }
        
        if (limit != NO_LIMIT && !requestParams.has("page_size"))
        {
            requestParams.put("page_size", Math.min(limit, 200));
        }        
        
        JSONObject response;
        try
        {
            response = (JSONObject) api.doRequest("GET", path, requestParams);
        }
        catch (IOException ex)
        {
            throw new RuntimeException(ex);
        }
        
        data = response.getJSONArray("data");
        truncated = response.optBoolean("truncated", false);
        nextMarker = response.optString("next_marker");
        pos = 0;
    }
    
    @Override
    public Iterator<T> iterator()
    {
        return this;
    }
}
