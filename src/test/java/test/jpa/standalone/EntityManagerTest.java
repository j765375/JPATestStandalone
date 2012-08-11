package test.jpa.standalone;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.management.RuntimeErrorException;
import javax.persistence.Cache;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.LockModeType;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.transaction.Transaction;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import test.jpa.entity.Book;
import test.jpa.entity.BookWithId;
import test.jpa.entity.BookWithVersion;

public class EntityManagerTest {

	private EntityManagerFactory emf;
	private EntityManager em;
	private EntityTransaction tx;

	@Before
	public void setUp() {
		emf = Persistence
				.createEntityManagerFactory(EntityManagerUtil.UNIT_NAME);
		em = emf.createEntityManager();
		tx = em.getTransaction();
	}

	@After
	public void tearDown() {
		em.close();
	}

	@Test(expected = EntityExistsException.class)
	public void testDuplicateId() {
		// book1とbook2で同じID(ISBN)を持たせる
		Book book1 = new Book();
		Book book2 = new Book();
		tx.begin();
		em.persist(book1);

		// ここでEntityExistsException
		// 永続コンテキスト内で重複しているため、DBへのSQLはまだ発行されていない段階
		em.persist(book2);

		// ここには到達しない。
		fail();
		// 仮に永続化コンテキスト内でIDが重複しない場合で、DBに同じIDを持つ行がすでに登録されている場合、
		// コミットのタイミングでPersistenceExceptionが発生する
		tx.commit();
	}

	@Test
	public void testGetReference() {
		tx.begin();
		BookWithId book = new BookWithId();
		em.persist(book);
		tx.commit();
		// 一旦永続化コンテキストをクリアする
		em.clear();

		BookWithId newBook = em.getReference(BookWithId.class, book.getId());
		System.out.println(newBook.getId());
		// ID以外の情報にアクセスすると、SQLを発行して情報を取得する（Lazy Fetch)
		System.out.println(newBook.getTitle());

		// 一度永続化コンテキストをクリアしているので、bookはdetachされている
		// そのため、newBook(Entity)とbook(not Entity)は違うインスタンスとなる
		assertThat(newBook, is(not(book)));
	}

	@Test
	public void testRefresh() {
		tx.begin();
		BookWithId book = new BookWithId();
		String beforeTitle = "before refresh";
		String afterTitle = "after refresh";
		book.setTitle(afterTitle);
		em.persist(book);

		book.setTitle(beforeTitle);

		System.out.println(book.getTitle());
		assertThat(book.getTitle(), is(beforeTitle));

		em.refresh(book);

		System.out.println(book.getTitle());
		assertThat(book.getTitle(), is(afterTitle));

		tx.commit();
	}

	@Test
	@Ignore("hibernateのcacheの設定をしないと動かない模様")
	public void testCache() {
		tx.begin();
		Cache cache = emf.getCache();

		BookWithId book = em.find(BookWithId.class, 1);
		if (book == null) {
			book = new BookWithId();
			em.persist(book);
		}
		assertThat(cache.contains(BookWithId.class, 1), is(true));

		tx.commit();
	}

	@Test
	//@Ignore("複数同時実行させてロックの動きを確かめること")
	public void testOptimisticLock() {
		tx.begin();
		BookWithVersion book = em.find(BookWithVersion.class, 1);
		if (book == null) {
			book = new BookWithVersion();
			em.persist(book);
			em.flush();
		}

		System.out.println("version: " + book.getVersion());

		// ロックを取得(暗黙的に行われるので必要ない）
		// em.lock(book, LockModeType.OPTIMISTIC);
		book.setTitle(String.valueOf((int)(Math.random() * 999999)));
		System.out.println("title: " + book.getTitle());
		
		waitUserInput();

		try{
			tx.commit();
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Test
	//@Ignore("複数同時実行させてロックの動きを確かめること")
	public void testPesimisticLock() {
		tx.begin();
		Query q = em.createQuery("select b from BookWithId b where b.id = 1");
		// PESSIMISTIC_WRITEだと、後続のトランザクションはデータを読み込めず、待たされる。
		// PESSIMISTIC_READだと、後続のトランザクションはデータを読めるが、コミット時に待たされる。
		q.setLockMode(LockModeType.PESSIMISTIC_READ);
		BookWithId book = (BookWithId) q.getSingleResult();
		System.out.println(book);
		book.setTitle(String.valueOf((int)(Math.random() * 999999)));
		
		waitUserInput();
		
		try {
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	private void waitUserInput() {
		// ユーザの一行入力を待つ
		try (InputStreamReader isr = new InputStreamReader(System.in);
				BufferedReader stdReader = new BufferedReader(isr)) {
			System.out.print("INPUT : ");
			String line = stdReader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
