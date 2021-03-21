package com.bridgelabzz.addressbooksystem;

public class Contact {
    public static final String[] fields = new String[]{"firstName", "lastName", "address", "city", "state", "zipCode",
            "phoneNumber", "email"};
    public String[] values;

    public Contact(String[] values) {
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

    @Override
    public String toString() {
        String contactInfo;
        contactInfo = "Name: " + values[0] + " " +  values[1];
        for(int i =2; i<fields.length; i ++){
            contactInfo = contactInfo + "\n" + fields[i] + ": " + values[i];
        }
        return contactInfo;
    }

    private boolean isEquivalent(Contact contact) {
        return (this.values[0].equals(contact.values[0]) && this.values[1].equals(contact.values[1]));
    }
}
