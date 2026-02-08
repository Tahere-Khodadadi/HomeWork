package ir.maktabHW13.service;

import ir.maktabHW13.model.Course;
import ir.maktabHW13.model.Exam;
import ir.maktabHW13.repository.ExamRepository;

import java.util.List;

public class ExamServiceImpl implements ExamService {

    ExamRepository examRepository;

    public ExamServiceImpl(ExamRepository examRepository) {
        this.examRepository = examRepository;

    }

    @Override
    public void addNewExam(Exam exam) {
        examRepository.save(exam);
    }

    @Override
    public void removeExam(Long examId) {
        Exam exam = examRepository.findById(Exam.class, examId);
        if (exam == null) {
            System.out.println("Exam not found");
            return;
        }
        try {
            examRepository.remove(examId);
            System.out.println("Exam removed successfully");
        } catch (Exception e) {
            throw new RuntimeException("Exam not removed successfully");
        }

    }

    @Override
    public Exam updateExam(Exam exam) {
        try {


            Exam existingExam = examRepository.findById(Exam.class, exam.getId());
            if (existingExam != null) {
                existingExam.setTitle_Exam(exam.getTitle_Exam());
                existingExam.setDescription_Exam(exam.getDescription_Exam());
                existingExam.setDuration_Exam(exam.getDuration_Exam());

                return examRepository.update(existingExam);

            }
        } catch (Exception e) {
            throw new RuntimeException(" update exam failed  " + e.getMessage());
        }
        return exam;
    }

    @Override
    public List<Exam> findAll() {
        List<Exam> examList = examRepository.findAll(Exam.class);
        if (examList == null) {
            System.out.println("exam is null");
        }

        try {
            for (Exam e : examList) {
                System.out.println(e);
            }
        }
        catch (Exception e) {
            throw new RuntimeException("findAll failed " + e.getMessage());
        }
        return examList;
    }
}