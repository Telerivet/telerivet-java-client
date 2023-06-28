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
    <li><p>service_type</p>
    
    <ul>
    <li>Type of the service.</li>
    <li>Read-only</li>
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
    <li><p>response_table_id</p>
    
    <ul>
    <li>ID of the data table where responses to this service will be stored</li>
    <li>Updatable via API</li>
    </ul></li>
    <li><p>phone_ids</p>
    
    <ul>
    <li>IDs of phones (basic routes) associated with this service, or null if the service is
      associated with all routes. Only applies for service types that handle incoming
      messages, voice calls, or USSD sessions.</li>
    <li>Updatable via API</li>
    </ul></li>
    <li><p>apply_mode</p>
    
    <ul>
    <li>If apply_mode is <code>unhandled</code>, the service will not be triggered if another service
      has already handled the incoming message. If apply_mode is <code>always</code>, the service will
      always be triggered regardless of other services. Only applies to services that handle
      incoming messages.</li>
    <li>Allowed values: always, unhandled</li>
    <li>Updatable via API</li>
    </ul></li>
    <li><p>contact_number_filter</p>
    
    <ul>
    <li>If contact_number_filter is <code>long_number</code>, this service will only be triggered if
      the contact phone number has at least 7 digits (ignoring messages from shortcodes and
      alphanumeric senders). If contact_number_filter is <code>all</code>, the service will be
      triggered for all contact phone numbers.  Only applies to services that handle
      incoming messages.</li>
    <li>Allowed values: long_number, all</li>
    <li>Updatable via API</li>
    </ul></li>
    <li><p>show_action (bool)</p>
    
    <ul>
    <li>Whether this service is shown in the 'Actions' menu within the Telerivet web app
      when the service is active. Only provided for service types that are manually
      triggered.</li>
    <li>Updatable via API</li>
    </ul></li>
    <li><p>direction</p>
    
    <ul>
    <li>Determines whether the service handles incoming voice calls, outgoing voice calls,
      or both. Only applies to services that handle voice calls.</li>
    <li>Allowed values: incoming, outgoing, both</li>
    <li>Updatable via API</li>
    </ul></li>
    <li><p>webhook_url</p>
    
    <ul>
    <li>URL that a third-party can invoke to trigger this service. Only provided for
      services that are triggered by a webhook request.</li>
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
        the ID of the contact to send the poll to. (To trigger a service to multiple contacts, use
        <a href="#Project.sendBroadcast">project.sendBroadcast</a>. To schedule a service in the future, use
        <a href="#Project.scheduleMessage">project.scheduleMessage</a>.)</p>
        
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
        <p>Gets configuration specific to the type of automated service.</p>
        
        <p>Only certain types of services provide their configuration via the
        API.</p>
    */
    public JSONObject getConfig() throws IOException
    {
        return (JSONObject) api.doRequest("GET", getBaseApiPath() + "/config");
    }

    /**
        <p>Updates configuration specific to the type of automated service.</p>
        
        <p>Only certain types of services support updating their configuration
        via the API.</p>
        
        <p>Note: when updating a service of type custom_template_instance,
        the validation script will be invoked when calling this method.</p>
    */
    public JSONObject setConfig(JSONObject options) throws IOException
    {
        return (JSONObject) api.doRequest("POST", getBaseApiPath() + "/config", options);
    }

    /**
        <p>Saves any fields or custom variables that have changed for this service.</p>
    */
    @Override
    public void save() throws IOException
    {
        super.save();
    }

    /**
        <p>Deletes this service.</p>
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

    public String getServiceType()
    {
        return (String) get("service_type");
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

    public String getResponseTableId()
    {
        return (String) get("response_table_id");
    }

    public void setResponseTableId(String value)
    {
        set("response_table_id", value);
    }

    public String getPhoneIds()
    {
        return (String) get("phone_ids");
    }

    public void setPhoneIds(String value)
    {
        set("phone_ids", value);
    }

    public String getApplyMode()
    {
        return (String) get("apply_mode");
    }

    public void setApplyMode(String value)
    {
        set("apply_mode", value);
    }

    public String getContactNumberFilter()
    {
        return (String) get("contact_number_filter");
    }

    public void setContactNumberFilter(String value)
    {
        set("contact_number_filter", value);
    }

    public Boolean getShowAction()
    {
        return (Boolean) get("show_action");
    }

    public void setShowAction(Boolean value)
    {
        set("show_action", value);
    }

    public String getDirection()
    {
        return (String) get("direction");
    }

    public void setDirection(String value)
    {
        set("direction", value);
    }

    public String getWebhookUrl()
    {
        return (String) get("webhook_url");
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
