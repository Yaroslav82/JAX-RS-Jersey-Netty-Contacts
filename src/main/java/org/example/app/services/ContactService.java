package org.example.app.services;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.app.entity.Contact;

import java.net.URI;
import java.util.*;

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

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public Response createContact(Contact contact) {
        if (Objects.isNull(contact.getId())) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }

        int index = Collections.binarySearch(contactList, contact, Comparator.comparing(Contact::getId));

        if (index < 0) {
            contactList.add(contact);
            return Response
                    .status(Response.Status.CREATED)
                    .location(URI.create("/api/v1.0/contacts/" + contact.getId()))
                    .build();
        } else {
            throw new WebApplicationException(Response.Status.CONFLICT);
        }
    }

    @PUT
    @Path("{id: [0-9]+}")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response updateContact(@PathParam("id") Long id, Contact contact) {
        contact.setId(id);

        int index = Collections.binarySearch(contactList, contact, Comparator.comparing(Contact::getId));

        if (index >= 0) {
            Contact updatedContact = contactList.get(index);

            if (Objects.nonNull(contact.getName())) {
                updatedContact.setName(contact.getName());
            }

            if (Objects.nonNull(contact.getPhone())) {
                updatedContact.setPhone(contact.getPhone());
            }

            return Response
                    .status(Response.Status.NO_CONTENT)
                    .build();
        } else {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
    }
}
