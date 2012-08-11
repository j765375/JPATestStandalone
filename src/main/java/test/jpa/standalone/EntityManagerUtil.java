package test.jpa.standalone;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerUtil {

	public static EntityManager createEntityManager() {
		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("JPATestStandalone");
		return emf.createEntityManager();
	}
}
