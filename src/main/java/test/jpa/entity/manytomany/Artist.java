package test.jpa.entity.manytomany;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Artist {

	@Id
	@GeneratedValue
	private long id;
	private String fitstName;
	private String lastName;
	@ManyToMany
	private List<CD> appearsOnCDs;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getFitstName() {
		return fitstName;
	}
	public void setFitstName(String fitstName) {
		this.fitstName = fitstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public List<CD> getAppearsOnCDs() {
		return appearsOnCDs;
	}
	public void setAppearsOnCDs(List<CD> appearsOnCDs) {
		this.appearsOnCDs = appearsOnCDs;
	}
	
}
