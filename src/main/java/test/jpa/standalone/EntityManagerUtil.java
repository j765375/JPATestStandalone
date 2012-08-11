package test.jpa.standalone;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerUtil {

	public static final String UNIT_NAME = "JPATestStandalone";
	
	public static EntityManager createEntityManager() {
		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory(UNIT_NAME);
		return emf.createEntityManager();
	}
}
