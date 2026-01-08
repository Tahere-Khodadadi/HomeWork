package ir.maktabHW13.service;

import ir.maktabHW13.dto.UserSignUpDTO;
import ir.maktabHW13.model.*;
import ir.maktabHW13.repository.UserRepository;
import ir.maktabHW13.util.TransactionHandler;
import ir.maktabHW13.util.Validation;
import jakarta.persistence.Persistence;

import java.util.List;


public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public void signUp(UserSignUpDTO dto) {
        User user;
        if (dto.getRoles() == Roles.Teacher) {
            user = new Teacher();
            ((Teacher) user).setTeacherId(dto.getSpecialCode());
        } else {
            user = new Student();
            ((Student) user).setStudentId(dto.getSpecialCode());
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


        TransactionHandler.execute(entityManager -> {
                    entityManager.persist(adminToEntity);
                    return entityManager;
                }
        );
    }


    @Override
    public List<User> findAll() {
        return
                TransactionHandler.execute(entityManager ->
                        entityManager.createQuery("select u from User u", User.class)
                                .getResultList()

                );

    }

    @Override
    public User update(User user) {
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
    public List<User> approvedUsersByAdmin() {
        return
                TransactionHandler.execute(entityManager ->
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


}

