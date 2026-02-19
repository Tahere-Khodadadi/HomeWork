package ir.maktabHW13.repository;

import ir.maktabHW13.model.Answer;
import ir.maktabHW13.util.JpaApplication;
import ir.maktabHW13.util.TransactionManager;
import jakarta.persistence.NoResultException;

import java.util.ArrayList;
import java.util.List;

public class AnswerRepositoryImpl implements AnswerRepository {


    private JpaApplication jpaApplication;

    public AnswerRepositoryImpl(JpaApplication jpaApplication) {
        this.jpaApplication=jpaApplication;
    }
    @Override
    public void addAnswer(Answer answer) {
        if (answer == null) {
            throw new NullPointerException("answer is null");
        }

            TransactionManager.executeForPersist(entityManager -> entityManager.persist(answer));
            System.out.println("Answer added successfully");

        }


    @Override
    public void updateAnswer(Answer answer) {

        if (answer == null || answer.getId() == null) {
            throw new NullPointerException("answer is null");
        }
            TransactionManager.execute(entityManager -> {
                entityManager.merge(answer);
                return answer;
            });

    }

    @Override
    public List<Answer> findByQuestionId(Long questionId) {
        if (questionId == null) {
            throw new NullPointerException("questionId is null");
        }
        return TransactionManager.execute((entityManager ->
                entityManager.createQuery("select a from Answer a where a.question.id =:questionId"
                                , Answer.class).
                        setParameter("questionId", questionId).
                        getResultList()));
    }

    @Override
    public List<Answer> findByStudentExamId(Long studentExamId) {
        return TransactionManager.execute(entityManager ->
                entityManager.createQuery("select a from Answer a where a.studentExam.id = :studentExamId", Answer.class)
                        .setParameter("studentExamId", studentExamId)
                        .getResultList()
        );
    }
}
