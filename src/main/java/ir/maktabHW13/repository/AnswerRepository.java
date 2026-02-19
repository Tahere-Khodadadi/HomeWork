package ir.maktabHW13.repository;

import ir.maktabHW13.model.Answer;

import java.util.List;

public interface AnswerRepository {

    List<Answer> findByQuestionId(Long questionId);

     List<Answer> findByStudentExamId(Long studentExamId);
    void addAnswer(Answer answer);

    void updateAnswer(Answer answer);
}
