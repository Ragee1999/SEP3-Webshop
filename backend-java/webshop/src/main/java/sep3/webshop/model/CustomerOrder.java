package sep3.webshop.model;

import jakarta.persistence.*;
import java.util.ArrayList;
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
  private List<OrderItem> orderItems = new ArrayList<>();

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
}

