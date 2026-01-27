
package com.telerivet;

import java.io.IOException;
import org.json.JSONObject;
import org.json.JSONArray;

/**
    <div class='markdown'><p>Represents a webhook that is triggered when specific events occur within a project.</p>
    
    <p>Webhooks allow your server to receive HTTP POST requests when certain events
    happen, such as when a message's status changes or when a contact is updated.</p>
    
    <p>When an event occurs that matches a webhook's event types, Telerivet will
    send an HTTP POST request to the webhook URL with information about the event. For more
    information about the webhook request format, see the <a href="/api/webhook">Webhook API</a>
    documentation.</p>
    
    <p>Note: The Webhook object is not used for notifying your server when incoming
    messages are received. To notify a URL when incoming messages are received, you can
    configure a webhook by <a href="#Project.createService">creating a Service</a> with type
    incoming_message_webhook.</p>
    
    <p>Fields:</p>
    
    <ul>
    <li><p>id (string, max 34 characters)</p>
    
    <ul>
    <li>ID of the webhook</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>url</p>
    
    <ul>
    <li>URL that webhook requests are sent to</li>
    <li>Updatable via API</li>
    </ul></li>
    <li><p>secret</p>
    
    <ul>
    <li>Secret included with webhook requests for authentication (sent as HTTP basic auth
      password with username 'telerivet')</li>
    <li>Updatable via API</li>
    </ul></li>
    <li><p>events (array of strings)</p>
    
    <ul>
    <li>Array of event types that trigger this webhook. Possible values: <code>send_status</code>
      (message status updates), <code>send_broadcast</code> (broadcast sent), <code>contact_update</code> (contact
      added/updated/deleted), <code>message_metadata</code> (message metadata updated)</li>
    <li>Updatable via API</li>
    </ul></li>
    <li><p>project_id</p>
    
    <ul>
    <li>ID of the project this webhook belongs to</li>
    <li>Read-only</li>
    </ul></li>
    </ul>
    </div>
*/
public class Webhook extends Entity
{
    /**
        <div class='markdown'><p>Saves any fields that have changed for this webhook.</p>
        </div>
    */
    @Override
    public void save() throws IOException
    {
        super.save();
    }

    /**
        <div class='markdown'><p>Deletes the webhook.</p>
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

    public String getUrl()
    {
        return (String) get("url");
    }

    public void setUrl(String value)
    {
        set("url", value);
    }

    public String getSecret()
    {
        return (String) get("secret");
    }

    public void setSecret(String value)
    {
        set("secret", value);
    }

    public JSONArray getEvents()
    {
        return (JSONArray) get("events");
    }

    public void setEvents(JSONArray value)
    {
        set("events", value);
    }

    public String getProjectId()
    {
        return (String) get("project_id");
    }

    @Override
    public String getBaseApiPath()
    {
        return "/projects/" + getProjectId() + "/webhooks/" + getId() + "";
    }

    public Webhook(TelerivetAPI api, JSONObject data)
    {
        this(api, data, true);
    }

    public Webhook(TelerivetAPI api, JSONObject data, boolean isLoaded)
    {
        super(api, data, isLoaded);
    }
}
