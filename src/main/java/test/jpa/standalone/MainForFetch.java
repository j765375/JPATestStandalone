package test.jpa.standalone;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import test.jpa.entity.Book;
import test.jpa.entity.manytoone.Department;
import test.jpa.entity.manytoone.Employee;
import test.jpa.entity.onetoone.Address;
import test.jpa.entity.onetoone.AddressForTwoWay;
import test.jpa.entity.onetoone.Customer;
import test.jpa.entity.onetoone.CustomerForTwoWay;

public class MainForFetch {

	private EntityManager em;
	
	public static void main(String[] args) {
		MainForFetch m = new MainForFetch();
		m.prepare();
		m.testLazy();
	}
	private void prepare() {
		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("JPATestStandalone");
		em = emf.createEntityManager();
	}
	
	private void testLazy() {
		em.getTransaction().begin();
		
		Customer c = em.find(Customer.class, 1L);
		if (c == null) {
			Address address = new Address();
			em.persist(address);
			c = new Customer(address);
			em.persist(c);
		}
		System.out.println("** get address start");
		Address address = c.getAddress();
		System.out.println("** get address : " + address);
		
		em.getTransaction().commit();
	}
	
}
