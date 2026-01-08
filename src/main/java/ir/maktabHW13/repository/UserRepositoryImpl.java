package ir.maktabHW13.repository;

import ir.maktabHW13.model.User;
import ir.maktabHW13.util.JpaApplication;
import ir.maktabHW13.util.TransactionHandler;

import java.util.ArrayList;
import java.util.List;

public class UserRepositoryImpl implements UserRepository {
    private JpaApplication jpaApplication;


    public UserRepositoryImpl(JpaApplication jpaApplication) {
        this.jpaApplication = jpaApplication;
    }

    @Override
    public <T> void save(T entity) {
        TransactionHandler.executeForPersist(entitymanager -> entitymanager.persist(entity));


    }

    @Override
    public <T> User update(T entity) {
        TransactionHandler.updateQuery(entity);

        return null;
    }

    public List<User> findAll(User user) {
       List<User> list = new ArrayList<>();
       return list;

    }

    @Override
    public  <T>  T findById(Class <T> tClass,Object id) {
    return TransactionHandler.findQuery(tClass,id);

    }

    @Override
    public User findByName(String userName) {
        return
                TransactionHandler.execute(entitymanager ->{
                    try {
                        return entitymanager.createQuery("from User u where u.firstName=:userName"
                                ,User.class)
                                .setParameter("userName", userName)
                                .getSingleResult();

                    }
                    catch (Exception e) {
                        return null;
                    }
    });
    }




}
