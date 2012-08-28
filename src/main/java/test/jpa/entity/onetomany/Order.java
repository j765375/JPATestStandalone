package test.jpa.entity.onetomany;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
// Derbyでは予約後であるORDERをテーブル名に使用することはできない
@Table(name = "ORDER_TABLE")
public class Order {
	
	@Id
	@GeneratedValue
	private long id;
	@Temporal(TemporalType.TIMESTAMP)
	private Date creationDate;
	@OneToMany(cascade = CascadeType.PERSIST)
	private List<OrderLine> orderLines;
	@OneToMany(cascade = CascadeType.PERSIST)
	private List<OrderLineForTwoWay> orderLines2;

	public Order() {
		this.creationDate = new Date();
		this.orderLines = new ArrayList<>();
		orderLines.add(new OrderLine());
		orderLines.add(new OrderLine());
		orderLines.add(new OrderLine());
		this.orderLines2 = new ArrayList<>();
		orderLines2.add(new OrderLineForTwoWay(this));
		orderLines2.add(new OrderLineForTwoWay(this));
		orderLines2.add(new OrderLineForTwoWay(this));
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public List<OrderLine> getOrderLines() {
		return orderLines;
	}
	public void setOrderLines(List<OrderLine> orderLines) {
		this.orderLines = orderLines;
	}
	public List<OrderLineForTwoWay> getOrderLines2() {
		return orderLines2;
	}
	public void setOrderLines2(List<OrderLineForTwoWay> orderLines2) {
		this.orderLines2 = orderLines2;
	}
	
	
}
