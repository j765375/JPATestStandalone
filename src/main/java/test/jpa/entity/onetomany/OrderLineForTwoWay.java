package test.jpa.entity.onetomany;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class OrderLineForTwoWay {

	@Id
	@GeneratedValue
	private long id;
	private String item;
	private double unitPrice;
	private int quantity;
	
	@ManyToOne
	private Order order;
	
	public OrderLineForTwoWay() {
	}
	
	public OrderLineForTwoWay(Order order) {
		this("itemTwoWay", 50.0, 100, order);
	}
	
	public OrderLineForTwoWay(String item, double unitPrice,
			int quantity, Order order) {
		this.item = item;
		this.unitPrice = unitPrice;
		this.quantity = quantity;
		this.order = order;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
	public double getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	
	
	
}
