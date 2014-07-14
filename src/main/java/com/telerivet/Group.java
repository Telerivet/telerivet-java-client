
package com.telerivet;
        
import java.io.IOException;
import org.json.JSONObject;
import org.json.JSONArray;
        
/**
    Represents a group used to organize contacts within Telerivet.
    
    Fields:
    
      - id (string, max 34 characters)
          * ID of the group
          * Read-only
      
      - name
          * Name of the group
          * Updatable via API
      
      - num_members (int)
          * Number of contacts in the group
          * Read-only
      
      - time_created (UNIX timestamp)
          * Time the group was created in Telerivet
          * Read-only
      
      - vars (JSONObject)
          * Custom variables stored for this group
          * Updatable via API
      
      - project_id
          * ID of the project this group belongs to
          * Read-only
*/
public class Group extends Entity
{
    /**
        Queries contacts that are members of the given group.
    */
    public APICursor<Contact> queryContacts(JSONObject options)
    {
        return api.newCursor(Contact.class, getBaseApiPath() + "/contacts", options);
    }

    public APICursor<Contact> queryContacts()
    {
        return queryContacts(null);
    }

    /**
        Queries scheduled messages to the given group.
    */
    public APICursor<ScheduledMessage> queryScheduledMessages(JSONObject options)
    {
        return api.newCursor(ScheduledMessage.class, getBaseApiPath() + "/scheduled", options);
    }

    public APICursor<ScheduledMessage> queryScheduledMessages()
    {
        return queryScheduledMessages(null);
    }

    /**
        Saves any fields that have changed for this group.
    */
    @Override
    public void save() throws IOException
    {
        super.save();
    }

    /**
        Deletes this group (Note: no contacts are deleted.)
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

    public Integer getNumMembers()
    {
        return (Integer) get("num_members");
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
        return "/projects/" + getProjectId() + "/groups/" + getId() + "";
    }

    public Group(TelerivetAPI api, JSONObject data)
    {
        this(api, data, true);
    }
    
    public Group(TelerivetAPI api, JSONObject data, boolean isLoaded)
    {
        super(api, data, isLoaded);
    }
}
