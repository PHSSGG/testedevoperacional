package phss.testedevoperacional.sale;

import phss.testedevoperacional.models.Company;
import phss.testedevoperacional.models.Customer;
import phss.testedevoperacional.models.Product;
import phss.testedevoperacional.models.Sale;

import java.util.ArrayList;
import java.util.List;

public class SaleManager {

    public Sale createSale(Company company, Customer customer, List<Sale> sales, List<Product> shoppingCart) {
        Double total = shoppingCart.stream().mapToDouble(Product::getPrice).sum();
        Double commission = total * company.getTax();
        int saleId = sales.isEmpty() ? 1 : sales.get(sales.size() - 1).getCode() + 1;
        Sale sale = new Sale(saleId, new ArrayList<>(shoppingCart), total, commission, company, customer);

        company.setBalance(company.getBalance() + total);

        return sale;
    }

}
