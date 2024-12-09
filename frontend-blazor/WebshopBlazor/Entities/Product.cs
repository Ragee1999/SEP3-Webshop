namespace WebshopBlazor.Entities;

public class Product
{
    public long Id { get; set; }
    public string Name { get; set; }
    public string Image { get; set; }
    public double Price { get; set; }
    public int StockQuantity { get; set; }
    public string Description { get; set; }
}