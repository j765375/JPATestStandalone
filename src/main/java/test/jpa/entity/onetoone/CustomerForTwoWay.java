package test.jpa.entity.onetoone;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class CustomerForTwoWay {

	@Id
	@GeneratedValue
	private long id;
	private String firstName;
	private String lastName;
	private String email;
	private String phoneNumber;
	
	// @OneToOne 双方向
	@OneToOne
	private AddressForTwoWay addressForTwoWay;
	
	public CustomerForTwoWay() {
		this("aaa", "bbb", "mail", "090-1111-2222", null);
	}
	
	public CustomerForTwoWay(String firstName, String lastName, String email,
			String phoneNumber, AddressForTwoWay addressFowTwoWay) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.addressForTwoWay = addressFowTwoWay;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public AddressForTwoWay getAddressForTwoWay() {
		return addressForTwoWay;
	}
	public void setAddressForTwoWay(AddressForTwoWay addressForTwoWay) {
		this.addressForTwoWay = addressForTwoWay;
	}
	
	
}
