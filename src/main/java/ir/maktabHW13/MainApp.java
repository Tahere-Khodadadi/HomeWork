package ir.maktabHW13;
import ir.maktabHW13.controller.UI;
import ir.maktabHW13.repository.*;
import ir.maktabHW13.service.CourseServiceImpl;
import ir.maktabHW13.service.ExamServiceImpl;
import ir.maktabHW13.service.QuestionServiceImpl;
import ir.maktabHW13.service.UserServiceImpl;
import ir.maktabHW13.util.JpaApplication;

public class MainApp {
     public static void main(String[] args) {


        UserRepository userRepository = new UserRepositoryImpl(new JpaApplication());

        UserServiceImpl userService = new UserServiceImpl(userRepository);

        CourseRepository courseRepository = new CourseRepositoryImpl(new JpaApplication(),userRepository);
        CourseServiceImpl courseService = new CourseServiceImpl(userRepository,courseRepository);

         QuestionRepository questionRepository=new QuestionRepositoryImpl(new JpaApplication());

         ExamRepository examRepository = new ExamRepositoryImpl(new JpaApplication());
        ExamServiceImpl examService = new ExamServiceImpl(examRepository,questionRepository);


        QuestionServiceImpl questionService=new QuestionServiceImpl(questionRepository,courseRepository);

        UI systemUI = new UI(userService, courseService,examService,questionService);
          userService.SignUpAdmin();
        systemUI.StartMenu();
    }
}
