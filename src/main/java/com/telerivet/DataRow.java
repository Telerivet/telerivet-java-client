
package com.telerivet;
        
import java.io.IOException;
import org.json.JSONObject;
import org.json.JSONArray;
        
/**
    DataRow
    
    Represents a row in a custom data table.
    
    For example, each response to a poll is stored as one row in a data table.
    If a poll has a question with ID 'q1', the verbatim response to that question would be
    stored in row.vars.q1, and the response code would be stored in row.vars.q1_code.
    
    Each custom variable name within a data row corresponds to a different
    column/field of the data table.
    
    Fields:
    
      - id (string, max 34 characters)
          * ID of the data row
          * Read-only
      
      - contact_id
          * ID of the contact this row is associated with (or null if not associated with any
              contact)
          * Updatable via API
      
      - from_number (string)
          * Phone number that this row is associated with (or null if not associated with any
              phone number)
          * Updatable via API
      
      - vars (JSONObject)
          * Custom variables stored for this data row
          * Updatable via API
      
      - table_id
          * ID of the table this data row belongs to
          * Read-only
      
      - project_id
          * ID of the project this data row belongs to
          * Read-only
*/
public class DataRow extends Entity
{
    /**
        row.save()
        
        Saves any fields or custom variables that have changed for this data row.
    */
    @Override
    public void save() throws IOException
    {
        super.save();
    }

    /**
        row.delete()
        
        Deletes this data row.
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
