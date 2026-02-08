package ir.maktabHW13.service;

import ir.maktabHW13.dto.UserSignUpDTO;
import ir.maktabHW13.model.*;
import ir.maktabHW13.repository.UserRepository;
import ir.maktabHW13.util.TransactionManager;
import ir.maktabHW13.util.Validation;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {
    private UserRepository userRepository;


    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public void signUp(UserSignUpDTO dto) {
        User user;
        if (dto.getRoles() == Roles.Teacher) {
            user = new User();
            ((User) user).setCode(dto.getSpecialCode());
        } else {
            user = new User();
            ((User) user).setCode(dto.getSpecialCode());
        }

        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setPassword(dto.getPassword());
        user.setRoles(dto.getRoles());
        user.setUserStatus(UserStatus.Pending);

        Validation.validators(user);
        userRepository.save(user);
    }

    @Override
    public void SignUpAdmin() {
        User existingAdmin = userRepository.findByName("admin");
        if (existingAdmin != null) {
            return;
        }

        Admin adminToEntity = new Admin();
        adminToEntity.setUserName("admin");
        adminToEntity.setPassword("admin123");
        adminToEntity.setRoles(Roles.Admin);


        TransactionManager.execute(entityManager -> {
                    entityManager.persist(adminToEntity);
                    return entityManager;
                }
        );
    }

    @Override
    public List<User> approvedUsersByAdmin() {
        return
                TransactionManager.execute(entityManager ->
                {
                    List<User> pendingUsers =
                            entityManager.createQuery("select u from User u where u.userStatus =: status ", User.class)
                                    .setParameter("status", UserStatus.Pending)
                                    .getResultList();

                    for (User approvedUsers : pendingUsers) {
                        if (approvedUsers.getUserStatus().equals(UserStatus.Approved)) {
                            System.out.println("this user is approved");
                        }
                        if (approvedUsers.getUserStatus() == UserStatus.Pending) {
                            approvedUsers.setUserStatus(UserStatus.Approved);
                        }
                    }

                    return pendingUsers;
                });

    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll(User.class);
    }

    @Override
    public User updateUser(User user) {

        User existingUser = userRepository.findById(User.class, user.getId());
        if (existingUser != null) {
            existingUser.setFirstName(user.getFirstName());
            existingUser.setLastName(user.getLastName());
            existingUser.setPassword(user.getPassword());
            existingUser.setRoles(user.getRoles());
            return userRepository.update(existingUser);

        }
        return null;
    }

    @Override
    public List<User> searchByInput(User user) {
        System.out.println("Service receiving search for: " + user.getFirstName()); // دیباگ
        return userRepository.findByInput(user);
    }

    @Override
    public void changeRoles(String name) {
        userRepository.changeRoles(name);
    }

    @Override
    public Long login(String firstName, String password) {

        if (firstName == null || firstName.isEmpty() || password == null || password.isEmpty()) {
            System.out.println(" username or password is null or empty.");
        }
        try {

            User user = userRepository.login(firstName, password);
            System.out.println("logged in successfully");

            return  user.getId();
        } catch (Exception e) {
            throw new RuntimeException("Invalid username or password.");
        }
    }
    @Override
    public void remove(Long userId) {
        User user = userRepository.findById(User.class, userId);
        if (user == null) {
            System.out.println("user not found");
            return;
        }
    try {
        userRepository.remove(userId);
        System.out.println("user removed successfully");
    }
    catch (Exception e) {
        throw new RuntimeException("user could not be removed");
    }
    }
}
