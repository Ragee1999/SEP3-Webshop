package sep3.webshop.model;

import jakarta.persistence.*;

@Entity
public class OrderItem {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "orderId", nullable = false)
  private CustomerOrder customerOrder;

  @ManyToOne
  @JoinColumn(name = "productId", nullable = false)
  private Product product;

  @Column(nullable = false)
  private int quantity;

  public Long getId() {
    return id;
  }

  public CustomerOrder getCustomerOrder() {
    return customerOrder;
  }

  public void setCustomerOrder(CustomerOrder customerOrder) {
    this.customerOrder = customerOrder;
  }

  public Product getProduct() {
    return product;
  }

  public void setProduct(Product product) {
    this.product = product;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  public void addQuantity(int quantity) {
    this.quantity += quantity;
  }
}
