package test.jpa.standalone;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import test.jpa.entity.Book;
import test.jpa.entity.BookWithId;

public class JPQLTest extends AbstractJPATest {

	private int existId;

	@Before
	public void setUp() {
		tx.begin();
		// 集計関数countの使用例
		TypedQuery<Long> countQuery = em.createQuery(
				"select count(b) from BookWithId b", Long.class);
		long count = countQuery.getSingleResult();
		// エントリが20以内だった場合、20まで増やす
		if (count < 20) {
			for (int i = 0; i < 20 - count; i++) {
				em.persist(new BookWithId());
			}
		}
		tx.commit();

		// 確実に存在するIDを一つ記憶しておく
		existId = em
				.createQuery("SELECT b FROM BookWithId b", BookWithId.class)
				.getResultList().get(0).getId();
	}

	@Test
	public void testDynamicQuery() {
		// 名前付きパラメータを利用した動的クエリの発行
		TypedQuery<BookWithId> q = em.createQuery(
				"select b from BookWithId b where id = :bookId",
				BookWithId.class);
		q.setParameter("bookId", existId);
		BookWithId book = q.getSingleResult();
		System.out.println(book);
		assertThat(book.getId(), is(existId));
	}

	@Test
	public void testDynamicQueryWithPaging() {
		// ページングの例
		TypedQuery<BookWithId> pagingQuery = em.createQuery(
				"select b from BookWithId b", BookWithId.class);

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
		TypedQuery<BookWithId> q = em.createNamedQuery(BookWithId.FIND_ALL,
				BookWithId.class);
		List<BookWithId> result = q.getResultList();
		assertThat(result.size() >= 20, is(true));
	}

	@Test
	public void testNamedQueryOnXML() {
		TypedQuery<BookWithId> q = em.createNamedQuery(
				"BookWithId.findAllFromXML", BookWithId.class);
		List<BookWithId> result = q.getResultList();
		assertThat(result.size() >= 20, is(true));
	}

	@Test
	public void testNativeQuery() {
		TypedQuery<BookWithId> q = em.createNamedQuery(
				BookWithId.FIND_ALL_NATIVE, BookWithId.class);
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
		Query normalQuery = em.createQuery(
				"select b from BookWithId b where b.id = 3", BookWithId.class);
		BookWithId normalResult = (BookWithId) normalQuery.getSingleResult();
		System.out.println(normalResult);
		assertThat(normalResult.getId(), is(3));
	}

	@Test
	public void testDelete() {
		BookWithId add = new BookWithId();
		tx.begin();
		em.persist(add);
		tx.commit();

		System.out.println("added book id is : " + add.getId());

		tx.begin();
		int result = em
				.createQuery("DELETE FROM BookWithId b WHERE b.id = :id")
				.setParameter("id", add.getId()).executeUpdate();
		tx.commit();

		assertThat(result, is(1));

		try {
			BookWithId b = em
					.createQuery("SELECT b FROM BookWithId b WHERE b.id = :id",
							BookWithId.class).setParameter("id", add.getId())
					.getSingleResult();
			fail();
		} catch (NoResultException e) {
			System.out.println("delete success");
		}
	}

	@Test
	public void testDynamicSelect() {
		String baseQuery = "SELECT b FROM BookWithId b WHERE 1 = 1 ";

		BookWithId criteria = new BookWithId("", "title1", null, "publisher2");
		String dynamicClause = "";

		boolean hasIsbn = !isEmpty(criteria.getIsbn());
		boolean hasTitle = !isEmpty(criteria.getTitle());
		boolean hasAuthor = !isEmpty(criteria.getAuthor());
		boolean hasPublisher = !isEmpty(criteria.getPublisher());

		if (hasIsbn) {
			dynamicClause += "AND b.isbn = :isbn ";
		}
		if (hasTitle) {
			dynamicClause += "AND b.title = :title ";
		}
		if (hasAuthor) {
			dynamicClause += "AND b.author = :author ";
		}
		if (hasPublisher) {
			dynamicClause += "AND b.publisher = :publisher ";
		}

		System.out.println("Query : " + baseQuery + dynamicClause);

		TypedQuery<BookWithId> query = em.createQuery(
				baseQuery + dynamicClause, BookWithId.class);

		if (hasIsbn) {
			query.setParameter("isbn", criteria.getIsbn());
		}
		if (hasTitle) {
			query.setParameter("title", criteria.getTitle());
		}
		if (hasAuthor) {
			query.setParameter("author", criteria.getAuthor());
		}
		if (hasPublisher) {
			query.setParameter("publisher", criteria.getPublisher());
		}

		List<BookWithId> result = query.getResultList();
		printBooks(result);
	}

	private boolean isEmpty(String str) {
		return (str == null) || (str.length() == 0);
	}

	private void printBooks(List<BookWithId> bookList) {
		for (BookWithId bookWithId : bookList) {
			System.out.println(bookWithId);
		}
	}
}
