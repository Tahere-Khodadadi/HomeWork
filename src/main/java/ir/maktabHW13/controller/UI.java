package ir.maktabHW13.controller;

import ir.maktabHW13.dto.UserSignUpDTO;
import ir.maktabHW13.model.Roles;
import ir.maktabHW13.model.User;
import ir.maktabHW13.repository.UserRepository;
import ir.maktabHW13.repository.UserRepositoryImpl;
import ir.maktabHW13.service.UserServiceImpl;
import ir.maktabHW13.util.JpaApplication;

import java.util.List;
import java.util.Scanner;

public class UI {
    private UserServiceImpl userService;
    private UserRepository userRepository;


    Scanner scanner = new Scanner(System.in);

    public UI(UserServiceImpl userService) {
        this.userService = userService;
        this.userRepository = new UserRepositoryImpl(new JpaApplication());
    }


    public void StartMenu() {
        while (true) {
            System.out.println("---WelCome to My Program-----");
            System.out.println("1. SignUp New User : ");
            System.out.println("2. login Admin : ");
            System.out.println("3. Exit :");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> SignupNewUser();
                case 2 -> LoginAdmin();
                case 3 -> {
                    System.out.println("Exit");

                    return;
                }
                default -> System.out.println("Invalid option .");

            }

        }

    }

    private void SignupNewUser() {
        UserSignUpDTO dto = new UserSignUpDTO();

        System.out.println("--- Registration ---");
        System.out.println("Please select your Role (Teacher / Student):");
        String roleInput = scanner.nextLine().trim();

        System.out.print("Enter First Name: ");
        dto.setFirstName(scanner.nextLine());

        System.out.print("Enter Last Name: ");
        dto.setLastName(scanner.nextLine());

        System.out.print("Enter Password: ");
        dto.setPassword(scanner.nextLine());

        if (roleInput.equalsIgnoreCase("Teacher")) {
            dto.setRoles(Roles.Teacher);
            System.out.print("Enter Teacher Code: ");
            dto.setSpecialCode(scanner.nextLine());
        } else if (roleInput.equalsIgnoreCase("Student")) {
            dto.setRoles(Roles.Student);
            System.out.print("Enter Student Number: ");
            dto.setSpecialCode(scanner.nextLine());
        } else {
            System.out.println("Invalid Role! Registration cancelled.");
            return;
        }
        try {
            userService.signUp(dto);
            System.out.println("Registration successful! Waiting for admin approval.");
        } catch (Exception e) {
            System.out.println("Error during registration: " + e.getMessage());
        }
    }


    private void LoginAdmin() {
        System.out.print("Enter Admin Username: ");
        String username = scanner.nextLine();
        System.out.print("Enter Admin Password: ");
        String password = scanner.nextLine();


        if (username.equals("admin") && password.equals("admin123")) {
            System.out.println("Login Successful! Welcome ");
            adminMenu();
        } else {
            System.out.println("Invalid Admin Credentials ");

        }
    }

    private void adminMenu() {

        System.out.println("------------- Users Management System  -----------");
        System.out.println("1. Show All Users");
        System.out.println("2. Change StatusUser : ");
        System.out.println("3. Edit  User Information : ");
        System.out.println("4. Exit : ");
        System.out.print("Choose an option: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        switch (choice) {
            case 1 -> showAllUsers();
            case 2 -> changeStatus();
            case 3 -> ediInfoUsers();
            case 4 -> {
                System.out.println("Exit");
                return;
            }

            default -> System.out.println("Invalid option .");

        }
    }

    private void ediInfoUsers() {
        System.out.println("------------- User Information ------------");
        System.out.println("Enter first name for edit user: ");
        Long id = scanner.nextLong();

        User existingUser= userRepository.findById(User.class,id);
        if (existingUser == null) {
            System.out.println("User not found!");
        }
        System.out.println(" Information Of currentUser"+existingUser);
        System.out.print("New First Name: ");
        String newName = scanner.next();
        existingUser.setFirstName(newName);

        System.out.print("New Last Name: ");
        String newLastName = scanner.next();
        existingUser.setLastName(newLastName);

        System.out.print("New Password: ");
        String newPassword = scanner.next();
        existingUser.setPassword(newPassword);
        scanner.nextLine();

        System.out.println("Do you want to change Role :  y/n ");
        String answer = scanner.nextLine();

        if (answer.equalsIgnoreCase("y")) {

            if (Roles.Teacher.equals(existingUser.getRoles())) {
                existingUser.setRoles(Roles.Student);
            } else if (Roles.Student.equals(existingUser.getRoles())) {
                existingUser.setRoles(Roles.Teacher);
            }
        }
            else {
                existingUser.setRoles(existingUser.getRoles());
            }


        userService.update(existingUser);


        System.out.println("update successful!");

    }

    private void showAllUsers() {
        List<User> users = userService.findAll();
        for (User user : users) {
            System.out.println(user);
        }
    }

    private void changeStatus() {
        List<User> users = userService.approvedUsersByAdmin();

        if (users == null || users.isEmpty()) {
            System.out.println("No Users Found");
            return;
        }
        System.out.println("Change Status Successful");
        for (User user : users) {

            System.out.println(user);
        }

    }
}
