
package com.telerivet;

import java.io.IOException;
import org.json.JSONObject;
import org.json.JSONArray;

/**
    <div class='markdown'><p>Represents a Telerivet project.</p>
    
    <p>Provides methods for sending and scheduling messages, as well as
    accessing, creating and updating a variety of entities, including contacts, messages,
    scheduled messages, groups, labels, phones, services, and data tables.</p>
    
    <p>Fields:</p>
    
    <ul>
    <li><p>id (string, max 34 characters)</p>
    
    <ul>
    <li>ID of the project</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>name</p>
    
    <ul>
    <li>Name of the project</li>
    <li>Updatable via API</li>
    </ul></li>
    <li><p>timezone_id</p>
    
    <ul>
    <li>Default TZ database timezone ID; see <a target="_blank" rel="noopener" href="http://en.wikipedia.org/wiki/List_of_tz_database_time_zones">List of tz database time zones Wikipedia
      article</a>.</li>
    <li>Updatable via API</li>
    </ul></li>
    <li><p>url_slug</p>
    
    <ul>
    <li>Unique string used as a component of the project's URL in the Telerivet web app</li>
    <li>Updatable via API</li>
    </ul></li>
    <li><p>default_route_id</p>
    
    <ul>
    <li>The ID of a basic route or custom route that will be used to send messages by
      default (via both the API and web app), unless a particular route ID is specified when
      sending the message.</li>
    <li>Updatable via API</li>
    </ul></li>
    <li><p>auto_create_contacts (bool)</p>
    
    <ul>
    <li>If true, a contact will be automatically created for each unique phone number that a
      message is sent to or received from. If false, contacts will not automatically be
      created (unless contact information is modified by an automated service). The
      Conversations tab in the web app will only show messages that are associated with a
      contact.</li>
    <li>Updatable via API</li>
    </ul></li>
    <li><p>message_retention_days (int)</p>
    
    <ul>
    <li>Number of days to retain messages in this project. Messages older than this will be
      automatically deleted. If null, messages will be retained forever.</li>
    <li>Updatable via API</li>
    </ul></li>
    <li><p>short_link_scheme (bool)</p>
    
    <ul>
    <li>If true (the default), short links in messages will include the scheme (e.g.,
      'https://rvt.me/xxxxxxxxx'). If false, short links will not include the scheme (e.g.,
      'rvt.me/xxxxxxxxx').</li>
    <li>Updatable via API</li>
    </ul></li>
    <li><p>vars (JSONObject)</p>
    
    <ul>
    <li>Custom variables stored for this project. Variable names may be up to 32 characters
      in length and can contain the characters a-z, A-Z, 0-9, and _.
      Values may be strings, numbers, or boolean (true/false).
      String values may be up to 4096 bytes in length when encoded as UTF-8.
      Up to 100 variables are supported per object.
      Setting a variable to null will delete the variable.</li>
    <li>Updatable via API</li>
    </ul></li>
    <li><p>organization_id (string, max 34 characters)</p>
    
    <ul>
    <li>ID of the organization this project belongs to</li>
    <li>Read-only</li>
    </ul></li>
    </ul>
    </div>
*/
public class Project extends Entity
{
    /**
        <div class='markdown'><p>Sends one message (SMS, MMS, chat app message, voice call, or USSD request).</p>
        </div>
    */
    public Message sendMessage(JSONObject options) throws IOException
    {
        return new Message(api, (JSONObject) api.doRequest("POST", getBaseApiPath() + "/messages/send", options));
    }

    /**
        <div class='markdown'><p>Sends a text message (optionally with mail-merge templates) or voice call to a group or a
        list of up to 500 phone numbers.</p>
        
        <p>With <code>message_type</code>=<code>service</code>, invokes an automated service (such as
        a poll) for a group or list of phone numbers. Any service that can be triggered for a
        contact can be invoked via this method, whether or not the service actually sends a message.</p>
        </div>
    */
    public Broadcast sendBroadcast(JSONObject options) throws IOException
    {
        return new Broadcast(api, (JSONObject) api.doRequest("POST", getBaseApiPath() + "/send_broadcast", options));
    }

    /**
        <div class='markdown'><p>Sends up to 100 different messages in a single API request. This method is significantly
        faster than sending a separate API request for each message.</p>
        </div>
    */
    public JSONObject sendMulti(JSONObject options) throws IOException
    {
        return (JSONObject) api.doRequest("POST", getBaseApiPath() + "/send_multi", options);
    }

    /**
        <div class='markdown'><p>(Deprecated) Send a message a to group or a list of phone numbers.
        This method is only needed to maintain backward compatibility with
        code developed using previous versions of the client library.
        Use <code>sendBroadcast</code> or <code>sendMulti</code> instead.</p>
        </div>
    */
    public JSONObject sendMessages(JSONObject options) throws IOException
    {
        return (JSONObject) api.doRequest("POST", getBaseApiPath() + "/messages/send_batch", options);
    }

    /**
        <div class='markdown'><p>Schedules a message to a group or single contact. Note that Telerivet only sends scheduled
        messages approximately once every 15 seconds, so it is not possible to control the exact
        second at which a scheduled message is sent.</p>
        
        <p>Only one of the parameters group_id, to_number, and contact_id
        should be provided.</p>
        
        <p>With <code>message_type</code>=<code>service</code>, schedules an automated service (such
        as a poll) to be invoked for a group or an individual contact. Any service that can be
        triggered for a contact can be scheduled via this method, whether or not the service
        actually sends a message.</p>
        </div>
    */
    public ScheduledMessage scheduleMessage(JSONObject options) throws IOException
    {
        return new ScheduledMessage(api, (JSONObject) api.doRequest("POST", getBaseApiPath() + "/scheduled", options));
    }

    /**
        <div class='markdown'><p>Creates a relative scheduled message. This allows scheduling messages on a different date
        for each contact, for example on their birthday, a certain number of days before an
        appointment, or a certain number of days after enrolling in a campaign.</p>
        
        <p>Telerivet will automatically create a
        <a href="#ScheduledMessage">ScheduledMessage</a> for each contact matching a RelativeScheduledMessage.</p>
        
        <p>Relative scheduled messages can be created for a group or an
        individual contact, although dynamic groups are not supported. Only one of the parameters
        group_id, to_number, and contact_id should be provided.</p>
        
        <p>With message_type=service, schedules an automated service (such as a
        poll). Any service that can be triggered for a contact can be scheduled via this method,
        whether or not the service actually sends a message.</p>
        </div>
    */
    public RelativeScheduledMessage createRelativeScheduledMessage(JSONObject options) throws IOException
    {
        return new RelativeScheduledMessage(api, (JSONObject) api.doRequest("POST", getBaseApiPath() + "/relative_scheduled", options));
    }

    /**
        <div class='markdown'><p>Add an incoming message to Telerivet. Acts the same as if the message was received by a
        phone. Also triggers any automated services that apply to the message.</p>
        </div>
    */
    public Message receiveMessage(JSONObject options) throws IOException
    {
        return new Message(api, (JSONObject) api.doRequest("POST", getBaseApiPath() + "/messages/receive", options));
    }

    /**
        <div class='markdown'><p>Retrieves OR creates and possibly updates a contact by name or phone number.</p>
        
        <p>If a phone number is provided, by default, Telerivet will search for
        an existing contact with that phone number (including suffix matches to allow finding
        contacts with phone numbers in a different format). If a phone number is not provided but a
        name is provided, Telerivet will search for a contact with that exact name (case
        insensitive). This behavior can be modified by setting the <code>lookup_key</code> parameter to look up
        a contact by another field, including a custom variable.</p>
        
        <p>If no existing contact is found, a new contact will be created.</p>
        
        <p>Then that contact will be updated with any parameters provided
        (<code>name</code>, <code>phone_number</code>, <code>vars</code>, <code>default_route_id</code>, <code>send_blocked</code>, <code>add_group_ids</code>,
        <code>remove_group_ids</code>).</p>
        </div>
    */
    public Contact getOrCreateContact(JSONObject options) throws IOException
    {
        return new Contact(api, (JSONObject) api.doRequest("POST", getBaseApiPath() + "/contacts", options));
    }

    /**
        <div class='markdown'><p>Creates and/or updates up to 200 contacts in a single API call. When creating or updating a
        large number of contacts, this method is significantly faster than sending a separate API
        request for each contact.</p>
        
        <p>By default, if the phone number for any contact matches an existing
        contact, the existing contact will be updated with any information provided. This behavior
        can be modified by setting the <code>lookup_key</code> parameter to look up contacts by another field,
        including a custom variable.</p>
        
        <p>If any contact was not found matching the provided <code>lookup_key</code>, a
        new contact will be created.</p>
        </div>
    */
    public JSONObject importContacts(JSONObject options) throws IOException
    {
        return (JSONObject) api.doRequest("POST", getBaseApiPath() + "/import_contacts", options);
    }

    /**
        <div class='markdown'><p>Queries contacts within the given project.</p>
        </div>
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
        <div class='markdown'><p>Retrieves the contact with the given ID.</p>
        </div>
    */
    public Contact getContactById(String id) throws IOException
    {
        return new Contact(api, (JSONObject) api.doRequest("GET", getBaseApiPath() + "/contacts/" + id));
    }

    /**
        <div class='markdown'><p>Initializes the Telerivet contact with the given ID without making an API request.</p>
        </div>
    */
    public Contact initContactById(String id)
    {
        return new Contact(api, Util.options("project_id", get("id"), "id", id), false);
    }

    /**
        <div class='markdown'><p>Queries basic routes within the given project.</p>
        </div>
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
        <div class='markdown'><p>Retrieves the basic route with the given ID.</p>
        </div>
    */
    public Phone getPhoneById(String id) throws IOException
    {
        return new Phone(api, (JSONObject) api.doRequest("GET", getBaseApiPath() + "/phones/" + id));
    }

    /**
        <div class='markdown'><p>Initializes the basic route with the given ID without making an API request.</p>
        </div>
    */
    public Phone initPhoneById(String id)
    {
        return new Phone(api, Util.options("project_id", get("id"), "id", id), false);
    }

    /**
        <div class='markdown'><p>Creates a new basic route (phone) with external API credentials.</p>
        
        <p>For some external API providers, this method automatically updates webhook URLs configured
        with the external provider so that incoming messages/calls and call status events are routed
        to Telerivet. In this case, if you have already configured a webhook URL with the external
        provider, it will be overwritten. For other external API providers, webhook URLs may require
        manual configuration in order for the route to be fully functional.</p>
        
        <p>Note: This API can only create routes that connect to external gateway APIs using your own
        account credentials. To add Android phones, WhatsApp routes, or routes with
        Telerivet-managed billing, use the web app instead.</p>
        
        <p>Credentials are passed via the <code>external_account_id</code>, <code>external_id</code>, <code>external_secret</code>, and
        <code>external_config</code> parameters. The meaning of these parameters for each phone type is as
        follows:</p>
        
        <p><strong>Twilio Virtual Number</strong> (<code>phone_type</code>: twilio):</p>
        
        <ul>
        <li><code>external_id</code> (optional): The SID of your Twilio phone number (starts with 'PN'); if not
        provided, it will be looked up by the phone_number parameter</li>
        <li><code>external_account_id</code> (required): Your Twilio Account SID (starts with 'AC')</li>
        <li><code>external_secret</code> (required): Your Twilio Auth Token</li>
        </ul>
        
        <p><strong>Twilio Messaging Service</strong> (<code>phone_type</code>: twilio_messaging_service):</p>
        
        <ul>
        <li><code>external_id</code> (required): The SID of your Twilio messaging service (starts with 'MG')</li>
        <li><code>external_account_id</code> (required): Your Twilio Account SID (starts with 'AC')</li>
        <li><code>external_secret</code> (required): Your Twilio Auth Token</li>
        </ul>
        
        <p><strong>Twilio Shortcode</strong> (<code>phone_type</code>: twilio_shortcode):</p>
        
        <ul>
        <li><code>external_id</code> (optional): The SID of your Twilio shortcode (starts with 'SC'); if not
        provided, it will be looked up by the phone_number parameter</li>
        <li><code>external_account_id</code> (required): Your Twilio Account SID (starts with 'AC')</li>
        <li><code>external_secret</code> (required): Your Twilio Auth Token</li>
        </ul>
        
        <p><strong>Twilio Caller ID</strong> (<code>phone_type</code>: twilio_caller_id):</p>
        
        <ul>
        <li><code>external_account_id</code> (required): Your Twilio Account SID (starts with 'AC')</li>
        <li><code>external_secret</code> (required): Your Twilio Auth Token</li>
        </ul>
        
        <p><strong>Vonage Virtual Number or Vonage Sender ID</strong> (<code>phone_type</code>: nexmo or nexmo_sender_id):</p>
        
        <ul>
        <li><code>external_account_id</code> (required): Your Vonage API Key</li>
        <li><code>external_secret</code> (required): Your Vonage API Secret</li>
        </ul>
        
        <p><strong>Telesign SMS</strong> (<code>phone_type</code>: telesign):</p>
        
        <ul>
        <li><code>external_account_id</code> (required): Your Telesign Customer ID</li>
        <li><code>external_secret</code> (required): Your Telesign API Key</li>
        <li><code>external_config.message_type</code>: <code>ARN</code>, <code>MKT</code>, or <code>OTP</code></li>
        <li><code>external_config.sender_type</code>: <code>one_way</code> (default) or <code>two_way</code></li>
        </ul>
        
        <p><strong>Infobip SMS</strong> (<code>phone_type</code>: infobip):</p>
        
        <ul>
        <li><code>external_account_id</code> (required): Your Infobip username</li>
        <li><code>external_secret</code> (required): Your Infobip API key</li>
        <li><code>external_config.sender_type</code>: <code>one_way</code> (default) or <code>two_way</code></li>
        <li><code>external_config.india_dlt_principal_entity_id</code>: India DLT Principal Entity ID (only
        needed if sending to India)</li>
        </ul>
        
        <p><strong>Infobip USSD</strong> (<code>phone_type</code>: infobip_ussd):</p>
        
        <ul>
        <li>No external credentials required</li>
        </ul>
        
        <p><strong>Africa's Talking SMS</strong> (<code>phone_type</code>: africas_talking):</p>
        
        <ul>
        <li><code>external_account_id</code> (required): Your Africa's Talking username</li>
        <li><code>external_secret</code> (required): Your Africa's Talking API key</li>
        <li><code>external_config.sender_type</code>: <code>one_way</code> (default) or <code>two_way</code></li>
        </ul>
        
        <p><strong>Africa's Talking USSD</strong> (<code>phone_type</code>: africas_talking_ussd):</p>
        
        <ul>
        <li>No external credentials required</li>
        </ul>
        
        <p><strong>Simulated Phone</strong> (<code>phone_type</code>: simulated):</p>
        
        <ul>
        <li>No external credentials required</li>
        <li>Messages are simulated locally and not sent to any external provider</li>
        <li>Useful for testing</li>
        </ul>
        
        <h4>Webhook URL Setup</h4>
        
        <p>Some route types require manual configuration of callback URLs in the external provider's
        dashboard. When the Phone response includes an <code>external_setup</code> object, you will need to
        configure the webhook URLs in your provider's account settings.</p>
        
        <p>The following phone types require manual webhook setup:</p>
        
        <ul>
        <li><code>africas_talking</code>: <code>message_status_url</code> (always), <code>incoming_message_url</code> (if two_way)</li>
        <li><code>africas_talking_ussd</code>: <code>incoming_call_url</code></li>
        <li><code>infobip_ussd</code>: <code>incoming_call_url</code></li>
        <li><code>twilio_messaging_service</code>: <code>incoming_message_url</code></li>
        <li><code>telesign</code> (two_way only): <code>incoming_message_url</code></li>
        </ul>
        
        <p>Telerivet automatically configures webhooks for the following phone types as follows:</p>
        
        <ul>
        <li><code>twilio</code> - automatically updates incoming SMS and voice configuration for the Twilio
        virtual number associated with the phone_number or external_id parameter</li>
        <li><code>twilio_shortcode</code> - automatically updates incoming SMS configuration for the Twilio short
        code associated with the phone_number or external_id parameter</li>
        <li><code>nexmo</code> - automatically updates incoming SMS and voice configuration for the Vonage
        virtual number matching the phone_number parameter</li>
        <li><code>infobip</code> - automatically updates incoming SMS configuration for the Infobip virtual
        number matching the phone_number parameter or external_id parameter</li>
        </ul>
        </div>
    */
    public Phone createPhone(JSONObject options) throws IOException
    {
        return new Phone(api, (JSONObject) api.doRequest("POST", getBaseApiPath() + "/phones", options));
    }

    /**
        <div class='markdown'><p>Queries messages within the given project.</p>
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
        <div class='markdown'><p>Retrieves the message with the given ID.</p>
        </div>
    */
    public Message getMessageById(String id) throws IOException
    {
        return new Message(api, (JSONObject) api.doRequest("GET", getBaseApiPath() + "/messages/" + id));
    }

    /**
        <div class='markdown'><p>Initializes the Telerivet message with the given ID without making an API request.</p>
        </div>
    */
    public Message initMessageById(String id)
    {
        return new Message(api, Util.options("project_id", get("id"), "id", id), false);
    }

    /**
        <div class='markdown'><p>Queries broadcasts within the given project.</p>
        </div>
    */
    public APICursor<Broadcast> queryBroadcasts(JSONObject options)
    {
        return api.newCursor(Broadcast.class, getBaseApiPath() + "/broadcasts", options);
    }

    public APICursor<Broadcast> queryBroadcasts()
    {
        return queryBroadcasts(null);
    }

    /**
        <div class='markdown'><p>Retrieves the broadcast with the given ID.</p>
        </div>
    */
    public Broadcast getBroadcastById(String id) throws IOException
    {
        return new Broadcast(api, (JSONObject) api.doRequest("GET", getBaseApiPath() + "/broadcasts/" + id));
    }

    /**
        <div class='markdown'><p>Initializes the Telerivet broadcast with the given ID without making an API request.</p>
        </div>
    */
    public Broadcast initBroadcastById(String id)
    {
        return new Broadcast(api, Util.options("project_id", get("id"), "id", id), false);
    }

    /**
        <div class='markdown'><p>Creates and starts an asynchronous task that is applied to all entities matching a filter
        (e.g. contacts, messages, or data rows).
        Tasks are designed to efficiently process a large number of
        entities. When processing a large number of entities,
        tasks are much faster than using the API to query and loop over
        all objects matching a filter.</p>
        
        <p>Several different types of tasks are supported, including
        applying services to contacts, messages, or data rows;
        adding or removing contacts from a group; blocking or unblocking
        sending messages to a contact; updating a custom variable;
        deleting contacts, messages, or data rows; or exporting data to
        CSV.</p>
        
        <p>When using a task to apply a Custom Actions or Cloud Script API
        service (<code>apply_service_to_contacts</code>, <code>apply_service_to_rows</code>, or
        <code>apply_service_to_messages</code>),
        the <code>task</code> variable will be available within the service. The
        service can use custom variables on the task object (e.g. <code>task.vars.example</code>), such as
        to store aggregate statistics for the rows matching the filter.</p>
        </div>
    */
    public Task createTask(JSONObject options) throws IOException
    {
        return new Task(api, (JSONObject) api.doRequest("POST", getBaseApiPath() + "/tasks", options));
    }

    /**
        <div class='markdown'><p>Queries batch tasks within the given project.</p>
        </div>
    */
    public APICursor<Task> queryTasks(JSONObject options)
    {
        return api.newCursor(Task.class, getBaseApiPath() + "/tasks", options);
    }

    public APICursor<Task> queryTasks()
    {
        return queryTasks(null);
    }

    /**
        <div class='markdown'><p>Retrieves the task with the given ID.</p>
        </div>
    */
    public Task getTaskById(String id) throws IOException
    {
        return new Task(api, (JSONObject) api.doRequest("GET", getBaseApiPath() + "/tasks/" + id));
    }

    /**
        <div class='markdown'><p>Initializes the task with the given ID without making an API request.</p>
        </div>
    */
    public Task initTaskById(String id)
    {
        return new Task(api, Util.options("project_id", get("id"), "id", id), false);
    }

    /**
        <div class='markdown'><p>Queries groups within the given project.</p>
        </div>
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
        <div class='markdown'><p>Retrieves or creates a group by name.</p>
        </div>
    */
    public Group getOrCreateGroup(String name) throws IOException
    {
        return new Group(api, (JSONObject) api.doRequest("POST", getBaseApiPath() + "/groups", Util.options("name", name)));
    }

    /**
        <div class='markdown'><p>Retrieves the group with the given ID.</p>
        </div>
    */
    public Group getGroupById(String id) throws IOException
    {
        return new Group(api, (JSONObject) api.doRequest("GET", getBaseApiPath() + "/groups/" + id));
    }

    /**
        <div class='markdown'><p>Initializes the group with the given ID without making an API request.</p>
        </div>
    */
    public Group initGroupById(String id)
    {
        return new Group(api, Util.options("project_id", get("id"), "id", id), false);
    }

    /**
        <div class='markdown'><p>Queries labels within the given project.</p>
        </div>
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
        <div class='markdown'><p>Gets or creates a label by name.</p>
        </div>
    */
    public Label getOrCreateLabel(String name) throws IOException
    {
        return new Label(api, (JSONObject) api.doRequest("POST", getBaseApiPath() + "/labels", Util.options("name", name)));
    }

    /**
        <div class='markdown'><p>Retrieves the label with the given ID.</p>
        </div>
    */
    public Label getLabelById(String id) throws IOException
    {
        return new Label(api, (JSONObject) api.doRequest("GET", getBaseApiPath() + "/labels/" + id));
    }

    /**
        <div class='markdown'><p>Initializes the label with the given ID without making an API request.</p>
        </div>
    */
    public Label initLabelById(String id)
    {
        return new Label(api, Util.options("project_id", get("id"), "id", id), false);
    }

    /**
        <div class='markdown'><p>Queries message templates within the given project.</p>
        </div>
    */
    public APICursor<MessageTemplate> queryMessageTemplates(JSONObject options)
    {
        return api.newCursor(MessageTemplate.class, getBaseApiPath() + "/message_templates", options);
    }

    public APICursor<MessageTemplate> queryMessageTemplates()
    {
        return queryMessageTemplates(null);
    }

    /**
        <div class='markdown'><p>Creates a new message template that can be used when composing or scheduling messages.</p>
        </div>
    */
    public MessageTemplate createMessageTemplate(JSONObject options) throws IOException
    {
        return new MessageTemplate(api, (JSONObject) api.doRequest("POST", getBaseApiPath() + "/message_templates", options));
    }

    /**
        <div class='markdown'><p>Retrieves the message template with the given ID.</p>
        </div>
    */
    public MessageTemplate getMessageTemplateById(String id) throws IOException
    {
        return new MessageTemplate(api, (JSONObject) api.doRequest("GET", getBaseApiPath() + "/message_templates/" + id));
    }

    /**
        <div class='markdown'><p>Initializes the message template with the given ID without making an API request.</p>
        </div>
    */
    public MessageTemplate initMessageTemplateById(String id)
    {
        return new MessageTemplate(api, Util.options("project_id", get("id"), "id", id), false);
    }

    /**
        <div class='markdown'><p>Queries data tables within the given project.</p>
        </div>
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
        <div class='markdown'><p>Gets or creates a data table by name.</p>
        </div>
    */
    public DataTable getOrCreateDataTable(String name) throws IOException
    {
        return new DataTable(api, (JSONObject) api.doRequest("POST", getBaseApiPath() + "/tables", Util.options("name", name)));
    }

    /**
        <div class='markdown'><p>Retrieves the data table with the given ID.</p>
        </div>
    */
    public DataTable getDataTableById(String id) throws IOException
    {
        return new DataTable(api, (JSONObject) api.doRequest("GET", getBaseApiPath() + "/tables/" + id));
    }

    /**
        <div class='markdown'><p>Initializes the data table with the given ID without making an API request.</p>
        </div>
    */
    public DataTable initDataTableById(String id)
    {
        return new DataTable(api, Util.options("project_id", get("id"), "id", id), false);
    }

    /**
        <div class='markdown'><p>Queries scheduled messages within the given project.</p>
        </div>
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
        <div class='markdown'><p>Queries relative scheduled messages within the given project.</p>
        </div>
    */
    public APICursor<RelativeScheduledMessage> queryRelativeScheduledMessages(JSONObject options)
    {
        return api.newCursor(RelativeScheduledMessage.class, getBaseApiPath() + "/relative_scheduled", options);
    }

    public APICursor<RelativeScheduledMessage> queryRelativeScheduledMessages()
    {
        return queryRelativeScheduledMessages(null);
    }

    /**
        <div class='markdown'><p>Retrieves the scheduled message with the given ID.</p>
        </div>
    */
    public ScheduledMessage getScheduledMessageById(String id) throws IOException
    {
        return new ScheduledMessage(api, (JSONObject) api.doRequest("GET", getBaseApiPath() + "/scheduled/" + id));
    }

    /**
        <div class='markdown'><p>Initializes the scheduled message with the given ID without making an API request.</p>
        </div>
    */
    public ScheduledMessage initScheduledMessageById(String id)
    {
        return new ScheduledMessage(api, Util.options("project_id", get("id"), "id", id), false);
    }

    /**
        <div class='markdown'><p>Retrieves the scheduled message with the given ID.</p>
        </div>
    */
    public RelativeScheduledMessage getRelativeScheduledMessageById(String id) throws IOException
    {
        return new RelativeScheduledMessage(api, (JSONObject) api.doRequest("GET", getBaseApiPath() + "/relative_scheduled/" + id));
    }

    /**
        <div class='markdown'><p>Initializes the relative scheduled message with the given ID without making an API request.</p>
        </div>
    */
    public RelativeScheduledMessage initRelativeScheduledMessageById(String id)
    {
        return new RelativeScheduledMessage(api, Util.options("project_id", get("id"), "id", id), false);
    }

    /**
        <div class='markdown'><p>Queries scheduled services within the given project.</p>
        </div>
    */
    public APICursor<ScheduledService> queryScheduledServices(JSONObject options)
    {
        return api.newCursor(ScheduledService.class, getBaseApiPath() + "/scheduled_services", options);
    }

    public APICursor<ScheduledService> queryScheduledServices()
    {
        return queryScheduledServices(null);
    }

    /**
        <div class='markdown'><p>Schedules a service to be triggered at a specified time.</p>
        
        <p>Only services that can be triggered for the project context and
        handle the default event can be scheduled. This includes services with types
        <code>scheduled_actions</code>, <code>scheduled_script</code>, and other service types that support project-level
        triggering.</p>
        
        <p>(Note: To schedule services that are triggered for a contact,
        <a href="#Project.scheduleMessage">project.scheduleMessage</a> should be used instead with
        <code>message_type</code>=<code>service</code>.)</p>
        </div>
    */
    public ScheduledService scheduleService(JSONObject options) throws IOException
    {
        return new ScheduledService(api, (JSONObject) api.doRequest("POST", getBaseApiPath() + "/scheduled_services", options));
    }

    /**
        <div class='markdown'><p>Retrieves the scheduled service with the given ID.</p>
        </div>
    */
    public ScheduledService getScheduledServiceById(String id) throws IOException
    {
        return new ScheduledService(api, (JSONObject) api.doRequest("GET", getBaseApiPath() + "/scheduled_services/" + id));
    }

    /**
        <div class='markdown'><p>Initializes the scheduled service with the given ID without making an API request.</p>
        </div>
    */
    public ScheduledService initScheduledServiceById(String id)
    {
        return new ScheduledService(api, Util.options("project_id", get("id"), "id", id), false);
    }

    /**
        <div class='markdown'><p>Creates a new automated service.</p>
        
        <p>Only certain types of automated services can be created via the API.
        Other types of services can only be created via the web app.</p>
        </div>
    */
    public Service createService(JSONObject options) throws IOException
    {
        return new Service(api, (JSONObject) api.doRequest("POST", getBaseApiPath() + "/services", options));
    }

    /**
        <div class='markdown'><p>Queries services within the given project.</p>
        </div>
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
        <div class='markdown'><p>Retrieves the service with the given ID.</p>
        </div>
    */
    public Service getServiceById(String id) throws IOException
    {
        return new Service(api, (JSONObject) api.doRequest("GET", getBaseApiPath() + "/services/" + id));
    }

    /**
        <div class='markdown'><p>Initializes the service with the given ID without making an API request.</p>
        </div>
    */
    public Service initServiceById(String id)
    {
        return new Service(api, Util.options("project_id", get("id"), "id", id), false);
    }

    /**
        <div class='markdown'><p>Queries service log entries associated with this project.</p>
        
        <p>Note: Service logs are automatically deleted and no longer available
        via the API after approximately one month.</p>
        </div>
    */
    public APICursor<JSONObject> queryServiceLogs(JSONObject options)
    {
        return api.newCursor(JSONObject.class, getBaseApiPath() + "/service_logs", options);
    }

    public APICursor<JSONObject> queryServiceLogs()
    {
        return queryServiceLogs(null);
    }

    /**
        <div class='markdown'><p>Queries custom routes that can be used to send messages (not including Phones).</p>
        </div>
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
        <div class='markdown'><p>Creates a new custom route that can be used to send messages via one or more basic routes
        (phones).</p>
        
        <p>The <code>actions</code> array defines the routing rules.</p>
        </div>
    */
    public Route createRoute(JSONObject options) throws IOException
    {
        return new Route(api, (JSONObject) api.doRequest("POST", getBaseApiPath() + "/routes", options));
    }

    /**
        <div class='markdown'><p>Gets a custom route by ID</p>
        </div>
    */
    public Route getRouteById(String id) throws IOException
    {
        return new Route(api, (JSONObject) api.doRequest("GET", getBaseApiPath() + "/routes/" + id));
    }

    /**
        <div class='markdown'><p>Initializes a custom route by ID without making an API request.</p>
        </div>
    */
    public Route initRouteById(String id)
    {
        return new Route(api, Util.options("project_id", get("id"), "id", id), false);
    }

    /**
        <div class='markdown'><p>Returns an array of user accounts that have access to this project. Each item in the array
        is an object containing <code>id</code>, <code>email</code>, and <code>name</code> properties. (The id corresponds to the
        <code>user_id</code> property of the Message object.)</p>
        </div>
    */
    public JSONArray getUsers() throws IOException
    {
        return (JSONArray) api.doRequest("GET", getBaseApiPath() + "/users");
    }

    /**
        <div class='markdown'><p>Returns information about each airtime transaction.</p>
        </div>
    */
    public APICursor<AirtimeTransaction> queryAirtimeTransactions(JSONObject options)
    {
        return api.newCursor(AirtimeTransaction.class, getBaseApiPath() + "/airtime_transactions", options);
    }

    public APICursor<AirtimeTransaction> queryAirtimeTransactions()
    {
        return queryAirtimeTransactions(null);
    }

    /**
        <div class='markdown'><p>Gets an airtime transaction by ID</p>
        </div>
    */
    public AirtimeTransaction getAirtimeTransactionById(String id) throws IOException
    {
        return new AirtimeTransaction(api, (JSONObject) api.doRequest("GET", getBaseApiPath() + "/airtime_transactions/" + id));
    }

    /**
        <div class='markdown'><p>Initializes an airtime transaction by ID without making an API request.</p>
        </div>
    */
    public AirtimeTransaction initAirtimeTransactionById(String id)
    {
        return new AirtimeTransaction(api, Util.options("project_id", get("id"), "id", id), false);
    }

    /**
        <div class='markdown'><p>Gets a list of all custom fields defined for contacts in this project. The return value is
        an array of objects with the properties 'name', 'variable', 'type', 'order', 'readonly',
        'lookup_key', and 'show_on_conversation'. (Fields are automatically created any time a
        Contact's 'vars' property is updated.)</p>
        </div>
    */
    public JSONArray getContactFields() throws IOException
    {
        return (JSONArray) api.doRequest("GET", getBaseApiPath() + "/contact_fields");
    }

    /**
        <div class='markdown'><p>Allows customizing how a custom contact field is displayed in the Telerivet web app.</p>
        
        <p>The variable path parameter can contain the characters a-z, A-Z,
        0-9, and _, and may be up to 32 characters in length.</p>
        </div>
    */
    public JSONObject setContactFieldMetadata(String variable, JSONObject options) throws IOException
    {
        return (JSONObject) api.doRequest("POST", getBaseApiPath() + "/contact_fields/" + variable, options);
    }

    /**
        <div class='markdown'><p>Gets a list of all custom fields defined for messages in this project. The return value is
        an array of objects with the properties 'name', 'variable', 'type', 'order', and
        'hide_values'. (Fields are automatically created any time a Message's 'vars' property is
        updated.)</p>
        </div>
    */
    public JSONArray getMessageFields() throws IOException
    {
        return (JSONArray) api.doRequest("GET", getBaseApiPath() + "/message_fields");
    }

    /**
        <div class='markdown'><p>Allows customizing how a custom message field is displayed in the Telerivet web app.</p>
        
        <p>The variable path parameter can contain the characters a-z, A-Z,
        0-9, and _, and may be up to 32 characters in length.</p>
        </div>
    */
    public JSONObject setMessageFieldMetadata(String variable, JSONObject options) throws IOException
    {
        return (JSONObject) api.doRequest("POST", getBaseApiPath() + "/message_fields/" + variable, options);
    }

    /**
        <div class='markdown'><p>Retrieves statistics about messages sent or received via Telerivet. This endpoint returns
        historical data that is computed shortly after midnight each day in the project's time zone.
        If the date range includes the current day, it will include statistics for the current day
        so far.</p>
        </div>
    */
    public JSONObject getMessageStats(JSONObject options) throws IOException
    {
        return (JSONObject) api.doRequest("GET", getBaseApiPath() + "/message_stats", options);
    }

    /**
        <div class='markdown'><p>Queries webhooks within the given project.</p>
        </div>
    */
    public APICursor<Webhook> queryWebhooks(JSONObject options)
    {
        return api.newCursor(Webhook.class, getBaseApiPath() + "/webhooks", options);
    }

    public APICursor<Webhook> queryWebhooks()
    {
        return queryWebhooks(null);
    }

    /**
        <div class='markdown'><p>Creates a new webhook that will be triggered when specific events occur within the project.</p>
        
        <p>Note: The Webhook object is not used for notifying your server when
        incoming messages are received. To notify a URL when incoming messages are received, you can
        configure a webhook by <a href="#Project.createService">creating a Service</a> with type
        incoming_message_webhook.</p>
        </div>
    */
    public Webhook createWebhook(JSONObject options) throws IOException
    {
        return new Webhook(api, (JSONObject) api.doRequest("POST", getBaseApiPath() + "/webhooks", options));
    }

    /**
        <div class='markdown'><p>Retrieves the webhook with the given ID.</p>
        </div>
    */
    public Webhook getWebhookById(String id) throws IOException
    {
        return new Webhook(api, (JSONObject) api.doRequest("GET", getBaseApiPath() + "/webhooks/" + id));
    }

    /**
        <div class='markdown'><p>Initializes the webhook with the given ID without making an API request.</p>
        </div>
    */
    public Webhook initWebhookById(String id)
    {
        return new Webhook(api, Util.options("project_id", get("id"), "id", id), false);
    }

    /**
        <div class='markdown'><p>Saves any fields or custom variables that have changed for the project.</p>
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

    public String getTimezoneId()
    {
        return (String) get("timezone_id");
    }

    public void setTimezoneId(String value)
    {
        set("timezone_id", value);
    }

    public String getUrlSlug()
    {
        return (String) get("url_slug");
    }

    public void setUrlSlug(String value)
    {
        set("url_slug", value);
    }

    public String getDefaultRouteId()
    {
        return (String) get("default_route_id");
    }

    public void setDefaultRouteId(String value)
    {
        set("default_route_id", value);
    }

    public Boolean getAutoCreateContacts()
    {
        return (Boolean) get("auto_create_contacts");
    }

    public void setAutoCreateContacts(Boolean value)
    {
        set("auto_create_contacts", value);
    }

    public Integer getMessageRetentionDays()
    {
        return (Integer) get("message_retention_days");
    }

    public void setMessageRetentionDays(Integer value)
    {
        set("message_retention_days", value);
    }

    public Boolean getShortLinkScheme()
    {
        return (Boolean) get("short_link_scheme");
    }

    public void setShortLinkScheme(Boolean value)
    {
        set("short_link_scheme", value);
    }

    public String getOrganizationId()
    {
        return (String) get("organization_id");
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
