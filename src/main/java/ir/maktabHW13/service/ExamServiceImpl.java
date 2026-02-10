package ir.maktabHW13.service;

import ir.maktabHW13.model.Course;
import ir.maktabHW13.model.Exam;
import ir.maktabHW13.repository.ExamRepository;

import java.util.ArrayList;
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
        try {
            examRepository.remove(examId);
        }
        catch (Exception e) {
            throw new RuntimeException("Exam could not be removed"+e.getMessage());
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

    @Override
    public List<Exam> findAllByCourse(Long courseId) {

        List <Exam> exams=examRepository.findAllCourseExams(courseId);
        if (exams == null || exams.isEmpty()) {

            System.out.println("exams is null");
            return new ArrayList<>();
        }
        try {
            for (Exam e : exams) {
                System.out.println(e);

            }

        }catch (Exception e) {
            throw new RuntimeException("findAllByCourse failed " + e.getMessage());
        }
        return exams;
    }
     @Override
    public Exam findById(Class<Exam> examClass, Long examId) {
     return examRepository.findById(Exam.class, examId);

    }

    @Override
    public void assignCourseToExam(Long examId, Long courseId) {
        if (examId == null || courseId == null) {
            System.out.println("examId and courseId is null");
        }
        try {


        Exam exam = examRepository.findById(Exam.class, examId);
        examRepository.assignCourseToExam(examId, courseId);
            System.out.println("Exam assigned successfully");


    }
        catch (Exception e) {
        throw new RuntimeException("findById failed " + e.getMessage());}
    }


}