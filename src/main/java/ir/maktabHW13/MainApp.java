package ir.maktabHW13;
import ir.maktabHW13.controller.UI;
import ir.maktabHW13.repository.CourseRepository;
import ir.maktabHW13.repository.CourseRepositoryImpl;
import ir.maktabHW13.repository.UserRepository;
import ir.maktabHW13.repository.UserRepositoryImpl;
import ir.maktabHW13.service.CourseServiceImpl;
import ir.maktabHW13.service.UserServiceImpl;
import ir.maktabHW13.util.JpaApplication;

public class MainApp {
     public static void main(String[] args) {


        UserRepository userRepository = new UserRepositoryImpl(new JpaApplication());

        UserServiceImpl userService = new UserServiceImpl(userRepository);

        CourseRepository courseRepository = new CourseRepositoryImpl(new JpaApplication(),userRepository);
        CourseServiceImpl courseService = new CourseServiceImpl(userRepository,courseRepository);

        UI systemUI = new UI(userService, courseService);
          userService.SignUpAdmin();
        systemUI.StartMenu();
    }
}
