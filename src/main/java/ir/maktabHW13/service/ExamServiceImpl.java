package ir.maktabHW13.service;

import ir.maktabHW13.model.*;
import ir.maktabHW13.repository.CourseRepository;
import ir.maktabHW13.repository.ExamRepository;
import ir.maktabHW13.repository.QuestionRepository;
import ir.maktabHW13.util.TransactionManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ExamServiceImpl implements ExamService {

    ExamRepository examRepository;
    QuestionRepository questionRepository;



    public ExamServiceImpl(ExamRepository examRepository,
                           QuestionRepository questionRepository
            ) {
        this.examRepository = examRepository;
        this.questionRepository = questionRepository;

    }

    @Override
    public void addNewExam(Exam exam) {
        examRepository.save(exam);
    }

    @Override
    public void removeExam(Long examId) {
        try {
            examRepository.remove(examId);
        } catch (Exception e) {
            throw new RuntimeException("Exam could not be removed" + e.getMessage());
        }

    }


    @Override
    public Exam updateExam(Exam exam) {
        try {

            Exam existingExam = examRepository.findById(Exam.class, exam.getId());


            if (exam.getQuestions() == null) {
                System.out.println("Exam has no questions");
                return null;
            }
            existingExam.setQuestions(exam.getQuestions());
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
        } catch (Exception e) {
            throw new RuntimeException("findAll failed " + e.getMessage());
        }
        return examList;
    }

    @Override
    public List<Exam> findAllByCourse(Long courseId) {
        List<Exam> exams = examRepository.findAllCourseExams(courseId);
        if (exams == null || exams.isEmpty()) {

            System.out.println("exams is null");
            return new ArrayList<>();
        }
        try {
            for (Exam e : exams) {
                System.out.println("Exam Id : " + e.getId());
                System.out.println(e);


            }

        } catch (Exception e) {
            throw new RuntimeException("findAllByCourse failed " + e.getMessage());
        }
        return new ArrayList<>();

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


        } catch (Exception e) {
            throw new RuntimeException("findById failed " + e.getMessage());
        }
    }

    @Override
    public void addQuestionToExam(Long examId, Questions question) {
        if (examId == null || question == null) {
            throw new IllegalArgumentException("Exam ID and Question cannot be null");
        }

        try {
            Optional<Exam> optionalExam = Optional.ofNullable(examRepository.findById(Exam.class, examId));
            if (optionalExam.isEmpty()) {
                throw new RuntimeException("Exam not found with id: " + examId);
            }
            Exam exam = optionalExam.get();

            if (question instanceof DescriptionQuestion) {
                questionRepository.save((DescriptionQuestion) question);
            } else if (question instanceof MultipleChoiceQuestion) {
                questionRepository.save((MultipleChoiceQuestion) question);
            } else {
                throw new IllegalArgumentException(" question type is invalid ");
            }

            if (exam.getQuestions() == null) {
                exam.setQuestions(new ArrayList<>());
            }
            exam.getQuestions().add(question);

            examRepository.update(exam);

            System.out.println("Question assigned successfully to exam: " + examId);

        } catch (Exception e) {
            throw new RuntimeException("Add question to exam failed: " + e.getMessage(), e);
        }
    }

    public List<Questions> getQuestionsByCourse(Long courseId) {
        try {


            if (courseId == null) {
                throw new IllegalArgumentException("courseId is null");
            }
            return examRepository.getQuestionByCourseId(courseId);
        } catch (Exception e) {
            throw new RuntimeException("getQuestionsByCourse failed " + e.getMessage());
        }
    }

    @Override
    public List<Exam> showStudentCourseExams(Long courseId) {
        if (courseId == null) {
            throw new IllegalArgumentException("courseId is null");
        }


        try {
            List<Exam> examLists = examRepository.getExamByCourseId(courseId);
            if (examLists == null || examLists.isEmpty()) {
                throw new IllegalArgumentException("No exams found for the given courseId: " + courseId);
            }
            return examLists;

        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid input: " + e.getMessage(), e);


        } catch (Exception e) {
            throw new RuntimeException("Failed to show student course exams: " + e.getMessage(), e);
        }
    }

}