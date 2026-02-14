package ir.maktabHW13.service;


import ir.maktabHW13.dto.DescriptionQuestionsDTO;
import ir.maktabHW13.dto.MultipleChoiceQuestionsDTO;
import ir.maktabHW13.model.*;

import java.util.List;

public interface QuestionService {

    void addDescriptionQuestion(DescriptionQuestionsDTO descriptionQuestionsDTO);

    void addMultipleChoiceQuestion(MultipleChoiceQuestionsDTO multipleChoiceQuestionsDTO);

    void removeQuestion(Long questionId);

    Questions updateQuestion(Questions questions);

    Questions searchByTitleQuestion(String titleQuestion);

    List<Questions> bankQuestions(Long courseId);


}
