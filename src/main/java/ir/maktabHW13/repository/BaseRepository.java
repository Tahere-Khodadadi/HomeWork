package ir.maktabHW13.repository;

import ir.maktabHW13.model.User;
import jakarta.persistence.EntityManager;

import java.util.List;

public interface BaseRepository {

    <T> void save(T entity);

    <T> T update(T entity);

    <T> List<T> findAll(Class<T> entity);


    <T> T findById(Class<T> entity, Long id);

    <T> void remove(Long  userId);


}
