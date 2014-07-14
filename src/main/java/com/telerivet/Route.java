
package com.telerivet;
        
import java.io.IOException;
import org.json.JSONObject;
import org.json.JSONArray;
        
/**
    Represents a custom route that can be used to send messages via one or more Phones.
    
    Note: Routing rules can currently only be configured via Telerivet's web UI.
    
    Fields:
    
      - id (string, max 34 characters)
          * Telerivet's internal ID for the route
          * Read-only
      
      - name
          * The name of the route
          * Updatable via API
      
      - vars (JSONObject)
          * Custom variables stored for this route
          * Updatable via API
      
      - project_id
          * ID of the project this route belongs to
          * Read-only
*/
public class Route extends Entity
{
    /**
        Saves any fields or custom variables that have changed for this route.
    */
    @Override
    public void save() throws IOException
    {
        super.save();
    }

    public String getId()
    {
        return (String) get("id");
    }

    public String getName()
    {
        return (String) get("name");
    }

    public void setName(String value)
    {
        set("name", value);
    }

    public String getProjectId()
    {
        return (String) get("project_id");
    }

    @Override
    public String getBaseApiPath()
    {
        return "/projects/" + getProjectId() + "/routes/" + getId() + "";
    }

    public Route(TelerivetAPI api, JSONObject data)
    {
        this(api, data, true);
    }
    
    public Route(TelerivetAPI api, JSONObject data, boolean isLoaded)
    {
        super(api, data, isLoaded);
    }
}
