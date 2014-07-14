package com.telerivet;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.Date;
import org.json.JSONObject;
import org.json.JSONArray;

/**
    Fields:
    
      - id (string, max 34 characters)
          * ID of the contact
          * Read-only
      
      - name
          * Name of the contact
          * Updatable via API
      
      - phone_number (string)
          * Phone number of the contact
          * Updatable via API
      
      - time_created (UNIX timestamp)
          * Time the contact was added in Telerivet
          * Read-only
      
      - last_message_time (UNIX timestamp)
          * Last time the contact sent or received a message (null if no messages have been sent
              or received)
          * Read-only
      
      - last_message_id
          * ID of the last message sent or received by this contact (null if no messages have
              been sent or received)
          * Read-only
      
      - default_route_id
          * ID of the phone or route that Telerivet will use by default to send messages to this
              contact (null if using project default route)
          * Updatable via API
      
      - group_ids (array of strings)
          * List of IDs of groups that this contact belongs to
          * Read-only
      
      - vars (JSONObject)
          * Custom variables stored for this contact
          * Updatable via API
      
      - project_id
          * ID of the project this contact belongs to
          * Read-only
 */
public class Contact extends Entity 
{    
    /**
        Returns true if this contact is in a particular group, false otherwise.
     */
    public boolean isInGroup(Group group)
    {
        assertLoaded();
        return groupIdsSet.contains(group.getId());
    }
      
    /**
        Adds this contact to a group.
     */
    public void addToGroup(Group group) throws IOException
    {
        api.doRequest("PUT", group.getBaseApiPath() + "/contacts/" + getId());
        groupIdsSet.add(group.getId());
    }
    
    /**
        Removes this contact from a group.
     */    
    public void removeFromGroup(Group group) throws IOException
    {    
        api.doRequest("DELETE", group.getBaseApiPath() + "/contacts/" + getId());
        groupIdsSet.remove(group.getId());
    }
    
    private Set<String> groupIdsSet;
    
    @Override
    public void setData(JSONObject data)
    {
        super.setData(data);
        
        groupIdsSet = new HashSet<String>();
        
        if (data.has("group_ids"))
        {
            JSONArray groupIds = data.getJSONArray("group_ids");
            int numGroupIds = groupIds.length();
            for (int i = 0; i < numGroupIds; i++)
            {
                groupIdsSet.add(groupIds.getString(i));                
            }
        }
    }

    /**
        Queries messages sent or received by this contact.
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
        Queries groups for which this contact is a member.
    */
    public APICursor<Group> queryGroups(JSONObject options)
    {
        return api.newCursor(Group.class, getBaseApiPath() + "/groups", options);
    }

    public APICursor<Group> queryGroups()
    {
        return queryGroups(null);
    }

    /**
        Queries messages scheduled to this contact (not including messages scheduled to groups that
        this contact is a member of)
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
        Queries data rows associated with this contact (in any data table).
    */
    public APICursor<DataRow> queryDataRows(JSONObject options)
    {
        return api.newCursor(DataRow.class, getBaseApiPath() + "/rows", options);
    }

    public APICursor<DataRow> queryDataRows()
    {
        return queryDataRows(null);
    }

    /**
        Queries this contact's current states for any service
    */
    public APICursor<ContactServiceState> queryServiceStates(JSONObject options)
    {
        return api.newCursor(ContactServiceState.class, getBaseApiPath() + "/states", options);
    }

    public APICursor<ContactServiceState> queryServiceStates()
    {
        return queryServiceStates(null);
    }

    /**
        Saves any fields or custom variables that have changed for this contact.
    */
    @Override
    public void save() throws IOException
    {
        super.save();
    }

    /**
        Deletes this contact.
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

    public String getPhoneNumber()
    {
        return (String) get("phone_number");
    }

    public void setPhoneNumber(String value)
    {
        set("phone_number", value);
    }

    public Long getTimeCreated()
    {
        return Util.toLong(get("time_created"));
    }

    public Long getLastMessageTime()
    {
        return Util.toLong(get("last_message_time"));
    }

    public String getLastMessageId()
    {
        return (String) get("last_message_id");
    }

    public String getDefaultRouteId()
    {
        return (String) get("default_route_id");
    }

    public void setDefaultRouteId(String value)
    {
        set("default_route_id", value);
    }

    public JSONArray getGroupIds()
    {
        return (JSONArray) get("group_ids");
    }

    public String getProjectId()
    {
        return (String) get("project_id");
    }

    @Override
    public String getBaseApiPath()
    {
        return "/projects/" + getProjectId() + "/contacts/" + getId() + "";
    }

    public Contact(TelerivetAPI api, JSONObject data)
    {
        this(api, data, true);
    }
    
    public Contact(TelerivetAPI api, JSONObject data, boolean isLoaded)
    {
        super(api, data, isLoaded);
    }
}
