package ir.maktabHW13.repository;

import ir.maktabHW13.model.Course;
import ir.maktabHW13.model.Roles;
import ir.maktabHW13.model.User;
import ir.maktabHW13.util.JpaApplication;
import ir.maktabHW13.util.TransactionManager;
import jakarta.persistence.EntityManager;
import lombok.Getter;
import org.h2.expression.ParameterRemote;
import org.h2.expression.aggregate.ListaggArguments;

import java.net.Authenticator;
import java.time.temporal.TemporalAccessor;
import java.util.List;
import java.util.Set;


public class CourseRepositoryImpl implements CourseRepository {
    private final JpaApplication jpaApplication;


    public CourseRepositoryImpl(JpaApplication jpaApplication, UserRepository userRepository) {
        this.jpaApplication = jpaApplication;
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
    public <T> void remove(Long courseId) {
        EntityManager em = jpaApplication.getEntityManagerFactory().createEntityManager();

        try {
            em.getTransaction().begin();


            Course course = em.find(Course.class, courseId);
            if (course == null) {
                em.getTransaction().commit();
                em.close();
                return;

            }
            if (course.getTeacher() != null) {
                course.setTeacher(null);

            }
            if (course.getStudents() != null) {
                course.getStudents().clear();
            }

            em.remove(course);
            em.getTransaction().commit();
        } catch (Exception e) {
            throw new RuntimeException("remove course error", e);
        }

    }


    @Override
    public Course findTitleCourse(String courseTitle) {

        try {
            TransactionManager.execute(entityManager ->
                            entityManager.createQuery(" from  Course c where c.title=:courseTitle")

                    ).setParameter("courseTitle", courseTitle)
                    .getResultList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return Course.class.cast(courseTitle);
    }

    @Override
    public void assignTeacherToCourse(String courseId, String teacherId) {
        TransactionManager.execute(entityManager ->
        {
            Course course = entityManager.createQuery(
                            "from Course c where c.identifier=:courseId", Course.class)
                    .setParameter("courseId", courseId)
                    .getResultList().
                    stream().
                    findFirst()
                    .orElseThrow(() -> new RuntimeException("course not found"));

            User teacher = entityManager.createQuery(
                            "from User u where u.code =:teacherId", User.class)
                    .setParameter("teacherId", teacherId)
                    .getResultList().
                    stream().
                    findFirst().
                    orElseThrow(() -> new RuntimeException("teacher not found"));

            if (teacher.getRoles() == null || !teacher.getRoles().equals(Roles.Teacher)) {
                throw new RuntimeException("teacher not found");
            }
            course.setTeacher(teacher);
            entityManager.merge(course);
            return "assign teacher to course is successful";
        });

    }

    @Override
    public void assignStudentToCourse(String courseId, String studentId) {

        TransactionManager.execute(entityManager ->
        {
            Course course = entityManager.createQuery(
                            "from Course c where c.identifier=:courseId", Course.class)
                    .setParameter("courseId", courseId)
                    .getResultList().
                    stream().
                    findFirst()
                    .orElseThrow(() -> new RuntimeException("course not found"));

            User student = entityManager.createQuery(
                            "from User u where u.code =:studentId", User.class)
                    .setParameter("studentId", studentId)
                    .getResultList().
                    stream().
                    findFirst().
                    orElseThrow(() -> new RuntimeException("student not found"));

            if (student.getRoles() == null || !student.getRoles().equals(Roles.Student)) {
                throw new RuntimeException("student not found");
            }
            course.getStudents().add(student);
            student.getStudentCourses().add(course);
            entityManager.merge(course);

            return "assign student to course is successful";
        });

    }

    @Override
    public void getDetailCourse(Long courseId) {

        EntityManager entityManager = jpaApplication.getEntityManagerFactory().createEntityManager();

        try {
            entityManager.getTransaction().begin();

            Course course = findById(Course.class, courseId);
            if (course == null) {
                System.out.println("course not found");
            }
            Set<User> students = course.getStudents();
            User teacher = course.getTeacher();

            if (students != null && !students.isEmpty()) {
                System.out.println("students are :");
                for (User u : students) {
                    System.out.println(u);
                }

            } else {
                System.out.println(" no students in this course ");
            }
            if (teacher != null) {
                System.out.println("teacher is :");
                System.out.println(teacher.getFirstName());
            }
            entityManager.getTransaction().commit();
            entityManager.close();
        } catch (Exception e) {
            throw new RuntimeException("get detail course error", e);
        }
    }


    @Override
    public List<Course> findCourseByTeacherId(Long teacherId) {


        if (teacherId == null) {
            System.out.println("teacherId is null");

        }

        return TransactionManager.execute(

                entityManager -> entityManager.createQuery(
                                "select c from Course c where c.teacher.id =: teacher_id", Course.class
                        ).setParameter("teacher_id", teacherId)
                        .getResultList());
    }


}
