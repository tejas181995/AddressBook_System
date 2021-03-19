package com.bridgelabzz.addressbooksystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class AddressBookSystem {

    ArrayList<Contact> contactBook = new ArrayList<Contact>();
    public static AddressBookSystem addressBook = new AddressBookSystem();
    public static HashMap<String, AddressBookSystem> addressBooks = new HashMap<>();

    public int addContacts(Contact contact) {
        int index = contactBook.indexOf(contact);
        if(index == -1) {
            contactBook.add(contact);
            System.out.println("contect added");


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

    public void deleteContact(int index){
        contactBook.remove(index);
    }

    public void printall(){
        for(Contact a:contactBook){
            System.out.println("------------------------------------------------");
            a.printContact();
        }
        System.out.println("------------------------------------------------");

    }
    public static void addEntries(){
        int  noOfContacts;

        Scanner sc = new Scanner(System.in);
        System.out.print("\nEnter Contact to be saved: ");
        noOfContacts = sc.nextInt();
        sc.nextLine();
        for (int j = 0; j < noOfContacts; j++) {
            String[] values = new String[8];
            for (int i = 0; i < values.length; i++) {
                System.out.print("\nenter " + Contact.fields[i] + ": ");
                values[i] = sc.nextLine();
            }
            Contact contact = new Contact(values);
            addressBook.addContacts(contact);
        }
    }

    public static void editEntry(){
        int reply = 1;
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter Contact to be edit");
        String searchName = sc.nextLine();
        int index = addressBook.findContact(searchName);
        if(index >= 0){
            addressBook.getContact(index).printContact();
            while(reply == 1){
                System.out.println("enter choice to edit = ");
                for(int i=0; i< Contact.fields.length; i++){
                    System.out.println(i +1 + " :" + " " + Contact.fields[i]);
                }
                int choice = sc.nextInt();
                sc.nextLine();
                System.out.println("Enter new value of "+ Contact.fields[choice - 1]);
                String newVal = sc.nextLine();
                addressBook.editContact(index, choice-1, newVal);
                addressBook.getContact(index).printContact();
                System.out.println("want to make more changes then press 1");
                reply =  sc.nextInt();
            }
        }else{
            System.out.println("Contact not presnt");
        }
    }
    public static void deleteEntry(){
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter contact Name to be deleted");
        String deleteName = sc.nextLine();
        int index = addressBook.findContact(deleteName);

        index = addressBook.findContact(deleteName);
        if(index >=0){
            addressBook.getContact(index).printContact();
            addressBook.deleteContact(index);
            System.out.println("Contact Deleted successfully");
        }else{
            System.out.println("contact not found");
        }

    }

    public static void addAddressBook(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter name of new address book");
        String name = sc.nextLine() ;
        addressBook  = new AddressBookSystem();
        addressBooks.put(name, addressBook) ;
        System.out.println("New addressbook added ");
        showAddressBooks();
    }
    public static void showAddressBooks(){
        System.out.println("Available address books are");
        for(Map.Entry<String,AddressBookSystem> e: addressBooks.entrySet() ){
            System.out.println(e.getKey());
        }
    }
    public static void searchByCity(String city){
        for(Map.Entry<String, AddressBookSystem> e:addressBooks.entrySet()){
            AddressBookSystem current = e.getValue();
            for(Contact c : current.getContact()){
                if(c.values[3].equals(city)){
                    c.printContact();
                }

            }
        }
    }
    public static void searchByState(String state){
        for(Map.Entry<String, AddressBookSystem> e:addressBooks.entrySet()){
            AddressBookSystem current = e.getValue();
            for(Contact c : current.getContact()){
                if(c.values[4].equals(state)){
                    c.printContact();
                }

            }
        }
    }
    public static void main(String[] args) {
        addressBooks.put("default", addressBook);
        int choice = 0;
        Scanner sc = new Scanner(System.in);

        while(choice != 7){
            System.out.println("0.Add Address book \n1. Add contact \n2. Edit contact \n3.delete contact \n4. view all contacts. \n5. search Contact by city. \n6. search contact by state. \n7 Exit");
            System.out.print("\nEnter choice: ");
            choice = sc.nextInt();

            switch(choice){
                case 0:
                    addAddressBook();
                    break;
                case 1:
                    addEntries();
                    break;
                case 2:
                    editEntry();
                    break;
                case 3:
                    deleteEntry();
                    break;
                case 4:
                    addressBook.printall();
                    break;
                case 5:
                    searchByCity("Satara");
                    break;
                case 6:
                    searchByState("Maha");
                    break;
                case 7:
                    System.out.println("thank you..!!!");
                    break;

            }
        }
    }

}
