
package com.telerivet;
        
import java.io.IOException;
import org.json.JSONObject;
import org.json.JSONArray;
        
/**
    Represents a custom data table that can store arbitrary rows.
    
    For example, poll services use data tables to store a row for each
    response.
    
    DataTables are schemaless -- each row simply stores custom variables. Each
    variable name is equivalent to a different "column" of the data table.
    Telerivet refers to these variables/columns as "fields", and automatically
    creates a new field for each variable name used in a row of the table.
    
    Fields:
    
      - id (string, max 34 characters)
          * ID of the data table
          * Read-only
      
      - name
          * Name of the data table
          * Updatable via API
      
      - num_rows (int)
          * Number of rows in the table
          * Read-only
      
      - vars (JSONObject)
          * Custom variables stored for this data table
          * Updatable via API
      
      - project_id
          * ID of the project this data table belongs to
          * Read-only
*/
public class DataTable extends Entity
{
    /**
        Queries rows in this data table.
    */
    public APICursor<DataRow> queryRows(JSONObject options)
    {
        return api.newCursor(DataRow.class, getBaseApiPath() + "/rows", options);
    }

    public APICursor<DataRow> queryRows()
    {
        return queryRows(null);
    }

    /**
        Adds a new row to this data table.
    */
    public DataRow createRow(JSONObject options) throws IOException
    {
        return new DataRow(api, (JSONObject) api.doRequest("POST", getBaseApiPath() + "/rows", options));
    }

    /**
        Retrieves the row in the given table with the given ID.
    */
    public DataRow getRowById(String id) throws IOException
    {
        return new DataRow(api, (JSONObject) api.doRequest("GET", getBaseApiPath() + "/rows/" + id));
    }

    /**
        Initializes the row in the given table with the given ID, without making an API request.
    */
    public DataRow initRowById(String id)
    {
        return new DataRow(api, Util.options("project_id", get("project_id"), "table_id", get("id"), "id", id), false);
    }

    /**
        Gets a list of all fields (columns) defined for this data table. The return value is an
        array of objects with the properties 'name' and 'variable'. (Fields are automatically
        created any time a DataRow's 'vars' property is updated.)
    */
    public JSONArray getFields() throws IOException
    {
        return (JSONArray) api.doRequest("GET", getBaseApiPath() + "/fields");
    }

    /**
        Returns the number of rows for each value of a given variable. This can be used to get the
        total number of responses for each choice in a poll, without making a separate query for
        each response choice. The return value is an object mapping values to row counts, e.g.
        `{"yes":7,"no":3}`
    */
    public JSONObject countRowsByValue(String variable) throws IOException
    {
        return (JSONObject) api.doRequest("GET", getBaseApiPath() + "/count_rows_by_value", Util.options("variable", variable));
    }

    /**
        Saves any fields that have changed for this data table.
    */
    @Override
    public void save() throws IOException
    {
        super.save();
    }

    /**
        Permanently deletes the given data table, including all its rows
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

    public Integer getNumRows()
    {
        return (Integer) get("num_rows");
    }

    public String getProjectId()
    {
        return (String) get("project_id");
    }

    @Override
    public String getBaseApiPath()
    {
        return "/projects/" + getProjectId() + "/tables/" + getId() + "";
    }

    public DataTable(TelerivetAPI api, JSONObject data)
    {
        this(api, data, true);
    }
    
    public DataTable(TelerivetAPI api, JSONObject data, boolean isLoaded)
    {
        super(api, data, isLoaded);
    }
}
