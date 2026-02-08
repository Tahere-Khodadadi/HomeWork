package ir.maktabHW13.repository;

import ir.maktabHW13.model.Exam;

import java.util.List;

public interface ExamRepository extends BaseRepository {

    List<Exam> findAllCourseExams(Long examId);
}
