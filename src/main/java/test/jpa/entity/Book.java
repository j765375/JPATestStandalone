package test.jpa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SecondaryTable;
import javax.persistence.Table;

@Entity
// NamedQueryはEntityに書く
@NamedQuery(name="selectAll", query="select b from Book as b")
public class Book {

	private String isbn;
	
	private String title;
	
	private String author;
	
	private String publisher;

	public Book() {
		this("isbn123456", "title1", "author1", "publisher1");
	}
	
	public Book(String isbn, String title, String author, String publisher) {
		this.isbn = isbn;
		this.title = title;
		this.author = author;
		this.publisher = publisher;
	}

	@Id
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

	@Column(length = 500)
	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	@Override
	public String toString() {
		return "Book [isbn=" + isbn + ", title=" + title
				+ ", author=" + author + ", publisher=" + publisher + "]";
	}
	
}
