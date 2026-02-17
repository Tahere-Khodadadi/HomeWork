package ir.maktabHW13.repository;

import ir.maktabHW13.model.*;
import ir.maktabHW13.service.ExamServiceImpl;
import ir.maktabHW13.util.JpaApplication;
import ir.maktabHW13.util.TransactionManager;
import jakarta.persistence.EntityManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ExamRepositoryImpl implements ExamRepository {

    private JpaApplication jpaApplication;
    private CourseRepository courseRepository;

    public ExamRepositoryImpl(JpaApplication jpaApplication
            , CourseRepository courseRepository) {
        this.jpaApplication = jpaApplication;
        this.courseRepository = courseRepository;
    }


    @Override
    public <T> void save(T entity) {
        TransactionManager.executeForPersist(entityManager ->
                entityManager.persist(entity));
    }

    @Override
    public <T> T update(T entity) {
        TransactionManager.updateQuery(entity);

        return entity;
    }

    @Override
    public <T> List<T> findAll(Class<T> entity) {
        return TransactionManager.execute(entityManager ->
                entityManager.createQuery("select e from " + entity.getName() + " e", entity).

                        getResultList()
        );
    }

    @Override
    public <T> T findById(Class<T> entity, Long id) {
        return TransactionManager.findQuery(entity, id);
    }

    @Override
    public <T> void remove(Long examId) {
        EntityManager em = jpaApplication.getEntityManagerFactory().createEntityManager();

        try {
            em.getTransaction().begin();


            Exam exam = em.find(Exam.class, examId);
            if (exam == null) {
                em.getTransaction().commit();
                em.close();
                return;

            }
            if (exam.getCourse() != null) {
                exam.getCourse().getExams().remove(exam);
                exam.setCourse(null);

            }


            em.remove(exam);
            em.getTransaction().commit();

        } catch (Exception e) {

            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("remove exam error", e);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Exam> findAllCourseExams(Long courseId) {
        List<Exam> exams;
        try {
            exams = TransactionManager.execute(entityManager -> {

                List<Exam> examList = entityManager.createQuery(
                                "SELECT e FROM Exam e " +
                                        "LEFT JOIN FETCH e.questions q " +
                                        "WHERE e.course.id = :courseId", Exam.class)
                        .setParameter("courseId", courseId)
                        .getResultList();

                for (Exam exam : examList) {
                    for (Questions q : exam.getQuestions()) {
                        if (q instanceof MultipleChoiceQuestion) {
                            MultipleChoiceQuestion mcq = (MultipleChoiceQuestion) q;
                            System.out.println("options are : " + mcq.getOptions().toString());
                        }
                    }
                }

                return examList;
            });
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("findAllCourseExams error", e);
        }
        return exams;
    }

    @Override
    public void assignCourseToExam(Long examId, Long courseId) {


        TransactionManager.execute(entityManager ->
        {
            Course course = entityManager.createQuery(
                            "select c from Course c where c.id=:courseId", Course.class)
                    .setParameter("courseId", courseId)
                    .getResultList()
                    .stream()
                    .findFirst().orElse(null);

            Exam exam = entityManager.find(Exam.class, examId);
            if (exam == null) {
                throw new RuntimeException("find exam by id error");
            }
            exam.setCourse(course);

            return entityManager.merge(exam);

        });


    }

    @Override
    public List<Questions> getQuestionByCourseId(Long courseId) {
        return TransactionManager.execute(entityManager -> {

            List<Exam> exams = entityManager.createQuery(
                            "select e from Exam e " +
                                    "LEFT JOIN FETCH e.questions " +
                                    "where e.course.id = :courseId", Exam.class)
                    .setParameter("courseId", courseId)
                    .getResultList();

            List<Questions> allQuestions = new ArrayList<>();
            for (Exam exam : exams) {
                allQuestions.addAll(exam.getQuestions());
            }

            return allQuestions;
        });
    }

    @Override
    public List<Exam> getExamByCourseId(Long courseId) {


        Course course = courseRepository.findById(Course.class, courseId);
        if (course == null || courseId == null) {

            System.out.println("course is null ");
            return Collections.emptyList();
        }
        return TransactionManager.execute(
                entityManager -> entityManager.createQuery(
                                "select e from Exam e where " +
                                        "e.course.id =: courseId", Exam.class
                        ).setParameter("courseId", courseId)
                        .getResultList());
    }
}