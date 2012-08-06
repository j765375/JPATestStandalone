package test.jpa.entity.manytomany;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class CD {

	@Id
	@GeneratedValue
	private long id;
	private String title;
	private float price;
	private String description;
	@ManyToMany(mappedBy = "appearsOnCDs")
	private List<Artist> createdByArtists;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<Artist> getCreatedByArtists() {
		return createdByArtists;
	}
	public void setCreatedByArtists(List<Artist> createdByArtists) {
		this.createdByArtists = createdByArtists;
	}
	
	
	
}
