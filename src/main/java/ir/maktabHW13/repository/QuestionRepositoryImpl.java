package ir.maktabHW13.repository;

import ir.maktabHW13.model.Questions;
import ir.maktabHW13.util.TransactionManager;

import java.util.List;

public class QuestionRepositoryImpl implements QuestionRepository {
    @Override
    public <T> void save(T entity) {
          TransactionManager.executeForPersist(
                  entityManager -> entityManager.persist(entity)
          );
    }

    @Override
    public <T> T update(T entity) {
        TransactionManager.execute(entityManager -> entityManager.merge(entity));
    return entity;
    }

    @Override
    public <T> List<T> findAll(Class<T> entity) {
        return TransactionManager.execute(entityManager ->
                entityManager.createQuery("select q from Questions q where q.id =:questionId"
                ,entity).getResultList());

    }

    @Override
    public <T> T findById(Class<T> entity, Long id) {
        return TransactionManager.execute(entityManager -> entityManager.find(entity, id));
    }

    @Override
    public <T> void remove(Long questionId) {
        TransactionManager.deleteQuery(Questions.class,questionId);
    }

    @Override
    public void searchByTitleQuestion(String titleQuestion) {

        TransactionManager.execute(entityManager ->
                entityManager.createQuery("from Questions q where q.title =:titleQuestion",Questions.class)
                        .getResultList());
    }
}
