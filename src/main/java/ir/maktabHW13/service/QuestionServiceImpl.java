package ir.maktabHW13.service;

import ir.maktabHW13.dto.DescriptionQuestionsDTO;
import ir.maktabHW13.dto.MultipleChoiceQuestionsDTO;
import ir.maktabHW13.model.*;
import ir.maktabHW13.repository.CourseRepository;
import ir.maktabHW13.repository.QuestionRepository;

import java.util.ArrayList;
import java.util.List;

public class QuestionServiceImpl implements QuestionService {
    private final QuestionRepository questionRepository;
    private final CourseRepository courseRepository;

    public QuestionServiceImpl(QuestionRepository questionRepository, CourseRepository courseRepository) {

        this.questionRepository = questionRepository;
        this.courseRepository = courseRepository;
    }


    @Override
    public void addDescriptionQuestion(DescriptionQuestionsDTO descriptionQuestionsDTO) {
        DescriptionQuestion descriptionQuestion = new DescriptionQuestion();

        try {

            descriptionQuestion.setTitle(descriptionQuestionsDTO.getTitle());
            descriptionQuestion.setQuestionText(descriptionQuestionsDTO.getQuestionText());
            questionRepository.save(descriptionQuestion);
        } catch (Exception e) {
            throw new RuntimeException(" add description question is failed : " + e.getMessage());
        }
    }

    @Override
    public void addMultipleChoiceQuestion(MultipleChoiceQuestionsDTO multipleChoiceQuestionsDTO) {
        MultipleChoiceQuestion multipleChoiceQuestion = new MultipleChoiceQuestion();


        try {
            multipleChoiceQuestion.setTitle(multipleChoiceQuestionsDTO.getTitle());
            multipleChoiceQuestion.setQuestionText(multipleChoiceQuestionsDTO.getQuestionText());

            List<MultiOption> multiOptions = new ArrayList<>();
            List<String> options = multipleChoiceQuestionsDTO.getOptions();

            for (int i = 0; i < options.size(); i++) {
                MultiOption option = new MultiOption();
                option.setOptionText(options.get(i));
                option.setOptionNumber(i + 1);
                option.setQuestions(multipleChoiceQuestion);
                multiOptions.add(option);
            }
            multipleChoiceQuestion.setOptions(multiOptions);


            questionRepository.save(multipleChoiceQuestion);

        } catch (Exception e) {
            throw new RuntimeException(" add multiple choice question is failed : " + e.getMessage());
        }
    }


    @Override
    public void removeQuestion(Long questionId) {
        if (questionId == null) {
            System.out.println("questionId is null");
            return;

        }
        try {
            questionRepository.remove(questionId);
            System.out.println("question removed");
        } catch (Exception e) {
            throw new RuntimeException(" remove question failed");
        }

    }

    @Override
    public Questions updateQuestion(Questions questions) {

//        return questionRepository.update(questions);

        return null;
    }

    @Override
    public Questions searchByTitleQuestion(String titleQuestion) {
        if (titleQuestion == null) {
            throw new NullPointerException("titleQuestion is null");
        }
        Questions question;
        try {
            question = questionRepository.searchByTitleQuestion(titleQuestion);
            System.out.println("question searched");
        } catch (Exception e) {
            throw new RuntimeException(" searchByTitleQuestion is failed : " + e.getMessage());
        }
        return question;
    }




}
