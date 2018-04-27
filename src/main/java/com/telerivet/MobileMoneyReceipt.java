
package com.telerivet;

import java.io.IOException;
import org.json.JSONObject;
import org.json.JSONArray;

/**
    <p>Represents a receipt received from a mobile money system such as Safaricom M-Pesa (Kenya),
    Vodacom M-Pesa (Tanzania), or Tigo Pesa (Tanzania).</p>
    
    <p>When your Android phone receives a SMS receipt from a supported mobile money
    service that Telerivet can understand, Telerivet will automatically parse it and create a
    MobileMoneyReceipt object.</p>
    
    <p>Fields:</p>
    
    <ul>
    <li><p>id (string, max 34 characters)</p>
    
    <ul>
    <li>Telerivet's internal ID for the receipt</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>tx_id</p>
    
    <ul>
    <li>Transaction ID from the receipt</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>tx_type</p>
    
    <ul>
    <li>Type of mobile money transaction</li>
    <li>Allowed values: receive<em>money, send</em>money, pay<em>bill, deposit, withdrawal,
      airtime</em>purchase, balance_inquiry, reversal</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>currency</p>
    
    <ul>
    <li><a href="http://en.wikipedia.org/wiki/ISO_4217">ISO 4217 Currency code</a> for the transaction,
      e.g. KES or TZS. Amount, balance, and fee are expressed in units of this currency.</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>amount (number)</p>
    
    <ul>
    <li>Amount of this transaction; positive numbers indicate money added to your account,
      negative numbers indicate money removed from your account</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>balance (number)</p>
    
    <ul>
    <li>The current balance of your mobile money account (null if not available)</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>fee (number)</p>
    
    <ul>
    <li>The transaction fee charged by the mobile money system (null if not available)</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>name</p>
    
    <ul>
    <li>The name of the other person in the transaction (null if not available)</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>phone_number</p>
    
    <ul>
    <li>The phone number of the other person in the transaction (null if not available)</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>time_created (UNIX timestamp)</p>
    
    <ul>
    <li>The time this receipt was created in Telerivet</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>other<em>tx</em>id</p>
    
    <ul>
    <li>The other transaction ID listed in the receipt (e.g. the transaction ID for a
      reversed transaction)</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>content</p>
    
    <ul>
    <li>The raw content of the mobile money receipt</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>provider_id</p>
    
    <ul>
    <li>Telerivet's internal ID for the mobile money provider</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>vars (JSONObject)</p>
    
    <ul>
    <li>Custom variables stored for this mobile money receipt</li>
    <li>Updatable via API</li>
    </ul></li>
    <li><p>contact_id</p>
    
    <ul>
    <li>ID of the contact associated with the name/phone number on the receipt. Note that
      some mobile money systems do not provide the other person's phone number, so it's
      possible Telerivet may not automatically assign a contact_id, or may assign it to a
      different contact with the same name.</li>
    <li>Updatable via API</li>
    </ul></li>
    <li><p>phone_id</p>
    
    <ul>
    <li>ID of the phone that received the receipt</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>message_id</p>
    
    <ul>
    <li>ID of the message corresponding to the receipt</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>project_id</p>
    
    <ul>
    <li>ID of the project this receipt belongs to</li>
    <li>Read-only</li>
    </ul></li>
    </ul>
*/
public class MobileMoneyReceipt extends Entity
{
    /**
        <p>Saves any fields or custom variables that have changed for this mobile money receipt.</p>
    */
    @Override
    public void save() throws IOException
    {
        super.save();
    }

    /**
        <p>Deletes this receipt.</p>
    */
    public void delete() throws IOException
    {
        api.doRequest("DELETE", getBaseApiPath());
    }

    public String getId()
    {
        return (String) get("id");
    }

    public String getTxId()
    {
        return (String) get("tx_id");
    }

    public String getTxType()
    {
        return (String) get("tx_type");
    }

    public String getCurrency()
    {
        return (String) get("currency");
    }

    public Double getAmount()
    {
        return Util.toDouble(get("amount"));
    }

    public Double getBalance()
    {
        return Util.toDouble(get("balance"));
    }

    public Double getFee()
    {
        return Util.toDouble(get("fee"));
    }

    public String getName()
    {
        return (String) get("name");
    }

    public String getPhoneNumber()
    {
        return (String) get("phone_number");
    }

    public Long getTimeCreated()
    {
        return Util.toLong(get("time_created"));
    }

    public String getOtherTxId()
    {
        return (String) get("other_tx_id");
    }

    public String getContent()
    {
        return (String) get("content");
    }

    public String getProviderId()
    {
        return (String) get("provider_id");
    }

    public String getContactId()
    {
        return (String) get("contact_id");
    }

    public void setContactId(String value)
    {
        set("contact_id", value);
    }

    public String getPhoneId()
    {
        return (String) get("phone_id");
    }

    public String getMessageId()
    {
        return (String) get("message_id");
    }

    public String getProjectId()
    {
        return (String) get("project_id");
    }

    @Override
    public String getBaseApiPath()
    {
        return "/projects/" + getProjectId() + "/receipts/" + getId() + "";
    }

    public MobileMoneyReceipt(TelerivetAPI api, JSONObject data)
    {
        this(api, data, true);
    }

    public MobileMoneyReceipt(TelerivetAPI api, JSONObject data, boolean isLoaded)
    {
        super(api, data, isLoaded);
    }
}
