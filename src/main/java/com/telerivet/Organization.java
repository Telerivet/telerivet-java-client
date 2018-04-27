
package com.telerivet;

import java.io.IOException;
import org.json.JSONObject;
import org.json.JSONArray;

/**
    <p>Represents a Telerivet organization.</p>
    
    <p>Fields:</p>
    
    <ul>
    <li><p>id (string, max 34 characters)</p>
    
    <ul>
    <li>ID of the organization</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>name</p>
    
    <ul>
    <li>Name of the organization</li>
    <li>Updatable via API</li>
    </ul></li>
    <li><p>timezone_id</p>
    
    <ul>
    <li>Billing quota time zone ID; see
      <a href="http://en.wikipedia.org/wiki/List_of_tz_database_time_zones">http://en.wikipedia.org/wiki/List_of_tz_database_time_zones</a></li>
    <li>Updatable via API</li>
    </ul></li>
    </ul>
*/
public class Organization extends Entity
{
    /**
        <p>Saves any fields that have changed for this organization.</p>
    */
    @Override
    public void save() throws IOException
    {
        super.save();
    }

    /**
        <p>Retrieves information about the organization's service plan and account balance.</p>
    */
    public JSONObject getBillingDetails() throws IOException
    {
        return (JSONObject) api.doRequest("GET", getBaseApiPath() + "/billing");
    }

    /**
        <p>Retrieves the current usage count associated with a particular service plan limit. Available
        usage types are <code>phones</code>, <code>projects</code>, <code>users</code>, <code>contacts</code>, <code>messages_day</code>,
        <code>stored_messages</code>, <code>data_rows</code>, and <code>api_requests_day</code>.</p>
    */
    public Integer getUsage(String usage_type) throws IOException
    {
        return (Integer) api.doRequest("GET", getBaseApiPath() + "/usage/" + usage_type);
    }

    /**
        <p>Queries projects in this organization.</p>
    */
    public APICursor<Project> queryProjects(JSONObject options)
    {
        return api.newCursor(Project.class, getBaseApiPath() + "/projects", options);
    }

    public APICursor<Project> queryProjects()
    {
        return queryProjects(null);
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

    public String getTimezoneId()
    {
        return (String) get("timezone_id");
    }

    public void setTimezoneId(String value)
    {
        set("timezone_id", value);
    }

    @Override
    public String getBaseApiPath()
    {
        return "/organizations/" + getId() + "";
    }

    public Organization(TelerivetAPI api, JSONObject data)
    {
        this(api, data, true);
    }

    public Organization(TelerivetAPI api, JSONObject data, boolean isLoaded)
    {
        super(api, data, isLoaded);
    }
}
