package com.bridgelabzz.addressbooksystem;

import java.util.ArrayList;
import java.util.Scanner;

public class AddressBookSystem {

    ArrayList<Contact> contactBook = new ArrayList<Contact>();

    public int addContacts(Contact contact) {
        contactBook.add(contact);
        contact.printContact();
        return contactBook.size();

    }

    public int findContact(String firstName) {
        for (int i = 0; i < contactBook.size(); i++) {
            if (contactBook.get(i).values[0].equals(firstName))
                return i;
        }
        return -1;
    }
    public Contact getContact(int index){
        return contactBook.get(index);
    }

    public void editContact(int index, int field, String val){
        contactBook.get(index).values[field] = val;
    }

    public static void main(String[] args) {
        int  noOfContacts;
        int reply = 1;
        int present = 0;
        AddressBookSystem addressBook = new AddressBookSystem();
        Scanner sc = new Scanner(System.in);
        System.out.print("\nEnter Contact to be saved: ");
        noOfContacts = sc.nextInt();
        sc.nextLine();
        for (int j = 0; j < noOfContacts; j++) {
            String[] values = new String[8];
            for (int i = 0; i < values.length; i++) {
                System.out.print("\nenter" + Contact.fields[i] + ": ");
                values[i] = sc.nextLine();
            }
            Contact contact = new Contact(values);
            // contact.printContact();
            addressBook.addContacts(contact);
        }
        System.out.println("Enter Contact to be search");
        String searchName = sc.nextLine();
        present = addressBook.findContact(searchName);
        if(present >= 0){
            addressBook.getContact(present).printContact();
            while(reply == 1){
                System.out.println("enter choice to edit = ");
                for(int i=0; i< Contact.fields.length; i++){
                    System.out.println(i +1 + " :" + " " + Contact.fields[i]);
                }
                int choice = sc.nextInt();
                sc.nextLine();
                System.out.println("Enter new value of "+ Contact.fields[choice - 1]);
                String newVal = sc.nextLine();
                addressBook.editContact(present, choice-1, newVal);
                addressBook.getContact(present).printContact();
                System.out.println("want to make more changes then press 1");
                reply =  sc.nextInt();
            }
        }else{
            System.out.println("Contact not presnt");
        }

    }
}
