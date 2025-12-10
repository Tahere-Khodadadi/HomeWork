package com.maktab.contactmanager.repository;

import com.maktab.contactmanager.entity.Contact;

import java.util.List;

public interface ContactManagementRepo {


    void save(Contact contact) throws Exception;
    void delete(Contact contact)throws Exception;
    List<Contact> findByNumber(String phoneNumber) throws Exception;
    List<Contact> findByName(String name)throws Exception;
    void undo() throws Exception;
    List<Contact> findAll(int pageSize, int pageNumber) throws Exception;
   void edit(String oldName,String phoneNumber1, String newName,String phoneNumber2)throws Exception;
}
