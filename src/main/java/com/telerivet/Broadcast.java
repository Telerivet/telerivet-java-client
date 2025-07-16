
package com.telerivet;

import java.io.IOException;
import org.json.JSONObject;
import org.json.JSONArray;

/**
    <div class='markdown'><p>Represents a collection of related outgoing messages.
    Typically, messages in a broadcast have the same content template and were
    sent at the same time; however, a broadcast can also contain messages with unrelated content
    and messages that were sent at different times.
    A broadcast is automatically created when sending a message to a group of
    contacts.</p>
    
    <p>Fields:</p>
    
    <ul>
    <li><p>id (string, max 34 characters)</p>
    
    <ul>
    <li>ID of the broadcast</li>
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
    <li><p>title</p>
    
    <ul>
    <li>Title of the broadcast. If a title was not provided when the broadcast was sent, it
      is automatically set to a human readable description of the first few recipients
      (possibly truncated)</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>time_created (UNIX timestamp)</p>
    
    <ul>
    <li>Time the broadcast was sent in Telerivet</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>last_message_time (UNIX timestamp)</p>
    
    <ul>
    <li>Time the most recent message was queued to send in this broadcast</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>last_send_time (UNIX timestamp)</p>
    
    <ul>
    <li>Time the most recent message was sent (or failed to send) in this broadcast, or null
      if no messages have been sent yet</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>status_counts (JSONObject)</p>
    
    <ul>
    <li>An object whose keys are the possible status codes (<code>"queued"</code>, <code>"sent"</code>,
      <code>"failed"</code>, <code>"failed_queued"</code>, <code>"delivered"</code>, <code>"not_delivered"</code>, and <code>"cancelled"</code>),
      and whose values are the number of messages in the broadcast currently in that status.</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>message_count (int)</p>
    
    <ul>
    <li>The total number of messages created for this broadcast. For large broadcasts, the
      messages may not be created immediately after the broadcast is sent. The
      <code>message_count</code> includes messages in any status, including messages that are still
      queued.</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>estimated_count (int)</p>
    
    <ul>
    <li>The estimated number of messages in this broadcast when it is complete. The
      <code>estimated_count</code> is calculated at the time the broadcast is sent. When the broadcast
      is completed, the <code>estimated_count</code> may differ from <code>message_count</code> if there are any
      blocked contacts among the recipients (blocked contacts are included in
      <code>estimated_count</code> but not in <code>message_count</code>), if any contacts don't have phone
      numbers, or if the group membership changed while the broadcast was being sent.</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>message_type</p>
    
    <ul>
    <li>Type of message sent from this broadcast</li>
    <li>Allowed values: sms, mms, ussd, ussd_session, call, chat, service</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>content (string)</p>
    
    <ul>
    <li>The text content of the message (null for USSD messages and calls)</li>
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
    <li><p>replace_variables (bool)</p>
    
    <ul>
    <li>Set to true if Telerivet will render variables like [[contact.name]] in the message
      content, false otherwise</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>status</p>
    
    <ul>
    <li>The current status of the broadcast.</li>
    <li>Allowed values: queuing, sending, complete, cancelled</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>source</p>
    
    <ul>
    <li>How the message originated within Telerivet</li>
    <li>Allowed values: phone, provider, web, api, service, webhook, scheduled, integration</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>simulated (bool)</p>
    
    <ul>
    <li>Whether this message was simulated within Telerivet for testing (and not actually
      sent to or received by a real phone)</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>track_clicks (boolean)</p>
    
    <ul>
    <li>If true, URLs in the message content will automatically be replaced with unique
      short URLs.</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>clicked_count (int)</p>
    
    <ul>
    <li>The number of messages in this broadcast containing short links that were clicked.
      At most one click per message is counted. If track_clicks is false, this property will
      be null.</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>label_ids (array)</p>
    
    <ul>
    <li>List of IDs of labels applied to all messages in the broadcast</li>
    <li>Read-only</li>
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
    <li><p>vars (JSONObject)</p>
    
    <ul>
    <li>Custom variables stored for this broadcast. Variable names may be up to 32
      characters in length and can contain the characters a-z, A-Z, 0-9, and _.
      Values may be strings, numbers, or boolean (true/false).
      String values may be up to 4096 bytes in length when encoded as UTF-8.
      Up to 100 variables are supported per object.
      Setting a variable to null will delete the variable.</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>route_params (JSONObject)</p>
    
    <ul>
    <li><p>Route-specific parameters for the messages in the broadcast.</p>
    
    <p>When sending messages via chat apps such as WhatsApp, the route_params
      parameter can be used to send messages with app-specific features such as quick
      replies and link buttons.</p>
    
    <p>For more details, see <a href="#route_params">Route-Specific Parameters</a>.</p></li>
    <li>Read-only</li>
    </ul></li>
    <li><p>price (number)</p>
    
    <ul>
    <li>The total price of all messages in this broadcast, if known.</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>price_currency</p>
    
    <ul>
    <li>The currency of the message price, if applicable.</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>reply_count (int)</p>
    
    <ul>
    <li>The number of replies received in response to a message sent in this broadcast.
      (Replies are not included in <code>message_count</code> ,<code>status_counts</code>, or <code>price</code>.)</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>last_reply_time (UNIX timestamp)</p>
    
    <ul>
    <li>Time the most recent reply was received in response to a message sent in this
      broadcast, or null if no replies have been sent yet</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>route_id (string, max 34 characters)</p>
    
    <ul>
    <li>ID of the phone or route used to send the broadcast (if applicable)</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>service_id (string, max 34 characters)</p>
    
    <ul>
    <li>The service associated with this broadcast (for voice calls, the service defines the
      call flow)</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>user_id (string, max 34 characters)</p>
    
    <ul>
    <li>ID of the Telerivet user who sent the broadcast (if applicable)</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>project_id</p>
    
    <ul>
    <li>ID of the project this broadcast belongs to</li>
    <li>Read-only</li>
    </ul></li>
    </ul>
    </div>
*/
public class Broadcast extends Entity
{
    /**
        <div class='markdown'><p>Cancels sending a broadcast that has not yet been completely sent. No additional messages
        will be queued, and any existing queued messages will be cancelled when they would otherwise
        have been sent (except for messages already queued on the Telerivet Android app, which will
        not be automatically cancelled).</p>
        </div>
    */
    public Broadcast cancel() throws IOException
    {
        return new Broadcast(api, (JSONObject) api.doRequest("POST", getBaseApiPath() + "/cancel"));
    }

    public String getId()
    {
        return (String) get("id");
    }

    public JSONArray getRecipients()
    {
        return (JSONArray) get("recipients");
    }

    public String getTitle()
    {
        return (String) get("title");
    }

    public Long getTimeCreated()
    {
        return Util.toLong(get("time_created"));
    }

    public Long getLastMessageTime()
    {
        return Util.toLong(get("last_message_time"));
    }

    public Long getLastSendTime()
    {
        return Util.toLong(get("last_send_time"));
    }

    public JSONObject getStatusCounts()
    {
        return (JSONObject) get("status_counts");
    }

    public Integer getMessageCount()
    {
        return (Integer) get("message_count");
    }

    public Integer getEstimatedCount()
    {
        return (Integer) get("estimated_count");
    }

    public String getMessageType()
    {
        return (String) get("message_type");
    }

    public String getContent()
    {
        return (String) get("content");
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

    public Boolean getReplaceVariables()
    {
        return (Boolean) get("replace_variables");
    }

    public String getStatus()
    {
        return (String) get("status");
    }

    public String getSource()
    {
        return (String) get("source");
    }

    public Boolean getSimulated()
    {
        return (Boolean) get("simulated");
    }

    public String getTrackClicks()
    {
        return (String) get("track_clicks");
    }

    public Integer getClickedCount()
    {
        return (Integer) get("clicked_count");
    }

    public JSONArray getLabelIds()
    {
        return (JSONArray) get("label_ids");
    }

    public JSONArray getMedia()
    {
        return (JSONArray) get("media");
    }

    public JSONObject getRouteParams()
    {
        return (JSONObject) get("route_params");
    }

    public Double getPrice()
    {
        return Util.toDouble(get("price"));
    }

    public String getPriceCurrency()
    {
        return (String) get("price_currency");
    }

    public Integer getReplyCount()
    {
        return (Integer) get("reply_count");
    }

    public Long getLastReplyTime()
    {
        return Util.toLong(get("last_reply_time"));
    }

    public String getRouteId()
    {
        return (String) get("route_id");
    }

    public String getServiceId()
    {
        return (String) get("service_id");
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
        return "/projects/" + getProjectId() + "/broadcasts/" + getId() + "";
    }

    public Broadcast(TelerivetAPI api, JSONObject data)
    {
        this(api, data, true);
    }

    public Broadcast(TelerivetAPI api, JSONObject data, boolean isLoaded)
    {
        super(api, data, isLoaded);
    }
}
