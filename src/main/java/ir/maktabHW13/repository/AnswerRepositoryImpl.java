package ir.maktabHW13.repository;

import ir.maktabHW13.model.Answer;
import ir.maktabHW13.util.TransactionManager;
import jakarta.persistence.NoResultException;

import java.util.ArrayList;
import java.util.List;

public class AnswerRepositoryImpl implements AnswerRepository {

    @Override
    public void addAnswer(Answer answer) {
        if (answer == null) {
            throw new NullPointerException("answer is null");
        }
        // id ==0 means :answer is new
        if (answer.getId() == 0) {
            TransactionManager.executeForPersist(entityManager -> entityManager.persist(answer));
            System.out.println("Answer added successfully");

        } else {
            TransactionManager.execute((entityManager ->
                    entityManager.merge(answer)));
            System.out.println("updated answer with id " + answer.getId());

        }
    }

    @Override
    public void updateAnswer(Answer answer) {

            TransactionManager.execute(entityManager -> {
                entityManager.merge(answer);
                return null;
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
    public Answer findByStudentExamAndQuestion(Long studentExamId, Long questionId) {
        try {
            return TransactionManager.execute(entityManager ->
                    entityManager.createQuery(
                                    "SELECT a FROM Answer a WHERE a.studentExam.id = :studentExamId AND a.question.id = :questionId",
                                    Answer.class)
                            .setParameter("studentExamId", studentExamId)
                            .setParameter("questionId", questionId)
                            .getSingleResult()
            );
        } catch (NoResultException e) {
            return null;
        }
    }
}
