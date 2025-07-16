
package com.telerivet;

import java.io.IOException;
import org.json.JSONObject;
import org.json.JSONArray;

/**
    <div class='markdown'><p>Represents a custom route that can be used to send messages via one or more basic routes
    (phones).</p>
    
    <p>Custom Routes were formerly referred to simply as "Routes" within Telerivet. API methods,
    parameters, and properties related to Custom Routes continue to use the term "Route" to
    maintain backwards compatibility.</p>
    
    <p>Custom routing rules can currently only be configured via Telerivet's web
    UI.</p>
    
    <p>Fields:</p>
    
    <ul>
    <li><p>id (string, max 34 characters)</p>
    
    <ul>
    <li>Telerivet's internal ID for the route</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>name</p>
    
    <ul>
    <li>The name of the route</li>
    <li>Updatable via API</li>
    </ul></li>
    <li><p>vars (JSONObject)</p>
    
    <ul>
    <li>Custom variables stored for this route. Variable names may be up to 32 characters in
      length and can contain the characters a-z, A-Z, 0-9, and _.
      Values may be strings, numbers, or boolean (true/false).
      String values may be up to 4096 bytes in length when encoded as UTF-8.
      Up to 100 variables are supported per object.
      Setting a variable to null will delete the variable.</li>
    <li>Updatable via API</li>
    </ul></li>
    <li><p>project_id</p>
    
    <ul>
    <li>ID of the project this route belongs to</li>
    <li>Read-only</li>
    </ul></li>
    </ul>
    </div>
*/
public class Route extends Entity
{
    /**
        <div class='markdown'><p>Saves any fields or custom variables that have changed for this custom route.</p>
        </div>
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

    public String getProjectId()
    {
        return (String) get("project_id");
    }

    @Override
    public String getBaseApiPath()
    {
        return "/projects/" + getProjectId() + "/routes/" + getId() + "";
    }

    public Route(TelerivetAPI api, JSONObject data)
    {
        this(api, data, true);
    }

    public Route(TelerivetAPI api, JSONObject data, boolean isLoaded)
    {
        super(api, data, isLoaded);
    }
}
