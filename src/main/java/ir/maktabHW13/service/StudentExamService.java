package ir.maktabHW13.service;

import ir.maktabHW13.model.StudentExam;

public interface StudentExamService {

    StudentExam startExam(Long studentId, Long ExamId);


    void startTimeExam(int duration, StudentExam studentExam);
    void endTimeExam(Long studentId, Long ExamId);



}
