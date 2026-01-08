package ir.maktabHW13.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JpaApplication {

      public static final  EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");


        public static   EntityManagerFactory getEntityManagerFactory() {
            return emf;
        }

        public static void shutdown() {
            if (emf != null) emf.close();
        }


}
