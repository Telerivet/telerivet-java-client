
package com.telerivet;

import java.io.IOException;
import org.json.JSONObject;
import org.json.JSONArray;

/**
    <div class='markdown'><p>Represents a group used to organize contacts within Telerivet.</p>
    
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
    <li><p>allow_sending (bool)</p>
    
    <ul>
    <li>True if messages can be sent to this group, false otherwise.</li>
    <li>Updatable via API</li>
    </ul></li>
    <li><p>add_time_variable (string)</p>
    
    <ul>
    <li>Variable name of a custom contact field that will automatically be set to the
      current date/time on any contact that is added to the group. This variable will only
      be set if the contact does not already have a value for this variable.</li>
    <li>Updatable via API</li>
    </ul></li>
    <li><p>vars (JSONObject)</p>
    
    <ul>
    <li>Custom variables stored for this group. Variable names may be up to 32 characters in
      length and can contain the characters a-z, A-Z, 0-9, and _.
      Values may be strings, numbers, or boolean (true/false).
      String values may be up to 4096 bytes in length when encoded as UTF-8.
      Up to 100 variables are supported per object.
      Setting a variable to null will delete the variable.</li>
    <li>Updatable via API</li>
    </ul></li>
    <li><p>project_id</p>
    
    <ul>
    <li>ID of the project this group belongs to</li>
    <li>Read-only</li>
    </ul></li>
    </ul>
    </div>
*/
public class Group extends Entity
{
    /**
        <div class='markdown'><p>Queries contacts that are members of the given group.</p>
        </div>
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
        <div class='markdown'><p>Queries scheduled messages to the given group.</p>
        </div>
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
        <div class='markdown'><p>Saves any fields that have changed for this group.</p>
        </div>
    */
    @Override
    public void save() throws IOException
    {
        super.save();
    }

    /**
        <div class='markdown'><p>Deletes this group (Note: no contacts are deleted.)</p>
        </div>
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

    public Boolean getAllowSending()
    {
        return (Boolean) get("allow_sending");
    }

    public void setAllowSending(Boolean value)
    {
        set("allow_sending", value);
    }

    public String getAddTimeVariable()
    {
        return (String) get("add_time_variable");
    }

    public void setAddTimeVariable(String value)
    {
        set("add_time_variable", value);
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
