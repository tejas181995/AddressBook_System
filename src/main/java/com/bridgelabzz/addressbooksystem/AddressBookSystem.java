package com.bridgelabzz.addressbooksystem;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class AddressBookSystem {

    ArrayList<Contact> contactBook = new ArrayList<Contact>();

    public int addContacts(Contact contact) {
        int index = contactBook.indexOf(contact);
        if(index == -1) {
            contactBook.add(contact);
            System.out.println("contect added");
            contact.printContact();


        }else{
            System.out.println("Contact Already Exist");
            getContact(index).printContact();

        }
        return contactBook.size();


    }
    public ArrayList<Contact> getContact(){
        return contactBook;
    }


    public int findContact(String firstName) {
        for (int i = 0; i < contactBook.size(); i++) {
            if (contactBook.get(i).values.get(Contact.fields.firstName).equals(firstName))
                return i;
        }
        return -1;
    }
    public Contact getContact(int index){
        return contactBook.get(index);
    }

    public void editContact(int index, Contact.fields field, String val){
        contactBook.get(index).values.put(field, val) ;
    }

    public void deleteContact(int index){
        contactBook.remove(index);
    }

    public void printall(){
        //  sortAddressBook();
        for(Contact a:contactBook){
            System.out.println("------------------------------------------------");
            a.printContact();
        }
        System.out.println("------------------------------------------------");

    }

}
