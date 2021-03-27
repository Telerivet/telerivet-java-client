
package com.telerivet;

import java.io.IOException;
import org.json.JSONObject;
import org.json.JSONArray;

/**
    <p>Represents a Telerivet project.</p>
    
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
    <li>Default TZ database timezone ID; see
      <a href="http://en.wikipedia.org/wiki/List_of_tz_database_time_zones">http://en.wikipedia.org/wiki/List_of_tz_database_time_zones</a></li>
    <li>Read-only</li>
    </ul></li>
    <li><p>url_slug</p>
    
    <ul>
    <li>Unique string used as a component of the project's URL in the Telerivet web app</li>
    <li>Read-only</li>
    </ul></li>
    <li><p>vars (JSONObject)</p>
    
    <ul>
    <li>Custom variables stored for this project</li>
    <li>Updatable via API</li>
    </ul></li>
    <li><p>organization_id (string, max 34 characters)</p>
    
    <ul>
    <li>ID of the organization this project belongs to</li>
    <li>Read-only</li>
    </ul></li>
    </ul>
*/
public class Project extends Entity
{
    /**
        <p>Sends one message (SMS, MMS, voice call, or USSD request).</p>
    */
    public Message sendMessage(JSONObject options) throws IOException
    {
        return new Message(api, (JSONObject) api.doRequest("POST", getBaseApiPath() + "/messages/send", options));
    }

    /**
        <p>Sends a text message (optionally with mail-merge templates) or voice call to a group or a
        list of up to 500 phone numbers.</p>
        
        <p>With <code>message_type</code>=<code>service</code>, invokes an automated service (such as
        a poll) for a group or list of phone numbers. Any service that can be triggered for a
        contact can be invoked via this method, whether or not the service actually sends a message.</p>
    */
    public Broadcast sendBroadcast(JSONObject options) throws IOException
    {
        return new Broadcast(api, (JSONObject) api.doRequest("POST", getBaseApiPath() + "/send_broadcast", options));
    }

    /**
        <p>Sends up to 100 different messages in a single API request. This method is significantly
        faster than sending a separate API request for each message.</p>
    */
    public JSONObject sendMulti(JSONObject options) throws IOException
    {
        return (JSONObject) api.doRequest("POST", getBaseApiPath() + "/send_multi", options);
    }

    /**
        <p>(Deprecated) Send a message a to group or a list of phone numbers.
        This method is only needed to maintain backward compatibility with
        code developed using previous versions of the client library.
        Use <code>sendBroadcast</code> or <code>sendMulti</code> instead.</p>
    */
    public JSONObject sendMessages(JSONObject options) throws IOException
    {
        return (JSONObject) api.doRequest("POST", getBaseApiPath() + "/messages/send_batch", options);
    }

    /**
        <p>Schedules a message to a group or single contact. Note that Telerivet only sends scheduled
        messages approximately once every 15 seconds, so it is not possible to control the exact
        second at which a scheduled message is sent.</p>
        
        <p>With <code>message_type</code>=<code>service</code>, schedules an automated service (such
        as a poll) to be invoked for a group or list of phone numbers. Any service that can be
        triggered for a contact can be scheduled via this method, whether or not the service
        actually sends a message.</p>
    */
    public ScheduledMessage scheduleMessage(JSONObject options) throws IOException
    {
        return new ScheduledMessage(api, (JSONObject) api.doRequest("POST", getBaseApiPath() + "/scheduled", options));
    }

    /**
        <p>Add an incoming message to Telerivet. Acts the same as if the message was received by a
        phone. Also triggers any automated services that apply to the message.</p>
    */
    public Message receiveMessage(JSONObject options) throws IOException
    {
        return new Message(api, (JSONObject) api.doRequest("POST", getBaseApiPath() + "/messages/receive", options));
    }

    /**
        <p>Retrieves OR creates and possibly updates a contact by name or phone number.</p>
        
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
    */
    public Contact getOrCreateContact(JSONObject options) throws IOException
    {
        return new Contact(api, (JSONObject) api.doRequest("POST", getBaseApiPath() + "/contacts", options));
    }

    /**
        <p>Creates and/or updates up to 200 contacts in a single API call. When creating or updating a
        large number of contacts, this method is significantly faster than sending a separate API
        request for each contact.</p>
        
        <p>By default, if the phone number for any contact matches an existing
        contact, the existing contact will be updated with any information provided. This behavior
        can be modified by setting the <code>lookup_key</code> parameter to look up contacts by another field,
        including a custom variable.</p>
        
        <p>If any contact was not found matching the provided <code>lookup_key</code>, a
        new contact will be created.</p>
    */
    public JSONObject importContacts(JSONObject options) throws IOException
    {
        return (JSONObject) api.doRequest("POST", getBaseApiPath() + "/import_contacts", options);
    }

    /**
        <p>Queries contacts within the given project.</p>
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
        <p>Retrieves the contact with the given ID.</p>
    */
    public Contact getContactById(String id) throws IOException
    {
        return new Contact(api, (JSONObject) api.doRequest("GET", getBaseApiPath() + "/contacts/" + id));
    }

    /**
        <p>Initializes the Telerivet contact with the given ID without making an API request.</p>
    */
    public Contact initContactById(String id)
    {
        return new Contact(api, Util.options("project_id", get("id"), "id", id), false);
    }

    /**
        <p>Queries phones within the given project.</p>
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
        <p>Retrieves the phone with the given ID.</p>
    */
    public Phone getPhoneById(String id) throws IOException
    {
        return new Phone(api, (JSONObject) api.doRequest("GET", getBaseApiPath() + "/phones/" + id));
    }

    /**
        <p>Initializes the phone with the given ID without making an API request.</p>
    */
    public Phone initPhoneById(String id)
    {
        return new Phone(api, Util.options("project_id", get("id"), "id", id), false);
    }

    /**
        <p>Queries messages within the given project.</p>
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
        <p>Retrieves the message with the given ID.</p>
    */
    public Message getMessageById(String id) throws IOException
    {
        return new Message(api, (JSONObject) api.doRequest("GET", getBaseApiPath() + "/messages/" + id));
    }

    /**
        <p>Initializes the Telerivet message with the given ID without making an API request.</p>
    */
    public Message initMessageById(String id)
    {
        return new Message(api, Util.options("project_id", get("id"), "id", id), false);
    }

    /**
        <p>Queries broadcasts within the given project.</p>
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
        <p>Retrieves the broadcast with the given ID.</p>
    */
    public Broadcast getBroadcastById(String id) throws IOException
    {
        return new Broadcast(api, (JSONObject) api.doRequest("GET", getBaseApiPath() + "/broadcasts/" + id));
    }

    /**
        <p>Initializes the Telerivet broadcast with the given ID without making an API request.</p>
    */
    public Broadcast initBroadcastById(String id)
    {
        return new Broadcast(api, Util.options("project_id", get("id"), "id", id), false);
    }

    /**
        <p>Creates and starts an asynchronous task that is applied to all entities matching a filter
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
    */
    public Task createTask(JSONObject options) throws IOException
    {
        return new Task(api, (JSONObject) api.doRequest("POST", getBaseApiPath() + "/tasks", options));
    }

    /**
        <p>Queries batch tasks within the given project.</p>
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
        <p>Retrieves the task with the given ID.</p>
    */
    public Task getTaskById(String id) throws IOException
    {
        return new Task(api, (JSONObject) api.doRequest("GET", getBaseApiPath() + "/tasks/" + id));
    }

    /**
        <p>Initializes the task with the given ID without making an API request.</p>
    */
    public Task initTaskById(String id)
    {
        return new Task(api, Util.options("project_id", get("id"), "id", id), false);
    }

    /**
        <p>Queries groups within the given project.</p>
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
        <p>Retrieves or creates a group by name.</p>
    */
    public Group getOrCreateGroup(String name) throws IOException
    {
        return new Group(api, (JSONObject) api.doRequest("POST", getBaseApiPath() + "/groups", Util.options("name", name)));
    }

    /**
        <p>Retrieves the group with the given ID.</p>
    */
    public Group getGroupById(String id) throws IOException
    {
        return new Group(api, (JSONObject) api.doRequest("GET", getBaseApiPath() + "/groups/" + id));
    }

    /**
        <p>Initializes the group with the given ID without making an API request.</p>
    */
    public Group initGroupById(String id)
    {
        return new Group(api, Util.options("project_id", get("id"), "id", id), false);
    }

    /**
        <p>Queries labels within the given project.</p>
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
        <p>Gets or creates a label by name.</p>
    */
    public Label getOrCreateLabel(String name) throws IOException
    {
        return new Label(api, (JSONObject) api.doRequest("POST", getBaseApiPath() + "/labels", Util.options("name", name)));
    }

    /**
        <p>Retrieves the label with the given ID.</p>
    */
    public Label getLabelById(String id) throws IOException
    {
        return new Label(api, (JSONObject) api.doRequest("GET", getBaseApiPath() + "/labels/" + id));
    }

    /**
        <p>Initializes the label with the given ID without making an API request.</p>
    */
    public Label initLabelById(String id)
    {
        return new Label(api, Util.options("project_id", get("id"), "id", id), false);
    }

    /**
        <p>Queries data tables within the given project.</p>
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
        <p>Gets or creates a data table by name.</p>
    */
    public DataTable getOrCreateDataTable(String name) throws IOException
    {
        return new DataTable(api, (JSONObject) api.doRequest("POST", getBaseApiPath() + "/tables", Util.options("name", name)));
    }

    /**
        <p>Retrieves the data table with the given ID.</p>
    */
    public DataTable getDataTableById(String id) throws IOException
    {
        return new DataTable(api, (JSONObject) api.doRequest("GET", getBaseApiPath() + "/tables/" + id));
    }

    /**
        <p>Initializes the data table with the given ID without making an API request.</p>
    */
    public DataTable initDataTableById(String id)
    {
        return new DataTable(api, Util.options("project_id", get("id"), "id", id), false);
    }

    /**
        <p>Queries scheduled messages within the given project.</p>
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
        <p>Retrieves the scheduled message with the given ID.</p>
    */
    public ScheduledMessage getScheduledMessageById(String id) throws IOException
    {
        return new ScheduledMessage(api, (JSONObject) api.doRequest("GET", getBaseApiPath() + "/scheduled/" + id));
    }

    /**
        <p>Initializes the scheduled message with the given ID without making an API request.</p>
    */
    public ScheduledMessage initScheduledMessageById(String id)
    {
        return new ScheduledMessage(api, Util.options("project_id", get("id"), "id", id), false);
    }

    /**
        <p>Queries services within the given project.</p>
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
        <p>Retrieves the service with the given ID.</p>
    */
    public Service getServiceById(String id) throws IOException
    {
        return new Service(api, (JSONObject) api.doRequest("GET", getBaseApiPath() + "/services/" + id));
    }

    /**
        <p>Initializes the service with the given ID without making an API request.</p>
    */
    public Service initServiceById(String id)
    {
        return new Service(api, Util.options("project_id", get("id"), "id", id), false);
    }

    /**
        <p>Queries custom routes that can be used to send messages (not including Phones).</p>
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
        <p>Gets a custom route by ID</p>
    */
    public Route getRouteById(String id) throws IOException
    {
        return new Route(api, (JSONObject) api.doRequest("GET", getBaseApiPath() + "/routes/" + id));
    }

    /**
        <p>Initializes a custom route by ID without making an API request.</p>
    */
    public Route initRouteById(String id)
    {
        return new Route(api, Util.options("project_id", get("id"), "id", id), false);
    }

    /**
        <p>Returns an array of user accounts that have access to this project. Each item in the array
        is an object containing <code>id</code>, <code>email</code>, and <code>name</code> properties. (The id corresponds to the
        <code>user_id</code> property of the Message object.)</p>
    */
    public JSONArray getUsers() throws IOException
    {
        return (JSONArray) api.doRequest("GET", getBaseApiPath() + "/users");
    }

    /**
        <p>Returns information about each airtime transaction.</p>
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
        <p>Gets an airtime transaction by ID</p>
    */
    public AirtimeTransaction getAirtimeTransactionById(String id) throws IOException
    {
        return new AirtimeTransaction(api, (JSONObject) api.doRequest("GET", getBaseApiPath() + "/airtime_transactions/" + id));
    }

    /**
        <p>Initializes an airtime transaction by ID without making an API request.</p>
    */
    public AirtimeTransaction initAirtimeTransactionById(String id)
    {
        return new AirtimeTransaction(api, Util.options("project_id", get("id"), "id", id), false);
    }

    /**
        <p>Saves any fields or custom variables that have changed for the project.</p>
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

    public String getUrlSlug()
    {
        return (String) get("url_slug");
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
