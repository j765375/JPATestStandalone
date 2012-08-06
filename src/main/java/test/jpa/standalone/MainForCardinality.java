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

public class MainForCardinality {

	private EntityManager em;
	
	public static void main(String[] args) {
		MainForCardinality m = new MainForCardinality();
		m.prepare();
		//m.testOneToOne();
		//m.testOneToOneForTwoWay();
		m.testManyToOne();
	}
	private void prepare() {
		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("JPATestStandalone");
		em = emf.createEntityManager();
	}
	
	private void testOneToOne() {
		em.getTransaction().begin();

		// addressは先に永続化しておかないと行けない（CUSTOMERの外部キーカラムはnullにできないため）
		Address address = new Address();
		em.persist(address);
		
		Customer customer = new Customer(address);
		em.persist(customer);
		
		// addressを共有してOneToOneではないようにしてもエラーにはならない
		Customer customer2 = new Customer(address);
		em.persist(customer2);
		
		em.getTransaction().commit();
		
		List<Customer> result = em.createQuery("select c from Customer as c").getResultList();
		for (Customer c : result) {
			System.out.println(c + " : " + c.getAddress());
		}
	}
	
	private void testOneToOneForTwoWay() {
		em.getTransaction().begin();
		
		AddressForTwoWay address = new AddressForTwoWay();
		em.persist(address);
		CustomerForTwoWay customer = new CustomerForTwoWay();
		em.persist(customer);
		customer.setAddressForTwoWay(address);
		address.setCustomerForTwoWay(customer);
		
		em.getTransaction().commit();
	}

	private void testManyToOne() {
		em.getTransaction().begin();
		
		Department dep1 = new Department();
		dep1.setDepName("dep1");
		Department dep2 = new Department();
		dep2.setDepName("dep2");
		em.persist(dep1);
		em.persist(dep2);
		
		Employee emp1 = new Employee("emp1", dep1);
		Employee emp2 = new Employee("emp2", dep1);
		Employee emp3 = new Employee("emp3", dep1);
		Employee emp4 = new Employee("emp4", dep2);
		
		em.persist(emp1);
		em.persist(emp2);
		em.persist(emp3);
		em.persist(emp4);
		
		em.getTransaction().commit();
	}
	
	private void test() {
		//Book book = new Book("1111", "aaa", "aaa", "aaa");
		//em.persist(book);
		//List<Book> result = em.createNamedQuery("selectAll").getResultList();
		// for (Book resultBook : result) {
		// System.out.println(resultBook.getId());
		// }
	}
}
