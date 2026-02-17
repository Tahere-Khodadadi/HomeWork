package ir.maktabHW13.repository;

import ir.maktabHW13.model.DescriptionQuestion;
import ir.maktabHW13.model.MultipleChoiceQuestion;
import ir.maktabHW13.model.Questions;
import ir.maktabHW13.util.JpaApplication;
import ir.maktabHW13.util.TransactionManager;
import jakarta.persistence.PersistenceException;

import java.util.List;

public class QuestionRepositoryImpl implements QuestionRepository {
    private final JpaApplication jpaApplication;



    public QuestionRepositoryImpl(JpaApplication jpaApplication) {
        this.jpaApplication = jpaApplication;
    }
    @Override
    public <T> void save(T entity) {
        try {

            if (entity instanceof DescriptionQuestion
                    || entity instanceof MultipleChoiceQuestion) {
                TransactionManager.executeForPersist(
                        entityManager ->
                                entityManager.persist(entity)
                );
            }
        } catch (Exception e) {
            throw new PersistenceException("failed to save");
        }
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
                        , entity).getResultList());

    }

    @Override
    public <T> T findById(Class<T> entity, Long id) {
        return TransactionManager.execute(entityManager -> entityManager.find(entity, id));
    }

    @Override
    public <T> void remove(Long questionId) {
        TransactionManager.deleteQuery(Questions.class, questionId);
    }

    @Override
    public Questions searchByTitleQuestion(String titleQuestion) {
        try {


            return (Questions) TransactionManager.execute(entityManager ->
                    entityManager.createQuery("from Questions q where q.title =:titleQuestion", Questions.class)
                            .getResultList());

        } catch (Exception e) {
            throw new PersistenceException("failed to save");
        }
    }
}

