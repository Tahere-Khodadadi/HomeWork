package ir.maktabHW13;
import ir.maktabHW13.controller.UI;
import ir.maktabHW13.repository.UserRepository;
import ir.maktabHW13.repository.UserRepositoryImpl;
import ir.maktabHW13.service.UserService;
import ir.maktabHW13.service.UserServiceImpl;
import ir.maktabHW13.util.JpaApplication;

public class MainApp {
    public static void main(String[] args) {
        UserRepository userRepository = new UserRepositoryImpl(new JpaApplication());

        UserService userService = new UserServiceImpl(userRepository);

        UI systemUI = new UI((UserServiceImpl) userService);
          userService.SignUpAdmin();
        systemUI.StartMenu();
    }
}
