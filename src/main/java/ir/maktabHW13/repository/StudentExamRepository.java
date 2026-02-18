package ir.maktabHW13.repository;

import ir.maktabHW13.model.StudentExam;

import java.util.List;

public interface StudentExamRepository {

   List< StudentExam> findStudentExams(Long studentId, Long examId);

    List<StudentExam> findInProgressByStudentExam(Long studentId);

    boolean checkAssignToExam(Long studentId, Long examId);

    void update(StudentExam studentExam);

    StudentExam findById(Long id);

    void save(StudentExam studentExam);

}
