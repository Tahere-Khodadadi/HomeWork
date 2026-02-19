package ir.maktabHW13.service;

import ir.maktabHW13.model.Answer;
import ir.maktabHW13.repository.AnswerRepository;

import java.util.Optional;

public class AnswerServiceImpl implements AnswerService {

    private AnswerRepository answerRepository;

    public AnswerServiceImpl(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    @Override
    public void addAnswer(Answer answer) {
        Optional.ofNullable(answer).orElseThrow(() -> new IllegalArgumentException("Answer cannot be null"));

        answerRepository.addAnswer(answer);
    }

    @Override
    public void updateAnswer(Answer answer) {
        try {
            Optional.ofNullable(answer).orElseThrow(() -> new IllegalArgumentException("Answer cannot be null"));
            answerRepository.updateAnswer(answer);
            System.out.println("Updated answer with id " + answer.getId());
        } catch (Exception e) {
            System.out.println("Update answer failed: " + e.getMessage());
        }
    }
}
