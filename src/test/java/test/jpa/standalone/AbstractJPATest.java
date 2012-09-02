package test.jpa.standalone;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;

public abstract class AbstractJPATest {

	protected EntityManagerFactory emf;
	protected EntityManager em;
	protected EntityTransaction tx;

	@Before
	public final void setUpCommon() {
		emf = Persistence
				.createEntityManagerFactory(EntityManagerUtil.UNIT_NAME);
		em = emf.createEntityManager();
		tx = em.getTransaction();
	}

	@After
	public final void tearDownCommon() {
		if (em != null) {
			em.close();
		}
	}

}
