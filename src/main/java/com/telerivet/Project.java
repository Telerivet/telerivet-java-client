
package com.telerivet;
        
import java.io.IOException;
import org.json.JSONObject;
import org.json.JSONArray;
        
/**
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
        Sends one message (SMS or USSD request).
    */
    public Message sendMessage(JSONObject options) throws IOException
    {
        return new Message(api, (JSONObject) api.doRequest("POST", getBaseApiPath() + "/messages/send", options));
    }

    /**
        Sends an SMS message (optionally with mail-merge templates) to a group or a list of up to
        500 phone numbers
    */
    public JSONObject sendMessages(JSONObject options) throws IOException
    {
        return (JSONObject) api.doRequest("POST", getBaseApiPath() + "/messages/send_batch", options);
    }

    /**
        Schedules an SMS message to a group or single contact. Note that Telerivet only sends
        scheduled messages approximately once per minute, so it is not possible to control the exact
        second at which a scheduled message is sent.
    */
    public ScheduledMessage scheduleMessage(JSONObject options) throws IOException
    {
        return new ScheduledMessage(api, (JSONObject) api.doRequest("POST", getBaseApiPath() + "/scheduled", options));
    }

    /**
        Add an incoming message to Telerivet. Acts the same as if the message was received by a
        phone. Also triggers any automated services that apply to the message.
    */
    public Message receiveMessage(JSONObject options) throws IOException
    {
        return new Message(api, (JSONObject) api.doRequest("POST", getBaseApiPath() + "/messages/receive", options));
    }

    /**
        Retrieves OR creates and possibly updates a contact by name or phone number.
        
        If a phone number is provided, Telerivet will search for an existing
        contact with that phone number (including suffix matches to allow finding contacts with
        phone numbers in a different format).
        
        If a phone number is not provided but a name is provided, Telerivet
        will search for a contact with that exact name (case insensitive).
        
        If no existing contact is found, a new contact will be created.
        
        Then that contact will be updated with any parameters provided
        (name, phone_number, and vars).
    */
    public Contact getOrCreateContact(JSONObject options) throws IOException
    {
        return new Contact(api, (JSONObject) api.doRequest("POST", getBaseApiPath() + "/contacts", options));
    }

    /**
        Queries contacts within the given project.
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
        Retrieves the contact with the given ID.
    */
    public Contact getContactById(String id) throws IOException
    {
        return new Contact(api, (JSONObject) api.doRequest("GET", getBaseApiPath() + "/contacts/" + id));
    }

    /**
        Initializes the Telerivet contact with the given ID without making an API request.
    */
    public Contact initContactById(String id)
    {
        return new Contact(api, Util.options("project_id", get("id"), "id", id), false);
    }

    /**
        Queries phones within the given project.
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
        Retrieves the phone with the given ID.
    */
    public Phone getPhoneById(String id) throws IOException
    {
        return new Phone(api, (JSONObject) api.doRequest("GET", getBaseApiPath() + "/phones/" + id));
    }

    /**
        Initializes the phone with the given ID without making an API request.
    */
    public Phone initPhoneById(String id)
    {
        return new Phone(api, Util.options("project_id", get("id"), "id", id), false);
    }

    /**
        Queries messages within the given project.
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
        Retrieves the message with the given ID.
    */
    public Message getMessageById(String id) throws IOException
    {
        return new Message(api, (JSONObject) api.doRequest("GET", getBaseApiPath() + "/messages/" + id));
    }

    /**
        Initializes the Telerivet message with the given ID without making an API request.
    */
    public Message initMessageById(String id)
    {
        return new Message(api, Util.options("project_id", get("id"), "id", id), false);
    }

    /**
        Queries groups within the given project.
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
        Retrieves or creates a group by name.
    */
    public Group getOrCreateGroup(String name) throws IOException
    {
        return new Group(api, (JSONObject) api.doRequest("POST", getBaseApiPath() + "/groups", Util.options("name", name)));
    }

    /**
        Retrieves the group with the given ID.
    */
    public Group getGroupById(String id) throws IOException
    {
        return new Group(api, (JSONObject) api.doRequest("GET", getBaseApiPath() + "/groups/" + id));
    }

    /**
        Initializes the group with the given ID without making an API request.
    */
    public Group initGroupById(String id)
    {
        return new Group(api, Util.options("project_id", get("id"), "id", id), false);
    }

    /**
        Queries labels within the given project.
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
        Gets or creates a label by name.
    */
    public Label getOrCreateLabel(String name) throws IOException
    {
        return new Label(api, (JSONObject) api.doRequest("POST", getBaseApiPath() + "/labels", Util.options("name", name)));
    }

    /**
        Retrieves the label with the given ID.
    */
    public Label getLabelById(String id) throws IOException
    {
        return new Label(api, (JSONObject) api.doRequest("GET", getBaseApiPath() + "/labels/" + id));
    }

    /**
        Initializes the label with the given ID without making an API request.
    */
    public Label initLabelById(String id)
    {
        return new Label(api, Util.options("project_id", get("id"), "id", id), false);
    }

    /**
        Queries data tables within the given project.
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
        Gets or creates a data table by name.
    */
    public DataTable getOrCreateDataTable(String name) throws IOException
    {
        return new DataTable(api, (JSONObject) api.doRequest("POST", getBaseApiPath() + "/tables", Util.options("name", name)));
    }

    /**
        Retrieves the data table with the given ID.
    */
    public DataTable getDataTableById(String id) throws IOException
    {
        return new DataTable(api, (JSONObject) api.doRequest("GET", getBaseApiPath() + "/tables/" + id));
    }

    /**
        Initializes the data table with the given ID without making an API request.
    */
    public DataTable initDataTableById(String id)
    {
        return new DataTable(api, Util.options("project_id", get("id"), "id", id), false);
    }

    /**
        Queries scheduled messages within the given project.
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
        Retrieves the scheduled message with the given ID.
    */
    public ScheduledMessage getScheduledMessageById(String id) throws IOException
    {
        return new ScheduledMessage(api, (JSONObject) api.doRequest("GET", getBaseApiPath() + "/scheduled/" + id));
    }

    /**
        Initializes the scheduled message with the given ID without making an API request.
    */
    public ScheduledMessage initScheduledMessageById(String id)
    {
        return new ScheduledMessage(api, Util.options("project_id", get("id"), "id", id), false);
    }

    /**
        Queries services within the given project.
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
        Retrieves the service with the given ID.
    */
    public Service getServiceById(String id) throws IOException
    {
        return new Service(api, (JSONObject) api.doRequest("GET", getBaseApiPath() + "/services/" + id));
    }

    /**
        Initializes the service with the given ID without making an API request.
    */
    public Service initServiceById(String id)
    {
        return new Service(api, Util.options("project_id", get("id"), "id", id), false);
    }

    /**
        Queries mobile money receipts within the given project.
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
        Retrieves the mobile money receipt with the given ID.
    */
    public MobileMoneyReceipt getReceiptById(String id) throws IOException
    {
        return new MobileMoneyReceipt(api, (JSONObject) api.doRequest("GET", getBaseApiPath() + "/receipts/" + id));
    }

    /**
        Initializes the mobile money receipt with the given ID without making an API request.
    */
    public MobileMoneyReceipt initReceiptById(String id)
    {
        return new MobileMoneyReceipt(api, Util.options("project_id", get("id"), "id", id), false);
    }

    /**
        Queries custom routes that can be used to send messages (not including Phones).
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
        Gets a custom route by ID
    */
    public Route getRouteById(String id) throws IOException
    {
        return new Route(api, (JSONObject) api.doRequest("GET", getBaseApiPath() + "/routes/" + id));
    }

    /**
        Initializes a custom route by ID without making an API request.
    */
    public Route initRouteById(String id)
    {
        return new Route(api, Util.options("project_id", get("id"), "id", id), false);
    }

    /**
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
