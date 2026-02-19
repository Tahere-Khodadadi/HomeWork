package ir.maktabHW13.service;

import ir.maktabHW13.model.Answer;
import ir.maktabHW13.model.StudentExam;
import ir.maktabHW13.repository.AnswerRepository;

public class AnswerServiceImpl implements AnswerService {

    private AnswerRepository answerRepository;

    public AnswerServiceImpl(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;

    }
    public void saveOrUpdateAnswer(Answer answer) {

        if (answer == null) {
            throw new IllegalArgumentException("Answer cannot be null");
        }

        Answer existingAnswer = (Answer) answerRepository
                .findByStudentExamId(
                        answer.getStudentExam().getId());

        if (existingAnswer == null) {
            answerRepository.addAnswer(answer);
            System.out.println("Answer saved successfully");
        } else {
            existingAnswer.setAnswerStudent(answer.getAnswerStudent());
            existingAnswer.setScoreStudent(answer.getScoreStudent());

            answerRepository.updateAnswer(existingAnswer);


            StudentExam studentExam = answer.getStudentExam();
            studentExam.calculateTotalScore();
            System.out.println("Answer updated successfully");
        }
    }

}
