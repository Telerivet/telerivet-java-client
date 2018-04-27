
package com.telerivet;

import java.io.IOException;
import org.json.JSONObject;
import org.json.JSONArray;

/**
    <p>Represents a scheduled message within Telerivet.</p>
    
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
    <li>Read-only</li>
    </ul></li>
    <li><p>rrule</p>
    
    <ul>
    <li>Recurrence rule for recurring scheduled messages, e.g. 'FREQ=MONTHLY' or
      'FREQ=WEEKLY;INTERVAL=2'; see <a href="https://tools.ietf.org/html/rfc2445#section-4.3.10">https://tools.ietf.org/html/rfc2445#section-4.3.10</a></li>
    <li>Read-only</li>
    </ul></li>
    <li><p>timezone_id</p>
    
    <ul>
    <li>Timezone ID used to compute times for recurring messages; see
      <a href="http://en.wikipedia.org/wiki/List_of_tz_database_time_zones">http://en.wikipedia.org/wiki/List_of_tz_database_time_zones</a></li>
    <li>Read-only</li>
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
    <li>Read-only</li>
    </ul></li>
    <li><p>contact_id</p>
    
    <ul>
    <li>ID of the contact to send the message to (null if the recipient is a group, or if
      there are multiple recipients)</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>to_number</p>
    
    <ul>
    <li>Phone number to send the message to (null if the recipient is a group, or if there
      are multiple recipients)</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>route_id</p>
    
    <ul>
    <li>ID of the phone or route the message will be sent from</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>service_id (string, max 34 characters)</p>
    
    <ul>
    <li>The service associated with this message (for voice calls, the service defines the
      call flow)</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>audio_url</p>
    
    <ul>
    <li>For voice calls, the URL of an MP3 file to play when the contact answers the call</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>tts_lang</p>
    
    <ul>
    <li>For voice calls, the language of the text-to-speech voice</li>
    <li>Allowed values: en-US, en-GB, en-GB-WLS, en-AU, en-IN, da-DK, nl-NL, fr-FR, fr-CA,
      de-DE, is-IS, it-IT, pl-PL, pt-BR, pt-PT, ru-RU, es-ES, es-US, sv-SE</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>tts_voice</p>
    
    <ul>
    <li>For voice calls, the text-to-speech voice</li>
    <li>Allowed values: female, male</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>message_type</p>
    
    <ul>
    <li>Type of scheduled message</li>
    <li>Allowed values: sms, ussd, call</li>
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
    <li>Read-only</li>
    </ul></li>
    <li><p>end_time (UNIX timestamp)</p>
    
    <ul>
    <li>Time after which a recurring message will stop (not applicable to non-recurring
      scheduled messages)</li>
    <li>Read-only</li>
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
    <li><p>is_template (bool)</p>
    
    <ul>
    <li>Set to true if Telerivet will render variables like [[contact.name]] in the message
      content, false otherwise</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>vars (JSONObject)</p>
    
    <ul>
    <li>Custom variables stored for this scheduled message (copied to Message when sent)</li>
    <li>Updatable via API</li>
    </ul></li>
    <li><p>label_ids (array)</p>
    
    <ul>
    <li>IDs of labels to add to the Message</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>project_id</p>
    
    <ul>
    <li>ID of the project this scheduled message belongs to</li>
    <li>Read-only</li>
    </ul></li>
    </ul>
*/
public class ScheduledMessage extends Entity
{
    /**
        <p>Saves any fields or custom variables that have changed for this scheduled message.</p>
    */
    @Override
    public void save() throws IOException
    {
        super.save();
    }

    /**
        <p>Cancels this scheduled message.</p>
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

    public String getRrule()
    {
        return (String) get("rrule");
    }

    public String getTimezoneId()
    {
        return (String) get("timezone_id");
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

    public String getContactId()
    {
        return (String) get("contact_id");
    }

    public String getToNumber()
    {
        return (String) get("to_number");
    }

    public String getRouteId()
    {
        return (String) get("route_id");
    }

    public String getServiceId()
    {
        return (String) get("service_id");
    }

    public String getAudioUrl()
    {
        return (String) get("audio_url");
    }

    public String getTtsLang()
    {
        return (String) get("tts_lang");
    }

    public String getTtsVoice()
    {
        return (String) get("tts_voice");
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

    public Long getEndTime()
    {
        return Util.toLong(get("end_time"));
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

    public Boolean getIsTemplate()
    {
        return (Boolean) get("is_template");
    }

    public JSONArray getLabelIds()
    {
        return (JSONArray) get("label_ids");
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
