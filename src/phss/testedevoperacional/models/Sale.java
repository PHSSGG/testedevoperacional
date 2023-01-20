package phss.testedevoperacional.models;

import java.util.List;

public class Sale {
	final private Integer code;
	private List<Product> items;
	private Double value;
	private Double commission;
	private Company company;
	private Customer customer;

	public Sale(Integer code, List<Product> items, Double value, Double commission, Company company, Customer customer) {
		this.code = code;
		this.items = items;
		this.value = value;
		this.commission = commission;
		this.company = company;
		this.customer = customer;
	}

	public Integer getCode() {
		return code;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public List<Product> getItems() {
		return items;
	}

	public void setItems(List<Product> items) {
		this.items = items;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public Double getCommission() {
		return commission;
	}

	public void setCommission(Double commission) {
		this.commission = commission;
	}

}
