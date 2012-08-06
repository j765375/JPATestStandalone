package test.jpa.entity.onetoone;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class AddressForTwoWay {
	@Id
	@GeneratedValue
	private long id;
	private String street1;
	private String street2;
	private String city;
	private String state;
	private String zipcode;
	private String country;
	
	// @OneToOneの双方向
	// 被所有テーブルではmappedByを書いておかないとエラーになる
	@OneToOne(mappedBy = "addressForTwoWay")
	private CustomerForTwoWay customerForTwoWay;
	
	public AddressForTwoWay() {
		this("street1", "street2", "city", "state", "zipcode", "country", null);
	}
	
	public AddressForTwoWay(String street1, String street2, String city,
			String state, String zipcode, String country,
			CustomerForTwoWay customerForTwoWay) {
		this.street1 = street1;
		this.street2 = street2;
		this.city = city;
		this.state = state;
		this.zipcode = zipcode;
		this.country = country;
		this.customerForTwoWay = customerForTwoWay;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getStreet1() {
		return street1;
	}
	public void setStreet1(String street1) {
		this.street1 = street1;
	}
	public String getStreet2() {
		return street2;
	}
	public void setStreet2(String street2) {
		this.street2 = street2;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public CustomerForTwoWay getCustomerForTwoWay() {
		return customerForTwoWay;
	}
	public void setCustomerForTwoWay(CustomerForTwoWay customerForTwoWay) {
		this.customerForTwoWay = customerForTwoWay;
	}
	
	
}
