package com.maktab.contactmanager.services;

import com.maktab.contactmanager.entity.Contact;
import com.maktab.contactmanager.entity.InputInValid;
import com.maktab.contactmanager.repository.ContactManagementRepo;

import java.util.List;

public class ContactManagementImpl implements ContactManagement {

    private final ContactManagementRepo repository;


    public ContactManagementImpl(ContactManagementRepo repository) {
        this.repository = repository;
    }


    @Override
    public void addContact(List<Contact> contacts) throws InputInValid {
        if (contacts == null || contacts.isEmpty()) {
            throw new InputInValid("لیست مخاطبین برای افزودن نباید خالی باشد.");
        }

        try {
            for (Contact contact : contacts) {
                InputInValid.validateName(contact.getName());
                InputInValid.validateNumber(contact.getPhoneNumber());

                repository.save(contact);
            }
        } catch (Exception e) {
            throw new InputInValid("خطا در افزودن مخاطب: " + e.getMessage());
        }
    }


    @Override
    public void deleteContact(List<String> contactNames) throws InputInValid {
        if (contactNames == null || contactNames.isEmpty()) {
            throw new InputInValid("لیست نام‌ها برای حذف نباید خالی باشد.");
        }

        try {
            for (String name : contactNames) {
                List<Contact> foundContacts = repository.findByName(name);

                if (foundContacts.isEmpty()) {
                    throw new InputInValid("مخاطبی با نام '" + name + "' برای حذف یافت نشد.");
                }

                repository.delete(foundContacts.get(0));
            }
        } catch (Exception e) {
            throw new InputInValid("خطا در حذف مخاطب: " + e.getMessage());
        }
    }

    @Override
    public void editContact(String oldName, String oldPhoneNumber, String newName, String newPhoneNumber) throws InputInValid {

        if (oldName == null || oldPhoneNumber == null || newName == null || newPhoneNumber == null) {
            throw new InputInValid("هیچ‌یک از پارامترهای ویرایش نباید خالی باشند.");
        }

        try {

            repository.edit(oldName, oldPhoneNumber, newName, newPhoneNumber);
            System.out.println(" ویرایش مخاطب با موفقیت انجام شد.");

        } catch (Exception e) {
            throw new InputInValid("خطا در ویرایش مخاطب: " + e.getMessage());
        }
    }


    @Override

        public void showAllContact(int pageSize, int pageNumber) throws InputInValid {


            if (pageSize <= 0 || pageNumber <= 0) {
                throw new InputInValid("اندازه صفحه (pageSize) و شماره صفحه (pageNumber) باید مثبت باشند.");
            }

            try {
                List<Contact> contacts = repository.findAll(pageSize, pageNumber);


                if (contacts.isEmpty()) {
                    if (pageNumber == 1) {
                        System.out.println("⚠️ هیچ مخاطبی در سیستم ثبت نشده است.");
                    } else {
                        System.out.println("⚠️ این صفحه خالی است. صفحه آخر لیست است.");
                    }
                    return;
                }

                System.out.println("\n--- لیست مخاطبین (صفحه " + pageNumber + "/" + pageSize + ") ---");
                for (Contact contact : contacts) {
                    System.out.println(contact);
                }
                System.out.println("----------------------------------------\n");

            } catch (Exception e) {
                // 4. مدیریت و تبدیل خطا
                System.err.println(" خطای دیتابیس در نمایش لیست: " + e.getMessage());
                throw new InputInValid("خطا در بارگیری لیست مخاطبین: " + e.getMessage());
            }
    }

    @Override
    public void findByName(List<String> contactNames) throws InputInValid {
        if (contactNames == null || contactNames.isEmpty()) return;

        try {
            for (String name : contactNames) {
                List<Contact> found = repository.findByName(name);

                if (found.isEmpty()) {
                    System.out.println(" مخاطبی با نام '" + name + "' یافت نشد.");
                } else {
                    System.out.println(" مخاطبین یافت شده برای '" + name + "':");
                    found.forEach(System.out::println);
                }
            }
        } catch (Exception e) {
            throw new InputInValid(" خطا در نمایش مخاطب بر اساس نام " + e.getMessage());

        }
    }

    @Override
    public void findByNumber(List<String> contactNumbers) throws InputInValid {
        // 1. بررسی لیست ورودی
        if (contactNumbers == null || contactNumbers.isEmpty()) {
            throw new InputInValid("لیست شماره تماس‌ها برای جستجو نباید خالی باشد.");
        }

        try {
            System.out.println("\n--- شروع جستجو بر اساس شماره تماس ---");


            for (String number : contactNumbers) {

                List<Contact> found = repository.findByNumber(number);

                if (found.isEmpty()) {
                    System.out.println("  مخاطبی با شماره '" + number + "' یافت نشد.");
                } else {
                    System.out.println(" مخاطبین یافت شده برای شماره '" + number + "':");

                    found.forEach(System.out::println);
                }
            }
            System.out.println("--------------------------------------\n");

        } catch (Exception e) {

            throw new InputInValid("خطا در جستجو بر اساس شماره: " + e.getMessage());
        }
    }

    @Override
    public void undoContact() throws InputInValid {
        try {

            repository.undo();

            System.out.println(" عملیات بازگردانی  با موفقیت انجام شد.");

        } catch (Exception e) {

            throw new InputInValid("خطا در عملیات بازگردانی: " + e.getMessage());
        }
    }

}