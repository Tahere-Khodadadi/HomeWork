package com.maktab.contactmanager.controller;

import com.maktab.contactmanager.entity.Contact;
import com.maktab.contactmanager.entity.InputInValid;
import com.maktab.contactmanager.services.ContactManagement;

import java.util.Collections;
import java.util.Scanner;

public class UI {
    private final ContactManagement service;
    private final Scanner scanner;


    public UI(ContactManagement service, Scanner scanner) {
        this.service = service;
        this.scanner = scanner;
    }

    public void startMenu() {
        int choice =-1;

        while (choice != 0) {
            printMenu();

            try {

                String input = scanner.nextLine();
                if (input.trim().isEmpty()) continue;

                choice = Integer.parseInt(input);

                switch (choice) {
                    case 1:
                        addContact();
                        break;
                    case 2:
                        deleteContact();
                        break;
                    case 3:
                        editContact();
                        break;
                    case 4:
                        showAllContact();
                        break;
                    case 5:
                        findByName();
                        break;
                    case 6:
                        findByNumber();
                        break;
                    case 7:
                        service.undoContact();
                        break;
                    case 0:
                        System.out.println(" خروج از برنامه. ");
                        break;
                    default:
                        System.out.println(" لطفا عددی بین 0 تا 7 وارد کنید.");
                }
            } catch (NumberFormatException e) {
                System.err.println(" خطای ورودی: لطفا فقط عدد وارد کنید.");
            } catch (InputInValid e) {

                System.err.println(" خطای عملیاتی: " + e.getMessage());
            } catch (Exception e) {
                System.err.println(" خطای غیرمنتظره در سیستم: " + e.getMessage());
            }
        }
    }


    private void printMenu() {
        System.out.println("\n==================================");
        System.out.println("=====  منوی مدیریت مخاطبین =====");
        System.out.println("==================================");
        System.out.println("1. افزودن مخاطب (Add Contact)");
        System.out.println("2. حذف مخاطب (Delete Contact)");
        System.out.println("3. ویرایش مخاطب (Edit Contact)");
        System.out.println("4. نمایش لیست (صفحه‌بندی) (Show All)");
        System.out.println("5. جستجو بر اساس نام (Find by Name)");
        System.out.println("6. جستجو بر اساس شماره (Find by Number)");
        System.out.println("7. بازگردانی عملیات (Undo)");
        System.out.println("0. خروج (Exit)");
        System.out.print(" انتخاب شما: ");
    }


    private void addContact() throws InputInValid {
        System.out.print("نام: ");
        String name = scanner.nextLine();
        System.out.print("شماره تماس: ");
        String number = scanner.nextLine();

        Contact newContact = new Contact(name, number);
        service.addContact(Collections.singletonList(newContact));
    }

    private void deleteContact() throws InputInValid {
        System.out.print("نام مخاطب مورد نظر برای حذف: ");
        String name = scanner.nextLine();
        service.deleteContact(Collections.singletonList(name));
    }

    private void editContact() throws InputInValid {
        System.out.println("--- ویرایش مخاطب ---");
        System.out.print("نام قدیمی مخاطب: ");
        String oldName = scanner.nextLine();
        System.out.print("شماره قدیمی مخاطب: ");
        String oldNumber = scanner.nextLine();

        System.out.print("نام جدید: ");
        String newName = scanner.nextLine();
        System.out.print("شماره جدید: ");
        String newNumber = scanner.nextLine();

        service.editContact(oldName, oldNumber, newName, newNumber);
    }

    private void showAllContact() throws InputInValid {
        int page = 1;
        final int pageSize = 5;

        while (true) {
            System.out.println("\n------------------------------------");
            System.out.println("صفحه فعلی: " + page + " | تعداد در صفحه: " + pageSize);
            System.out.println("------------------------------------");

            service.showAllContact(pageSize, page);

            System.out.println("دستورات: [N] صفحه بعد | [P] صفحه قبل | [M] بازگشت به منوی اصلی");
            System.out.print("دستور: ");
            String command = scanner.nextLine().toUpperCase();

            if (command.equals("N")) {
                page++;
            } else if (command.equals("P") && page > 1) {
                page--;
            } else if (command.equals("M")) {
                break;
            } else if (command.equals("P") && page == 1) {
                System.out.println(" شما در صفحه اول هستید.");
            } else {
                System.out.println(" دستور نامعتبر.");
            }
        }
    }

    private void findByName() throws InputInValid {
        System.out.print("نام یا بخشی از نام برای جستجو: ");
        String name = scanner.nextLine();
        service.findByName(Collections.singletonList(name));
    }

    private void findByNumber() throws InputInValid {
        System.out.print("شماره تماس برای جستجو: ");
        String number = scanner.nextLine();
        service.findByNumber(Collections.singletonList(number));
    }
}
