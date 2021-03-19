package com.bridgelabzz.addressbooksystem;

public class Contact {
    public static final String[] fields = new String[] { "firstName", "lastName", "address", "city", "state", "zipCode",
            "phoneNumber", "email" };
    public String[] values;

    public Contact(String[] values) {
        this.values = values;

    }

    public void printContact() {
        for (int i = 0; i < fields.length; i++) {
            System.out.println(fields[i] + ":" + values[i]);
        }
    }

}
