package ir.maktabHW13.repository;

import ir.maktabHW13.model.Exam;
import ir.maktabHW13.model.Questions;

import java.util.List;

public interface ExamRepository extends BaseRepository {

    List<Exam> findAllCourseExams(Long examId);

    void assignCourseToExam(Long examId, Long courseId);

    List<Questions> getQuestionByCourseId(Long courseId);

    List<Exam> getExamByCourseId(Long courseId);

}
