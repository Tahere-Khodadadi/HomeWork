package ir.maktabHW13.service;

import ir.maktabHW13.model.Course;
import ir.maktabHW13.model.Exam;

import java.util.List;

public interface ExamService {
    void addNewExam(Exam exam);

    void removeExam(Long examId);

    Exam updateExam(Exam exam );

    List<Exam> findAll();

}
