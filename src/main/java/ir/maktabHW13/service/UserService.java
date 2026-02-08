package ir.maktabHW13.service;

import ir.maktabHW13.dto.UserSignUpDTO;
import ir.maktabHW13.model.User;

import java.util.List;

public interface UserService {

    void signUp(UserSignUpDTO user);

    void SignUpAdmin();

    List<User> approvedUsersByAdmin();

    List<User> findAll();

    User updateUser(User user);

    List<User> searchByInput(User user);

    void changeRoles(String name);

    Long login(String username, String password);

    void remove(Long userId);

}
