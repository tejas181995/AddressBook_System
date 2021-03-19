package com.bridgelabzz.addressbooksystem;

import java.util.Scanner;

public class AddressBookSystem {

    public static void main(String[] args) {
        String[] values = new String[8];
        Scanner sc = new Scanner(System.in);
        for (int i = 0; i < values.length; i++) {
            System.out.println("enter" + Contact.fields[i] + ": ");
            values[i] = sc.nextLine();
        }
        Contact contact = new Contact(values);
        contact.printContact();
    }
}
