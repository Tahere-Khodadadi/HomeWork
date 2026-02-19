package ir.maktabHW13.repository;

import ir.maktabHW13.model.ExamStatus;
import ir.maktabHW13.model.StudentExam;
import ir.maktabHW13.util.JpaApplication;
import ir.maktabHW13.util.TransactionManager;
import jakarta.persistence.NoResultException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StudentExamRepositoryImpl implements StudentExamRepository {

private JpaApplication jpaApplication;

public StudentExamRepositoryImpl(JpaApplication jpaApplication) {
    this.jpaApplication=jpaApplication;
}


    @Override
    public void update(StudentExam studentExam) {
        if (studentExam == null) {
            throw new IllegalArgumentException("StudentExam cannot be null");
        }

        TransactionManager.execute(entityManager -> {
            return entityManager.merge(studentExam);
        });
    }

    @Override
    public StudentExam findById(Long id) {
        try {
            return TransactionManager.execute(entityManager ->
                    entityManager.find(StudentExam.class, id));
        } catch (Exception e) {
            throw new RuntimeException("Find student exam by id failed: " + e.getMessage());
        }
    }

    @Override
    public void save(StudentExam studentExam) {
        if (studentExam == null) {
            throw new IllegalArgumentException("StudentExam cannot be null");
        }
        try {
         TransactionManager.execute(entityManager -> {
                return entityManager.merge(studentExam);
         });

        }catch (Exception e) {
            throw new RuntimeException("Save student exam failed: " + e.getMessage());
        }
    }


    @Override
    public List<StudentExam> findStudentExams(Long studentId, Long examId) {
        List<StudentExam> studentExams;
        try {

            studentExams = TransactionManager.execute(entityManager ->
                    entityManager.createQuery(" select se from StudentExam se  where se.student.id=:studentId " +
                                            "and se.exam.id =:examId",
                                    StudentExam.class).
                            setParameter("studentId", studentId).setParameter("examId", examId)
                            .getResultList());

        } catch (Exception e) {
            throw new RuntimeException(" find student exams failed");
        }
        if (studentExams == null) {
            throw new IllegalArgumentException("studentExams is null");
        }
        return studentExams;
    }

    @Override
    public List<StudentExam> findInProgressByStudentExam(Long studentId) {
        List<StudentExam> studentExams;
        try {
            studentExams= TransactionManager.execute(
                    entityManager -> entityManager.createQuery(
                                    "select  se from StudentExam se " +
                                            "where se.student.id =:studentId" +
                                            " and se.examStatus =:examStatus", StudentExam.class
                            ).setParameter("studentId", studentId)
                            .setParameter("examStatus", ExamStatus.InProgress)
                            .getResultList());


        } catch (NoResultException e) {
            return Collections.emptyList();
        } catch (Exception e) {
            throw new RuntimeException(" find student exams failed");

        }
        return  studentExams;
    }

    @Override
    public boolean checkAssignToExam(Long studentId, Long examId) {
        if (examId == null || studentId == null) {
            throw new IllegalArgumentException("examId  or studentId is null");
        }
        Long participant = TransactionManager.execute(
                entityManager -> entityManager.createQuery(
                                "select count (se) from StudentExam se where " +
                                        "se.student.id =:studentId and " +
                                        "se.exam.id =:examId", Long.class).
                        setParameter("studentId", studentId).
                        setParameter("examId", examId)
                        .getSingleResult());
        return participant != null || participant.doubleValue() > 0;//return true

    }
}
