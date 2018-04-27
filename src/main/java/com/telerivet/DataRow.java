
package com.telerivet;

import java.io.IOException;
import org.json.JSONObject;
import org.json.JSONArray;

/**
    <p>Represents a row in a custom data table.</p>
    
    <p>For example, each response to a poll is stored as one row in a data table.
    If a poll has a question with ID 'q1', the verbatim response to that question would be
    stored in row.vars.q1, and the response code would be stored in row.vars.q1_code.</p>
    
    <p>Each custom variable name within a data row corresponds to a different
    column/field of the data table.</p>
    
    <p>Fields:</p>
    
    <ul>
    <li><p>id (string, max 34 characters)</p>
    
    <ul>
    <li>ID of the data row</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>contact_id</p>
    
    <ul>
    <li>ID of the contact this row is associated with (or null if not associated with any
      contact)</li>
    <li>Updatable via API</li>
    </ul></li>
    <li><p>from_number (string)</p>
    
    <ul>
    <li>Phone number that this row is associated with (or null if not associated with any
      phone number)</li>
    <li>Updatable via API</li>
    </ul></li>
    <li><p>vars (JSONObject)</p>
    
    <ul>
    <li>Custom variables stored for this data row</li>
    <li>Updatable via API</li>
    </ul></li>
    <li><p>time_created (UNIX timestamp)</p>
    
    <ul>
    <li>The time this row was created in Telerivet</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>time_updated (UNIX timestamp)</p>
    
    <ul>
    <li>The time this row was last updated in Telerivet</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>table_id</p>
    
    <ul>
    <li>ID of the table this data row belongs to</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>project_id</p>
    
    <ul>
    <li>ID of the project this data row belongs to</li>
    <li>Read-only</li>
    </ul></li>
    </ul>
*/
public class DataRow extends Entity
{
    /**
        <p>Saves any fields or custom variables that have changed for this data row.</p>
    */
    @Override
    public void save() throws IOException
    {
        super.save();
    }

    /**
        <p>Deletes this data row.</p>
    */
    public void delete() throws IOException
    {
        api.doRequest("DELETE", getBaseApiPath());
    }

    public String getId()
    {
        return (String) get("id");
    }

    public String getContactId()
    {
        return (String) get("contact_id");
    }

    public void setContactId(String value)
    {
        set("contact_id", value);
    }

    public String getFromNumber()
    {
        return (String) get("from_number");
    }

    public void setFromNumber(String value)
    {
        set("from_number", value);
    }

    public Long getTimeCreated()
    {
        return Util.toLong(get("time_created"));
    }

    public Long getTimeUpdated()
    {
        return Util.toLong(get("time_updated"));
    }

    public String getTableId()
    {
        return (String) get("table_id");
    }

    public String getProjectId()
    {
        return (String) get("project_id");
    }

    @Override
    public String getBaseApiPath()
    {
        return "/projects/" + getProjectId() + "/tables/" + getTableId() + "/rows/" + getId() + "";
    }

    public DataRow(TelerivetAPI api, JSONObject data)
    {
        this(api, data, true);
    }

    public DataRow(TelerivetAPI api, JSONObject data, boolean isLoaded)
    {
        super(api, data, isLoaded);
    }
}
