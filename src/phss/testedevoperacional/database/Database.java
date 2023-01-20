package phss.testedevoperacional.database;

import phss.testedevoperacional.models.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Database {

    // SIMULANDO BANCO DE DADOS

    List<Product> shoppingCart = new ArrayList<>();
    List<Sale> sales = new ArrayList<>();

    List<Company> companies = populateCompanies();
    List<User> users = populateUsers();
    List<Product> products = populateProducts();

    private List<Company> populateCompanies() {
        Company company = new Company(2, "SafeWay Padaria", "30021423000159", 0.15, 0.0);
        Company company2 = new Company(1, "Level Varejo", "53239160000154", 0.05, 0.0);
        Company company3 = new Company(3, "SafeWay Restaurante", "41361511000116", 0.20, 0.0);

        return Arrays.asList(company, company2, company3);
    }

    private List<Product> populateProducts() {
        Company company = companies.stream().filter(it -> it.getId().equals(2)).toList().get(0);
        Company company2 = companies.stream().filter(it -> it.getId().equals(1)).toList().get(0);
        Company company3 = companies.stream().filter(it -> it.getId().equals(3)).toList().get(0);

        Product product = new Product(1, "Pão Frances", 5, 3.50, company);
        Product product2 = new Product(2, "Coturno", 10, 400.0, company2);
        Product product3 = new Product(3, "Jaqueta Jeans", 15, 150.0, company2);
        Product product4 = new Product(4, "Calça Sarja", 15, 150.0, company2);
        Product product5 = new Product(5, "Prato feito - Frango", 10, 25.0, company3);
        Product product6 = new Product(6, "Prato feito - Carne", 10, 25.0, company3);
        Product product7 = new Product(7, "Suco Natural", 30, 10.0, company3);
        Product product8 = new Product(8, "Sonho", 5, 8.50, company);
        Product product9 = new Product(9, "Croissant", 7, 6.50, company);
        Product product10 = new Product(10, "Ché Gelado", 4, 5.50, company);

        return Arrays.asList(product, product2, product3, product4, product5, product6, product7,
                product8, product9, product10);
    }

    private List<User> populateUsers() {
        Customer customer = new Customer("07221134049", "Allan da Silva", "customer", 20);
        Customer customer2 = new Customer("72840700050", "Samuel da Silva", "customer2", 24);

        Company company = companies.stream().filter(it -> it.getId().equals(2)).toList().get(0);
        Company company2 = companies.stream().filter(it -> it.getId().equals(1)).toList().get(0);
        Company company3 = companies.stream().filter(it -> it.getId().equals(3)).toList().get(0);

        User user1 = new User("admin", "1234", null, null);
        User user2 = new User("company", "1234", null, company);
        User user3 = new User("customer", "1234", customer, null);
        User user4 = new User("customer2", "1234", customer2, null);
        User user5 = new User("company2", "1234", null, company2);
        User user6 = new User("company3", "1234", null, company3);

        return Arrays.asList(user1, user2, user3, user4, user5, user6);
    }

    public List<Product> getShoppingCart() {
        return shoppingCart;
    }

    public List<Sale> getSales() {
        return sales;
    }

    public List<User> getUsers() {
        return users;
    }

    public List<Company> getCompanies() {
        return companies;
    }

    public List<Product> getProducts() {
        return products;
    }

}
