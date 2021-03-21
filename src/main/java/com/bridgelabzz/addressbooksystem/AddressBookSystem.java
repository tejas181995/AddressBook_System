package com.bridgelabzz.addressbooksystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class AddressBookSystem {
    ArrayList<Contact> contactBook = new ArrayList<Contact>();
    public static AddressBookSystem addressBook = new AddressBookSystem();
    public static HashMap<String, AddressBookSystem> addressBooks = new HashMap<>();
    //public static HashMap<String, ArrayList<Contact>> byState = new HashMap<>();
    //public static HashMap<String, ArrayList<Contact>> byCity = new HashMap<>();
    public static Scanner sc = new Scanner(System.in);



    public int addContacts(Contact contact) {
        int index = contactBook.indexOf(contact);
        if(index == -1) {
            contactBook.add(contact);
            System.out.println("contect added");
            contact.printContact();
//            ArrayList<Contact> currStateList = byState.get(contact.values[4]);
//            if(currStateList == null)
//                currStateList = new ArrayList<>();
//            currStateList.add(contact);
//            byState.put(contact.values[4], currStateList);
//            ArrayList<Contact> currCityList = byState.get(contact.values[3]);
//            if(currCityList == null)
//                currCityList = new ArrayList<>();
//            currCityList.add(contact);
//            byCity.put(contact.values[3], currCityList);


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
        //  sortAddressBook();
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
        int index ;

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
    public static void searchBy(int field, String region){
        addressBook.contactBook.stream().filter(contact -> {
            return contact.values[field].equals(region);
        }).forEach(c -> c.printContact());


    }
    public static void totalContacts(int field, String region){

        System.out.println(addressBook.contactBook.stream().filter(contact -> {
            return contact.values[field].equals(region);
        }).count());
    }
    public static void sortAddressBook(int index){
        addressBook.contactBook.sort((Contact c1, Contact c2) -> {
            String name1 = c1.values[index] ;
            String name2 = c2.values[index] ;
            return name1.compareTo(name2);
        });
        System.out.println("Successfully Sorted.........!!!!!!!!");
    }
    public static void sortByparams() {
        int choice_ = 0;

        System.out.println("Enter choice for sorting \n0. Sort by name  \n1. sort by city \n2. sort by state \n3. sort by zip");
        choice_ = sc.nextInt();
        switch (choice_) {
            case 0:
                sortAddressBook(0);
                break;
            case 1:
                sortAddressBook(3);
                break;
            case 2:
                sortAddressBook(4);
                break;
            case 3:
                sortAddressBook(5);
                break;
            default:
                System.out.println("invalid choice");
                sortByparams();
        }

    }
    public static void main(String[] args) {
        addressBooks.put("default", addressBook);
        int choice = 0;

        while(choice != 10){
            System.out.println("0.Add Address book \n1. Add contact \n2. Edit contact \n3.delete contact \n4. view all contacts. \n5. search Contact by city. \n6. search contact by state. \n7 no of contact by city \n8. no of contact by state \n9.paramert to search \n10.exit");
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
                    sc.nextLine();
                    System.out.println("Enter city Name");
                    String city = sc.nextLine();
                    searchBy(3, city);
                    break;
                case 6:
                    System.out.println("Enter state Name");
                    sc.nextLine();
                    String state = sc.nextLine();
                    searchBy(4, state);
                    break;
                case 7:
                    System.out.println("Enter city Name");
                    sc.nextLine();
                    String city1 = sc.nextLine();
                    totalContacts(3, city1);
                    break;
                case 8:
                    System.out.println("Enter state Name");
                    sc.nextLine();
                    String state1 = sc.nextLine();
                    totalContacts(4, state1);
                    break;
                case 9:
                    sortByparams();
                    break;
                case 10:
                    System.out.println("thank you..!!!");
                    break;
            }
        }
    }


}
