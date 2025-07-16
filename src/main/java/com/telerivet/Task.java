
package com.telerivet;

import java.io.IOException;
import org.json.JSONObject;
import org.json.JSONArray;

/**
    <div class='markdown'><p>Represents an asynchronous task that is applied to all entities matching a filter.</p>
    
    <p>Tasks include services applied to contacts, messages, or data rows; adding
    or removing contacts from a group; blocking or unblocking sending messages to a contact;
    updating a custom variable; deleting contacts, messages, or data rows; or
    exporting data to CSV.</p>
    
    <p>Fields:</p>
    
    <ul>
    <li><p>id (string, max 34 characters)</p>
    
    <ul>
    <li>ID of the task</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>task_type (string)</p>
    
    <ul>
    <li>The task type</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>task_params (JSONObject)</p>
    
    <ul>
    <li>Parameters applied to all matching rows (specific to <code>task_type</code>). See
      <a href="#Project.createTask">project.createTask</a>.</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>filter_type</p>
    
    <ul>
    <li>Type of filter defining the rows that the task is applied to</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>filter_params (JSONObject)</p>
    
    <ul>
    <li>Parameters defining the rows that the task is applied to (specific to
      <code>filter_type</code>). See <a href="#Project.createTask">project.createTask</a>.</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>time_created (UNIX timestamp)</p>
    
    <ul>
    <li>Time the task was created in Telerivet</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>time_active (UNIX timestamp)</p>
    
    <ul>
    <li>Time Telerivet started executing the task</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>time_complete (UNIX timestamp)</p>
    
    <ul>
    <li>Time Telerivet finished executing the task</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>total_rows (int)</p>
    
    <ul>
    <li>The total number of rows matching the filter (null if not known)</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>current_row (int)</p>
    
    <ul>
    <li>The number of rows that have been processed so far</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>status (string)</p>
    
    <ul>
    <li>The current status of the task</li>
    <li>Allowed values: created, queued, active, complete, failed, cancelled</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>vars (JSONObject)</p>
    
    <ul>
    <li>Custom variables stored for this task. Variable names may be up to 32 characters in
      length and can contain the characters a-z, A-Z, 0-9, and _.
      Values may be strings, numbers, or boolean (true/false).
      String values may be up to 4096 bytes in length when encoded as UTF-8.
      Up to 100 variables are supported per object.
      Setting a variable to null will delete the variable.</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>table_id (string, max 34 characters)</p>
    
    <ul>
    <li>ID of the data table this task is applied to (if applicable)</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>user_id (string, max 34 characters)</p>
    
    <ul>
    <li>ID of the Telerivet user who created the task (if applicable)</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>project_id</p>
    
    <ul>
    <li>ID of the project this task belongs to</li>
    <li>Read-only</li>
    </ul></li>
    </ul>
    </div>
*/
public class Task extends Entity
{
    /**
        <div class='markdown'><p>Cancels a task that is not yet complete.</p>
        </div>
    */
    public Task cancel() throws IOException
    {
        return new Task(api, (JSONObject) api.doRequest("POST", getBaseApiPath() + "/cancel"));
    }

    public String getId()
    {
        return (String) get("id");
    }

    public String getTaskType()
    {
        return (String) get("task_type");
    }

    public JSONObject getTaskParams()
    {
        return (JSONObject) get("task_params");
    }

    public String getFilterType()
    {
        return (String) get("filter_type");
    }

    public JSONObject getFilterParams()
    {
        return (JSONObject) get("filter_params");
    }

    public Long getTimeCreated()
    {
        return Util.toLong(get("time_created"));
    }

    public Long getTimeActive()
    {
        return Util.toLong(get("time_active"));
    }

    public Long getTimeComplete()
    {
        return Util.toLong(get("time_complete"));
    }

    public Integer getTotalRows()
    {
        return (Integer) get("total_rows");
    }

    public Integer getCurrentRow()
    {
        return (Integer) get("current_row");
    }

    public String getStatus()
    {
        return (String) get("status");
    }

    public String getTableId()
    {
        return (String) get("table_id");
    }

    public String getUserId()
    {
        return (String) get("user_id");
    }

    public String getProjectId()
    {
        return (String) get("project_id");
    }

    @Override
    public String getBaseApiPath()
    {
        return "/projects/" + getProjectId() + "/tasks/" + getId() + "";
    }

    public Task(TelerivetAPI api, JSONObject data)
    {
        this(api, data, true);
    }

    public Task(TelerivetAPI api, JSONObject data, boolean isLoaded)
    {
        super(api, data, isLoaded);
    }
}
