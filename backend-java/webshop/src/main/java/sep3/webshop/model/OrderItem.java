package sep3.webshop.model;

import jakarta.persistence.*;

@Entity
public class OrderItem
{

  @Id @ManyToOne @JoinColumn(name = "orderId", nullable = false) private CustomerOrder customerOrder;

  @Id @ManyToOne @JoinColumn(name = "productId", nullable = false) private Product product;

  @Column(nullable = false) private int quantity;

  public CustomerOrder getCustomerOrder()
  {
    return customerOrder;
  }

  public Product getProduct()
  {
    return product;
  }

  public int getQuantity()
  {
    return quantity;
  }

  public void addQuantity(int quantity)
  {
    this.quantity += quantity;
  }

  public void removeQuantity(int quantity)
  {
    this.quantity -= quantity;
  }

}
