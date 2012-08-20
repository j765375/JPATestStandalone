package test.jpa.standalone;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import test.jpa.entity.onetoone.Address;
import test.jpa.entity.onetoone.Customer;

public class NPlusOneSelectTest {

	private EntityManagerFactory emf;
	private EntityManager em;
	private EntityTransaction tx;

	@Before
	public void setUp() {
		emf = Persistence
				.createEntityManagerFactory(EntityManagerUtil.UNIT_NAME);
		em = emf.createEntityManager();
		tx = em.getTransaction();

		tx.begin();
		// 集計関数countの使用例
		TypedQuery<Long> countQuery = em.createQuery("select count(a) from Address a", Long.class);
		long count = countQuery.getSingleResult();
		// エントリが20以内だった場合、20まで増やす
		if (count < 20) {
			for (int i = 0; i < 20 - count; i++) {
				em.persist(new Customer(new Address()));
			}
		}
		tx.commit();
}

	@After
	public void tearDown() {
		if (em != null) {
			em.close();
		}
	}

	@Test
	public void testNPlusOne() {
		TypedQuery<Customer> q = em.createQuery("select c from Customer c", Customer.class);
		List<Customer> result = q.getResultList();
		
		System.out.println("get customer : " + result.size());
		
		for (Customer customer : result) {
			System.out.println("address id : " + customer.getAddress().getId());
		}
		
		// SQL発行回数がN+1回になっていることをログで確認する
	}
	
	@Test
	public void testNPlusOneWithJoinFetch() {
		TypedQuery<Customer> q = em.createQuery("select c from Customer c join fetch c.address", Customer.class);
		List<Customer> result = q.getResultList();
		
		System.out.println("get customer: " + result.size());
		
		for (Customer customer : result) {
			System.out.println("address id : " + customer.getAddress().getId());
		}
		
		// SQL発行回数が1回であることをログで確認
	}
}
