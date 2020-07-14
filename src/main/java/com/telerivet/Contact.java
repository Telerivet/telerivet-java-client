package com.telerivet;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.Date;
import org.json.JSONObject;
import org.json.JSONArray;

/**
    <p>Fields:</p>
    
    <ul>
    <li><p>id (string, max 34 characters)</p>
    
    <ul>
    <li>ID of the contact</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>name</p>
    
    <ul>
    <li>Name of the contact</li>
    <li>Updatable via API</li>
    </ul></li>
    <li><p>phone_number (string)</p>
    
    <ul>
    <li>Phone number of the contact</li>
    <li>Updatable via API</li>
    </ul></li>
    <li><p>time_created (UNIX timestamp)</p>
    
    <ul>
    <li>Time the contact was added in Telerivet</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>time_updated (UNIX timestamp)</p>
    
    <ul>
    <li>Time the contact was last updated in Telerivet</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>send_blocked (bool)</p>
    
    <ul>
    <li>True if Telerivet is blocked from sending messages to this contact</li>
    <li>Updatable via API</li>
    </ul></li>
    <li><p>conversation_status</p>
    
    <ul>
    <li>Current status of the conversation with this contact</li>
    <li>Allowed values: closed, active, handled</li>
    <li>Updatable via API</li>
    </ul></li>
    <li><p>last<em>message</em>time (UNIX timestamp)</p>
    
    <ul>
    <li>Last time the contact sent or received a message (null if no messages have been sent
      or received)</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>last<em>incoming</em>message_time (UNIX timestamp)</p>
    
    <ul>
    <li>Last time a message was received from this contact</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>last<em>outgoing</em>message_time (UNIX timestamp)</p>
    
    <ul>
    <li>Last time a message was sent to this contact</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>message_count (int)</p>
    
    <ul>
    <li>Total number of non-deleted messages sent to or received from this contact</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>incoming<em>message</em>count (int)</p>
    
    <ul>
    <li>Number of messages received from this contact</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>outgoing<em>message</em>count (int)</p>
    
    <ul>
    <li>Number of messages sent to this contact</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>last<em>message</em>id</p>
    
    <ul>
    <li>ID of the last message sent to or received from this contact (null if no messages
      have been sent or received)</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>default<em>route</em>id</p>
    
    <ul>
    <li>ID of the phone or route that Telerivet will use by default to send messages to this
      contact (null if using project default route)</li>
    <li>Updatable via API</li>
    </ul></li>
    <li><p>group_ids (array of strings)</p>
    
    <ul>
    <li>List of IDs of groups that this contact belongs to</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>vars (JSONObject)</p>
    
    <ul>
    <li>Custom variables stored for this contact</li>
    <li>Updatable via API</li>
    </ul></li>
    <li><p>project_id</p>
    
    <ul>
    <li>ID of the project this contact belongs to</li>
    <li>Read-only</li>
    </ul></li>
    </ul>
 */
public class Contact extends Entity 
{    
    /**
        <p>Returns true if this contact is in a particular group, false otherwise.</p>
     */
    public boolean isInGroup(Group group)
    {
        assertLoaded();
        return groupIdsSet.contains(group.getId());
    }
      
    /**
        <p>Adds this contact to a group.</p>
     */
    public void addToGroup(Group group) throws IOException
    {
        api.doRequest("PUT", group.getBaseApiPath() + "/contacts/" + getId());
        groupIdsSet.add(group.getId());
    }
    
    /**
        <p>Removes this contact from a group.</p>
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
        <p>Queries messages sent or received by this contact.</p>
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
        <p>Queries groups for which this contact is a member.</p>
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
        <p>Queries messages scheduled to this contact (not including messages scheduled to groups that
        this contact is a member of)</p>
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
        <p>Queries data rows associated with this contact (in any data table).</p>
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
        <p>Queries this contact's current states for any service</p>
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
        <p>Saves any fields or custom variables that have changed for this contact.</p>
    */
    @Override
    public void save() throws IOException
    {
        super.save();
    }

    /**
        <p>Deletes this contact.</p>
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

    public Long getTimeUpdated()
    {
        return Util.toLong(get("time_updated"));
    }

    public Boolean getSendBlocked()
    {
        return (Boolean) get("send_blocked");
    }

    public void setSendBlocked(Boolean value)
    {
        set("send_blocked", value);
    }

    public String getConversationStatus()
    {
        return (String) get("conversation_status");
    }

    public void setConversationStatus(String value)
    {
        set("conversation_status", value);
    }

    public Long getLastMessageTime()
    {
        return Util.toLong(get("last_message_time"));
    }

    public Long getLastIncomingMessageTime()
    {
        return Util.toLong(get("last_incoming_message_time"));
    }

    public Long getLastOutgoingMessageTime()
    {
        return Util.toLong(get("last_outgoing_message_time"));
    }

    public Integer getMessageCount()
    {
        return (Integer) get("message_count");
    }

    public Integer getIncomingMessageCount()
    {
        return (Integer) get("incoming_message_count");
    }

    public Integer getOutgoingMessageCount()
    {
        return (Integer) get("outgoing_message_count");
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
