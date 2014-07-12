
package com.telerivet;
        
import java.io.IOException;
import org.json.JSONObject;
import org.json.JSONArray;
        
/**
    Project
    
    Represents a Telerivet project.
    
    Provides methods for sending and scheduling messages, as well as
    accessing, creating and updating a variety of entities, including contacts, messages,
    scheduled messages, groups, labels, phones, services, and data tables.
    
    Fields:
    
      - id (string, max 34 characters)
          * ID of the project
          * Read-only
      
      - name
          * Name of the project
          * Updatable via API
      
      - timezone_id
          * Default TZ database timezone ID; see
              <http://en.wikipedia.org/wiki/List_of_tz_database_time_zones>
          * Read-only
      
      - vars (JSONObject)
          * Custom variables stored for this project
          * Updatable via API
*/
public class Project extends Entity
{
    /**
        project.sendMessage(options)
        
        Sends one message (SMS or USSD request).
        
        Arguments:
          - options (JSONObject)
              * Required
            
            - content
                * Content of the message to send
                * Required if sending SMS message
            
            - to_number (string)
                * Phone number to send the message to
                * Required if contact_id not set
            
            - contact_id
                * ID of the contact to send the message to
                * Required if to_number not set
            
            - route_id
                * ID of the phone or route to send the message from
                * Default: default sender phone ID for your project
            
            - status_url
                * Webhook callback URL to be notified when message status changes
            
            - status_secret
                * POST parameter 'secret' passed to status_url
            
            - is_template (bool)
                * Set to true to evaluate variables like [[contact.name]] in message content. [(See
                    available variables)](#variables)
                * Default: false
            
            - label_ids (array)
                * List of IDs of labels to add to this message
            
            - message_type
                * Type of message to send
                * Allowed values: sms, ussd
                * Default: sms
            
            - vars (JSONObject)
                * Custom variables to store with the message
            
            - priority (int)
                * Priority of the message (currently only observed for Android phones). Telerivet
                    will attempt to send messages with higher priority numbers first (for example, so
                    you can prioritize an auto-reply ahead of a bulk message to a large group).
                * Default: 1
          
        Returns:
            Message
    */
    public Message sendMessage(JSONObject options) throws IOException
    {
        return new Message(api, (JSONObject) api.doRequest("POST", getBaseApiPath() + "/messages/send", options));
    }

    /**
        project.sendMessages(options)
        
        Sends an SMS message (optionally with mail-merge templates) to a group or a list of up to
        500 phone numbers
        
        Arguments:
          - options (JSONObject)
              * Required
            
            - content
                * Content of the message to send
                * Required
            
            - group_id
                * ID of the group to send the message to
                * Required if to_numbers not set
            
            - to_numbers (array of strings)
                * List of up to 500 phone numbers to send the message to
                * Required if group_id not set
            
            - route_id
                * ID of the phone or route to send the message from
                * Default: default sender phone ID
            
            - status_url
                * Webhook callback URL to be notified when message status changes
            
            - label_ids (array)
                * Array of IDs of labels to add to all messages sent (maximum 5)
            
            - status_secret
                * POST parameter 'secret' passed to status_url
            
            - exclude_contact_id
                * Optionally excludes one contact from receiving the message (only when group_id is
                    set)
            
            - is_template (bool)
                * Set to true to evaluate variables like [[contact.name]] in message content [(See
                    available variables)](#variables)
                * Default: false
            
            - vars (JSONObject)
                * Custom variables to set for each message
          
        Returns:
            (associative array)
              - count_queued (int)
                  * Number of messages queued to send
    */
    public JSONObject sendMessages(JSONObject options) throws IOException
    {
        return (JSONObject) api.doRequest("POST", getBaseApiPath() + "/messages/send_batch", options);
    }

    /**
        project.scheduleMessage(options)
        
        Schedules an SMS message to a group or single contact. Note that Telerivet only sends
        scheduled messages approximately once per minute, so it is not possible to control the exact
        second at which a scheduled message is sent.
        
        Arguments:
          - options (JSONObject)
              * Required
            
            - content
                * Content of the message to schedule
                * Required
            
            - group_id
                * ID of the group to send the message to
                * Required if to_number not set
            
            - to_number (string)
                * Phone number to send the message to
                * Required if group_id not set
            
            - start_time (UNIX timestamp)
                * The time that the message will be sent (or first sent for recurring messages)
                * Required if start_time_offset not set
            
            - start_time_offset (int)
                * Number of seconds from now until the message is sent
                * Required if start_time not set
            
            - rrule
                * A recurrence rule describing the how the schedule repeats, e.g. 'FREQ=MONTHLY' or
                    'FREQ=WEEKLY;INTERVAL=2'; see <https://tools.ietf.org/html/rfc2445#section-4.3.10>.
                    (UNTIL is ignored; use end_time parameter instead).
                * Default: COUNT=1 (one-time scheduled message, does not repeat)
            
            - route_id
                * ID of the phone or route to send the message from
                * Default: default sender phone ID
            
            - message_type
                * Type of message to send
                * Allowed values: sms, ussd
                * Default: sms
            
            - is_template (bool)
                * Set to true to evaluate variables like [[contact.name]] in message content
                * Default: false
            
            - label_ids (array)
                * Array of IDs of labels to add to the sent messages (maximum 5)
            
            - timezone_id
                * TZ database timezone ID; see
                    <http://en.wikipedia.org/wiki/List_of_tz_database_time_zones>
                * Default: project default timezone
            
            - end_time (UNIX timestamp)
                * Time after which a recurring message will stop (not applicable to non-recurring
                    scheduled messages)
            
            - end_time_offset (int)
                * Number of seconds from now until the recurring message will stop
          
        Returns:
            ScheduledMessage
    */
    public ScheduledMessage scheduleMessage(JSONObject options) throws IOException
    {
        return new ScheduledMessage(api, (JSONObject) api.doRequest("POST", getBaseApiPath() + "/scheduled", options));
    }

    /**
        project.getOrCreateContact(options)
        
        Retrieves OR creates and possibly updates a contact by name or phone number.
        
        If a phone number is provided, Telerivet will search for an existing
        contact with that phone number (including suffix matches to allow finding contacts with
        phone numbers in a different format).
        
        If a phone number is not provided but a name is provided, Telerivet
        will search for a contact with that exact name (case insensitive).
        
        If no existing contact is found, a new contact will be created.
        
        Then that contact will be updated with any parameters provided
        (name, phone_number, and vars).
        
        Arguments:
          - options (JSONObject)
              * Required
            
            - name
                * Name of the contact
                * Required if phone_number not set
            
            - phone_number
                * Phone number of the contact
                * Required if name not set
            
            - vars (JSONObject)
                * Custom variables and values to update on the contact
          
        Returns:
            Contact
    */
    public Contact getOrCreateContact(JSONObject options) throws IOException
    {
        return new Contact(api, (JSONObject) api.doRequest("POST", getBaseApiPath() + "/contacts", options));
    }

    /**
        project.queryContacts(options)
        
        Queries contacts within the given project.
        
        Arguments:
          - options (JSONObject)
            
            - name
                * Filter contacts by name
                * Allowed modifiers: name[ne], name[prefix], name[not_prefix], name[gte], name[gt],
                    name[lt], name[lte]
            
            - phone_number
                * Filter contacts by phone number
                * Allowed modifiers: phone_number[ne], phone_number[prefix],
                    phone_number[not_prefix], phone_number[gte], phone_number[gt], phone_number[lt],
                    phone_number[lte]
            
            - time_created (UNIX timestamp)
                * Filter contacts by time created
                * Allowed modifiers: time_created[ne], time_created[min], time_created[max]
            
            - last_message_time (UNIX timestamp)
                * Filter contacts by last time a message was sent or received
                * Allowed modifiers: last_message_time[exists], last_message_time[ne],
                    last_message_time[min], last_message_time[max]
            
            - vars (JSONObject)
                * Filter contacts by value of a custom variable (e.g. vars[email], vars[foo], etc.)
                * Allowed modifiers: vars[foo][exists], vars[foo][ne], vars[foo][prefix],
                    vars[foo][not_prefix], vars[foo][gte], vars[foo][gt], vars[foo][lt], vars[foo][lte],
                    vars[foo][min], vars[foo][max]
            
            - sort
                * Sort the results based on a field
                * Allowed values: default, name, phone_number, last_message_time
                * Default: default
            
            - sort_dir
                * Sort the results in ascending or descending order
                * Allowed values: asc, desc
                * Default: asc
            
            - page_size (int)
                * Number of results returned per page (max 200)
                * Default: 50
            
            - offset (int)
                * Number of items to skip from beginning of result set
                * Default: 0
          
        Returns:
            APICursor (of Contact)
    */
    public APICursor<Contact> queryContacts(JSONObject options)
    {
        return api.newCursor(Contact.class, getBaseApiPath() + "/contacts", options);
    }

    public APICursor<Contact> queryContacts()
    {
        return queryContacts(null);
    }

    /**
        project.getContactById(id)
        
        Retrieves the contact with the given ID.
        
        Arguments:
          - id
              * ID of the contact
              * Required
          
        Returns:
            Contact
    */
    public Contact getContactById(String id) throws IOException
    {
        return new Contact(api, (JSONObject) api.doRequest("GET", getBaseApiPath() + "/contacts/" + id));
    }

    /**
        project.initContactById(id)
        
        Initializes the Telerivet contact with the given ID without making an API request.
        
        Arguments:
          - id
              * ID of the contact
              * Required
          
        Returns:
            Contact
    */
    public Contact initContactById(String id)
    {
        return new Contact(api, Util.options("project_id", get("id"), "id", id), false);
    }

    /**
        project.queryPhones(options)
        
        Queries phones within the given project.
        
        Arguments:
          - options (JSONObject)
            
            - name
                * Filter phones by name
                * Allowed modifiers: name[ne], name[prefix], name[not_prefix], name[gte], name[gt],
                    name[lt], name[lte]
            
            - phone_number
                * Filter phones by phone number
                * Allowed modifiers: phone_number[ne], phone_number[prefix],
                    phone_number[not_prefix], phone_number[gte], phone_number[gt], phone_number[lt],
                    phone_number[lte]
            
            - last_active_time (UNIX timestamp)
                * Filter phones by last active time
                * Allowed modifiers: last_active_time[exists], last_active_time[ne],
                    last_active_time[min], last_active_time[max]
            
            - sort
                * Sort the results based on a field
                * Allowed values: default, name, phone_number
                * Default: default
            
            - sort_dir
                * Sort the results in ascending or descending order
                * Allowed values: asc, desc
                * Default: asc
            
            - page_size (int)
                * Number of results returned per page (max 200)
                * Default: 50
            
            - offset (int)
                * Number of items to skip from beginning of result set
                * Default: 0
          
        Returns:
            APICursor (of Phone)
    */
    public APICursor<Phone> queryPhones(JSONObject options)
    {
        return api.newCursor(Phone.class, getBaseApiPath() + "/phones", options);
    }

    public APICursor<Phone> queryPhones()
    {
        return queryPhones(null);
    }

    /**
        project.getPhoneById(id)
        
        Retrieves the phone with the given ID.
        
        Arguments:
          - id
              * ID of the phone - see <https://telerivet.com/dashboard/api>
              * Required
          
        Returns:
            Phone
    */
    public Phone getPhoneById(String id) throws IOException
    {
        return new Phone(api, (JSONObject) api.doRequest("GET", getBaseApiPath() + "/phones/" + id));
    }

    /**
        project.initPhoneById(id)
        
        Initializes the phone with the given ID without making an API request.
        
        Arguments:
          - id
              * ID of the phone - see <https://telerivet.com/dashboard/api>
              * Required
          
        Returns:
            Phone
    */
    public Phone initPhoneById(String id)
    {
        return new Phone(api, Util.options("project_id", get("id"), "id", id), false);
    }

    /**
        project.queryMessages(options)
        
        Queries messages within the given project.
        
        Arguments:
          - options (JSONObject)
            
            - direction
                * Filter messages by direction
                * Allowed values: incoming, outgoing
            
            - message_type
                * Filter messages by message_type
                * Allowed values: sms, mms, ussd, call
            
            - source
                * Filter messages by source
                * Allowed values: phone, provider, web, api, service, webhook, scheduled
            
            - starred (bool)
                * Filter messages by starred/unstarred
            
            - status
                * Filter messages by status
                * Allowed values: ignored, processing, received, sent, queued, failed,
                    failed_queued, cancelled, delivered, not_delivered
            
            - time_created[min] (UNIX timestamp)
                * Filter messages created on or after a particular time
            
            - time_created[max] (UNIX timestamp)
                * Filter messages created before a particular time
            
            - contact_id
                * ID of the contact who sent/received the message
            
            - phone_id
                * ID of the phone that sent/received the message
            
            - sort
                * Sort the results based on a field
                * Allowed values: default
                * Default: default
            
            - sort_dir
                * Sort the results in ascending or descending order
                * Allowed values: asc, desc
                * Default: asc
            
            - page_size (int)
                * Number of results returned per page (max 200)
                * Default: 50
            
            - offset (int)
                * Number of items to skip from beginning of result set
                * Default: 0
          
        Returns:
            APICursor (of Message)
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
        project.getMessageById(id)
        
        Retrieves the message with the given ID.
        
        Arguments:
          - id
              * ID of the message
              * Required
          
        Returns:
            Message
    */
    public Message getMessageById(String id) throws IOException
    {
        return new Message(api, (JSONObject) api.doRequest("GET", getBaseApiPath() + "/messages/" + id));
    }

    /**
        project.initMessageById(id)
        
        Initializes the Telerivet message with the given ID without making an API request.
        
        Arguments:
          - id
              * ID of the message
              * Required
          
        Returns:
            Message
    */
    public Message initMessageById(String id)
    {
        return new Message(api, Util.options("project_id", get("id"), "id", id), false);
    }

    /**
        project.queryGroups(options)
        
        Queries groups within the given project.
        
        Arguments:
          - options (JSONObject)
            
            - name
                * Filter groups by name
                * Allowed modifiers: name[ne], name[prefix], name[not_prefix], name[gte], name[gt],
                    name[lt], name[lte]
            
            - sort
                * Sort the results based on a field
                * Allowed values: default, name
                * Default: default
            
            - sort_dir
                * Sort the results in ascending or descending order
                * Allowed values: asc, desc
                * Default: asc
            
            - page_size (int)
                * Number of results returned per page (max 200)
                * Default: 50
            
            - offset (int)
                * Number of items to skip from beginning of result set
                * Default: 0
          
        Returns:
            APICursor (of Group)
    */
    public APICursor<Group> queryGroups(JSONObject options)
    {
        return api.newCursor(Group.class, getBaseApiPath() + "/groups", options);
    }

    public APICursor<Group> queryGroups()
    {
        return queryGroups(null);
    }

    /**
        project.getOrCreateGroup(name)
        
        Retrieves or creates a group by name.
        
        Arguments:
          - name
              * Name of the group
              * Required
          
        Returns:
            Group
    */
    public Group getOrCreateGroup(String name) throws IOException
    {
        return new Group(api, (JSONObject) api.doRequest("POST", getBaseApiPath() + "/groups", Util.options("name", name)));
    }

    /**
        project.getGroupById(id)
        
        Retrieves the group with the given ID.
        
        Arguments:
          - id
              * ID of the group
              * Required
          
        Returns:
            Group
    */
    public Group getGroupById(String id) throws IOException
    {
        return new Group(api, (JSONObject) api.doRequest("GET", getBaseApiPath() + "/groups/" + id));
    }

    /**
        project.initGroupById(id)
        
        Initializes the group with the given ID without making an API request.
        
        Arguments:
          - id
              * ID of the group
              * Required
          
        Returns:
            Group
    */
    public Group initGroupById(String id)
    {
        return new Group(api, Util.options("project_id", get("id"), "id", id), false);
    }

    /**
        project.queryLabels(options)
        
        Queries labels within the given project.
        
        Arguments:
          - options (JSONObject)
            
            - name
                * Filter labels by name
                * Allowed modifiers: name[ne], name[prefix], name[not_prefix], name[gte], name[gt],
                    name[lt], name[lte]
            
            - sort
                * Sort the results based on a field
                * Allowed values: default, name
                * Default: default
            
            - sort_dir
                * Sort the results in ascending or descending order
                * Allowed values: asc, desc
                * Default: asc
            
            - page_size (int)
                * Number of results returned per page (max 200)
                * Default: 50
            
            - offset (int)
                * Number of items to skip from beginning of result set
                * Default: 0
          
        Returns:
            APICursor (of Label)
    */
    public APICursor<Label> queryLabels(JSONObject options)
    {
        return api.newCursor(Label.class, getBaseApiPath() + "/labels", options);
    }

    public APICursor<Label> queryLabels()
    {
        return queryLabels(null);
    }

    /**
        project.getOrCreateLabel(name)
        
        Gets or creates a label by name.
        
        Arguments:
          - name
              * Name of the label
              * Required
          
        Returns:
            Label
    */
    public Label getOrCreateLabel(String name) throws IOException
    {
        return new Label(api, (JSONObject) api.doRequest("POST", getBaseApiPath() + "/labels", Util.options("name", name)));
    }

    /**
        project.getLabelById(id)
        
        Retrieves the label with the given ID.
        
        Arguments:
          - id
              * ID of the label
              * Required
          
        Returns:
            Label
    */
    public Label getLabelById(String id) throws IOException
    {
        return new Label(api, (JSONObject) api.doRequest("GET", getBaseApiPath() + "/labels/" + id));
    }

    /**
        project.initLabelById(id)
        
        Initializes the label with the given ID without making an API request.
        
        Arguments:
          - id
              * ID of the label
              * Required
          
        Returns:
            Label
    */
    public Label initLabelById(String id)
    {
        return new Label(api, Util.options("project_id", get("id"), "id", id), false);
    }

    /**
        project.queryDataTables(options)
        
        Queries data tables within the given project.
        
        Arguments:
          - options (JSONObject)
            
            - name
                * Filter data tables by name
                * Allowed modifiers: name[ne], name[prefix], name[not_prefix], name[gte], name[gt],
                    name[lt], name[lte]
            
            - sort
                * Sort the results based on a field
                * Allowed values: default, name
                * Default: default
            
            - sort_dir
                * Sort the results in ascending or descending order
                * Allowed values: asc, desc
                * Default: asc
            
            - page_size (int)
                * Number of results returned per page (max 200)
                * Default: 50
            
            - offset (int)
                * Number of items to skip from beginning of result set
                * Default: 0
          
        Returns:
            APICursor (of DataTable)
    */
    public APICursor<DataTable> queryDataTables(JSONObject options)
    {
        return api.newCursor(DataTable.class, getBaseApiPath() + "/tables", options);
    }

    public APICursor<DataTable> queryDataTables()
    {
        return queryDataTables(null);
    }

    /**
        project.getOrCreateDataTable(name)
        
        Gets or creates a data table by name.
        
        Arguments:
          - name
              * Name of the data table
              * Required
          
        Returns:
            DataTable
    */
    public DataTable getOrCreateDataTable(String name) throws IOException
    {
        return new DataTable(api, (JSONObject) api.doRequest("POST", getBaseApiPath() + "/tables", Util.options("name", name)));
    }

    /**
        project.getDataTableById(id)
        
        Retrieves the data table with the given ID.
        
        Arguments:
          - id
              * ID of the data table
              * Required
          
        Returns:
            DataTable
    */
    public DataTable getDataTableById(String id) throws IOException
    {
        return new DataTable(api, (JSONObject) api.doRequest("GET", getBaseApiPath() + "/tables/" + id));
    }

    /**
        project.initDataTableById(id)
        
        Initializes the data table with the given ID without making an API request.
        
        Arguments:
          - id
              * ID of the data table
              * Required
          
        Returns:
            DataTable
    */
    public DataTable initDataTableById(String id)
    {
        return new DataTable(api, Util.options("project_id", get("id"), "id", id), false);
    }

    /**
        project.queryScheduledMessages(options)
        
        Queries scheduled messages within the given project.
        
        Arguments:
          - options (JSONObject)
            
            - message_type
                * Filter scheduled messages by message_type
                * Allowed values: sms, mms, ussd, call
            
            - time_created (UNIX timestamp)
                * Filter scheduled messages by time_created
                * Allowed modifiers: time_created[ne], time_created[min], time_created[max]
            
            - next_time (UNIX timestamp)
                * Filter scheduled messages by next_time
                * Allowed modifiers: next_time[exists], next_time[ne], next_time[min],
                    next_time[max]
            
            - sort
                * Sort the results based on a field
                * Allowed values: default, name
                * Default: default
            
            - sort_dir
                * Sort the results in ascending or descending order
                * Allowed values: asc, desc
                * Default: asc
            
            - page_size (int)
                * Number of results returned per page (max 200)
                * Default: 50
            
            - offset (int)
                * Number of items to skip from beginning of result set
                * Default: 0
          
        Returns:
            APICursor (of ScheduledMessage)
    */
    public APICursor<ScheduledMessage> queryScheduledMessages(JSONObject options)
    {
        return api.newCursor(ScheduledMessage.class, getBaseApiPath() + "/scheduled", options);
    }

    public APICursor<ScheduledMessage> queryScheduledMessages()
    {
        return queryScheduledMessages(null);
    }

    /**
        project.getScheduledMessageById(id)
        
        Retrieves the scheduled message with the given ID.
        
        Arguments:
          - id
              * ID of the scheduled message
              * Required
          
        Returns:
            ScheduledMessage
    */
    public ScheduledMessage getScheduledMessageById(String id) throws IOException
    {
        return new ScheduledMessage(api, (JSONObject) api.doRequest("GET", getBaseApiPath() + "/scheduled/" + id));
    }

    /**
        project.initScheduledMessageById(id)
        
        Initializes the scheduled message with the given ID without making an API request.
        
        Arguments:
          - id
              * ID of the scheduled message
              * Required
          
        Returns:
            ScheduledMessage
    */
    public ScheduledMessage initScheduledMessageById(String id)
    {
        return new ScheduledMessage(api, Util.options("project_id", get("id"), "id", id), false);
    }

    /**
        project.queryServices(options)
        
        Queries services within the given project.
        
        Arguments:
          - options (JSONObject)
            
            - name
                * Filter services by name
                * Allowed modifiers: name[ne], name[prefix], name[not_prefix], name[gte], name[gt],
                    name[lt], name[lte]
            
            - active (bool)
                * Filter services by active/inactive state
            
            - context
                * Filter services that can be invoked in a particular context
                * Allowed values: message, contact, project, receipt
            
            - sort
                * Sort the results based on a field
                * Allowed values: default, priority, name
                * Default: default
            
            - sort_dir
                * Sort the results in ascending or descending order
                * Allowed values: asc, desc
                * Default: asc
            
            - page_size (int)
                * Number of results returned per page (max 200)
                * Default: 50
            
            - offset (int)
                * Number of items to skip from beginning of result set
                * Default: 0
          
        Returns:
            APICursor (of Service)
    */
    public APICursor<Service> queryServices(JSONObject options)
    {
        return api.newCursor(Service.class, getBaseApiPath() + "/services", options);
    }

    public APICursor<Service> queryServices()
    {
        return queryServices(null);
    }

    /**
        project.getServiceById(id)
        
        Retrieves the service with the given ID.
        
        Arguments:
          - id
              * ID of the service
              * Required
          
        Returns:
            Service
    */
    public Service getServiceById(String id) throws IOException
    {
        return new Service(api, (JSONObject) api.doRequest("GET", getBaseApiPath() + "/services/" + id));
    }

    /**
        project.initServiceById(id)
        
        Initializes the service with the given ID without making an API request.
        
        Arguments:
          - id
              * ID of the service
              * Required
          
        Returns:
            Service
    */
    public Service initServiceById(String id)
    {
        return new Service(api, Util.options("project_id", get("id"), "id", id), false);
    }

    /**
        project.queryReceipts(options)
        
        Queries mobile money receipts within the given project.
        
        Arguments:
          - options (JSONObject)
            
            - tx_id
                * Filter receipts by transaction ID
            
            - tx_type
                * Filter receipts by transaction type
                * Allowed values: receive_money, send_money, pay_bill, deposit, withdrawal,
                    airtime_purchase, balance_inquiry, reversal
            
            - tx_time (UNIX timestamp)
                * Filter receipts by transaction time
                * Allowed modifiers: tx_time[ne], tx_time[min], tx_time[max]
            
            - name
                * Filter receipts by other person's name
                * Allowed modifiers: name[ne], name[prefix], name[not_prefix], name[gte], name[gt],
                    name[lt], name[lte]
            
            - phone_number
                * Filter receipts by other person's phone number
                * Allowed modifiers: phone_number[ne], phone_number[prefix],
                    phone_number[not_prefix], phone_number[gte], phone_number[gt], phone_number[lt],
                    phone_number[lte]
            
            - sort
                * Sort the results based on a field
                * Allowed values: default
                * Default: default
            
            - sort_dir
                * Sort the results in ascending or descending order
                * Allowed values: asc, desc
                * Default: asc
            
            - page_size (int)
                * Number of results returned per page (max 200)
                * Default: 50
            
            - offset (int)
                * Number of items to skip from beginning of result set
                * Default: 0
          
        Returns:
            APICursor (of MobileMoneyReceipt)
    */
    public APICursor<MobileMoneyReceipt> queryReceipts(JSONObject options)
    {
        return api.newCursor(MobileMoneyReceipt.class, getBaseApiPath() + "/receipts", options);
    }

    public APICursor<MobileMoneyReceipt> queryReceipts()
    {
        return queryReceipts(null);
    }

    /**
        project.getReceiptById(id)
        
        Retrieves the mobile money receipt with the given ID.
        
        Arguments:
          - id
              * ID of the mobile money receipt
              * Required
          
        Returns:
            MobileMoneyReceipt
    */
    public MobileMoneyReceipt getReceiptById(String id) throws IOException
    {
        return new MobileMoneyReceipt(api, (JSONObject) api.doRequest("GET", getBaseApiPath() + "/receipts/" + id));
    }

    /**
        project.initReceiptById(id)
        
        Initializes the mobile money receipt with the given ID without making an API request.
        
        Arguments:
          - id
              * ID of the mobile money receipt
              * Required
          
        Returns:
            MobileMoneyReceipt
    */
    public MobileMoneyReceipt initReceiptById(String id)
    {
        return new MobileMoneyReceipt(api, Util.options("project_id", get("id"), "id", id), false);
    }

    /**
        project.queryRoutes(options)
        
        Queries custom routes that can be used to send messages (not including Phones).
        
        Arguments:
          - options (JSONObject)
            
            - name
                * Filter routes by name
                * Allowed modifiers: name[ne], name[prefix], name[not_prefix], name[gte], name[gt],
                    name[lt], name[lte]
            
            - sort
                * Sort the results based on a field
                * Allowed values: default, name
                * Default: default
            
            - sort_dir
                * Sort the results in ascending or descending order
                * Allowed values: asc, desc
                * Default: asc
            
            - page_size (int)
                * Number of results returned per page (max 200)
                * Default: 50
            
            - offset (int)
                * Number of items to skip from beginning of result set
                * Default: 0
          
        Returns:
            APICursor (of Route)
    */
    public APICursor<Route> queryRoutes(JSONObject options)
    {
        return api.newCursor(Route.class, getBaseApiPath() + "/routes", options);
    }

    public APICursor<Route> queryRoutes()
    {
        return queryRoutes(null);
    }

    /**
        project.getRouteById(id)
        
        Gets a custom route by ID
        
        Arguments:
          - id
              * ID of the route
              * Required
          
        Returns:
            Route
    */
    public Route getRouteById(String id) throws IOException
    {
        return new Route(api, (JSONObject) api.doRequest("GET", getBaseApiPath() + "/routes/" + id));
    }

    /**
        project.initRouteById(id)
        
        Initializes a custom route by ID without making an API request.
        
        Arguments:
          - id
              * ID of the route
              * Required
          
        Returns:
            Route
    */
    public Route initRouteById(String id)
    {
        return new Route(api, Util.options("project_id", get("id"), "id", id), false);
    }

    /**
        project.save()
        
        Saves any fields or custom variables that have changed for the project.
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

    public String getTimezoneId()
    {
        return (String) get("timezone_id");
    }

    @Override
    public String getBaseApiPath()
    {
        return "/projects/" + getId() + "";
    }

    public Project(TelerivetAPI api, JSONObject data)
    {
        this(api, data, true);
    }
    
    public Project(TelerivetAPI api, JSONObject data, boolean isLoaded)
    {
        super(api, data, isLoaded);
    }
}
