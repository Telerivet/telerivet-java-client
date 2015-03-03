package com.telerivet;

import java.io.IOException;
import java.util.Date;
import org.json.JSONObject;
import org.json.JSONArray;

/**
    Represents an automated service on Telerivet, for example a poll, auto-reply, webhook
    service, etc.
    
    A service, generally, defines some automated behavior that can be
    invoked/triggered in a particular context, and may be invoked either manually or when a
    particular event occurs.
    
    Most commonly, services work in the context of a particular message, when
    the message is originally received by Telerivet.
    
    Fields:
    
      - id (string, max 34 characters)
          * ID of the service
          * Read-only
      
      - name
          * Name of the service
          * Updatable via API
      
      - active (bool)
          * Whether the service is active or inactive. Inactive services are not automatically
              triggered and cannot be invoked via the API.
          * Updatable via API
      
      - priority (int)
          * A number that determines the order that services are triggered when a particular
              event occurs (smaller numbers are triggered first). Any service can determine whether
              or not execution "falls-through" to subsequent services (with larger priority values)
              by setting the return_value variable within Telerivet's Rules Engine.
          * Updatable via API
      
      - contexts (JSONObject)
          * A key/value map where the keys are the names of contexts supported by this service
              (e.g. message, contact), and the values are themselves key/value maps where the keys
              are event names and the values are all true. (This structure makes it easy to test
              whether a service can be invoked for a particular context and event.)
          * Read-only
      
      - vars (JSONObject)
          * Custom variables stored for this service
          * Updatable via API
      
      - project_id
          * ID of the project this service belongs to
          * Read-only
      
      - label_id
          * ID of the label containing messages sent or received by this service (currently only
              used for polls)
          * Read-only
      
      - response_table_id
          * ID of the data table where responses to this service will be stored (currently only
              used for polls)
          * Read-only
      
      - sample_group_id
          * ID of the group containing contacts that have been invited to interact with this
              service (currently only used for polls)
          * Read-only
      
      - respondent_group_id
          * ID of the group containing contacts that have completed an interaction with this
              service (currently only used for polls)
          * Read-only
      
      - questions (array)
          * Array of objects describing each question in a poll (only used for polls). Each
              object has the properties "id" (the question ID), "content" (the text of the
              question), and "question_type" (either "multiple_choice", "missed_call", or "open").
          * Read-only
 */
public class Service extends Entity 
{    
    /**
        Gets the current state for a particular contact for this service.
        
        If the contact doesn't already have a state, this method will return
        a valid state object with id=null. However this object would not be returned by
        queryContactStates() unless it is saved with a non-null state id.
     */
    public ContactServiceState getContactState(Contact contact) throws IOException
    {
        return new ContactServiceState(api, (JSONObject) api.doRequest("GET", getBaseApiPath() + "/states/" + contact.get("id")));
    }
    
    /**
        Initializes or updates the current state for a particular contact for the given service. If
        the state id is null, the contact's state will be reset.
     */
    public ContactServiceState setContactState(Contact contact, JSONObject options) throws IOException
    {
        return new ContactServiceState(api, (JSONObject) api.doRequest("POST", getBaseApiPath() + "/states/" + contact.get("id"), options));
    }    
        
    /**
        Resets the current state for a particular contact for the given service.
     */
    public ContactServiceState resetContactState(Contact contact) throws IOException
    {
        return new ContactServiceState(api, (JSONObject) api.doRequest("DELETE", getBaseApiPath() + "/states/" + contact.get("id")));
    }            
    
    /**
        Manually invoke this service in a particular context.
        
        For example, to send a poll to a particular contact (or resend the
        current question), you can invoke the poll service with context=contact, and contact_id as
        the ID of the contact to send the poll to.
        
        Or, to manually apply a service for an incoming message, you can
        invoke the service with context=message, event=incoming\_message, and message_id as the ID
        of the incoming message. (This is normally not necessary, but could be used if you want to
        override Telerivet's standard priority-ordering of services.)
    */
    public JSONObject invoke(JSONObject options) throws IOException
    {
        return (JSONObject) api.doRequest("POST", getBaseApiPath() + "/invoke", options);
    }

    /**
        Query the current states of contacts for this service.
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
        Saves any fields or custom variables that have changed for this service.
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
