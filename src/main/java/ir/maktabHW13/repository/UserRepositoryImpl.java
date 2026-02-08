package ir.maktabHW13.repository;

import ir.maktabHW13.model.Course;
import ir.maktabHW13.model.Roles;
import ir.maktabHW13.model.User;
import ir.maktabHW13.util.JpaApplication;
import ir.maktabHW13.util.TransactionManager;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


public class UserRepositoryImpl implements UserRepository {
    private final JpaApplication jpaApplication;


    public UserRepositoryImpl(JpaApplication jpaApplication) {
        this.jpaApplication = jpaApplication;
    }

    @Override
    public <T> void save(T entity) {
        TransactionManager.executeForPersist(entitymanager -> entitymanager.persist(entity));


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
    public <T> void remove(Long userId) {
        EntityManager em = jpaApplication.getEntityManagerFactory().createEntityManager();
        em.getTransaction().begin();
        User user = em.find(User.class, userId);
        if (user == null) {
            em.getTransaction().commit();
            em.close();
            return;
        }

        if (user.getRoles() == Roles.Teacher) {
            for (Course c : user.getTeachingCourses()) {
                c.setTeacher(null);
                em.merge(c);
            }
        } else if (user.getRoles() == Roles.Student) {
            for (Course c : user.getStudentCourses()) {
                c.getStudents().remove(user);
                em.merge(c);
            }
        }
        em.flush();
        em.remove(user);
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public <T> T update(T entity) {
        TransactionManager.updateQuery(entity);

        return (T) entity;
    }


    @Override
    public User findByName(String userName) {
        return
                TransactionManager.execute(entitymanager -> {
                    try {
                        return entitymanager.createQuery("from User u where u.firstName=:userName"
                                        , User.class)
                                .setParameter("userName", userName)
                                .getSingleResult();

                    } catch (Exception e) {
                        return null;
                    }
                });
    }

    @Override
    public List<User> findByInput(User filter) {
        return
                TransactionManager.execute(entitymanager -> {
                    String hql = "from User u where " +
                            "(:fname is null or :fname = 'null' or u.firstName = :fname) and " +
                            "(:lname is null or :lname = 'null' or u.lastName = :lname) and " +
                            "(:roles is null or u.roles = :roles) and " +
                            "(:status is null or u.userStatus = :status)";

                    TypedQuery<User> query = entitymanager.createQuery(hql, User.class);

                    query.setParameter("fname", filter.getFirstName());
                    query.setParameter("lname", filter.getLastName());
                    query.setParameter("roles", filter.getRoles());
                    query.setParameter("status", filter.getUserStatus());

                    return query.getResultList();
                });


    }

    @Override
    public void changeRoles(String name) {


        if (name == null || name.isEmpty()) {
            throw new RuntimeException("Username is null or name is empty");
        }
        User existingUser = findByName(name);
        if (existingUser == null) {
            throw new RuntimeException("Username not found");
        }
        if (existingUser.getRoles().equals(Roles.Teacher)) {

            existingUser.setRoles(Roles.Student);
            TransactionManager.updateQuery(existingUser);

        } else if (existingUser.getRoles().equals(Roles.Student)) {
            existingUser.setRoles(Roles.Teacher);
            TransactionManager.updateQuery(existingUser);
        } else {
            System.out.println(" change failed ");
        }
    }

    @Override
    public User login(String firstName, String password) {
        if (firstName == null || password == null) {
            throw new IllegalArgumentException("firstName or password is null");
        }

        return
                TransactionManager.execute(entityManager ->

                        entityManager
                                .createQuery("from User u where u.firstName= :firstName and u.password= :password", User.class)
                                .setParameter("firstName", firstName).setParameter("password", password)
                                .getSingleResult());


    }
}