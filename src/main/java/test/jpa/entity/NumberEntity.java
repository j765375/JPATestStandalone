package test.jpa.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class NumberEntity {

	private long id;
	private long num;
	
	@Id
	@GeneratedValue
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	@GeneratedValue
	public long getNum() {
		return num;
	}
	public void setNum(long num) {
		this.num = num;
	}
	
	
	
}

