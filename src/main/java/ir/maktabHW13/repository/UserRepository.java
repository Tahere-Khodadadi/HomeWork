package ir.maktabHW13.repository;

import ir.maktabHW13.model.User;

import java.util.List;

public interface UserRepository extends BaseRepository {


    User findByName(String userName);

    List<User> findByInput(User filter);

    void changeRoles(String name);


    User login(String firstName, String password);
}
