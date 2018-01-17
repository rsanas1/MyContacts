package com.invoice2go.rishi.mycontacts.Model;

/**
 * Created by rishi on 1/15/2018.
 */

public interface ModelInterface {

     void getAllContacts();
     void saveContact(Contact contact);
     void updateContact(Contact contact);
     void getMatchingContacts(String query);
     void deleteContact(Contact contact);

}
