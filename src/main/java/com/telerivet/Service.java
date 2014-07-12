package com.telerivet;

import java.io.IOException;
import java.util.Date;
import org.json.JSONObject;
import org.json.JSONArray;

/**
    Service
    
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
 */
public class Service extends Entity 
{    
    /**
        service.getContactState(contact)
        
        Gets the current state for a particular contact for this service.
        
        If the contact doesn't already have a state, this method will return
        a valid state object with id=null. However this object would not be returned by
        queryContactStates() unless it is saved with a non-null state id.
        
        Arguments:
          - contact (Contact)
              * The contact whose state you want to retrieve.
              * Required
          
        Returns:
            ContactServiceState
     */
    public ContactServiceState getContactState(Contact contact) throws IOException
    {
        return new ContactServiceState(api, (JSONObject) api.doRequest("GET", getBaseApiPath() + "/states/" + contact.get("id")));
    }
    
    /**
        service.setContactState(contact, options)
        
        Initializes or updates the current state for a particular contact for the given service. If
        the state id is null, the contact's state will be reset.
        
        Arguments:
          - contact (Contact)
              * The contact whose state you want to update.
              * Required
          
          - options (JSONObject)
              * Required
            
            - id (string, max 63 characters)
                * Arbitrary string representing the contact's current state for this service, e.g.
                    'q1', 'q2', etc.
                * Required
            
            - vars (JSONObject)
                * Custom variables stored for this contact's state
          
        Returns:
            ContactServiceState
     */
    public ContactServiceState setContactState(Contact contact, JSONObject options) throws IOException
    {
        return new ContactServiceState(api, (JSONObject) api.doRequest("POST", getBaseApiPath() + "/states/" + contact.get("id"), options));
    }    
        
    /**
        service.resetContactState(contact)
        
        Resets the current state for a particular contact for the given service.
        
        Arguments:
          - contact (Contact)
              * The contact whose state you want to reset.
              * Required
          
        Returns:
            ContactServiceState
     */
    public ContactServiceState resetContactState(Contact contact) throws IOException
    {
        return new ContactServiceState(api, (JSONObject) api.doRequest("DELETE", getBaseApiPath() + "/states/" + contact.get("id")));
    }            
    
    /**
        service.invoke(options)
        
        Manually invoke this service in a particular context.
        
        For example, to send a poll to a particular contact (or resend the
        current question), you can invoke the poll service with context=contact, and contact_id as
        the ID of the contact to send the poll to.
        
        Or, to manually apply a service for an incoming message, you can
        invoke the service with context=message, event=incoming\_message, and message_id as the ID
        of the incoming message. (This is normally not necessary, but could be used if you want to
        override Telerivet's standard priority-ordering of services.)
        
        Arguments:
          - options (JSONObject)
              * Required
            
            - context
                * The name of the context in which this service is invoked
                * Allowed values: message, contact, project, receipt
                * Required
            
            - event
                * The name of the event that is triggered (must be supported by this service)
                * Default: default
            
            - message_id
                * The ID of the message this service is triggered for
                * Required if context is 'message'
            
            - contact_id
                * The ID of the contact this service is triggered for
                * Required if context is 'contact'
          
        Returns:
            object
    */
    public JSONObject invoke(JSONObject options) throws IOException
    {
        return (JSONObject) api.doRequest("POST", getBaseApiPath() + "/invoke", options);
    }

    /**
        service.queryContactStates(options)
        
        Query the current states of contacts for this service.
        
        Arguments:
          - options (JSONObject)
            
            - id
                * Filter states by id
                * Allowed modifiers: id[ne], id[prefix], id[not_prefix], id[gte], id[gt], id[lt],
                    id[lte]
            
            - vars (JSONObject)
                * Filter states by value of a custom variable (e.g. vars[email], vars[foo], etc.)
                * Allowed modifiers: vars[foo][exists], vars[foo][ne], vars[foo][prefix],
                    vars[foo][not_prefix], vars[foo][gte], vars[foo][gt], vars[foo][lt], vars[foo][lte],
                    vars[foo][min], vars[foo][max]
            
            - sort
                * Sort the results based on a field
                * Allowed values: default
                * Default: default
            
            - sort_dir
                * Sort the results in ascending or descending order
                * Allowed values: asc, desc
                * Default: asc
            
            - page_size (int)
                * Number of results returned per page (max 200)
                * Default: 50
            
            - offset (int)
                * Number of items to skip from beginning of result set
                * Default: 0
          
        Returns:
            APICursor (of ContactServiceState)
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
        service.save()
        
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
