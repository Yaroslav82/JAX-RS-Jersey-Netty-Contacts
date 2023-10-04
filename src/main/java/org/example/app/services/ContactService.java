package org.example.app.services;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.app.entity.Contact;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Path("/api/v1.0/contacts")
@Produces({MediaType.APPLICATION_JSON})
public class ContactService {

    private static final List<Contact> contactList;

    static {
        contactList = new ArrayList<>();
        contactList.add(new Contact(1L, "John", "+380123541298"));
        contactList.add(new Contact(2L, "Bob", "+380831006478"));
        contactList.add(new Contact(3L, "Ron", "+380639550124"));
        contactList.add(new Contact(4L, "Frank", "+380739625106"));
    }

    @GET
    public List<Contact> getContacts() {
        return contactList;
    }

    @GET
    @Path("{id: [0-9]+}")
    public Contact getContactById(@PathParam("id") Long id) {
        Contact contact = new Contact();
        contact.setId(id);

        int index = Collections.binarySearch(contactList, contact, Comparator.comparing(Contact::getId));

        if (index >= 0) {
            return contactList.get(index);
        } else {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
    }
}
