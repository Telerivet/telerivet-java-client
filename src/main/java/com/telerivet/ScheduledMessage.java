
package com.telerivet;

import java.io.IOException;
import org.json.JSONObject;
import org.json.JSONArray;

/**
    <div class='markdown'><p>Represents a scheduled message within Telerivet.</p>
    
    <p>Fields:</p>
    
    <ul>
    <li><p>id (string, max 34 characters)</p>
    
    <ul>
    <li>ID of the scheduled message</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>content</p>
    
    <ul>
    <li>Text content of the scheduled message</li>
    <li>Updatable via API</li>
    </ul></li>
    <li><p>rrule</p>
    
    <ul>
    <li>Recurrence rule for recurring scheduled messages, e.g. 'FREQ=MONTHLY' or
      'FREQ=WEEKLY;INTERVAL=2'; see
      <a target="_blank" rel="noopener" href="https://tools.ietf.org/html/rfc2445#section-4.3.10">RFC2445</a>.</li>
    <li>Updatable via API</li>
    </ul></li>
    <li><p>timezone_id</p>
    
    <ul>
    <li>Timezone ID used to compute times for recurring messages; see <a target="_blank" rel="noopener" href="http://en.wikipedia.org/wiki/List_of_tz_database_time_zones">List of tz database
      time zones Wikipedia
      article</a>.</li>
    <li>Updatable via API</li>
    </ul></li>
    <li><p>recipients (array of objects)</p>
    
    <ul>
    <li><p>List of recipients. Each recipient is an object with a string <code>type</code> property, which
      may be <code>"phone_number"</code>, <code>"group"</code>, or <code>"filter"</code>.</p>
    
    <p>If the type is <code>"phone_number"</code>, the <code>phone_number</code> property will
      be set to the recipient's phone number.</p>
    
    <p>If the type is <code>"group"</code>, the <code>group_id</code> property will be set to
      the ID of the group, and the <code>group_name</code> property will be set to the name of the
      group.</p>
    
    <p>If the type is <code>"filter"</code>, the <code>filter_type</code> property (string) and
      <code>filter_params</code> property (object) describe the filter used to send the broadcast. (API
      clients should not rely on a particular value or format of the <code>filter_type</code> or
      <code>filter_params</code> properties, as they may change without notice.)</p></li>
    <li>Read-only</li>
    </ul></li>
    <li><p>recipients_str</p>
    
    <ul>
    <li>A string with a human readable description of the first few recipients (possibly
      truncated)</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>group_id</p>
    
    <ul>
    <li>ID of the group to send the message to (null if the recipient is an individual
      contact, or if there are multiple recipients)</li>
    <li>Updatable via API</li>
    </ul></li>
    <li><p>contact_id</p>
    
    <ul>
    <li>ID of the contact to send the message to (null if the recipient is a group, or if
      there are multiple recipients)</li>
    <li>Updatable via API</li>
    </ul></li>
    <li><p>to_number</p>
    
    <ul>
    <li>Phone number to send the message to (null if the recipient is a group, or if there
      are multiple recipients)</li>
    <li>Updatable via API</li>
    </ul></li>
    <li><p>route_id</p>
    
    <ul>
    <li>ID of the phone or route the message will be sent from</li>
    <li>Updatable via API</li>
    </ul></li>
    <li><p>service_id (string, max 34 characters)</p>
    
    <ul>
    <li>The service associated with this message (for voice calls, the service defines the
      call flow)</li>
    <li>Updatable via API</li>
    </ul></li>
    <li><p>audio_url</p>
    
    <ul>
    <li>For voice calls, the URL of an MP3 file to play when the contact answers the call</li>
    <li>Updatable via API</li>
    </ul></li>
    <li><p>tts_lang</p>
    
    <ul>
    <li>For voice calls, the language of the text-to-speech voice</li>
    <li>Allowed values: en-US, en-GB, en-GB-WLS, en-AU, en-IN, da-DK, nl-NL, fr-FR, fr-CA,
      de-DE, is-IS, it-IT, pl-PL, pt-BR, pt-PT, ru-RU, es-ES, es-US, sv-SE</li>
    <li>Updatable via API</li>
    </ul></li>
    <li><p>tts_voice</p>
    
    <ul>
    <li>For voice calls, the text-to-speech voice</li>
    <li>Allowed values: female, male</li>
    <li>Updatable via API</li>
    </ul></li>
    <li><p>message_type</p>
    
    <ul>
    <li>Type of scheduled message</li>
    <li>Allowed values: sms, mms, ussd, ussd_session, call, chat, service</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>time_created (UNIX timestamp)</p>
    
    <ul>
    <li>Time the scheduled message was created in Telerivet</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>start_time (UNIX timestamp)</p>
    
    <ul>
    <li>The time that the message will be sent (or first sent for recurring messages)</li>
    <li>Updatable via API</li>
    </ul></li>
    <li><p>end_time (UNIX timestamp)</p>
    
    <ul>
    <li>Time after which a recurring message will stop (not applicable to non-recurring
      scheduled messages)</li>
    <li>Updatable via API</li>
    </ul></li>
    <li><p>prev_time (UNIX timestamp)</p>
    
    <ul>
    <li>The most recent time that Telerivet has sent this scheduled message (null if it has
      never been sent)</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>next_time (UNIX timestamp)</p>
    
    <ul>
    <li>The next upcoming time that Telerivet will sent this scheduled message (null if it
      will not be sent again)</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>occurrences (int)</p>
    
    <ul>
    <li>Number of times this scheduled message has already been sent</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>replace_variables (bool)</p>
    
    <ul>
    <li>Set to true if Telerivet will render variables like [[contact.name]] in the message
      content, false otherwise</li>
    <li>Updatable via API</li>
    </ul></li>
    <li><p>track_clicks (boolean)</p>
    
    <ul>
    <li>If true, URLs in the message content will automatically be replaced with unique
      short URLs</li>
    <li>Updatable via API</li>
    </ul></li>
    <li><p>media (array)</p>
    
    <ul>
    <li>For text messages containing media files, this is an array of objects with the
      properties <code>url</code>, <code>type</code> (MIME type), <code>filename</code>, and <code>size</code> (file size in bytes).
      Unknown properties are null. This property is undefined for messages that do not
      contain media files. Note: For files uploaded via the Telerivet web app, the URL is
      temporary and may not be valid for more than 1 day.</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>route_params (JSONObject)</p>
    
    <ul>
    <li><p>Route-specific parameters to use when sending the message.</p>
    
    <p>When sending messages via chat apps such as WhatsApp, the route_params
      parameter can be used to send messages with app-specific features such as quick
      replies and link buttons.</p>
    
    <p>For more details, see <a href="#route_params">Route-Specific Parameters</a>.</p></li>
    <li>Updatable via API</li>
    </ul></li>
    <li><p>vars (JSONObject)</p>
    
    <ul>
    <li>Custom variables stored for this scheduled message (copied to Message when sent).
      Variable names may be up to 32 characters in length and can contain the characters
      a-z, A-Z, 0-9, and _.
      Values may be strings, numbers, or boolean (true/false).
      String values may be up to 4096 bytes in length when encoded as UTF-8.
      Up to 100 variables are supported per object.
      Setting a variable to null will delete the variable.</li>
    <li>Updatable via API</li>
    </ul></li>
    <li><p>label_ids (array)</p>
    
    <ul>
    <li>IDs of labels to add to the Message</li>
    <li>Updatable via API</li>
    </ul></li>
    <li><p>relative_scheduled_id</p>
    
    <ul>
    <li>ID of the relative scheduled message this scheduled message was created from, if
      applicable</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>project_id</p>
    
    <ul>
    <li>ID of the project this scheduled message belongs to</li>
    <li>Read-only</li>
    </ul></li>
    </ul>
    </div>
*/
public class ScheduledMessage extends Entity
{
    /**
        <div class='markdown'><p>Saves any fields or custom variables that have changed for this scheduled message.</p>
        </div>
    */
    @Override
    public void save() throws IOException
    {
        super.save();
    }

    /**
        <div class='markdown'><p>Cancels this scheduled message.</p>
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

    public String getContent()
    {
        return (String) get("content");
    }

    public void setContent(String value)
    {
        set("content", value);
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

    public JSONArray getRecipients()
    {
        return (JSONArray) get("recipients");
    }

    public String getRecipientsStr()
    {
        return (String) get("recipients_str");
    }

    public String getGroupId()
    {
        return (String) get("group_id");
    }

    public void setGroupId(String value)
    {
        set("group_id", value);
    }

    public String getContactId()
    {
        return (String) get("contact_id");
    }

    public void setContactId(String value)
    {
        set("contact_id", value);
    }

    public String getToNumber()
    {
        return (String) get("to_number");
    }

    public void setToNumber(String value)
    {
        set("to_number", value);
    }

    public String getRouteId()
    {
        return (String) get("route_id");
    }

    public void setRouteId(String value)
    {
        set("route_id", value);
    }

    public String getServiceId()
    {
        return (String) get("service_id");
    }

    public void setServiceId(String value)
    {
        set("service_id", value);
    }

    public String getAudioUrl()
    {
        return (String) get("audio_url");
    }

    public void setAudioUrl(String value)
    {
        set("audio_url", value);
    }

    public String getTtsLang()
    {
        return (String) get("tts_lang");
    }

    public void setTtsLang(String value)
    {
        set("tts_lang", value);
    }

    public String getTtsVoice()
    {
        return (String) get("tts_voice");
    }

    public void setTtsVoice(String value)
    {
        set("tts_voice", value);
    }

    public String getMessageType()
    {
        return (String) get("message_type");
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

    public Boolean getReplaceVariables()
    {
        return (Boolean) get("replace_variables");
    }

    public void setReplaceVariables(Boolean value)
    {
        set("replace_variables", value);
    }

    public String getTrackClicks()
    {
        return (String) get("track_clicks");
    }

    public void setTrackClicks(String value)
    {
        set("track_clicks", value);
    }

    public JSONArray getMedia()
    {
        return (JSONArray) get("media");
    }

    public JSONObject getRouteParams()
    {
        return (JSONObject) get("route_params");
    }

    public void setRouteParams(JSONObject value)
    {
        set("route_params", value);
    }

    public JSONArray getLabelIds()
    {
        return (JSONArray) get("label_ids");
    }

    public void setLabelIds(JSONArray value)
    {
        set("label_ids", value);
    }

    public String getRelativeScheduledId()
    {
        return (String) get("relative_scheduled_id");
    }

    public String getProjectId()
    {
        return (String) get("project_id");
    }

    @Override
    public String getBaseApiPath()
    {
        return "/projects/" + getProjectId() + "/scheduled/" + getId() + "";
    }

    public ScheduledMessage(TelerivetAPI api, JSONObject data)
    {
        this(api, data, true);
    }

    public ScheduledMessage(TelerivetAPI api, JSONObject data, boolean isLoaded)
    {
        super(api, data, isLoaded);
    }
}
