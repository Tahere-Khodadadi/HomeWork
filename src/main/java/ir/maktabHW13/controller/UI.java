package ir.maktabHW13.controller;

import ir.maktabHW13.dto.UserSignUpDTO;
import ir.maktabHW13.model.*;
import ir.maktabHW13.repository.*;
import ir.maktabHW13.service.CourseServiceImpl;
import ir.maktabHW13.service.ExamServiceImpl;
import ir.maktabHW13.service.UserServiceImpl;
import ir.maktabHW13.util.JpaApplication;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class UI {
    private final UserServiceImpl userService;
    private final UserRepository userRepository;
    private final CourseServiceImpl courseService;
    private final ExamServiceImpl examService;

    Scanner scanner = new Scanner(System.in);

    public UI(UserServiceImpl userService, CourseServiceImpl courseService, ExamServiceImpl examService) {
        this.userService = userService;

        this.userRepository = new UserRepositoryImpl(new JpaApplication());
        this.courseService = courseService;
        this.examService = examService;

    }


    public void StartMenu() {
        while (true) {
            System.out.println("---WelCome to My Program-----");
            System.out.println("1. Users : ");
            System.out.println("2. login Admin : ");
            System.out.println("3. Courses ");
            System.out.println("4. Exit :");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> Users();
                case 2 -> LoginAdmin();
                case 3 -> Courses();
                case 4 -> {
                    System.out.println("Exit");

                    return;
                }
                default -> System.out.println("Invalid option .");

            }

        }

    }

    private void Courses() {
        System.out.println(" ------------ Courses Pages -------");
        System.out.println("1. Add Course : ");
        addCourse();
    }


    private void Users() {

        while (true) {
            System.out.println("---Users Page -----");
            System.out.println("1. Sign Up  : ");
            System.out.println("2. Login  : ");
            System.out.println("3. Exit: ");
            System.out.print(" Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1 -> signUpNewUser();
                case 2 -> loginUsers();
                case 3 -> {
                    System.out.println(" Exit ");
                    return;
                }
                default -> System.out.println("Invalid option .");
            }
        }

    }


    private void teacherDashboards(Long teacherId) {
        while (true) {
             scanner.nextLine();
            System.out.println(" Teacher Dashboards: ");
            System.out.println(" 1. Show All Teacher Courses Page: ");
            System.out.println(" 2. Exam Pages : ");
            System.out.println(" 3. add Exam To Course : ");
            System.out.println(" 4. Show All Course Exam Page: ");
            System.out.println(" 5. Exit: ");
            System.out.println(" Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1 -> showAllTeacherCourses(teacherId);
                case 2 -> examPage();
                case 3-> addExamToCourse();
                case 4 -> showCourseExam();
                case 5 -> {
                    System.out.println(" Exit ");
                    return;
                }
                default -> System.out.println("Invalid option .");

            }
            scanner.nextLine();
        }
    }

    private void addExamToCourse() {
        System.out.println();
        System.out.println(" Add Exam To Course Page : ");
        System.out.println(" Enter Exam ID : ");
        Long examId = scanner.nextLong();
        System.out.println(" Enter Course ID : ");
        Long courseId = scanner.nextLong();

        try {
            examService.assignCourseToExam(examId, courseId);

        }
        catch (Exception e) {
            throw  new RuntimeException(" Error to add exam to course " +e.getMessage());
        }

    }

    private void examPage() {
        System.out.println(" ------------ Exam Pages --------");
        System.out.println("1. Add Exam  : ");
        System.out.println("2. Remove Exam : ");
        System.out.println("3. Update Exam : ");
        System.out.println("4. Exit: ");
        System.out.println(" Choose an option: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        switch (choice) {
            case 1 -> addExam();
            case 2 -> removeExam();
            case 3 -> editExam();
            case 4 -> {
                System.out.println(" Exit ");
                return;
            }
            default -> System.out.println("Invalid option .");

        }
    }

    private void editExam() {

        System.out.println();

        System.out.println(" ------------ Update Exam Pages --------");
        System.out.println(" Enter Exam ID for Edit: ");
        Long examId = scanner.nextLong();


        Exam exitingExam = examService.findById(Exam.class, examId);
        if (exitingExam == null) {
            System.out.println("User not found!");
        }

        System.out.println(" Information Of currentExam : " + exitingExam);
        System.out.println("New Title: ");
        String newName = scanner.next();
        exitingExam.setTitle_Exam(newName);

        System.out.println("New Description: ");
        String newDescription = scanner.next();
        exitingExam.setDescription_Exam(newDescription);

        System.out.println("New Duration: ");
        int newDuration = scanner.nextInt();
        exitingExam.setDuration_Exam(newDuration);


        examService.updateExam(exitingExam);

        System.out.println("update successful!");


    }

    private void removeExam() {
        System.out.println();
        System.out.println(" ------------ Remove Exam --------");

        System.out.println(" Enter Exam ID for Remove: ");
        Long examId = scanner.nextLong();

        try {
            examService.removeExam(examId);
            System.out.println(" remove successful! ");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void addExam() {
        System.out.println();
        System.out.println(" ------------ Add Exam --------");
        Exam exam = new Exam();

        System.out.println("Enter Exam Title: ");
        String titleExam = scanner.nextLine();
        exam.setTitle_Exam(titleExam);

        System.out.println("Enter Description: ");
        String descriptionExam = scanner.nextLine();
        exam.setDescription_Exam(descriptionExam);

        System.out.println("Enter Duration: ");
        int durationExam = scanner.nextInt();
        exam.setDuration_Exam(durationExam);


        try {
            examService.addNewExam(exam);
            System.out.println(" add exam successful!");
        } catch (Exception e) {
            System.out.println(" add exam failed!" + e.getMessage());
        }

    }

    private void showCourseExam() {
        System.out.println();
        System.out.println(" ------------ Course Exam --------");
        System.out.println("1. Enter Course ID   : ");
        Long courseId = scanner.nextLong();

        try {
            examService.findAllByCourse(courseId);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }


    }

    private void showAllTeacherCourses(Long teacherId) {
        System.out.println(" ----------- Show All Teacher Courses -------");

        try {

            List<Course> courses = courseService.showTeacherCourse(teacherId);
            for (Course listCourses : courses) {
                System.out.println(" list courses are : ");
                System.out.println(listCourses);
            }
        } catch (Exception ex) {
            throw new RuntimeException(" error for show All Teacher Courses : " + ex.getMessage());
        }


    }

    private Long loginUsers() {
        System.out.println("---- Login Users ----");
        System.out.print("Enter your username: ");
        String username = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        try {
            Long userId = userService.login(username, password);

            if (userId == null) {
                System.out.println("Invalid username or password.");
                return null;
            }

            User user = userRepository.findById(User.class, userId);
            if (user == null) {
                System.out.println("User not found.");
                return null;
            }

            if (Roles.Teacher.equals(user.getRoles())) {
                teacherDashboards(userId);
            } else if (Roles.Student.equals(user.getRoles())) {
                studentDashboard(userId);
            } else {
                System.out.println("not found any roles ");
            }

            return userId;

        } catch (Exception e) {
            System.out.println("Login failed: " + e.getMessage());
            return null;
        }
    }


    private void studentDashboard(Long userId) {
    }


    private void signUpNewUser() {
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

        while (true) {
            System.out.println("------------- Users Management System  -----------");
            System.out.println("1. Show All Users");
            System.out.println("2. Change StatusUser : ");
            System.out.println("3. Edit  User Information : ");
            System.out.println("4. Filtering User : ");
            System.out.println("5. Change Roles : ");
            System.out.println("6. Remove User : ");
            System.out.println("7. Show Courses : ");
            System.out.println("8. Edit Info Courses : ");
            System.out.println("9. Add Teacher To Course : ");
            System.out.println("10. Add Student To Course : ");
            System.out.println("11. Remove Courses : ");
            System.out.println("12. Show Detail Courses : ");
            System.out.println("13. Exit : ");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1 -> showAllUsers();
                case 2 -> changeStatus();
                case 3 -> ediInfoUsers();
                case 4 -> filterUsers();
                case 5 -> changeRoles();
                case 6 -> removeUser();
                case 7 -> showAllCourses();
                case 8 -> editInfoCourses();
                case 9 -> addTeacherToCourse();
                case 10 -> addStudentToCourse();
                case 11 -> removeCourse();
                case 12 -> showDetailCourse();
                case 13 -> {
                    System.out.println("Exit");
                    return;
                }

                default -> System.out.println("Invalid option .");

            }
        }
    }

    private void removeCourse() {
        System.out.println("--- Remove Course ---");
        System.out.println("Enter Course Code : ");
        Long courseCode = scanner.nextLong();
        courseService.removeCourses(courseCode);
    }

    private void editInfoCourses() {
        System.out.println("--- Edit Courses ----");
        System.out.println(" Enter CourseId for Edit");
        Long id = scanner.nextLong();

        Course exitingCourse = userRepository.findById(Course.class, id);
        if (exitingCourse == null) {
            System.out.println("User not found!");
        }

        System.out.println(" Information Of currentCourse" + exitingCourse);
        System.out.print("New Title: ");
        String newName = scanner.next();
        exitingCourse.setTitle(newName);

        System.out.print("New Identifier: ");
        String newLastName = scanner.next();
        exitingCourse.setIdentifier(newLastName);

        System.out.print("New startDate: ");
        LocalDate newStartDate = LocalDate.parse(scanner.next());
        exitingCourse.setStartDate(newStartDate);

        System.out.print("New EndDate: ");
        LocalDate newEndDate = LocalDate.parse(scanner.next());
        exitingCourse.setEndDate(newEndDate);
        scanner.nextLine();
        courseService.updateCourse(exitingCourse);

        System.out.println("update successful!");


    }


    private void removeUser() {
        System.out.println("--- Delete User ----");
        System.out.println("Enter userId for delete user : ");
        Long userId = scanner.nextLong();

        userService.remove(userId);
    }

    private void changeRoles() {
        System.out.print("Enter Name : ");
        String name = scanner.nextLine();

        userService.changeRoles(name);
    }

    private void addTeacherToCourse() {

        System.out.println("--- Add Teacher to Course ---");

        System.out.print("Enter Teacher Id: ");
        String teacherId = scanner.nextLine();

        System.out.print("Enter Course Id: ");
        String courseId = scanner.nextLine();

        courseService.addTeacherToCourse(courseId, teacherId);
        System.out.println("Course added successfully!");
    }

    private void showAllCourses() {
        List<Course> course = courseService.findAll();
        for (Course courses : course) {
            System.out.println(courses);
        }

    }

    private void addStudentToCourse() {
        System.out.println("--- Add Student to Course ---");
        System.out.print("Enter Student Id: ");
        String studentId = scanner.nextLine();
        System.out.print("Enter Course Id: ");
        String courseId = scanner.nextLine();

        courseService.addStudentToCourse(courseId, studentId);
        System.out.println("Student successfully added");
    }

    private void addCourse() {
        Course course = new Course();
        System.out.println("--- Add Course ---");
        System.out.println("Enter Course Title: ");
        String title = scanner.nextLine();
        course.setTitle(title);

        System.out.println("Enter Corse Code: ");
        String courseCode = scanner.nextLine();
        course.setIdentifier(courseCode);

        System.out.println("Enter Course StartDate: ");
        LocalDate startDate = LocalDate.parse(scanner.nextLine());
        course.setStartDate(startDate);

        System.out.println("Enter Course EndDate: ");
        LocalDate endDate = LocalDate.parse(scanner.nextLine());
        course.setEndDate(endDate);

        courseService.addCourse(course);

    }

    private void filterUsers() {
        System.out.println("--------- Filtering Users ----------");
        System.out.println("Search by: (1. FirstName | 2. LastName | 3. Role | 4. Status)");
        String choice = scanner.nextLine();

        User user = new User();
        user.setUserStatus(null);

        if (choice.equals("1")) {
            System.out.print("Enter First Name: ");
            user.setFirstName(scanner.nextLine().trim());
        } else if (choice.equals("2")) {
            System.out.print("Enter Last Name: ");
            user.setLastName(scanner.nextLine().trim());


        } else if (choice.equals("3")) {
            System.out.print("Enter Role (Teacher/Student): ");
            String roleInput = scanner.nextLine().trim();

            try {
                user.setRoles(Roles.valueOf(roleInput));
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid Role! Use Teacher or Student.");
                return;
            }
        } else if (choice.equals("4")) {
            System.out.println("Enter Status (Approved/Rejected/Pending): ");
            String statusInput = scanner.nextLine().trim();


            try {
                user.setUserStatus(UserStatus.valueOf(statusInput));
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid Status.");
            }
        }

        List<User> results = userService.searchByInput(user);
        System.out.println(results);
        scanner.nextLine();
    }


    private void ediInfoUsers() {
        System.out.println("------------- User Information ------------");
        System.out.println("Enter id for edit user: ");
        Long id = scanner.nextLong();

        User existingUser = userRepository.findById(User.class, id);
        if (existingUser == null) {
            System.out.println("User not found!");
        }

        System.out.println(" Information Of currentUser" + existingUser);
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

        userService.updateUser(existingUser);

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

    private void showDetailCourse() {
        System.out.println("--- Show Detail Course ---");
        System.out.println("Enter Course Id: ");
        Long courseId = scanner.nextLong();

        courseService.showDetailCourse(courseId);
    }
}
