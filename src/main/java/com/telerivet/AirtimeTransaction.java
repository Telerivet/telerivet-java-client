
package com.telerivet;

import java.io.IOException;
import org.json.JSONObject;
import org.json.JSONArray;

/**
    <div class='markdown'><p>Represents a transaction where airtime is sent to a mobile phone number.</p>
    
    <p>To send airtime, first <a href="/dashboard/add_service?subtype_id=main.service.rules.contact&amp;action_id=main.rule.sendairtime">create a Custom Actions service to send a particular amount of
    airtime</a>,
    then trigger the service using <a href="#Service.invoke">service.invoke</a>,
    <a href="#Project.sendBroadcast">project.sendBroadcast</a>, or
    <a href="#Project.scheduleMessage">project.scheduleMessage</a>.</p>
    
    <p>Fields:</p>
    
    <ul>
    <li><p>id</p>
    
    <ul>
    <li>ID of the airtime transaction</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>to_number</p>
    
    <ul>
    <li>Destination phone number in international format (no leading +)</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>operator_name</p>
    
    <ul>
    <li>Operator name</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>country</p>
    
    <ul>
    <li>Country code</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>time_created (UNIX timestamp)</p>
    
    <ul>
    <li>The time that the airtime transaction was created on Telerivet's servers</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>transaction_time (UNIX timestamp)</p>
    
    <ul>
    <li>The time that the airtime transaction was sent, or null if it has not been sent</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>status</p>
    
    <ul>
    <li>Current status of airtime transaction (<code>successful</code>, <code>failed</code>, <code>cancelled</code>,
      <code>queued</code>, <code>processing</code>, <code>submitted</code>, <code>pending_approval</code>, or <code>pending_payment</code>)</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>status_text</p>
    
    <ul>
    <li>Error or success message returned by airtime provider, if available</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>value</p>
    
    <ul>
    <li>Value of airtime sent to destination phone number, in units of value_currency</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>value_currency</p>
    
    <ul>
    <li>Currency code of price</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>price</p>
    
    <ul>
    <li>Price charged for airtime transaction, in units of price_currency</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>price_currency</p>
    
    <ul>
    <li>Currency code of price</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>contact_id</p>
    
    <ul>
    <li>ID of the contact the airtime was sent to</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>service_id</p>
    
    <ul>
    <li>ID of the service that sent the airtime</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>project_id</p>
    
    <ul>
    <li>ID of the project that the airtime transaction belongs to</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>external_id</p>
    
    <ul>
    <li>The ID of this transaction from an external airtime gateway provider, if available.</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>user_id (string, max 34 characters)</p>
    
    <ul>
    <li>ID of the Telerivet user who sent the airtime transaction (if applicable)</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>vars (JSONObject)</p>
    
    <ul>
    <li>Custom variables stored for this transaction. Variable names may be up to 32
      characters in length and can contain the characters a-z, A-Z, 0-9, and _.
      Values may be strings, numbers, or boolean (true/false).
      String values may be up to 4096 bytes in length when encoded as UTF-8.
      Up to 100 variables are supported per object.
      Setting a variable to null will delete the variable.</li>
    <li>Updatable via API</li>
    </ul></li>
    </ul>
    </div>
*/
public class AirtimeTransaction extends Entity
{
    public String getId()
    {
        return (String) get("id");
    }

    public String getToNumber()
    {
        return (String) get("to_number");
    }

    public String getOperatorName()
    {
        return (String) get("operator_name");
    }

    public String getCountry()
    {
        return (String) get("country");
    }

    public Long getTimeCreated()
    {
        return Util.toLong(get("time_created"));
    }

    public Long getTransactionTime()
    {
        return Util.toLong(get("transaction_time"));
    }

    public String getStatus()
    {
        return (String) get("status");
    }

    public String getStatusText()
    {
        return (String) get("status_text");
    }

    public String getValue()
    {
        return (String) get("value");
    }

    public String getValueCurrency()
    {
        return (String) get("value_currency");
    }

    public String getPrice()
    {
        return (String) get("price");
    }

    public String getPriceCurrency()
    {
        return (String) get("price_currency");
    }

    public String getContactId()
    {
        return (String) get("contact_id");
    }

    public String getServiceId()
    {
        return (String) get("service_id");
    }

    public String getProjectId()
    {
        return (String) get("project_id");
    }

    public String getExternalId()
    {
        return (String) get("external_id");
    }

    public String getUserId()
    {
        return (String) get("user_id");
    }

    @Override
    public String getBaseApiPath()
    {
        return "/projects/" + getProjectId() + "/airtime_transactions/" + getId() + "";
    }

    public AirtimeTransaction(TelerivetAPI api, JSONObject data)
    {
        this(api, data, true);
    }

    public AirtimeTransaction(TelerivetAPI api, JSONObject data, boolean isLoaded)
    {
        super(api, data, isLoaded);
    }
}
