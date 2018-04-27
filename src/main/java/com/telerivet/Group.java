
package com.telerivet;

import java.io.IOException;
import org.json.JSONObject;
import org.json.JSONArray;

/**
    <p>Represents a group used to organize contacts within Telerivet.</p>
    
    <p>Fields:</p>
    
    <ul>
    <li><p>id (string, max 34 characters)</p>
    
    <ul>
    <li>ID of the group</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>name</p>
    
    <ul>
    <li>Name of the group</li>
    <li>Updatable via API</li>
    </ul></li>
    <li><p>dynamic (bool)</p>
    
    <ul>
    <li>Whether this is a dynamic or normal group</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>num_members (int)</p>
    
    <ul>
    <li>Number of contacts in the group (null if the group is dynamic)</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>time_created (UNIX timestamp)</p>
    
    <ul>
    <li>Time the group was created in Telerivet</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>vars (JSONObject)</p>
    
    <ul>
    <li>Custom variables stored for this group</li>
    <li>Updatable via API</li>
    </ul></li>
    <li><p>project_id</p>
    
    <ul>
    <li>ID of the project this group belongs to</li>
    <li>Read-only</li>
    </ul></li>
    </ul>
*/
public class Group extends Entity
{
    /**
        <p>Queries contacts that are members of the given group.</p>
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
        <p>Queries scheduled messages to the given group.</p>
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
        <p>Saves any fields that have changed for this group.</p>
    */
    @Override
    public void save() throws IOException
    {
        super.save();
    }

    /**
        <p>Deletes this group (Note: no contacts are deleted.)</p>
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

    public Boolean getDynamic()
    {
        return (Boolean) get("dynamic");
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
