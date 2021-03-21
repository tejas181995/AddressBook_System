package com.bridgelabzz.addressbooksystem;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Contact {
    //    public static final String[] fields = new String[]{"firstName", "lastName", "address", "city", "state", "zipCode",
//            "phoneNumber", "email"};
    public static enum fields{
        firstName, lastName, address, city, state,zipCode, phoneNumber, email;
    }

    public HashMap<fields, String> values;

    public Contact(HashMap<fields, String> values) {
        this.values = values;

    }

    public void printContact() {

        System.out.println(this.toString());
    }

    @Override
    public boolean equals(Object contact) {
        if(contact instanceof Contact){
            return isEquivalent((Contact) contact);
        }
        return false;
    }
    private boolean isEquivalent(Contact contact) {
        return (this.values.get(fields.firstName).equals(contact.values.get(fields.firstName)) &&
                this.values.get(fields.lastName).equals(contact.values.get(fields.lastName)));
    }

    @Override
    public String toString() {
        String contactInfo;
        contactInfo = "Name: " + values.get(fields.firstName) + " " +  values.get(fields.lastName);
        List<fields> field = Arrays.asList(fields.values());
        for(int i =2; i<field.size(); i ++){
            contactInfo = contactInfo + "\n" + field.get(i) + ": " + values.get(field.get(i));
        }
        return contactInfo;
    }
    public String CSVformat(String bookname){
        String format = bookname;
        List<fields> field = Arrays.asList(fields.values());
        for(fields f: field){
            format = format + " " + values.get(f);
        }
        return format;
    }
}
