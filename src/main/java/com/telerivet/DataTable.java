
package com.telerivet;
        
import java.io.IOException;
import org.json.JSONObject;
import org.json.JSONArray;
        
/**
    DataTable
    
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
        table.queryRows(options)
        
        Queries rows in this data table.
        
        Arguments:
          - options (JSONObject)
            
            - time_created (UNIX timestamp)
                * Filter data rows by the time they were created
                * Allowed modifiers: time_created[ne], time_created[min], time_created[max]
            
            - vars (JSONObject)
                * Filter data rows by value of a custom variable (e.g. vars[q1], vars[foo], etc.)
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
            APICursor (of DataRow)
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
        table.createRow(options)
        
        Adds a new row to this data table.
        
        Arguments:
          - options (JSONObject)
            
            - contact_id
                * ID of the contact that this row is associated with (if applicable)
            
            - from_number (string)
                * Phone number that this row is associated with (if applicable)
            
            - vars
                * Custom variables and values to set for this data row
          
        Returns:
            DataRow
    */
    public DataRow createRow(JSONObject options) throws IOException
    {
        return new DataRow(api, (JSONObject) api.doRequest("POST", getBaseApiPath() + "/rows", options));
    }

    /**
        table.getRowById(id)
        
        Retrieves the row in the given table with the given ID.
        
        Arguments:
          - id
              * ID of the row
              * Required
          
        Returns:
            DataRow
    */
    public DataRow getRowById(String id) throws IOException
    {
        return new DataRow(api, (JSONObject) api.doRequest("GET", getBaseApiPath() + "/rows/" + id));
    }

    /**
        table.initRowById(id)
        
        Initializes the row in the given table with the given ID, without making an API request.
        
        Arguments:
          - id
              * ID of the row
              * Required
          
        Returns:
            DataRow
    */
    public DataRow initRowById(String id)
    {
        return new DataRow(api, Util.options("project_id", get("project_id"), "table_id", get("id"), "id", id), false);
    }

    /**
        table.getFields()
        
        Gets a list of all fields (columns) defined for this data table. The return value is an
        array of objects with the properties 'name' and 'variable'. (Fields are automatically
        created any time a DataRow's 'vars' property is updated.)
        
        Returns:
            array
    */
    public JSONArray getFields() throws IOException
    {
        return (JSONArray) api.doRequest("GET", getBaseApiPath() + "/fields");
    }

    /**
        table.countRowsByValue(variable)
        
        Returns the number of rows for each value of a given variable. This can be used to get the
        total number of responses for each choice in a poll, without making a separate query for
        each response choice. The return value is an object mapping values to row counts, e.g.
        `{"yes":7,"no":3}`
        
        Arguments:
          - variable
              * Variable of field to count by.
              * Required
          
        Returns:
            object
    */
    public JSONObject countRowsByValue(String variable) throws IOException
    {
        return (JSONObject) api.doRequest("GET", getBaseApiPath() + "/count_rows_by_value", Util.options("variable", variable));
    }

    /**
        table.save()
        
        Saves any fields that have changed for this data table.
    */
    @Override
    public void save() throws IOException
    {
        super.save();
    }

    /**
        table.delete()
        
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
