
package com.telerivet;

import java.io.IOException;
import org.json.JSONObject;
import org.json.JSONArray;

/**
    <p>A relative scheduled message is a message that is scheduled relative to a date stored as a
    custom field for each recipient contact.
    This allows scheduling messages on a different date for each contact, for
    example on their birthday, a certain number of days before an appointment, or a certain
    number of days after enrolling in a campaign.</p>
    
    <p>Telerivet will automatically create a <a href="#ScheduledMessage">ScheduledMessage</a>
    for each contact matching a RelativeScheduledMessage.</p>
    
    <p>Any service that can be manually triggered for a contact (including polls)
    may also be scheduled via a relative scheduled message, whether or not the service actually
    sends a message.</p>
    
    <p>Fields:</p>
    
    <ul>
    <li><p>id (string, max 34 characters)</p>
    
    <ul>
    <li>ID of the relative scheduled message</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>content</p>
    
    <ul>
    <li>Text content of the relative scheduled message</li>
    <li>Updatable via API</li>
    </ul></li>
    <li><p>time_of_day</p>
    
    <ul>
    <li>Time of day when scheduled messages will be sent in HH:MM format (with hours from 00
      to 23)</li>
    <li>Updatable via API</li>
    </ul></li>
    <li><p>date_variable</p>
    
    <ul>
    <li>Custom contact variable storing date or date/time values relative to which messages
      will be scheduled.</li>
    <li>Updatable via API</li>
    </ul></li>
    <li><p>offset_scale</p>
    
    <ul>
    <li>The type of interval (day/week/month/year) that will be used to adjust the scheduled
      date relative to the date stored in the contact's date_variable, when offset_count is
      non-zero (D=day, W=week, M=month, Y=year)</li>
    <li>Allowed values: D, W, M, Y</li>
    <li>Updatable via API</li>
    </ul></li>
    <li><p>offset_count (int)</p>
    
    <ul>
    <li>The number of days/weeks/months/years to adjust the date of the scheduled message
      relative relative to the date stored in the contact's date_variable. May be positive,
      negative, or zero.</li>
    <li>Updatable via API</li>
    </ul></li>
    <li><p>rrule</p>
    
    <ul>
    <li>Recurrence rule for recurring scheduled messages, e.g. 'FREQ=MONTHLY' or
      'FREQ=WEEKLY;INTERVAL=2'; see
      <a target="_blank" rel="noopener" href="https://tools.ietf.org/html/rfc2445#section-4.3.10">RFC2445</a>.</li>
    <li>Updatable via API</li>
    </ul></li>
    <li><p>end_time (UNIX timestamp)</p>
    
    <ul>
    <li>Time after which recurring messages will stop (not applicable to non-recurring
      scheduled messages)</li>
    <li>Updatable via API</li>
    </ul></li>
    <li><p>timezone_id</p>
    
    <ul>
    <li>Timezone ID used to compute times for recurring messages; see <a target="_blank" rel="noopener" href="http://en.wikipedia.org/wiki/List_of_tz_database_time_zones">List of tz database
      time zones Wikipedia
      article</a>.</li>
    <li>Updatable via API</li>
    </ul></li>
    <li><p>recipients_str</p>
    
    <ul>
    <li>A string with a human readable description of the recipient</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>group_id</p>
    
    <ul>
    <li>ID of the group to send the message to (null if the recipient is an individual
      contact)</li>
    <li>Updatable via API</li>
    </ul></li>
    <li><p>contact_id</p>
    
    <ul>
    <li>ID of the contact to send the message to (null if the recipient is a group)</li>
    <li>Updatable via API</li>
    </ul></li>
    <li><p>to_number</p>
    
    <ul>
    <li>Phone number to send the message to (null if the recipient is a group)</li>
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
    <li>Time the relative scheduled message was created in Telerivet</li>
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
    <li>Route-specific parameters to use when sending the message. The parameters object may
      have keys matching the <code>phone_type</code> field of a phone (basic route) that may be used to
      send the message. The corresponding value is an object with route-specific parameters
      to use when sending a message with that type of route.</li>
    <li>Updatable via API</li>
    </ul></li>
    <li><p>vars (JSONObject)</p>
    
    <ul>
    <li>Custom variables stored for this scheduled message (copied to each ScheduledMessage
      and Message when sent)</li>
    <li>Updatable via API</li>
    </ul></li>
    <li><p>label_ids (array)</p>
    
    <ul>
    <li>IDs of labels to add to the Message</li>
    <li>Updatable via API</li>
    </ul></li>
    <li><p>project_id</p>
    
    <ul>
    <li>ID of the project this relative scheduled message belongs to</li>
    <li>Read-only</li>
    </ul></li>
    </ul>
*/
public class RelativeScheduledMessage extends Entity
{
    /**
        <p>Saves any fields or custom variables that have changed for this relative scheduled message.</p>
    */
    @Override
    public void save() throws IOException
    {
        super.save();
    }

    /**
        <p>Deletes this relative scheduled message and any associated scheduled messages.</p>
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

    public String getTimeOfDay()
    {
        return (String) get("time_of_day");
    }

    public void setTimeOfDay(String value)
    {
        set("time_of_day", value);
    }

    public String getDateVariable()
    {
        return (String) get("date_variable");
    }

    public void setDateVariable(String value)
    {
        set("date_variable", value);
    }

    public String getOffsetScale()
    {
        return (String) get("offset_scale");
    }

    public void setOffsetScale(String value)
    {
        set("offset_scale", value);
    }

    public Integer getOffsetCount()
    {
        return (Integer) get("offset_count");
    }

    public void setOffsetCount(Integer value)
    {
        set("offset_count", value);
    }

    public String getRrule()
    {
        return (String) get("rrule");
    }

    public void setRrule(String value)
    {
        set("rrule", value);
    }

    public Long getEndTime()
    {
        return Util.toLong(get("end_time"));
    }

    public void setEndTime(Long value)
    {
        set("end_time", value);
    }

    public String getTimezoneId()
    {
        return (String) get("timezone_id");
    }

    public void setTimezoneId(String value)
    {
        set("timezone_id", value);
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

    public String getProjectId()
    {
        return (String) get("project_id");
    }

    @Override
    public String getBaseApiPath()
    {
        return "/projects/" + getProjectId() + "/relative_scheduled/" + getId() + "";
    }

    public RelativeScheduledMessage(TelerivetAPI api, JSONObject data)
    {
        this(api, data, true);
    }

    public RelativeScheduledMessage(TelerivetAPI api, JSONObject data, boolean isLoaded)
    {
        super(api, data, isLoaded);
    }
}
