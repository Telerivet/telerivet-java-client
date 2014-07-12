package com.telerivet;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.Date;
import org.json.JSONObject;
import org.json.JSONArray;

/**
    Message
    
    Represents a single message.
    
    Fields:
    
      - id (string, max 34 characters)
          * ID of the message
          * Read-only
      
      - direction
          * Direction of the message: incoming messages are sent from one of your contacts to
              your phone; outgoing messages are sent from your phone to one of your contacts
          * Allowed values: incoming, outgoing
          * Read-only
      
      - status
          * Current status of the message
          * Allowed values: ignored, processing, received, sent, queued, failed, failed_queued,
              cancelled, delivered, not_delivered
          * Read-only
      
      - message_type
          * Type of the message
          * Allowed values: sms, mms, ussd, call
          * Read-only
      
      - source
          * How the message originated within Telerivet
          * Allowed values: phone, provider, web, api, service, webhook, scheduled
          * Read-only
      
      - time_created (UNIX timestamp)
          * The time that the message was created on Telerivet's servers
          * Read-only
      
      - time_sent (UNIX timestamp)
          * The time that the message was reported to have been sent (null for incoming messages
              and messages that have not yet been sent)
          * Read-only
      
      - from_number (string)
          * The phone number that the message originated from (your number for outgoing
              messages, the contact's number for incoming messages)
          * Read-only
      
      - to_number (string)
          * The phone number that the message was sent to (your number for incoming messages,
              the contact's number for outgoing messages)
          * Read-only
      
      - content (string)
          * The text content of the message (null for USSD messages and calls)
          * Read-only
      
      - starred (bool)
          * Whether this message is starred in Telerivet
          * Updatable via API
      
      - simulated (bool)
          * Whether this message is was simulated within Telerivet for testing (and not actually
              sent to or received by a real phone)
          * Read-only
      
      - label_ids (array)
          * List of IDs of labels applied to this message
          * Read-only
      
      - vars (JSONObject)
          * Custom variables stored for this message
          * Updatable via API
      
      - error_message
          * A description of the error encountered while sending a message. (This field is
              omitted from the API response if there is no error message.)
          * Updatable via API
      
      - external_id
          * The ID of this message from an external SMS gateway provider (e.g. Twilio or Nexmo),
              if available.
          * Read-only
      
      - price (number)
          * The price of this message, if known. By convention, message prices are negative.
          * Read-only
      
      - price_currency
          * The currency of the message price, if applicable.
          * Read-only
      
      - mms_parts (array)
          * A list of parts in the MMS message, the same as returned by the
              [getMMSParts](#Message.getMMSParts) method.
              
              Note: This property is only present when retrieving an individual
              MMS message by ID, not when querying a list of messages. In other cases, use
              [getMMSParts](#Message.getMMSParts).
          * Read-only
      
      - phone_id (string, max 34 characters)
          * ID of the phone that sent or received the message
          * Read-only
      
      - contact_id (string, max 34 characters)
          * ID of the contact that sent or received the message
          * Read-only
      
      - project_id
          * ID of the project this contact belongs to
          * Read-only
 */
public class Message extends Entity 
{    
    /**
        message.hasLabel(label)
        
        Returns true if this message has a particular label, false otherwise.
        
        Arguments:
          - label (Label)
              * Required
          
        Returns:
            bool
     */
    public boolean hasLabel(Label label) throws IOException
    {
        assertLoaded();
        return labelIdsSet.contains(label.getId());
    }
      
    /**
        message.addLabel(label)
        
        Adds a label to the given message.
        
        Arguments:
          - label (Label)
              * Required
     */
    public void addLabel(Label label) throws IOException
    {
        api.doRequest("PUT", label.getBaseApiPath() + "/messages/" + getId());
        labelIdsSet.add(label.getId());
    }
    
    /**
        message.removeLabel(label)
        
        Removes a label from the given message.
        
        Arguments:
          - label (Label)
              * Required
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
        message.getMMSParts()
        
        Retrieves a list of MMS parts for this message (empty for non-MMS messages).
        
        Each MMS part in the list is an object with the following
        properties:
        
        - cid: MMS content-id
        - type: MIME type
        - filename: original filename
        - size (int): number of bytes
        - url: URL where the content for this part is stored (secret but
        publicly accessible, so you could link/embed it in a web page without having to re-host it
        yourself)
        
        Returns:
            array
    */
    public JSONArray getMMSParts() throws IOException
    {
        return (JSONArray) api.doRequest("GET", getBaseApiPath() + "/mms_parts");
    }

    /**
        message.save()
        
        Saves any fields that have changed for this message.
    */
    @Override
    public void save() throws IOException
    {
        super.save();
    }

    /**
        message.delete()
        
        Deletes this message.
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

    public String getErrorMessage()
    {
        return (String) get("error_message");
    }

    public void setErrorMessage(String value)
    {
        set("error_message", value);
    }

    public String getExternalId()
    {
        return (String) get("external_id");
    }

    public Double getPrice()
    {
        return Util.toDouble(get("price"));
    }

    public String getPriceCurrency()
    {
        return (String) get("price_currency");
    }

    public JSONArray getMmsParts()
    {
        return (JSONArray) get("mms_parts");
    }

    public String getPhoneId()
    {
        return (String) get("phone_id");
    }

    public String getContactId()
    {
        return (String) get("contact_id");
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