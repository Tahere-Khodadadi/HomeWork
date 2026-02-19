package ir.maktabHW13;

import ir.maktabHW13.controller.UI;
import ir.maktabHW13.repository.*;
import ir.maktabHW13.service.*;
import ir.maktabHW13.util.JpaApplication;

public class MainApp {
    public static void main(String[] args) {


        UserRepository userRepository = new UserRepositoryImpl(new JpaApplication());

        UserServiceImpl userService = new UserServiceImpl(userRepository);

        CourseRepository courseRepository = new CourseRepositoryImpl(new JpaApplication(), userRepository);
        CourseServiceImpl courseService = new CourseServiceImpl(userRepository, courseRepository);

        QuestionRepository questionRepository = new QuestionRepositoryImpl(new JpaApplication());

        ExamRepository examRepository = new ExamRepositoryImpl(new JpaApplication(), courseRepository);
        ExamServiceImpl examService = new ExamServiceImpl(examRepository, questionRepository);


        QuestionServiceImpl questionService = new QuestionServiceImpl(questionRepository, courseRepository);

        FileServiceImpl fileService = new FileServiceImpl(examRepository);

        AnswerRepository answerRepository = new AnswerRepositoryImpl(new JpaApplication());
        AnswerServiceImpl answerService = new AnswerServiceImpl(answerRepository);


        StudentExamRepository studentExamRepository = new StudentExamRepositoryImpl(new JpaApplication());
        StudentExamServiceImpl studentExamService = new StudentExamServiceImpl(
                studentExamRepository, examRepository, userRepository, answerRepository
        );


        UI systemUI = new UI(
                userService, courseService, examService, questionService, fileService,answerService, studentExamService);
        userService.SignUpAdmin();
        systemUI.StartMenu();
    }
}
