
package com.telerivet;

import java.io.IOException;
import org.json.JSONObject;
import org.json.JSONArray;

/**
    <div class='markdown'><p>Represents a scheduled service within Telerivet.</p>
    
    <p>Scheduled services allow services to be triggered automatically at specified
    times.
    The ScheduledService object is only used for services that are triggered for
    a project (e.g. <code>scheduled_actions</code> or <code>scheduled_script</code>).</p>
    
    <p>Note: To schedule services that are triggered for a contact, the
    <a href="#ScheduledMessage">ScheduledMessage</a> object should be used instead, with
    <code>message_type</code>=<code>service</code>.</p>
    
    <p>Fields:</p>
    
    <ul>
    <li><p>id (string, max 34 characters)</p>
    
    <ul>
    <li>ID of the scheduled service</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>service_id (string, max 34 characters)</p>
    
    <ul>
    <li>ID of the service to be triggered</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>rrule</p>
    
    <ul>
    <li>Recurrence rule for recurring scheduled services, e.g. 'FREQ=MONTHLY' or
      'FREQ=WEEKLY;INTERVAL=2'; see
      <a target="_blank" rel="noopener" href="https://tools.ietf.org/html/rfc2445#section-4.3.10">RFC2445</a>.</li>
    <li>Updatable via API</li>
    </ul></li>
    <li><p>timezone_id</p>
    
    <ul>
    <li>Timezone ID used to compute times for recurring scheduled services; see <a target="_blank" rel="noopener" href="http://en.wikipedia.org/wiki/List_of_tz_database_time_zones">List of tz
      database time zones Wikipedia
      article</a>.</li>
    <li>Updatable via API</li>
    </ul></li>
    <li><p>time_created (UNIX timestamp)</p>
    
    <ul>
    <li>Time the scheduled service was created in Telerivet</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>start_time (UNIX timestamp)</p>
    
    <ul>
    <li>The time that the service will be triggered (or first triggered for recurring
      scheduled services)</li>
    <li>Updatable via API</li>
    </ul></li>
    <li><p>end_time (UNIX timestamp)</p>
    
    <ul>
    <li>Time after which a recurring scheduled service will stop (not applicable to
      non-recurring scheduled services)</li>
    <li>Updatable via API</li>
    </ul></li>
    <li><p>prev_time (UNIX timestamp)</p>
    
    <ul>
    <li>The most recent time that Telerivet triggered this scheduled service (null if it has
      never been triggered)</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>next_time (UNIX timestamp)</p>
    
    <ul>
    <li>The next upcoming time that Telerivet will trigger this scheduled service (null if
      it will not be triggered again)</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>occurrences (int)</p>
    
    <ul>
    <li>Number of times this scheduled service has already been triggered</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>vars (JSONObject)</p>
    
    <ul>
    <li>Custom variables stored for this scheduled service. Variable names may be up to 32
      characters in length and can contain the characters a-z, A-Z, 0-9, and _.
      Values may be strings, numbers, or boolean (true/false).
      String values may be up to 4096 bytes in length when encoded as UTF-8.
      Up to 100 variables are supported per object.
      Setting a variable to null will delete the variable.</li>
    <li>Updatable via API</li>
    </ul></li>
    <li><p>project_id</p>
    
    <ul>
    <li>ID of the project this scheduled service belongs to</li>
    <li>Read-only</li>
    </ul></li>
    </ul>
    </div>
*/
public class ScheduledService extends Entity
{
    /**
        <div class='markdown'><p>Saves any fields or custom variables that have changed for this scheduled service.</p>
        </div>
    */
    @Override
    public void save() throws IOException
    {
        super.save();
    }

    /**
        <div class='markdown'><p>Cancels this scheduled service.</p>
        </div>
    */
    public void delete() throws IOException
    {
        api.doRequest("DELETE", getBaseApiPath());
    }

    public String getId()
    {
        return (String) get("id");
    }

    public String getServiceId()
    {
        return (String) get("service_id");
    }

    public String getRrule()
    {
        return (String) get("rrule");
    }

    public void setRrule(String value)
    {
        set("rrule", value);
    }

    public String getTimezoneId()
    {
        return (String) get("timezone_id");
    }

    public void setTimezoneId(String value)
    {
        set("timezone_id", value);
    }

    public Long getTimeCreated()
    {
        return Util.toLong(get("time_created"));
    }

    public Long getStartTime()
    {
        return Util.toLong(get("start_time"));
    }

    public void setStartTime(Long value)
    {
        set("start_time", value);
    }

    public Long getEndTime()
    {
        return Util.toLong(get("end_time"));
    }

    public void setEndTime(Long value)
    {
        set("end_time", value);
    }

    public Long getPrevTime()
    {
        return Util.toLong(get("prev_time"));
    }

    public Long getNextTime()
    {
        return Util.toLong(get("next_time"));
    }

    public Integer getOccurrences()
    {
        return (Integer) get("occurrences");
    }

    public String getProjectId()
    {
        return (String) get("project_id");
    }

    @Override
    public String getBaseApiPath()
    {
        return "/projects/" + getProjectId() + "/scheduled_services/" + getId() + "";
    }

    public ScheduledService(TelerivetAPI api, JSONObject data)
    {
        this(api, data, true);
    }

    public ScheduledService(TelerivetAPI api, JSONObject data, boolean isLoaded)
    {
        super(api, data, isLoaded);
    }
}
