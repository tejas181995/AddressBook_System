package com.bridgelabzz.addressbooksystem;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.opencsv.CSVWriter;
import java.sql.Connection;

import java.io.*;
import java.lang.reflect.Type;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class AddressBookDemo {
    public static AddressBookSystem addressBook = new AddressBookSystem();
    public static HashMap<String, AddressBookSystem> addressBooks = new HashMap<>();
    public static String currAddressBook = "default";
    public static Scanner sc = new Scanner(System.in);

    public static void addEntries(){
        int  noOfContacts;
        Scanner sc = new Scanner(System.in);
        System.out.print("\nEnter Contact to be saved: ");
        noOfContacts = sc.nextInt();
        sc.nextLine();
        for (int j = 0; j < noOfContacts; j++) {
            HashMap<Contact.fields, String> contact = new HashMap<>();
            List<Contact.fields> field = Arrays.asList(Contact.fields.values());

            for (int i = 0; i < field.size(); i++) {
                System.out.print("\nenter " + field.get(i) + ": ");
                String val = sc.nextLine();
                contact.put(field.get(i), val) ;
            }
            Contact newContact = new Contact(contact);
            addressBook.addContacts(newContact);
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
                List<Contact.fields> field = Arrays.asList(Contact.fields.values());
                for(int i=0; i<field.size(); i++){
                    System.out.println(i +1 + " :" + " " + field.get(i));
                }
                int choice = sc.nextInt();
                sc.nextLine();
                System.out.println("Enter new value of "+ field.get(choice - 1));
                String newVal = sc.nextLine();
                addressBook.editContact(index, field.get(choice - 1), newVal);
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

    public static void switchAddressbook(){
        int i=0;
        for(Map.Entry<String,AddressBookSystem> e: addressBooks.entrySet() ){
            System.out.println((i++) + "." + " " + e.getKey());
        }
        int choice = sc.nextInt();
        currAddressBook = addressBooks.entrySet().stream().map(x -> x.getKey()).collect(Collectors.toList()).get(choice);
        addressBook = addressBooks.get(currAddressBook);

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

    public static void searchBy(Contact.fields field, String region){
        addressBook.contactBook.stream().filter(contact -> {
            return contact.values.get(field).equals(region);
        }).forEach(c -> c.printContact());


    }

    public static void totalContacts(Contact.fields field, String region){

        System.out.println(addressBook.contactBook.stream().filter(contact -> {
            return contact.values.get(field).equals(region);
        }).count());
    }

    public static void sortAddressBook(Contact.fields field){
        addressBook.contactBook.sort((Contact c1, Contact c2) -> {
            String name1 = c1.values.get(field);
            String name2 = c2.values.get(field) ;
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
                sortAddressBook(Contact.fields.firstName);
                break;
            case 1:
                sortAddressBook(Contact.fields.city);
                break;
            case 2:
                sortAddressBook(Contact.fields.state);
                break;
            case 3:
                sortAddressBook(Contact.fields.zipCode);
                break;
            default:
                System.out.println("invalid choice");
                sortByparams();
        }
    }

    public static void saveToCSV() {
        String csvFile = "addressbook.csv";
        try {
            CSVWriter writer = new CSVWriter(new FileWriter(csvFile));
            String headers[] = new String[]{"Bookname",  "firstName", "lastName", "address",
                    "city", "state","zipCode", "phoneNumber", "email"};
            writer.writeNext(headers);
            List<String[]> alldata = new ArrayList<>();
            for(Map.Entry<String, AddressBookSystem> e: addressBooks.entrySet()){
                for(Contact c: e.getValue().contactBook){
                    alldata.add(c.CSVformat(e.getKey()).split(" "));
                }
            }
            writer.writeAll(alldata);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveToJSON() {
        Gson gson = new GsonBuilder().create();
        try {
            String data = gson.toJson(addressBooks);
            FileWriter writer = new FileWriter("addressBook.json");
            writer.write(data);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveData(){
        saveToCSV();
        saveToJSON();

    }

    public static void loadDatafromJson() {
        Gson gson = new Gson();
        Type type = new TypeToken<HashMap<String, AddressBookSystem>>(){}.getType();
        try {


            addressBooks = gson.fromJson(new FileReader("addressBook.json"), type);
        } catch (FileNotFoundException e) {
            addressBooks = new HashMap<>();
        }
        if(addressBooks == null) addressBooks = new HashMap<>();
    }
    public static void loadData() {
        String jdbcURL = "jdbc:mysql://localhost:3306/Address_book_service?allowPublicKeyRetrieval=true&useSSL=false";
        String userName = "tejas";
        String password = "Password@123";
        Connection connection;
        System.out.println("Connecting to database:" + jdbcURL);
        try {

            connection = DriverManager.getConnection(jdbcURL, userName, password);
            ResultSet res = connection.createStatement().executeQuery("SELECT * from AddressBookTable");
            while(res.next()){
                HashMap<Contact.fields, String> map = new HashMap<>();
                map.put(Contact.fields.firstName, res.getString("firstName"));
                map.put(Contact.fields.lastName, res.getString("lastname"));
                map.put(Contact.fields.address, res.getString("address"));
                map.put(Contact.fields.city, res.getString("city"));
                map.put(Contact.fields.state, res.getString("state"));
                map.put(Contact.fields.zipCode, String.valueOf(res.getInt("zip")));
                map.put(Contact.fields.phoneNumber, res.getString("phoneNumber"));
                map.put(Contact.fields.email, res.getString("email"));
                AddressBookSystem curr = addressBooks.get(res.getString("addressbookname"));
                if(curr == null)
                    curr = new AddressBookSystem();
                curr.addContacts(new Contact(map));
                addressBooks.put(res.getString("addressbookname"), curr);
            }
        } catch (SQLException throwables) {
            System.out.println("Unable to retrive data");
        }


    }
    public static void main(String[] args) {
        int choice = 0;
        loadData();
        addressBook = addressBooks.get("default");
        if(addressBook == null){
            addressBook = new AddressBookSystem();
            addressBooks.put("default", addressBook);
        }
        while(choice != 11){
            System.out.println("0.Add Address book \n1. Add contact \n2. Edit contact \n3.delete contact \n4. view all contacts. \n5. search Contact by city. \n6. search contact by state." +
                    " \n7 no of contact by city \n8. no of contact by state \n9.paramert to search \n10. switch addressbook \n11.exit");
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
                    searchBy(Contact.fields.city, city);
                    break;
                case 6:
                    System.out.println("Enter state Name");
                    sc.nextLine();
                    String state = sc.nextLine();
                    searchBy(Contact.fields.state, state);
                    break;
                case 7:
                    System.out.println("Enter city Name");
                    sc.nextLine();
                    String city1 = sc.nextLine();
                    totalContacts(Contact.fields.city, city1);
                    break;
                case 8:
                    System.out.println("Enter state Name");
                    sc.nextLine();
                    String state1 = sc.nextLine();
                    totalContacts(Contact.fields.state, state1);
                    break;
                case 9:
                    sortByparams();
                    break;
                case 10:
                    System.out.println("Select address book");
                    switchAddressbook();
                    break;
                case 11:
                    saveData();
                    System.out.println("thank you..!!!");
                    break;
            }
        }
    }
}
