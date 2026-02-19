package ir.maktabHW13.service;

import ir.maktabHW13.model.*;
import ir.maktabHW13.repository.AnswerRepository;
import ir.maktabHW13.repository.ExamRepository;
import ir.maktabHW13.repository.StudentExamRepository;
import ir.maktabHW13.repository.UserRepository;
import ir.maktabHW13.util.JpaApplication;
import ir.maktabHW13.util.TransactionManager;

import java.time.Duration;
import java.time.LocalTime;

public class StudentExamServiceImpl implements StudentExamService {

    private  StudentExamRepository studentExamRepository;
    private ExamRepository examRepository;
    private UserRepository userRepository;
    private AnswerRepository answerRepository;


    public StudentExamServiceImpl(StudentExamRepository studentExamRepository
            , ExamRepository examRepository, UserRepository userRepository
            , AnswerRepository answerRepository) {
        this.studentExamRepository = studentExamRepository;
        this.examRepository = examRepository;
        this.userRepository = userRepository;
        this.answerRepository = answerRepository;
    }


    @Override
    public StudentExam startExam(Long studentId, Long examId) {


        if (studentId == null || examId == null) {
            throw new IllegalArgumentException("StudentId and ExamId can't be null");
        }

        StudentExam existingStudentExam = (StudentExam) studentExamRepository.findStudentExams(studentId, examId);
        if (existingStudentExam != null) {


            if (existingStudentExam.getExamStatus() == ExamStatus.InProgress) {
                return existingStudentExam;

            } else if (existingStudentExam.getExamStatus() == ExamStatus.Finished) {
                throw new RuntimeException("you before finish exam ");

            }
        }
        Exam exam = examRepository.findById(Exam.class, examId);
        User student = userRepository.findById(User.class, studentId);

        if (student == null || exam == null) {
            throw new IllegalArgumentException("StudentId and ExamId can't be null");

        }
        StudentExam studentExam = new StudentExam();
        studentExam.setExamStatus(ExamStatus.InProgress);
        studentExam.setStudent(student);
        studentExam.setExam(exam);
        studentExam.setStartExamTime(LocalTime.now());
        studentExam.setCurrentQuestionIndex(0);

        studentExamRepository.save(studentExam);
        return existingStudentExam;
    }


    @Override
    public void startTimeExam(int duration, StudentExam studentExam) {

        LocalTime endTime = LocalTime.now().plusMinutes(duration);

//this is for reverse time
        Thread t = new Thread() {
            @Override
            public void run() {
            while (LocalTime.now().isBefore(endTime)) {
                //calculate time
                Duration remaining = Duration.between(LocalTime.now(), endTime);

                if (remaining.isNegative() || remaining.isZero()) {
                    System.out.println(" end time exam and exam is finish ");
                    endExam(studentExam.getStudent().getId(), studentExam.getExam().getId());
                    break;
                }
                updateRemainingTime(remaining);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println("sleep interrupted" + e.getMessage());
                }
            }

            }};
        t.start();
    }

    private void updateRemainingTime(Duration remaining) {
        int minus = Math.toIntExact(remaining.toMinutes());
        int second = Math.toIntExact(remaining.toSeconds() % 60);

        System.out.println("minus:" + minus + " second:" + second);
    }

    @Override
    public void endExam(Long studentId, Long examId) {
        StudentExam studentExam = (StudentExam) studentExamRepository.findStudentExams(studentId, examId);
        if (studentExam == null || studentExam.getExamStatus() == ExamStatus.Finished) {
            throw new RuntimeException("Exam not in progress or already finished.");
        }

        studentExam.setExamStatus(ExamStatus.Finished);
        studentExam.setEndExamTime(LocalTime.now());

        if (studentExam.getAnswers() != null && !studentExam.getAnswers().isEmpty()) {
            for (Answer answer : studentExam.getAnswers()) {
                answerRepository.addAnswer(answer);
            }
        }
        studentExam.calculateTotalScore();
        studentExamRepository.save(studentExam);
    }


}
