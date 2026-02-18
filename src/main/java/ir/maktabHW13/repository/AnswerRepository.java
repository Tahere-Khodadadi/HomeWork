package ir.maktabHW13.repository;

import ir.maktabHW13.model.Answer;

import java.util.List;

public interface AnswerRepository {

    List<Answer> findByQuestionId(Long questionId);

    Answer findByStudentExamAndQuestion(Long studentExamId,Long questionId);

    void addAnswer(Answer answer);

    void updateAnswer(Answer answer);
}
