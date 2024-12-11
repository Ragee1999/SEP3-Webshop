package sep3.webshop.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class CustomerOrder {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private double totalPrice;

  @Column(nullable = false)
  private int totalQuantity;

  @ManyToOne
  @JoinColumn(name = "customerId", nullable = false)
  private Customer customer;

  @ManyToOne
  @JoinColumn(name = "addressId", nullable = false)
  private Address address;

  @OneToMany(mappedBy = "customerOrder", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonManagedReference // Allows serialization of the child collection
  private List<OrderItem> orderItems = new ArrayList<>();

  @Column(name = "created_at", updatable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date createdAt = new Date();

  @Column(nullable = false)
  private String status;

  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  public Long getId() {
    return id;
  }

  public double getTotalPrice() {
    return totalPrice;
  }

  public void setTotalPrice(double totalPrice) {
    this.totalPrice = totalPrice;
  }

  public int getTotalQuantity() {
    return totalQuantity;
  }

  public void setTotalQuantity(int totalQuantity) {
    this.totalQuantity = totalQuantity;
  }

  public void addQuantity(int quantity) {
    this.totalQuantity += quantity;
  }

  public void removeQuantity(int quantity) {
    this.totalQuantity -= quantity;
  }

  public Customer getCustomer() {
    return customer;
  }

  public void setCustomer(Customer customer) {
    this.customer = customer;
  }

  public Address getAddress() {
    return address;
  }

  public void setAddress(Address address) {
    this.address = address;
  }

  public List<OrderItem> getOrderItems() {
    return orderItems;
  }

  public void addOrderItem(OrderItem orderItem) {
    this.orderItems.add(orderItem);
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public Long getCustomerId() {
    return customer != null ? customer.getId() : null;
  }

  public Long getAddressId() {
    return address != null ? address.getId() : null;
  }
}
