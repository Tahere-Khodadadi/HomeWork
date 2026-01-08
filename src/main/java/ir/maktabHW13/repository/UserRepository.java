package ir.maktabHW13.repository;

import ir.maktabHW13.model.User;

import java.util.List;

public interface UserRepository {
     <T> void save(T entity);
    <T> User update(T entity);
    List <User>  findAll(User user );
    <T>  T findById(Class <T> tClass,Object id);
    User findByName(String userName);
}
