package test.jpa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SecondaryTable;
import javax.persistence.Table;

@Entity
@SecondaryTable(name="SUB")
public class BookWithSubTable {

	private int id;

	private String isbn;
	
	private String title;
	
	private String author;
	
	private String publisher;

	public BookWithSubTable() {
		this("isbn123456", "title1", "author1", "publisher1");
	}
	
	public BookWithSubTable(String isbn, String title, String author, String publisher) {
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

	@Column(table="SUB")
	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	@Column(length = 500)
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
