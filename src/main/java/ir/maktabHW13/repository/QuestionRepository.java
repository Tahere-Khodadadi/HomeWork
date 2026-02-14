package ir.maktabHW13.repository;

import ir.maktabHW13.model.Questions;

import java.util.List;

public interface QuestionRepository extends BaseRepository{

    Questions searchByTitleQuestion(String titleQuestion);


}
