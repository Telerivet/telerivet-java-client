
package com.telerivet;

import java.io.IOException;
import org.json.JSONObject;
import org.json.JSONArray;

/**
    <div class='markdown'><p>Represents a reusable message template that can be used when composing or scheduling
    messages.</p>
    
    <p>Message templates can include placeholder variables using double square
    brackets (e.g. <code>[[contact.name]]</code>) that are replaced with actual values when the message is
    sent. For available variables, see <a href="#variables">Variable Reference</a>. The double square
    brackets may also include <a href="/api/rules_engine#filters">filters</a> to transform variables, such
    as to set a default value.</p>
    
    <p>Templates synchronized from a WhatsApp Business Account are read-only and
    cannot be modified or deleted via the API.
    (When using Telerivet to send messages via a WhatsApp route, Telerivet tries
    to match the content of each outgoing message to one of these WhatsApp message templates. If
    a template matches, Telerivet sends a WhatsApp template message instead of a session
    message.)</p>
    
    <p>Fields:</p>
    
    <ul>
    <li><p>id (string, max 34 characters)</p>
    
    <ul>
    <li>ID of the message template</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>name (string)</p>
    
    <ul>
    <li>Name of the template (max 127 characters)</li>
    <li>Updatable via API</li>
    </ul></li>
    <li><p>content (string)</p>
    
    <ul>
    <li>Content of the message template (max 2000 characters)</li>
    <li>Updatable via API</li>
    </ul></li>
    <li><p>track_clicks (bool)</p>
    
    <ul>
    <li>If true, URLs in the content will be replaced with short links that track clicks
      when the template is used</li>
    <li>Updatable via API</li>
    </ul></li>
    <li><p>readonly (bool)</p>
    
    <ul>
    <li>True if the template cannot be modified or deleted (i.e., it was synchronized from a
      WhatsApp Business Account)</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>short_link_params (JSONObject)</p>
    
    <ul>
    <li>Custom parameters for short links (only if track_clicks is true)</li>
    <li>Updatable via API</li>
    </ul></li>
    <li><p>attachments (array)</p>
    
    <ul>
    <li>List of attachment objects with file URLs</li>
    <li>Updatable via API</li>
    </ul></li>
    <li><p>route_params (JSONObject)</p>
    
    <ul>
    <li><p>Route-specific parameters to use when sending messages with this template.</p>
    
    <p>When sending messages via chat apps such as WhatsApp, the route_params
      parameter can be used to send messages with app-specific features such as quick
      replies and link buttons.</p>
    
    <p>For more details, see <a href="#route_params">Route-Specific Parameters</a>.</p></li>
    <li>Updatable via API</li>
    </ul></li>
    <li><p>waba_id (string)</p>
    
    <ul>
    <li>ID of the WhatsApp Business Account that this template is associated with (only
      present for WhatsApp templates)</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>whatsapp_template (JSONObject)</p>
    
    <ul>
    <li>For templates synchronized from a WhatsApp Business Account, contains the full
      template data from WhatsApp with properties including <code>id</code>, <code>name</code>, <code>language</code>,
      <code>status</code>, <code>category</code>, and <code>components</code>. The full format is defined in the <a target="_blank" rel="noopener" href="https://developers.facebook.com/documentation/business-messaging/whatsapp/reference/whatsapp-business-account/template-api#get-version-template-id">WhatsApp
      Template API
      documentation</a>.
      Only present for WhatsApp templates.</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>time_created (UNIX timestamp)</p>
    
    <ul>
    <li>Time the template was created in Telerivet</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>time_updated (UNIX timestamp)</p>
    
    <ul>
    <li>Time the template was last updated</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>vars (JSONObject)</p>
    
    <ul>
    <li>Custom variables stored for this template. Variable names may be up to 32 characters
      in length and can contain the characters a-z, A-Z, 0-9, and _.
      Values may be strings, numbers, or boolean (true/false).
      String values may be up to 4096 bytes in length when encoded as UTF-8.
      Up to 100 variables are supported per object.
      Setting a variable to null will delete the variable.</li>
    <li>Updatable via API</li>
    </ul></li>
    <li><p>project_id</p>
    
    <ul>
    <li>ID of the project this template belongs to</li>
    <li>Read-only</li>
    </ul></li>
    </ul>
    </div>
*/
public class MessageTemplate extends Entity
{
    /**
        <div class='markdown'><p>Saves any fields that have changed for the message template.</p>
        </div>
    */
    @Override
    public void save() throws IOException
    {
        super.save();
    }

    /**
        <div class='markdown'><p>Deletes the message template.</p>
        
        <p>Note: Templates synchronized from a WhatsApp Business Account cannot be deleted via the API.</p>
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

    public String getName()
    {
        return (String) get("name");
    }

    public void setName(String value)
    {
        set("name", value);
    }

    public String getContent()
    {
        return (String) get("content");
    }

    public void setContent(String value)
    {
        set("content", value);
    }

    public Boolean getTrackClicks()
    {
        return (Boolean) get("track_clicks");
    }

    public void setTrackClicks(Boolean value)
    {
        set("track_clicks", value);
    }

    public Boolean getReadonly()
    {
        return (Boolean) get("readonly");
    }

    public JSONObject getShortLinkParams()
    {
        return (JSONObject) get("short_link_params");
    }

    public void setShortLinkParams(JSONObject value)
    {
        set("short_link_params", value);
    }

    public JSONArray getAttachments()
    {
        return (JSONArray) get("attachments");
    }

    public void setAttachments(JSONArray value)
    {
        set("attachments", value);
    }

    public JSONObject getRouteParams()
    {
        return (JSONObject) get("route_params");
    }

    public void setRouteParams(JSONObject value)
    {
        set("route_params", value);
    }

    public String getWabaId()
    {
        return (String) get("waba_id");
    }

    public JSONObject getWhatsappTemplate()
    {
        return (JSONObject) get("whatsapp_template");
    }

    public Long getTimeCreated()
    {
        return Util.toLong(get("time_created"));
    }

    public Long getTimeUpdated()
    {
        return Util.toLong(get("time_updated"));
    }

    public String getProjectId()
    {
        return (String) get("project_id");
    }

    @Override
    public String getBaseApiPath()
    {
        return "/projects/" + getProjectId() + "/message_templates/" + getId() + "";
    }

    public MessageTemplate(TelerivetAPI api, JSONObject data)
    {
        this(api, data, true);
    }

    public MessageTemplate(TelerivetAPI api, JSONObject data, boolean isLoaded)
    {
        super(api, data, isLoaded);
    }
}
