package ir.maktabHW13.repository;

import ir.maktabHW13.model.ExamStatus;
import ir.maktabHW13.util.TransactionManager;
import jakarta.persistence.NoResultException;
import java.util.Collections;
import java.util.List;

public class StudentExamImpl implements StudentExam {
    @Override
    public void findStudentExams(Long studentId, Long examId) {
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
        System.out.println("studentExams details : " + studentExams);
    }

    @Override
    public List<StudentExam> findInProgressByStudentExam(Long studentId) {
        try {
            return TransactionManager.execute(
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
    }

    @Override
    public boolean checkAssignToExam(Long studentId, Long examId) {
        if (examId == null || studentId == null) {
            throw new IllegalArgumentException("examId  or studentId is null");
        }
        Number participant = (Number) TransactionManager.execute(
                entityManager -> entityManager.createQuery(
                                "select count (se) from StudentExam se where " +
                                        "se.student.id =:studentId and " +
                                        "se.exam.id =:examId", StudentExam.class).
                        setParameter("studentId", studentId).
                        setParameter("examId", examId)
                        .getSingleResult());
        return participant != null || participant.doubleValue() > 0;//return true


    }
}
