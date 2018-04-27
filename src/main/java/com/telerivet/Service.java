package com.telerivet;

import java.io.IOException;
import java.util.Date;
import org.json.JSONObject;
import org.json.JSONArray;

/**
    <p>Represents an automated service on Telerivet, for example a poll, auto-reply, webhook
    service, etc.</p>
    
    <p>A service, generally, defines some automated behavior that can be
    invoked/triggered in a particular context, and may be invoked either manually or when a
    particular event occurs.</p>
    
    <p>Most commonly, services work in the context of a particular message, when
    the message is originally received by Telerivet.</p>
    
    <p>Fields:</p>
    
    <ul>
    <li><p>id (string, max 34 characters)</p>
    
    <ul>
    <li>ID of the service</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>name</p>
    
    <ul>
    <li>Name of the service</li>
    <li>Updatable via API</li>
    </ul></li>
    <li><p>active (bool)</p>
    
    <ul>
    <li>Whether the service is active or inactive. Inactive services are not automatically
      triggered and cannot be invoked via the API.</li>
    <li>Updatable via API</li>
    </ul></li>
    <li><p>priority (int)</p>
    
    <ul>
    <li>A number that determines the order that services are triggered when a particular
      event occurs (smaller numbers are triggered first). Any service can determine whether
      or not execution "falls-through" to subsequent services (with larger priority values)
      by setting the return_value variable within Telerivet's Rules Engine.</li>
    <li>Updatable via API</li>
    </ul></li>
    <li><p>contexts (JSONObject)</p>
    
    <ul>
    <li>A key/value map where the keys are the names of contexts supported by this service
      (e.g. message, contact), and the values are themselves key/value maps where the keys
      are event names and the values are all true. (This structure makes it easy to test
      whether a service can be invoked for a particular context and event.)</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>vars (JSONObject)</p>
    
    <ul>
    <li>Custom variables stored for this service</li>
    <li>Updatable via API</li>
    </ul></li>
    <li><p>project_id</p>
    
    <ul>
    <li>ID of the project this service belongs to</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>label_id</p>
    
    <ul>
    <li>ID of the label containing messages sent or received by this service (currently only
      used for polls)</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>response<em>table</em>id</p>
    
    <ul>
    <li>ID of the data table where responses to this service will be stored (currently only
      used for polls)</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>sample<em>group</em>id</p>
    
    <ul>
    <li>ID of the group containing contacts that have been invited to interact with this
      service (currently only used for polls)</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>respondent<em>group</em>id</p>
    
    <ul>
    <li>ID of the group containing contacts that have completed an interaction with this
      service (currently only used for polls)</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>questions (array)</p>
    
    <ul>
    <li>Array of objects describing each question in a poll (only used for polls). Each
      object has the properties <code>"id"</code> (the question ID), <code>"content"</code> (the text of the
      question), and <code>"question_type"</code> (either <code>"multiple_choice"</code>, <code>"missed_call"</code>, or
      <code>"open"</code>).</li>
    <li>Read-only</li>
    </ul></li>
    </ul>
 */
public class Service extends Entity 
{    
    /**
        <p>Gets the current state for a particular contact for this service.</p>
        
        <p>If the contact doesn't already have a state, this method will return
        a valid state object with id=null. However this object would not be returned by
        queryContactStates() unless it is saved with a non-null state id.</p>
     */
    public ContactServiceState getContactState(Contact contact) throws IOException
    {
        return new ContactServiceState(api, (JSONObject) api.doRequest("GET", getBaseApiPath() + "/states/" + contact.get("id")));
    }
    
    /**
        <p>Initializes or updates the current state for a particular contact for the given service. If
        the state id is null, the contact's state will be reset.</p>
     */
    public ContactServiceState setContactState(Contact contact, JSONObject options) throws IOException
    {
        return new ContactServiceState(api, (JSONObject) api.doRequest("POST", getBaseApiPath() + "/states/" + contact.get("id"), options));
    }    
        
    /**
        <p>Resets the current state for a particular contact for the given service.</p>
     */
    public ContactServiceState resetContactState(Contact contact) throws IOException
    {
        return new ContactServiceState(api, (JSONObject) api.doRequest("DELETE", getBaseApiPath() + "/states/" + contact.get("id")));
    }            
    
    /**
        <p>Manually invoke this service in a particular context.</p>
        
        <p>For example, to send a poll to a particular contact (or resend the
        current question), you can invoke the poll service with context=contact, and <code>contact_id</code> as
        the ID of the contact to send the poll to.</p>
        
        <p>Or, to manually apply a service for an incoming message, you can
        invoke the service with <code>context</code>=<code>message</code>, <code>event</code>=<code>incoming_message</code>, and <code>message_id</code> as
        the ID of the incoming message. (This is normally not necessary, but could be used if you
        want to override Telerivet's standard priority-ordering of services.)</p>
    */
    public JSONObject invoke(JSONObject options) throws IOException
    {
        return (JSONObject) api.doRequest("POST", getBaseApiPath() + "/invoke", options);
    }

    /**
        <p>Query the current states of contacts for this service.</p>
    */
    public APICursor<ContactServiceState> queryContactStates(JSONObject options)
    {
        return api.newCursor(ContactServiceState.class, getBaseApiPath() + "/states", options);
    }

    public APICursor<ContactServiceState> queryContactStates()
    {
        return queryContactStates(null);
    }

    /**
        <p>Saves any fields or custom variables that have changed for this service.</p>
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

    public Boolean getActive()
    {
        return (Boolean) get("active");
    }

    public void setActive(Boolean value)
    {
        set("active", value);
    }

    public Integer getPriority()
    {
        return (Integer) get("priority");
    }

    public void setPriority(Integer value)
    {
        set("priority", value);
    }

    public JSONObject getContexts()
    {
        return (JSONObject) get("contexts");
    }

    public String getProjectId()
    {
        return (String) get("project_id");
    }

    public String getLabelId()
    {
        return (String) get("label_id");
    }

    public String getResponseTableId()
    {
        return (String) get("response_table_id");
    }

    public String getSampleGroupId()
    {
        return (String) get("sample_group_id");
    }

    public String getRespondentGroupId()
    {
        return (String) get("respondent_group_id");
    }

    public JSONArray getQuestions()
    {
        return (JSONArray) get("questions");
    }

    @Override
    public String getBaseApiPath()
    {
        return "/projects/" + getProjectId() + "/services/" + getId() + "";
    }

    public Service(TelerivetAPI api, JSONObject data)
    {
        this(api, data, true);
    }

    public Service(TelerivetAPI api, JSONObject data, boolean isLoaded)
    {
        super(api, data, isLoaded);
    }
}
