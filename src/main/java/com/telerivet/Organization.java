
package com.telerivet;

import java.io.IOException;
import org.json.JSONObject;
import org.json.JSONArray;

/**
    <div class='markdown'><p>Represents a Telerivet organization.</p>
    
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
    <li>Billing quota time zone ID; see <a target="_blank" rel="noopener" href="http://en.wikipedia.org/wiki/List_of_tz_database_time_zones">List of tz database time zones Wikipedia
      article</a>.</li>
    <li>Updatable via API</li>
    </ul></li>
    </ul>
    </div>
*/
public class Organization extends Entity
{
    /**
        <div class='markdown'><p>Creates a new project.</p>
        
        <p>Some project settings are not currently possible to configure via
        the API, and can only be edited via the web app after the project is created.</p>
        </div>
    */
    public Project createProject(JSONObject options) throws IOException
    {
        return new Project(api, (JSONObject) api.doRequest("POST", getBaseApiPath() + "/projects", options));
    }

    /**
        <div class='markdown'><p>Saves any fields that have changed for this organization.</p>
        </div>
    */
    @Override
    public void save() throws IOException
    {
        super.save();
    }

    /**
        <div class='markdown'><p>Retrieves information about the organization's service plan and account balance.</p>
        </div>
    */
    public JSONObject getBillingDetails() throws IOException
    {
        return (JSONObject) api.doRequest("GET", getBaseApiPath() + "/billing");
    }

    /**
        <div class='markdown'><p>Retrieves the current usage count associated with a particular service plan limit. Available
        usage types are <code>phones</code>, <code>projects</code>, <code>users</code>, <code>contacts</code>, <code>messages_day</code>,
        <code>stored_messages</code>, <code>data_rows</code>, and <code>api_requests_day</code>.</p>
        </div>
    */
    public Integer getUsage(String usage_type) throws IOException
    {
        return (Integer) api.doRequest("GET", getBaseApiPath() + "/usage/" + usage_type);
    }

    /**
        <div class='markdown'><p>Retrieves statistics about messages sent or received via Telerivet. This endpoint returns
        historical data that is computed shortly after midnight each day in the project's time zone,
        and does not contain message statistics for the current day.</p>
        </div>
    */
    public JSONObject getMessageStats(JSONObject options) throws IOException
    {
        return (JSONObject) api.doRequest("GET", getBaseApiPath() + "/message_stats", options);
    }

    /**
        <div class='markdown'><p>Queries projects in this organization.</p>
        </div>
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
