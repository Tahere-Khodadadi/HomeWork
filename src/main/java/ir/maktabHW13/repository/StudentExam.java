package ir.maktabHW13.repository;

import java.util.List;

public interface StudentExam {

    void findStudentExams(Long studentId, Long examId);

    List<StudentExam> findInProgressByStudentExam(Long studentId);

    boolean checkAssignToExam(Long studentId, Long examId);


}
