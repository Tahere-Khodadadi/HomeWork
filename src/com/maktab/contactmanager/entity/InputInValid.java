package com.maktab.contactmanager.entity;

import java.util.regex.Pattern;

public class InputInValid extends Exception {

    private Contact contact;

    public  InputInValid(String message) {
        super(message);

    }
    public static String validateName(String name) throws InputInValid {
        if (name == null || name.trim().isEmpty())
            throw new InputInValid("نام نمی‌تواند خالی باشد.");
        if (name.trim().length() > 64) {
            throw new InputInValid("نام نباید بیش از 64 کاراکتر باشد.");
        }
        return name;
    }
    public static Integer validateNumber (String phoneNumber) throws InputInValid {
        if(phoneNumber.length()>12){
            throw new InputInValid("شماره تماس نباید بیشتر از 12 عدد باشد.");
        }
                 // چک کردن که ورودی فقط عدد باشد(digit)
        if (!phoneNumber.matches("\\d+")){
            System.out.println(" شماره تلفن فقط شامل عدد میباشد ");
        }
        return Integer.valueOf(phoneNumber);
    }

}
