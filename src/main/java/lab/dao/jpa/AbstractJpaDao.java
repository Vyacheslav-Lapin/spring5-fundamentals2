package lab.dao.jpa;

import lombok.Setter;
import lombok.val;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import java.util.function.Consumer;
import java.util.function.Function;

public class AbstractJpaDao {

	@Setter(onMethod = @__(@PersistenceUnit))
	protected EntityManagerFactory emf;

	protected <T> T mapEntityManagerFactory(Function<EntityManagerFactory, T> entityManagerFactoryMapper) {
		return entityManagerFactoryMapper.apply(emf);
	}

	protected void withEntityManagerFactory(Consumer<EntityManagerFactory> entityManagerFactoryConsumer) {
	    entityManagerFactoryConsumer.accept(emf);
    }

    protected <T> T mapEntityManager(Function<EntityManager, T> entityManagerMapper) {
        val entityManager = emf.createEntityManager();
        val result = entityManagerMapper.apply(entityManager);
        entityManager.close();
        return result;
    }

    protected void withEntityManager(Consumer<EntityManager> entityManagerConsumer) {
        val entityManager = emf.createEntityManager();
        entityManagerConsumer.accept(entityManager);
        entityManager.close();
    }

    protected <T> T mapEntityManagerInTransaction(Function<EntityManager, T> entityManagerMapper) {
	    return mapEntityManager(entityManager -> {
            val transaction = entityManager.getTransaction();
            val result =  entityManagerMapper.apply(entityManager);
            transaction.commit();
            return result;
        });
    }

    protected void withEntityManagerInTransaction(Consumer<EntityManager> entityManagerConsumer) {
	    withEntityManager(entityManager -> {
            val transaction = entityManager.getTransaction();
            entityManagerConsumer.accept(entityManager);
            transaction.commit();
        });
    }
}