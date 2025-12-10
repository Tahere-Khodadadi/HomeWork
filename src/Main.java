import com.maktab.contactmanager.controller.UI;
import com.maktab.contactmanager.repository.ContactManagementImplRepo; // فرض می‌کنیم JDBC بدون Undo است
import com.maktab.contactmanager.repository.ContactManagementRepo;
import com.maktab.contactmanager.services.ContactManagement;
import com.maktab.contactmanager.services.ContactManagementImpl;
import com.maktab.contactmanager.services.ContactManagementInMemory;

import java.util.Scanner;

public class Main {


    public static void main(String[] args) {

        ContactManagementRepo repository;
        Scanner scanner = new Scanner(System.in);

        System.out.println("--- راه‌اندازی سیستم ---");
        System.out.println(" true/false");
        boolean flag=scanner.nextBoolean();
        if (flag==true) {
            System.out.println(" حالت: حافظه موقت (In-Memory)");
                repository = new ContactManagementInMemory();

            }

        else {
            System.out.println(" حالت: اتصال به پایگاه داده (JDBC)");
            repository = new ContactManagementImplRepo();
        }

        ContactManagement service = new ContactManagementImpl(repository);

        UI  ui = new UI(service, scanner);
        System.out.println("\n--- سیستم آماده کار است ---");
        ui.startMenu();
    }
}