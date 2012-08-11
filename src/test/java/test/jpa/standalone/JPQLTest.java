package test.jpa.standalone;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import test.jpa.entity.BookWithId;

public class JPQLTest {

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
		Query countQuery = em.createQuery("select count(b) from BookWithId b");
		long count = (long)countQuery.getSingleResult();
		// エントリが20以内だった場合、20まで増やす
		if (count < 20) {
			for (int i = 0; i < 20 - count; i++) {
				em.persist(new BookWithId());
			}
		}
		tx.commit();
}

	@After
	public void tearDown() {
		em.close();
	}

	@Test
	public void testDynamicQuery() {
		// 名前付きパラメータを利用した動的クエリの発行
		Query q = em.createQuery("select b from BookWithId b where id = :bookId", BookWithId.class);
		q.setParameter("bookId", 5);
		BookWithId book = (BookWithId) q.getSingleResult();
		System.out.println(book);
		assertThat(book.getId(), is(5));		
	}
	
	@Test
	public void testDynamicQueryWithPaging() {
		// ページングの例
		Query pagingQuery = em.createQuery("select b from BookWithId b", BookWithId.class);
		
		final int MAX_OF_PAGE = 6;
		pagingQuery.setMaxResults(MAX_OF_PAGE);
		
		int currentPage = 0;
		List<BookWithId> result;
		
		do {
			pagingQuery.setFirstResult(currentPage * MAX_OF_PAGE);
			result = pagingQuery.getResultList();
			System.out.println("===== page: " + currentPage + " ====");
			printBooks(result);
			currentPage++;
		} while (result.size() > 0);
	}
	
	@Test
	public void testNamedQuery() {
		Query q = em.createNamedQuery(BookWithId.FIND_ALL, BookWithId.class);
		List<BookWithId> result = q.getResultList();
		assertThat(result.size() >= 20, is(true));
	}
	
	@Test
	public void testNativeQuery() {
		Query q = em.createNamedQuery(BookWithId.FIND_ALL_NATIVE, BookWithId.class);
		List<BookWithId> bookList = q.getResultList();
		printBooks(bookList);		
	}
	
	@Test
	public void testCriteriaAPI() {
		// select b from BookWithId where b.id = 3
		// をCriteriaAPIを使って実現
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<BookWithId> query = builder.createQuery(BookWithId.class);
		Root<BookWithId> b = query.from(BookWithId.class);
		query.select(b).where(builder.equal(b.get("id"), 3));
		Query q = em.createQuery(query);
		BookWithId result = (BookWithId) q.getSingleResult();
		System.out.println(result);
		assertThat(result.getId(), is(3));
		
		// 1普通にJPQLを使用した場合
		Query normalQuery = em.createQuery("select b from BookWithId b where b.id = 3", BookWithId.class);
		BookWithId normalResult = (BookWithId)normalQuery.getSingleResult();
		System.out.println(normalResult);
		assertThat(normalResult.getId(), is(3));
		
	}
	
	private void printBooks(List<BookWithId> bookList) {
		for (BookWithId bookWithId : bookList) {
			System.out.println(bookWithId);
		}
	}
}
