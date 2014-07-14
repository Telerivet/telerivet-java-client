
package com.telerivet;
        
import java.io.IOException;
import org.json.JSONObject;
import org.json.JSONArray;
        
/**
    Represents a label used to organize messages within Telerivet.
    
    Fields:
    
      - id (string, max 34 characters)
          * ID of the label
          * Read-only
      
      - name
          * Name of the label
          * Updatable via API
      
      - time_created (UNIX timestamp)
          * Time the label was created in Telerivet
          * Read-only
      
      - vars (JSONObject)
          * Custom variables stored for this label
          * Updatable via API
      
      - project_id
          * ID of the project this label belongs to
          * Read-only
*/
public class Label extends Entity
{
    /**
        Queries messages with the given label.
    */
    public APICursor<Message> queryMessages(JSONObject options)
    {
        return api.newCursor(Message.class, getBaseApiPath() + "/messages", options);
    }

    public APICursor<Message> queryMessages()
    {
        return queryMessages(null);
    }

    /**
        Saves any fields that have changed for the label.
    */
    @Override
    public void save() throws IOException
    {
        super.save();
    }

    /**
        Deletes the given label (Note: no messages are deleted.)
    */
    public void delete() throws IOException
    {
        api.doRequest("DELETE", getBaseApiPath());
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

    public Long getTimeCreated()
    {
        return Util.toLong(get("time_created"));
    }

    public String getProjectId()
    {
        return (String) get("project_id");
    }

    @Override
    public String getBaseApiPath()
    {
        return "/projects/" + getProjectId() + "/labels/" + getId() + "";
    }

    public Label(TelerivetAPI api, JSONObject data)
    {
        this(api, data, true);
    }
    
    public Label(TelerivetAPI api, JSONObject data, boolean isLoaded)
    {
        super(api, data, isLoaded);
    }
}
