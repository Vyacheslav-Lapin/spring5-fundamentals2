package lab.dao.jpa;

import lombok.Setter;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceUnit;
import java.util.function.Consumer;
import java.util.function.Function;

public class AbstractJpaDao {

    @Setter(onMethod = @__(@PersistenceUnit))
    protected EntityManagerFactory lcemf;

    protected void runWithEntityManager(Consumer<EntityManager> consumer) {
        doWithEntityManager((em) -> {
            consumer.accept(em);
            return new Object();
        });
    }

    protected <T> T doWithEntityManager(Function<EntityManager, T> function) {
        EntityManager em = lcemf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        try {
            T result = function.apply(em);
            em.flush();
            transaction.commit();
            return result;
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

}