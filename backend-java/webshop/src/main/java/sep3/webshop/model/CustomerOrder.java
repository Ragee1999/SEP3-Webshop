package sep3.webshop.model;

import jakarta.persistence.*;

@Entity
public class CustomerOrder
{
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;

  @Column(nullable = false) private double Totalprice;

  @Column(nullable = false) private int totalQuantity;

  @ManyToOne @JoinColumn(name = "customerId", nullable = false) private Customer customer;

  @ManyToOne @JoinColumn(name = "addressId", nullable = false) private Address address;

  // Getters and Setters
  public Long getId()
  {
    return id;
  }

  public double getPrice()
  {
    return Totalprice;
  }

  public void setPrice(double price)
  {
    this.Totalprice = price;
  }

  public int getTotalQuantity()
  {
    return totalQuantity;
  }

  public void setTotalQuantity(int totalQuantity)
  {
    this.totalQuantity = totalQuantity;
  }

  public void addQuantity(int quantity)
  {
    this.totalQuantity += quantity;
  }

  public void removeQuantity(int quantity)
  {
    this.totalQuantity -= quantity;
  }

  public Customer getCustomer()
  {
    return customer;
  }

  public void getAddress(Address address)
  {
    this.address = address;
  }

  public void setAddress(Address address)
  {
    this.address = address;
  }

}

