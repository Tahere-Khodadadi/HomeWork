package ir.maktabHW13.repository;

import ir.maktabHW13.model.Exam;
import ir.maktabHW13.util.JpaApplication;
import ir.maktabHW13.util.TransactionManager;
import jakarta.persistence.EntityManager;

import java.util.List;

public class ExamRepositoryImpl implements ExamRepository {

    private JpaApplication jpaApplication;

    public ExamRepositoryImpl(JpaApplication jpaApplication) {
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
                exam.setCourse(null);

            }

            em.remove(exam);
            em.getTransaction().commit();
        } catch (Exception e) {
            throw new RuntimeException("remove exam error", e);
        }

    }

    @Override
    public List<Exam> findAllCourseExams(Long courseId) {
       List<Exam> exams;
        try {
            exams=TransactionManager.execute(entityManager ->
                    entityManager.createQuery("select e from Exam e where e.course.id = :courseId", Exam.class)
                            .setParameter("courseId", courseId)
                            .getResultList()
            );
        }
        catch (Exception e) {
            throw new RuntimeException("findAllCourseExams error", e);
        }


        return exams;
    }
}
