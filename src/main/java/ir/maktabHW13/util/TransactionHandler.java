package ir.maktabHW13.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.function.Consumer;
import java.util.function.Function;

public class TransactionHandler {


    public static <T> T execute(Function<EntityManager,T> action) {
        EntityManager entityManager = JpaApplication.getEntityManagerFactory()
                .createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();
            T result=action.apply(entityManager);
            entityTransaction.commit();
            return result;
        } catch (RuntimeException e) {

            if (entityTransaction.isActive()) entityTransaction.rollback();
            throw e;
        } finally {
            entityManager.close();
        }

    }
    public static void executeForPersist(Consumer<EntityManager> action) {
        execute(em -> {
            action.accept(em);
            return null;
        });
    }

    public void deleteQuery() {

    }

    public static  <T> T findQuery(Class<T> tClass, Object id) {
        return execute(entityManager -> entityManager.find(tClass, id));
    }
public static   <T> void updateQuery(T entity) {
    execute(entityManager ->

            entityManager.merge(entity));
}

}
