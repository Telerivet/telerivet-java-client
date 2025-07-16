package com.telerivet;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.Date;
import org.json.JSONObject;
import org.json.JSONArray;

/**
    <div class='markdown'><p>Represents a single message.</p>
    
    <p>Fields:</p>
    
    <ul>
    <li><p>id (string, max 34 characters)</p>
    
    <ul>
    <li>ID of the message</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>direction</p>
    
    <ul>
    <li>Direction of the message: incoming messages are sent from one of your contacts to
      your phone; outgoing messages are sent from your phone to one of your contacts</li>
    <li>Allowed values: incoming, outgoing</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>status</p>
    
    <ul>
    <li>Current status of the message</li>
    <li>Allowed values: ignored, processing, received, sent, queued, failed, failed_queued,
      cancelled, delivered, not_delivered, read</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>message_type</p>
    
    <ul>
    <li>Type of the message</li>
    <li>Allowed values: sms, mms, ussd, ussd_session, call, chat, service</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>source</p>
    
    <ul>
    <li>How the message originated within Telerivet</li>
    <li>Allowed values: phone, provider, web, api, service, webhook, scheduled, integration</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>time_created (UNIX timestamp)</p>
    
    <ul>
    <li>The time that the message was created on Telerivet's servers</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>time_sent (UNIX timestamp)</p>
    
    <ul>
    <li>The time that the message was reported to have been sent (null for incoming messages
      and messages that have not yet been sent)</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>time_updated (UNIX timestamp)</p>
    
    <ul>
    <li>The time that the message was last updated in Telerivet.</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>from_number (string)</p>
    
    <ul>
    <li>The phone number that the message originated from (your number for outgoing
      messages, the contact's number for incoming messages)</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>to_number (string)</p>
    
    <ul>
    <li>The phone number that the message was sent to (your number for incoming messages,
      the contact's number for outgoing messages)</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>content (string)</p>
    
    <ul>
    <li>The text content of the message (null for USSD messages and calls)</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>starred (bool)</p>
    
    <ul>
    <li>Whether this message is starred in Telerivet</li>
    <li>Updatable via API</li>
    </ul></li>
    <li><p>simulated (bool)</p>
    
    <ul>
    <li>Whether this message was simulated within Telerivet for testing (and not actually
      sent to or received by a real phone)</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>label_ids (array)</p>
    
    <ul>
    <li>List of IDs of labels applied to this message</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>route_params (JSONObject)</p>
    
    <ul>
    <li><p>Route-specific parameters for the message.</p>
    
    <p>When sending messages via chat apps such as WhatsApp, the route_params
      parameter can be used to send messages with app-specific features such as quick
      replies and link buttons.</p>
    
    <p>For more details, see <a href="#route_params">Route-Specific Parameters</a>.</p></li>
    <li>Read-only</li>
    </ul></li>
    <li><p>vars (JSONObject)</p>
    
    <ul>
    <li>Custom variables stored for this message. Variable names may be up to 32 characters
      in length and can contain the characters a-z, A-Z, 0-9, and _.
      Values may be strings, numbers, or boolean (true/false).
      String values may be up to 4096 bytes in length when encoded as UTF-8.
      Up to 100 variables are supported per object.
      Setting a variable to null will delete the variable.</li>
    <li>Updatable via API</li>
    </ul></li>
    <li><p>priority (int)</p>
    
    <ul>
    <li>Priority of this message. Telerivet will attempt to send messages with higher
      priority numbers first. Only defined for outgoing messages.</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>error_message</p>
    
    <ul>
    <li>A description of the error encountered while sending a message. (This field is
      omitted from the API response if there is no error message.)</li>
    <li>Updatable via API</li>
    </ul></li>
    <li><p>error_code</p>
    
    <ul>
    <li>A route-specific error code encountered while sending a message. The error code
      values depend on the provider and may be described in the provider's API
      documentation. Error codes may be strings or numbers, depending on the provider. (This
      field is omitted from the API response if there is no error code.)</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>external_id</p>
    
    <ul>
    <li>The ID of this message from an external SMS gateway provider (e.g. Twilio or
      Vonage), if available.</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>num_parts (number)</p>
    
    <ul>
    <li>The number of SMS parts associated with the message, if applicable and if known.</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>price (number)</p>
    
    <ul>
    <li>The price of this message, if known.</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>price_currency</p>
    
    <ul>
    <li>The currency of the message price, if applicable.</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>duration (number)</p>
    
    <ul>
    <li>The duration of the call in seconds, if known, or -1 if the call was not answered.</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>ring_time (number)</p>
    
    <ul>
    <li>The length of time the call rang in seconds before being answered or hung up, if
      known.</li>
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
    <li><p>track_clicks (boolean)</p>
    
    <ul>
    <li>If true, URLs in the message content are short URLs that redirect to a destination
      URL.</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>short_urls (array)</p>
    
    <ul>
    <li>For text messages containing short URLs, this is an array of objects with the
      properties <code>short_url</code>, <code>link_type</code>, <code>time_clicked</code> (the first time that URL was
      clicked), and <code>expiration_time</code>. If <code>link_type</code> is "redirect", the object also
      contains a <code>destination_url</code> property. If <code>link_type</code> is "media", the object also
      contains an <code>media_index</code> property (the index in the media array). If <code>link_type</code> is
      "service", the object also contains a <code>service_id</code> property. This property is
      undefined for messages that do not contain short URLs.</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>network_code (string)</p>
    
    <ul>
    <li>A string identifying the network that sent or received the message, if known. For
      mobile networks, this string contains the 3-digit mobile country code (MCC) followed
      by the 2- or 3-digit mobile network code (MNC), which results in a 5- or 6-digit
      number. For lists of mobile network operators and their corresponding MCC/MNC values,
      see <a target="_blank" rel="noopener" href="https://en.wikipedia.org/wiki/Mobile_country_code">Mobile country code Wikipedia
      article</a>. The network_code property
      may be non-numeric for messages not sent via mobile networks.</li>
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
    <li><p>mms_parts (array)</p>
    
    <ul>
    <li><p>A list of parts in the MMS message (only for incoming MMS messages received via
      Telerivet Gateway Android app).</p>
    
    <p>Each MMS part in the list is an object with the following
      properties:</p>
    
    <ul>
    <li>cid: MMS content-id</li>
    <li>type: MIME type</li>
    <li>filename: original filename</li>
    <li>size (int): number of bytes</li>
    <li><p>url: URL where the content for this part is stored (secret but
    publicly accessible, so you could link/embed it in a web page without having to
    re-host it yourself)</p>
    
    <p>In general, the <code>media</code> property of the message is recommended for
    retrieving information about MMS media files, instead of <code>mms_parts</code>.
    The <code>mms_parts</code> property is also only present when retrieving an
    individual MMS message by ID, not when querying a list of messages.</p></li>
    </ul></li>
    <li>Read-only</li>
    </ul></li>
    <li><p>time_clicked (UNIX timestamp)</p>
    
    <ul>
    <li>If the message contains any short URLs, this is the first time that a short URL in
      the message was clicked.  This property is undefined for messages that do not contain
      short URLs.</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>service_id (string, max 34 characters)</p>
    
    <ul>
    <li>ID of the service that handled the message (for voice calls, the service defines the
      call flow)</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>phone_id (string, max 34 characters)</p>
    
    <ul>
    <li>ID of the phone (basic route) that sent or received the message</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>contact_id (string, max 34 characters)</p>
    
    <ul>
    <li>ID of the contact that sent or received the message</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>route_id (string, max 34 characters)</p>
    
    <ul>
    <li>ID of the custom route that sent the message (if applicable)</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>broadcast_id (string, max 34 characters)</p>
    
    <ul>
    <li>ID of the broadcast that this message is part of (if applicable)</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>scheduled_id (string, max 34 characters)</p>
    
    <ul>
    <li>ID of the scheduled message that created this message is part of (if applicable)</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>user_id (string, max 34 characters)</p>
    
    <ul>
    <li>ID of the Telerivet user who sent the message (if applicable)</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>project_id</p>
    
    <ul>
    <li>ID of the project this contact belongs to</li>
    <li>Read-only</li>
    </ul></li>
    </ul>
    </div>
 */
public class Message extends Entity 
{    
    /**
        <div class='markdown'><p>Returns true if this message has a particular label, false otherwise.</p>
        </div>
     */
    public boolean hasLabel(Label label) throws IOException
    {
        assertLoaded();
        return labelIdsSet.contains(label.getId());
    }
      
    /**
        <div class='markdown'><p>Adds a label to the given message.</p>
        </div>
     */
    public void addLabel(Label label) throws IOException
    {
        api.doRequest("PUT", label.getBaseApiPath() + "/messages/" + getId());
        labelIdsSet.add(label.getId());
    }
    
    /**
        <div class='markdown'><p>Removes a label from the given message.</p>
        </div>
     */    
    public void removeLabel(Label label) throws IOException
    {    
        api.doRequest("DELETE", label.getBaseApiPath() + "/messages/" + getId());
        labelIdsSet.remove(label.getId());
    }
    
    private Set<String> labelIdsSet;
    
    @Override
    public void setData(JSONObject data)
    {
        super.setData(data);
        
        labelIdsSet = new HashSet<String>();
        
        if (data.has("label_ids"))
        {
            JSONArray labelIds = data.getJSONArray("label_ids");
            int numLabelIds = labelIds.length();
            for (int i = 0; i < numLabelIds; i++)
            {
                labelIdsSet.add(labelIds.getString(i));                
            }
        }
    }

    /**
        <div class='markdown'><p>(Deprecated) Retrieves a list of MMS parts for this message (only for incoming MMS messages
        received via Telerivet Gateway Android app).
        Note: This only works for MMS messages received via the Telerivet
        Gateway Android app.
        In general, the <code>media</code> property of the message is recommended for
        retrieving information about MMS media files.</p>
        
        <p>The return value has the same format as the <code>mms_parts</code> property of
        the Message object.</p>
        </div>
    */
    public JSONArray getMMSParts() throws IOException
    {
        return (JSONArray) api.doRequest("GET", getBaseApiPath() + "/mms_parts");
    }

    /**
        <div class='markdown'><p>Saves any fields that have changed for this message.</p>
        </div>
    */
    @Override
    public void save() throws IOException
    {
        super.save();
    }

    /**
        <div class='markdown'><p>Resends a message, for example if the message failed to send or if it was not delivered. If
        the message was originally in the queued, retrying, failed, or cancelled states, then
        Telerivet will return the same message object. Otherwise, Telerivet will create and return a
        new message object.</p>
        </div>
    */
    public Message resend(JSONObject options) throws IOException
    {
        return new Message(api, (JSONObject) api.doRequest("POST", getBaseApiPath() + "/resend", options));
    }

    /**
        <div class='markdown'><p>Cancels sending a message that has not yet been sent. Returns the updated message object.
        Only valid for outgoing messages that are currently in the queued, retrying, or cancelled
        states. For other messages, the API will return an error with the code 'not_cancellable'.</p>
        </div>
    */
    public Message cancel() throws IOException
    {
        return new Message(api, (JSONObject) api.doRequest("POST", getBaseApiPath() + "/cancel"));
    }

    /**
        <div class='markdown'><p>Deletes this message.</p>
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

    public String getDirection()
    {
        return (String) get("direction");
    }

    public String getStatus()
    {
        return (String) get("status");
    }

    public String getMessageType()
    {
        return (String) get("message_type");
    }

    public String getSource()
    {
        return (String) get("source");
    }

    public Long getTimeCreated()
    {
        return Util.toLong(get("time_created"));
    }

    public Long getTimeSent()
    {
        return Util.toLong(get("time_sent"));
    }

    public Long getTimeUpdated()
    {
        return Util.toLong(get("time_updated"));
    }

    public String getFromNumber()
    {
        return (String) get("from_number");
    }

    public String getToNumber()
    {
        return (String) get("to_number");
    }

    public String getContent()
    {
        return (String) get("content");
    }

    public Boolean getStarred()
    {
        return (Boolean) get("starred");
    }

    public void setStarred(Boolean value)
    {
        set("starred", value);
    }

    public Boolean getSimulated()
    {
        return (Boolean) get("simulated");
    }

    public JSONArray getLabelIds()
    {
        return (JSONArray) get("label_ids");
    }

    public JSONObject getRouteParams()
    {
        return (JSONObject) get("route_params");
    }

    public Integer getPriority()
    {
        return (Integer) get("priority");
    }

    public String getErrorMessage()
    {
        return (String) get("error_message");
    }

    public void setErrorMessage(String value)
    {
        set("error_message", value);
    }

    public String getErrorCode()
    {
        return (String) get("error_code");
    }

    public String getExternalId()
    {
        return (String) get("external_id");
    }

    public Double getNumParts()
    {
        return Util.toDouble(get("num_parts"));
    }

    public Double getPrice()
    {
        return Util.toDouble(get("price"));
    }

    public String getPriceCurrency()
    {
        return (String) get("price_currency");
    }

    public Double getDuration()
    {
        return Util.toDouble(get("duration"));
    }

    public Double getRingTime()
    {
        return Util.toDouble(get("ring_time"));
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

    public String getTrackClicks()
    {
        return (String) get("track_clicks");
    }

    public JSONArray getShortUrls()
    {
        return (JSONArray) get("short_urls");
    }

    public String getNetworkCode()
    {
        return (String) get("network_code");
    }

    public JSONArray getMedia()
    {
        return (JSONArray) get("media");
    }

    public JSONArray getMmsParts()
    {
        return (JSONArray) get("mms_parts");
    }

    public Long getTimeClicked()
    {
        return Util.toLong(get("time_clicked"));
    }

    public String getServiceId()
    {
        return (String) get("service_id");
    }

    public String getPhoneId()
    {
        return (String) get("phone_id");
    }

    public String getContactId()
    {
        return (String) get("contact_id");
    }

    public String getRouteId()
    {
        return (String) get("route_id");
    }

    public String getBroadcastId()
    {
        return (String) get("broadcast_id");
    }

    public String getScheduledId()
    {
        return (String) get("scheduled_id");
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
        return "/projects/" + getProjectId() + "/messages/" + getId() + "";
    }

    public Message(TelerivetAPI api, JSONObject data)
    {
        this(api, data, true);
    }

    public Message(TelerivetAPI api, JSONObject data, boolean isLoaded)
    {
        super(api, data, isLoaded);
    }
}