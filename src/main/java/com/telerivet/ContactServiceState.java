
package com.telerivet;

import java.io.IOException;
import org.json.JSONObject;
import org.json.JSONArray;

/**
    <p>Represents the current state of a particular contact for a particular Telerivet service.</p>
    
    <p>Some automated services (including polls) are 'stateful'. For polls,
    Telerivet needs to keep track of which question the contact is currently answering, and
    stores store the ID of each contact's current question (e.g. 'q1' or 'q2') as the ID of the
    contact's state for the poll service. Any type of conversation-like service will also need
    to store state for each contact.</p>
    
    <p>For this type of entity, the 'id' field is NOT a read-only unique ID (unlike
    all other types of entities). Instead it is an arbitrary string that identifies the
    contact's current state within your poll/conversation; many contacts may have the same state
    ID, and it may change over time. Additional custom fields may be stored in the 'vars'.</p>
    
    <p>Initially, the state 'id' for any contact is null. When saving the state,
    setting the 'id' to null is equivalent to resetting the state (so all 'vars' will be
    deleted); if you want to save custom variables, the state 'id' must be non-null.</p>
    
    <p>Many Telerivet services are stateless, such as auto-replies or keyword-based
    services where the behavior only depends on the current message, and not any previous
    messages sent by the same contact. Telerivet doesn't store any state for contacts that
    interact with stateless services.</p>
    
    <p>Fields:</p>
    
    <ul>
    <li><p>id (string, max 63 characters)</p>
    
    <ul>
    <li>Arbitrary string representing the contact's current state for this service, e.g.
      'q1', 'q2', etc.</li>
    <li>Updatable via API</li>
    </ul></li>
    <li><p>contact_id</p>
    
    <ul>
    <li>ID of the contact</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>service_id</p>
    
    <ul>
    <li>ID of the service</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>vars (JSONObject)</p>
    
    <ul>
    <li>Custom variables stored for this contact/service state</li>
    <li>Updatable via API</li>
    </ul></li>
    <li><p>time_created (UNIX timestamp)</p>
    
    <ul>
    <li>Time the state was first created in Telerivet</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>time_updated (UNIX timestamp)</p>
    
    <ul>
    <li>Time the state was last updated in Telerivet</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>project_id</p>
    
    <ul>
    <li>ID of the project this contact/service state belongs to</li>
    <li>Read-only</li>
    </ul></li>
    </ul>
*/
public class ContactServiceState extends Entity
{
    /**
        <p>Saves the state id and any custom variables for this contact. If the state id is null, this
        is equivalent to calling reset().</p>
    */
    @Override
    public void save() throws IOException
    {
        super.save();
    }

    /**
        <p>Resets the state for this contact for this service.</p>
    */
    public void reset() throws IOException
    {
        api.doRequest("DELETE", getBaseApiPath());
    }

    public String getId()
    {
        return (String) get("id");
    }

    public void setId(String value)
    {
        set("id", value);
    }

    public String getContactId()
    {
        return (String) get("contact_id");
    }

    public String getServiceId()
    {
        return (String) get("service_id");
    }

    public Long getTimeCreated()
    {
        return Util.toLong(get("time_created"));
    }

    public Long getTimeUpdated()
    {
        return Util.toLong(get("time_updated"));
    }

    public String getProjectId()
    {
        return (String) get("project_id");
    }

    @Override
    public String getBaseApiPath()
    {
        return "/projects/" + getProjectId() + "/services/" + getServiceId() + "/states/" + getContactId() + "";
    }

    public ContactServiceState(TelerivetAPI api, JSONObject data)
    {
        this(api, data, true);
    }

    public ContactServiceState(TelerivetAPI api, JSONObject data, boolean isLoaded)
    {
        super(api, data, isLoaded);
    }
}
