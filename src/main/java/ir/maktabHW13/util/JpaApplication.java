package ir.maktabHW13.util;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JpaApplication {



      private static final  EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");


        public  EntityManagerFactory getEntityManagerFactory() {
            return emf;
        }

        public static void shutdown() {
            if (emf != null) emf.close();
        }


}
