Java client library for Telerivet REST API

http://telerivet.com/api

Overview
--------
This library makes it easy to integrate your Java application with Telerivet.
You can use it to:

- send SMS messages via an Android phone or SMS gateway service
- update contact information in Telerivet (e.g. from a signup form on your own website)
- add or remove contacts from groups
- export your message/contact data from Telerivet into your own systems
- schedule messages to be sent at a later time
- control automated services
- much more

All API methods are fully documented at https://telerivet.com/api/rest/java ,
as well as in the comments of the Java source files.

See the example_project folder for an example project using this API.

System Requirements
-------------------
JRE
Apache Maven (to build from source)

Installation
------------
A pre-compiled version of this library is distributed using Maven Central.

When using Maven, simply add the following dependency to your pom.xml:

```
    <dependency>
        <groupId>com.telerivet</groupId>
        <artifactId>TelerivetAPIClient</artifactId>
        <version>1.4.2</version>
    </dependency>
```

If you want to build the Java client library from source instead of using Maven Central,
clone this repository then run `mvn install` from the root folder.

Example Usage
-------------

```
import com.telerivet.*;
import java.io.IOException;

public class Test
{
    public static void main(String[] args) throws IOException {

        String API_KEY = "YOUR_API_KEY";  // from https://telerivet.com/api/keys
        String PROJECT_ID = "YOUR_PROJECT_ID";

        TelerivetAPI tr = new TelerivetAPI(API_KEY);

        Project project = tr.initProjectById(PROJECT_ID);

        // Send a SMS message
        project.sendMessage(Util.options(
            "to_number", "555-0001",
            "content", "Hello world!"
        ));

        // Note: Util.options(...) creates a JSONObject from key, value parameters

        // Query contacts
        String name_prefix = "John";
        APICursor<Contact> cursor = project.queryContacts(Util.options(
            "name[prefix]", name_prefix,
            "sort", "name"
        )).limit(20);

        System.out.println(cursor.count() + " contacts matching " +name_prefix+ ":");

        while (cursor.hasNext())
        {
            Contact contact = cursor.next();
            System.out.println(contact.getName() + " " + contact.getPhoneNumber()
                + " " + contact.vars().get("birthdate"));
        }

        // Import a contact
        Contact contact = project.getOrCreateContact(Util.options(
            "name", "John Smith",
            "phone_number", "555-0001",
            "vars", Util.options(
                "birthdate", "1981-03-04",
                "network", "Vodacom"
            )
        ));

        // Add a contact to a group
        Group group = project.getOrCreateGroup("Subscribers");
        contact.addToGroup(group);
    }
}
```