
package com.telerivet;

import java.io.IOException;
import org.json.JSONObject;
import org.json.JSONArray;

/**
    <p>Represents a transaction where airtime is sent to a mobile phone number.</p>
    
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
    <li><p>status</p>
    
    <ul>
    <li>Current status of airtime transaction (<code>successful</code>, <code>failed</code>, <code>cancelled</code>,
      <code>queued</code>, <code>pending_approval</code>, or <code>pending_payment</code>)</li>
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
    <li><p>vars (JSONObject)</p>
    
    <ul>
    <li>Custom variables stored for this transaction</li>
    <li>Updatable via API</li>
    </ul></li>
    </ul>
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
