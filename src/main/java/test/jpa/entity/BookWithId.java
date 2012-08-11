package test.jpa.entity;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
//@Cacheable(true)
@NamedQueries({
	@NamedQuery(name = BookWithId.FIND_ALL, query = "select b from BookWithId b")
})
@NamedNativeQueries({
	@NamedNativeQuery(
			name = BookWithId.FIND_ALL_NATIVE, 
			query = "select * from BookWithId",
			resultClass = BookWithId.class
	)
})
public class BookWithId {

	// タイプミスを避けるためにクエリ名を定数化。
	// クエリ名は永続性ユニットごとに一意である必要が有るため、一般的にEntityクラス名を頭につける
	public static final String FIND_ALL = "BookWithId.findAll";
	
	public static final String FIND_ALL_NATIVE = "native.BookWithId.findAll";
	public static final String COUNT_NATIVE = "native.BookWithId.count";
	
	private int id;

	private String isbn;
	
	private String title;
	
	private String author;
	
	private String publisher;

	public BookWithId() {
		this("isbn123456", "title1", "author1", "publisher1");
	}
	
	public BookWithId(String isbn, String title, String author, String publisher) {
		this.isbn = isbn;
		this.title = title;
		this.author = author;
		this.publisher = publisher;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Book [id=" + id + ", isbn=" + isbn + ", title=" + title
				+ ", author=" + author + ", publisher=" + publisher + "]";
	}
	
}
