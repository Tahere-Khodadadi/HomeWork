package com.maktab.contactmanager.services;

import com.maktab.contactmanager.entity.Contact;
import com.maktab.contactmanager.entity.InputInValid;

import java.util.List;

public interface ContactManagement {

    void addContact(List<Contact> contacts) throws InputInValid;

    void deleteContact(List<String >contactName)throws InputInValid;

    void editContact(String oldName,String phoneNumber1, String newName,String phoneNumber2) throws InputInValid;

    void showAllContact(int pageSize, int pageNumber)throws InputInValid;

    void findByName(List<String >contactName)throws InputInValid;

    void findByNumber(List<String>contactNumber)throws InputInValid;

    void undoContact()throws InputInValid;
}
