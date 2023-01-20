package phss.testedevoperacional.models;

public class Product {

	final private Integer id;
	private String name;
	private Integer quantity;
	private Double price;
	private Company company;

	public Product(Integer id, String name, Integer quantity, Double price, Company company) {
		this.id = id;
		this.name = name;
		this.quantity = quantity;
		this.price = price;
		this.company = company;
	}

	public Integer getId() {
		return id;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

}
