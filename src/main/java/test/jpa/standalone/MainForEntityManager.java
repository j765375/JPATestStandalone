package test.jpa.standalone;

import javax.persistence.EntityManager;

import test.jpa.entity.Book;

public class MainForEntityManager {

	private EntityManager em;
	
	public static void main(String[] args) {
		
		MainForEntityManager m = new MainForEntityManager();
		m.prepare();
		m.testDuplicateEntity();
	}

	private void prepare() {
		em = EntityManagerUtil.createEntityManager();
	}
	
	private void testDuplicateEntity() {
		Book book1 = new Book();
		Book book2 = new Book();
		book2.setTitle("title2");
		// book1とbook2で同じIDを持たせる
		book1.setId(1);
		book2.setId(book1.getId());
		
		em.getTransaction().begin();
		
		em.persist(book1);
		// ここでEntityExistsException
		// 永続コンテキスト内で重複しているため、DBへのSQLはまだ発行されていない段階
		em.persist(book2);
		
		// 仮に永続化コンテキスト内でIDが重複しない場合で、DBに同じIDを持つ行がすでに登録されている場合、
		// コミットのタイミングでPersistenceExceptionが発生する
		em.getTransaction().commit();
		
		Book result = em.find(Book.class, book1.getId());
		
		System.out.println(result);
		
	}

	
}
