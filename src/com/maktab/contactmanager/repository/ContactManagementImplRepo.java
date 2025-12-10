package com.maktab.contactmanager.repository;

import com.maktab.contactmanager.entity.Contact;
import com.maktab.contactmanager.util.ApplicationContext;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContactManagementImplRepo implements ContactManagementRepo {
    private Contact lastDeletedContact = null;

    private Connection getConnection() throws Exception {

        return DriverManager.getConnection(
                ApplicationContext.DATABASE_URL,
                ApplicationContext.DATABASE_USER,
                ApplicationContext.PASSWORD

        );
    }
    @Override
    public void save(Contact contact) throws Exception {
        String sql = "INSERT INTO contact (name,phoneNumber)" + "VALUES(? ,? )";
        try (Connection con = getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(sql)) {

            preparedStatement.setString(1, contact.getName());
            preparedStatement.setString(2, contact.getPhoneNumber());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(" عملیات ثبت مخاطب انجام نشد " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void delete(Contact contact) throws SQLException {
        String sql = "delete from contact where id=?";
        try (Connection con = getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(sql)) {
            this.lastDeletedContact = new Contact();
            this.lastDeletedContact.setId(contact.getId());
            this.lastDeletedContact.setName(contact.getName());
            this.lastDeletedContact.setPhoneNumber(contact.getPhoneNumber());


            preparedStatement.setInt(1, contact.getId());
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println(" مخاطب با موفقیت حذف شد. می‌توانید عملیات را Undo کنید.");
            } else {

                this.lastDeletedContact = null;
                System.out.println(" مخاطبی با این ID یافت نشد یا حذف انجام نشد.");
            }

        } catch (SQLException e) {
            System.out.println(" مخاطب حذف نشد " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<Contact> findByNumber(String phoneNumber) throws Exception {
        String sql = "SELECT id, name, phoneNumber FROM contact WHERE phoneNumber = ?";
        List<Contact> contacts = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            preparedStatement.setString(1, phoneNumber);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Contact contact = new Contact();
                contact.setId(resultSet.getInt("id"));
                contact.setName(resultSet.getString("name"));
                contact.setPhoneNumber(resultSet.getString("phoneNumber"));

                contacts.add(contact);
            }

            return contacts;
        } catch (SQLException e) {
            System.out.println(" مخاطبی با این شماره موجود نیست " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new ArrayList<>();
    }

    @Override
    public List<Contact> findByName(String name) {
        String sql = "SELECT id, name, phoneNumber FROM contact WHERE name = ?";
        List<Contact> contacts = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Contact contact = new Contact();
                contact.setId(resultSet.getInt("id"));
                contact.setName(resultSet.getString("name"));
                contact.setPhoneNumber(resultSet.getString("phoneNumber"));

                contacts.add(contact);
            }

            return contacts;

        } catch (SQLException e) {
            System.out.println(" مخاطبی با این نام موجود نیست " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new ArrayList<>();
    }

    public void undo() throws Exception {
        if (this.lastDeletedContact == null) {
            System.out.println(" هیچ عملیات حذفی برای بازگردانی وجود ندارد.");
            return;
        }

        try {
            Contact contactToRestore = this.lastDeletedContact;

            contactToRestore.setId(0);

            this.save(contactToRestore);

            System.out.println(" مخاطب '" + contactToRestore.getName() + "' با موفقیت بازگردانده شد.");

        } catch (Exception e) {
            System.err.println(" بازگردانی (Undo) ناموفق بود: " + e.getMessage());
            throw e;
        } finally {
            // گام ۳: در هر صورت، متغیر را خالی می‌کنیم تا فقط یک بار Undo انجام شود.
            this.lastDeletedContact = null;
        }
    }


    public List<Contact> findAll(int pageSize, int pageNumber) throws Exception {

        int offset = (pageNumber - 1) * pageSize;

        String sql = "SELECT id, name, phone_number FROM contacts ORDER BY name LIMIT ? OFFSET ?";

        List<Contact> contacts = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            preparedStatement.setInt(1, pageSize);
            preparedStatement.setInt(2, offset);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Contact contact = new Contact(
                        resultSet.getString("name"),
                        resultSet.getString("phone_number")

                );

                contacts.add(contact);
            }
            return contacts;

        } catch (SQLException e) {

            throw new Exception("خطا در صفحه‌بندی: " + e.getMessage());
        }
    }


    @Override
    public void edit(String oldName, String oldPhoneNumber,
                     String newName, String newPhoneNumber) throws Exception {


        if (oldName == null || oldPhoneNumber == null || newName == null || newPhoneNumber == null ||
                oldName.trim().isEmpty() || oldPhoneNumber.trim().isEmpty()) {

            throw new IllegalArgumentException("تمامی فیلدهای نام و شماره تلفن باید پر شوند.");
        }

        String sql = "UPDATE contact SET name = ?, phoneNumber = ? WHERE name = ? AND phoneNumber = ?";

        int rowsChange = 0;

        try (Connection con = getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(sql)) {


            preparedStatement.setString(1, newName);
            preparedStatement.setString(2, newPhoneNumber);


            preparedStatement.setString(3, oldName);
            preparedStatement.setString(4, oldPhoneNumber);

            rowsChange = preparedStatement.executeUpdate();

            if (rowsChange > 0) {
                System.out.println(" ویرایش مخاطب با موفقیت انجام شد. تعداد ردیف‌های تغییر یافته: " + rowsChange);
            } else {
                System.out.println("⚠️ عملیات ویرایش انجام نشد. مخاطبی با نام '" + oldName + "' و شماره '" + oldPhoneNumber + "' یافت نشد.");
            }

        } catch (SQLException e) {
            System.out.println(" ویرایش انجام نشد " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
        }


// برای رول بک چون متد حذف داخل بلوک ترای هست و عملا بعد عملیات بسته میشود پس عملیات حذف نباید
// بسته شود و یک متغیر که اخرین حذف هست تعریف میکنیم