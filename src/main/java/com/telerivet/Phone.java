
package com.telerivet;

import java.io.IOException;
import org.json.JSONObject;
import org.json.JSONArray;

/**
    <div class='markdown'><p>Represents a basic route (i.e. a phone or gateway) that you use to send/receive messages via
    Telerivet.</p>
    
    <p>Basic Routes were formerly referred to as "Phones" within Telerivet. API
    methods, parameters, and properties related to Basic Routes continue to use the term "Phone"
    to maintain backwards compatibility.</p>
    
    <p>Fields:</p>
    
    <ul>
    <li><p>id (string, max 34 characters)</p>
    
    <ul>
    <li>ID of the phone</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>name</p>
    
    <ul>
    <li>Name of the phone</li>
    <li>Updatable via API</li>
    </ul></li>
    <li><p>phone_number (string)</p>
    
    <ul>
    <li>Phone number or sender ID</li>
    <li>Updatable via API</li>
    </ul></li>
    <li><p>phone_type</p>
    
    <ul>
    <li>Type of this phone/route (e.g. android, twilio, nexmo, etc)</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>country</p>
    
    <ul>
    <li>2-letter country code (ISO 3166-1 alpha-2) where phone is from</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>send_paused (bool)</p>
    
    <ul>
    <li>True if sending messages is currently paused, false if the phone can currently send
      messages</li>
    <li>Updatable via API</li>
    </ul></li>
    <li><p>time_created (UNIX timestamp)</p>
    
    <ul>
    <li>Time the phone was created in Telerivet</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>last_active_time (UNIX timestamp)</p>
    
    <ul>
    <li>Approximate time this phone last connected to Telerivet</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>vars (JSONObject)</p>
    
    <ul>
    <li>Custom variables stored for this phone. Variable names may be up to 32 characters in
      length and can contain the characters a-z, A-Z, 0-9, and _.
      Values may be strings, numbers, or boolean (true/false).
      String values may be up to 4096 bytes in length when encoded as UTF-8.
      Up to 100 variables are supported per object.
      Setting a variable to null will delete the variable.</li>
    <li>Updatable via API</li>
    </ul></li>
    <li><p>project_id</p>
    
    <ul>
    <li>ID of the project this phone belongs to</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>validate_recipient_numbers (bool)</p>
    
    <ul>
    <li>Set to true to check that recipient phone numbers appear to be valid before sending
      messages. Messages to invalid phone numbers will fail without being sent. Only applies
      to phones capable of sending messages.</li>
    <li>Updatable via API</li>
    </ul></li>
    <li><p>allowed_recipient_countries (array of strings)</p>
    
    <ul>
    <li>Array of 2-letter country codes (ISO 3166-1 alpha-2) to which this phone is allowed
      to send messages. When null, there is no restriction on recipient countries.</li>
    <li>Updatable via API</li>
    </ul></li>
    <li><p>send_delay (number)</p>
    
    <ul>
    <li>Number of seconds to wait after sending each message from this phone, to avoid
      exceeding carrier rate limits.</li>
    <li>Updatable via API</li>
    </ul></li>
    <li><p>timezone_id (string)</p>
    
    <ul>
    <li>A string specifying the time zone that will be used when implementing quiet hours or
      estimating send times; see <a target="_blank" rel="noopener" href="http://en.wikipedia.org/wiki/List_of_tz_database_time_zones">List of tz database time zones Wikipedia
      article</a>. When null, the
      project's default time zone is used.</li>
    <li>Updatable via API</li>
    </ul></li>
    <li><p>quiet_mode (string)</p>
    
    <ul>
    <li>Controls behavior during quiet hours (see quiet_start/quiet_end). Possible values
      are 'off' (quiet hours disabled), 'delay' (messages are delayed until quiet hours
      end), or 'confirm' (requires manual confirmation to send during quiet hours).</li>
    <li>Allowed values: off, delay, confirm</li>
    <li>Updatable via API</li>
    </ul></li>
    <li><p>quiet_start (string)</p>
    
    <ul>
    <li>Time of day when quiet hours begin, in HH:MM format (24-hour time) in the phone's
      time zone. Messages sent during quiet hours are delayed or require confirmation
      depending on quiet_mode. Only applicable when quiet_mode is not 'off'.</li>
    <li>Updatable via API</li>
    </ul></li>
    <li><p>quiet_end (string)</p>
    
    <ul>
    <li>Time of day when quiet hours end, in HH:MM format (24-hour time) in the phone's time
      zone. Messages sent during quiet hours are delayed or require confirmation depending
      on quiet_mode. Only applicable when quiet_mode is not 'off'.</li>
    <li>Updatable via API</li>
    </ul></li>
    <li><p>battery (int)</p>
    
    <ul>
    <li>Current battery level, on a scale from 0 to 100, as of the last time the phone
      connected to Telerivet (only present for Android phones)</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>charging (bool)</p>
    
    <ul>
    <li>True if the phone is currently charging, false if it is running on battery, as of
      the last time it connected to Telerivet (only present for Android phones)</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>internet_type</p>
    
    <ul>
    <li>String describing the current type of internet connectivity for an Android phone,
      for example WIFI or MOBILE (only present for Android phones)</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>app_version</p>
    
    <ul>
    <li>Currently installed version of Telerivet Android app (only present for Android
      phones)</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>android_sdk (int)</p>
    
    <ul>
    <li>Android SDK level, indicating the approximate version of the Android OS installed on
      this phone; see <a target="_blank" rel="noopener" href="http://developer.android.com/guide/topics/manifest/uses-sdk-element.html#ApiLevels">list of Android SDK
      levels</a>
      (only present for Android phones)</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>mccmnc</p>
    
    <ul>
    <li>Code indicating the Android phone's current country (MCC) and mobile network
      operator (MNC); see <a target="_blank" rel="noopener" href="https://en.wikipedia.org/wiki/Mobile_country_code">Mobile country code Wikipedia
      article</a> (only present for Android
      phones). Note this is a string containing numeric digits, not an integer.</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>manufacturer</p>
    
    <ul>
    <li>Android phone manufacturer (only present for Android phones)</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>model</p>
    
    <ul>
    <li>Android phone model (only present for Android phones)</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>send_limit (int)</p>
    
    <ul>
    <li>Maximum number of SMS messages per hour that can be sent by this Android phone. To
      increase this limit, install additional SMS expansion packs in the Telerivet Gateway
      app. (only present for Android phones)</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>external_account_id (string)</p>
    
    <ul>
    <li>Account ID for the external API (e.g. Twilio Account SID, Vonage API Key). Only
      present for routes that support external API configuration. See
      <a href="#Project.createPhone">createPhone</a> for the meaning of this field for each phone type.</li>
    <li>Updatable via API</li>
    </ul></li>
    <li><p>external_id (string)</p>
    
    <ul>
    <li>External phone/resource ID within the external API account (if applicable). Only
      present for routes that support external API configuration.</li>
    <li>Updatable via API</li>
    </ul></li>
    <li><p>external_config (JSONObject)</p>
    
    <ul>
    <li>Additional configuration settings specific to the phone_type. Only present for
      routes that support external API configuration. See
      <a href="#Project.createPhone">createPhone</a> for available settings for each phone type.</li>
    <li>Updatable via API</li>
    </ul></li>
    <li><p>external_setup (JSONObject)</p>
    
    <ul>
    <li>Configuration info for routes that require manual webhook setup. Only present for
      routes that require manual configuration of callback URLs with the external provider.
      Contains URL properties for each webhook type that needs to be configured (e.g.,
      <code>incoming_message_url</code>, <code>message_status_url</code>, <code>incoming_call_url</code>).</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>external_secret (string)</p>
    
    <ul>
    <li>Secret/API key for the external API (e.g. Twilio Auth Token, Vonage API Secret).
      Only applicable for routes that support external API configuration. Write-only; not
      returned by the API when retrieving a phone.</li>
    <li>Updatable via API</li>
    </ul></li>
    </ul>
    </div>
*/
public class Phone extends Entity
{
    /**
        <div class='markdown'><p>Queries messages sent or received by this basic route.</p>
        </div>
    */
    public APICursor<Message> queryMessages(JSONObject options)
    {
        return api.newCursor(Message.class, getBaseApiPath() + "/messages", options);
    }

    public APICursor<Message> queryMessages()
    {
        return queryMessages(null);
    }

    /**
        <div class='markdown'><p>Saves any fields or custom variables that have changed for this basic route.</p>
        </div>
    */
    @Override
    public void save() throws IOException
    {
        super.save();
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

    public String getPhoneNumber()
    {
        return (String) get("phone_number");
    }

    public void setPhoneNumber(String value)
    {
        set("phone_number", value);
    }

    public String getPhoneType()
    {
        return (String) get("phone_type");
    }

    public String getCountry()
    {
        return (String) get("country");
    }

    public Boolean getSendPaused()
    {
        return (Boolean) get("send_paused");
    }

    public void setSendPaused(Boolean value)
    {
        set("send_paused", value);
    }

    public Long getTimeCreated()
    {
        return Util.toLong(get("time_created"));
    }

    public Long getLastActiveTime()
    {
        return Util.toLong(get("last_active_time"));
    }

    public String getProjectId()
    {
        return (String) get("project_id");
    }

    public Boolean getValidateRecipientNumbers()
    {
        return (Boolean) get("validate_recipient_numbers");
    }

    public void setValidateRecipientNumbers(Boolean value)
    {
        set("validate_recipient_numbers", value);
    }

    public JSONArray getAllowedRecipientCountries()
    {
        return (JSONArray) get("allowed_recipient_countries");
    }

    public void setAllowedRecipientCountries(JSONArray value)
    {
        set("allowed_recipient_countries", value);
    }

    public Double getSendDelay()
    {
        return Util.toDouble(get("send_delay"));
    }

    public void setSendDelay(Double value)
    {
        set("send_delay", value);
    }

    public String getTimezoneId()
    {
        return (String) get("timezone_id");
    }

    public void setTimezoneId(String value)
    {
        set("timezone_id", value);
    }

    public String getQuietMode()
    {
        return (String) get("quiet_mode");
    }

    public void setQuietMode(String value)
    {
        set("quiet_mode", value);
    }

    public String getQuietStart()
    {
        return (String) get("quiet_start");
    }

    public void setQuietStart(String value)
    {
        set("quiet_start", value);
    }

    public String getQuietEnd()
    {
        return (String) get("quiet_end");
    }

    public void setQuietEnd(String value)
    {
        set("quiet_end", value);
    }

    public Integer getBattery()
    {
        return (Integer) get("battery");
    }

    public Boolean getCharging()
    {
        return (Boolean) get("charging");
    }

    public String getInternetType()
    {
        return (String) get("internet_type");
    }

    public String getAppVersion()
    {
        return (String) get("app_version");
    }

    public Integer getAndroidSdk()
    {
        return (Integer) get("android_sdk");
    }

    public String getMccmnc()
    {
        return (String) get("mccmnc");
    }

    public String getManufacturer()
    {
        return (String) get("manufacturer");
    }

    public String getModel()
    {
        return (String) get("model");
    }

    public Integer getSendLimit()
    {
        return (Integer) get("send_limit");
    }

    public String getExternalAccountId()
    {
        return (String) get("external_account_id");
    }

    public void setExternalAccountId(String value)
    {
        set("external_account_id", value);
    }

    public String getExternalId()
    {
        return (String) get("external_id");
    }

    public void setExternalId(String value)
    {
        set("external_id", value);
    }

    public JSONObject getExternalConfig()
    {
        return (JSONObject) get("external_config");
    }

    public void setExternalConfig(JSONObject value)
    {
        set("external_config", value);
    }

    public JSONObject getExternalSetup()
    {
        return (JSONObject) get("external_setup");
    }

    public String getExternalSecret()
    {
        return (String) get("external_secret");
    }

    public void setExternalSecret(String value)
    {
        set("external_secret", value);
    }

    @Override
    public String getBaseApiPath()
    {
        return "/projects/" + getProjectId() + "/phones/" + getId() + "";
    }

    public Phone(TelerivetAPI api, JSONObject data)
    {
        this(api, data, true);
    }

    public Phone(TelerivetAPI api, JSONObject data, boolean isLoaded)
    {
        super(api, data, isLoaded);
    }
}
