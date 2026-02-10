package ir.maktabHW13.repository;

public interface QuestionRepository extends BaseRepository{

    void searchByTitleQuestion(String titleQuestion);
}
