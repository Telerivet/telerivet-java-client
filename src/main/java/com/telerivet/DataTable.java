
package com.telerivet;

import java.io.IOException;
import org.json.JSONObject;
import org.json.JSONArray;

/**
    <p>Represents a custom data table that can store arbitrary rows.</p>
    
    <p>For example, poll services use data tables to store a row for each response.</p>
    
    <p>DataTables are schemaless -- each row simply stores custom variables. Each
    variable name is equivalent to a different "column" of the data table.
    Telerivet refers to these variables/columns as "fields", and automatically
    creates a new field for each variable name used in a row of the table.</p>
    
    <p>Fields:</p>
    
    <ul>
    <li><p>id (string, max 34 characters)</p>
    
    <ul>
    <li>ID of the data table</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>name</p>
    
    <ul>
    <li>Name of the data table</li>
    <li>Updatable via API</li>
    </ul></li>
    <li><p>num_rows (int)</p>
    
    <ul>
    <li>Number of rows in the table. For performance reasons, this number may sometimes be
      out-of-date.</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>show<em>add</em>row (bool)</p>
    
    <ul>
    <li>Whether to allow adding or importing rows via the web app</li>
    <li>Updatable via API</li>
    </ul></li>
    <li><p>show_stats (bool)</p>
    
    <ul>
    <li>Whether to show row statistics in the web app</li>
    <li>Updatable via API</li>
    </ul></li>
    <li><p>show<em>contact</em>columns (bool)</p>
    
    <ul>
    <li>Whether to show 'Contact Name' and 'Phone Number' columns in the web app</li>
    <li>Updatable via API</li>
    </ul></li>
    <li><p>vars (JSONObject)</p>
    
    <ul>
    <li>Custom variables stored for this data table</li>
    <li>Updatable via API</li>
    </ul></li>
    <li><p>project_id</p>
    
    <ul>
    <li>ID of the project this data table belongs to</li>
    <li>Read-only</li>
    </ul></li>
    </ul>
*/
public class DataTable extends Entity
{
    /**
        <p>Queries rows in this data table.</p>
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
        <p>Adds a new row to this data table.</p>
    */
    public DataRow createRow(JSONObject options) throws IOException
    {
        return new DataRow(api, (JSONObject) api.doRequest("POST", getBaseApiPath() + "/rows", options));
    }

    /**
        <p>Retrieves the row in the given table with the given ID.</p>
    */
    public DataRow getRowById(String id) throws IOException
    {
        return new DataRow(api, (JSONObject) api.doRequest("GET", getBaseApiPath() + "/rows/" + id));
    }

    /**
        <p>Initializes the row in the given table with the given ID, without making an API request.</p>
    */
    public DataRow initRowById(String id)
    {
        return new DataRow(api, Util.options("project_id", get("project_id"), "table_id", get("id"), "id", id), false);
    }

    /**
        <p>Gets a list of all fields (columns) defined for this data table. The return value is an
        array of objects with the properties 'name', 'variable', 'type', 'order', 'readonly', and
        'lookup_key'. (Fields are automatically created any time a DataRow's 'vars' property is
        updated.)</p>
    */
    public JSONArray getFields() throws IOException
    {
        return (JSONArray) api.doRequest("GET", getBaseApiPath() + "/fields");
    }

    /**
        <p>Allows customizing how a field (column) is displayed in the Telerivet web app.</p>
    */
    public JSONObject setFieldMetadata(String variable, JSONObject options) throws IOException
    {
        return (JSONObject) api.doRequest("POST", getBaseApiPath() + "/fields/" + variable, options);
    }

    /**
        <p>Returns the number of rows for each value of a given variable. This can be used to get the
        total number of responses for each choice in a poll, without making a separate query for
        each response choice. The return value is an object mapping values to row counts, e.g.
        <code>{"yes":7,"no":3}</code></p>
    */
    public JSONObject countRowsByValue(String variable) throws IOException
    {
        return (JSONObject) api.doRequest("GET", getBaseApiPath() + "/count_rows_by_value", Util.options("variable", variable));
    }

    /**
        <p>Saves any fields that have changed for this data table.</p>
    */
    @Override
    public void save() throws IOException
    {
        super.save();
    }

    /**
        <p>Permanently deletes the given data table, including all its rows</p>
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

    public Boolean getShowAddRow()
    {
        return (Boolean) get("show_add_row");
    }

    public void setShowAddRow(Boolean value)
    {
        set("show_add_row", value);
    }

    public Boolean getShowStats()
    {
        return (Boolean) get("show_stats");
    }

    public void setShowStats(Boolean value)
    {
        set("show_stats", value);
    }

    public Boolean getShowContactColumns()
    {
        return (Boolean) get("show_contact_columns");
    }

    public void setShowContactColumns(Boolean value)
    {
        set("show_contact_columns", value);
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
