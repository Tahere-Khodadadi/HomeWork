package com.maktab.contactmanager.services;

import com.maktab.contactmanager.entity.Contact;
import com.maktab.contactmanager.repository.ContactManagementRepo;

import java.util.*;

public class ContactManagementInMemory implements ContactManagementRepo {

    private final List<Contact> contacts = new ArrayList<>();

    private final Stack<Runnable> undoStack = new Stack<>();

    private int counter = 1;


    @Override
    public void save(Contact contact) throws Exception {
        for (Contact c : contacts) {
            if (c.getPhoneNumber().equals(contact.getPhoneNumber())) {
                throw new Exception("شماره تماس تکراری است: " + contact.getPhoneNumber());
            }
        }

        contact.setId(counter);
        counter++;

        contacts.add(contact);
    }



    @Override
    public void delete(Contact contact) throws Exception {

        for (Contact contact1 : contacts) {
            if (contact1.getName().equals(contact.getName())) {
                throw new Exception(" این مخاطب موجود نیست : " + contact.getName());
            }
        }

        contact.setId(counter);
        counter--;

        contacts.remove(contact);
        Contact found=null;
        final Contact contactToRestore = found;

        undoStack.push(() -> {
            contacts.add(contactToRestore);
            System.out.println("Undo: مخاطب '" + contactToRestore.getName() + "' بازگردانده شد.");
        });
    }


    @Override
    public void edit(String oldName, String oldPhoneNumber, String newName, String newPhoneNumber) throws Exception {

        for (int i = 0; i < contacts.size(); i++) {
            Contact c = contacts.get(i);

            if (c.getName().equals(oldName) && c.getPhoneNumber().equals(oldPhoneNumber)) {

                final String backupOldName = c.getName();
                final String backupOldPhone = c.getPhoneNumber();

                c.setName(newName);
                c.setPhoneNumber(newPhoneNumber);

                undoStack.push(() -> {
                    c.setName(backupOldName);
                    c.setPhoneNumber(backupOldPhone);
                    System.out.println("Undo: ویرایش مخاطب به حالت قبلی بازگشت.");
                });

                return;
            }
        }
        throw new Exception("مخاطبی با نام و شماره تماس قدیمی برای ویرایش یافت نشد.");
    }


    @Override
    public List<Contact> findAll(int pageSize, int pageNumber) throws Exception {

        int startIndex = (pageNumber - 1) * pageSize;
        int totalSize = contacts.size();

        if (startIndex >= totalSize) {
            return Collections.emptyList();
        }

        int endIndex = startIndex + pageSize;
        if (endIndex > totalSize) {
            endIndex = totalSize;
        }

        try {
            //
            return contacts.subList(startIndex, endIndex);
        } catch (IndexOutOfBoundsException e) {
            return Collections.emptyList();
        }
    }

    @Override
    public List<Contact> findByName(String name) throws Exception {
        List<Contact> found = new ArrayList<>();

        for (Contact c : contacts) {
            if (c.getName().equalsIgnoreCase(name)) {
                found.add(c);
            }
        }
        return found;
    }

    @Override
    public List<Contact> findByNumber(String phoneNumber) throws Exception {
        List<Contact> found = new ArrayList<>();

        for (Contact c : contacts) {
            if (c.getPhoneNumber().equals(phoneNumber)) {
                found.add(c);
            }
        }
        return found;
    }

    @Override
    public void undo() throws Exception {
        //
        if (undoStack.isEmpty()) {
            throw new Exception("هیچ عملیاتی برای بازگردانی (Undo) وجود ندارد.");
        }
        undoStack.pop().run();
    }
}