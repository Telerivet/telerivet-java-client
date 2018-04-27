
package com.telerivet;

import java.io.IOException;
import org.json.JSONObject;
import org.json.JSONArray;

/**
    <p>Represents a phone or gateway that you use to send/receive messages via Telerivet.</p>
    
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
    <li>Phone number of the phone</li>
    <li>Updatable via API</li>
    </ul></li>
    <li><p>phone_type</p>
    
    <ul>
    <li>Type of this phone/gateway (e.g. android, twilio, nexmo, etc)</li>
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
    <li><p>last<em>active</em>time (UNIX timestamp)</p>
    
    <ul>
    <li>Approximate time this phone last connected to Telerivet</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>vars (JSONObject)</p>
    
    <ul>
    <li>Custom variables stored for this phone</li>
    <li>Updatable via API</li>
    </ul></li>
    <li><p>project_id</p>
    
    <ul>
    <li>ID of the project this phone belongs to</li>
    <li>Read-only</li>
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
      this phone; see
      <a href="http://developer.android.com/guide/topics/manifest/uses-sdk-element.html#ApiLevels">http://developer.android.com/guide/topics/manifest/uses-sdk-element.html#ApiLevels</a>
      (only present for Android phones)</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>mccmnc</p>
    
    <ul>
    <li>Code indicating the Android phone's current country (MCC) and mobile network
      operator (MNC); see <a href="http://en.wikipedia.org/wiki/Mobile_country_code">http://en.wikipedia.org/wiki/Mobile_country_code</a> (only present
      for Android phones). Note this is a string containing numeric digits, not an integer.</li>
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
    </ul>
*/
public class Phone extends Entity
{
    /**
        <p>Queries messages sent or received by this phone.</p>
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
        <p>Saves any fields or custom variables that have changed for this phone.</p>
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
