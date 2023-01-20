package phss.testedevoperacional;

import phss.testedevoperacional.data.DataResult;
import phss.testedevoperacional.data.repository.UserRepository;
import phss.testedevoperacional.database.Database;
import phss.testedevoperacional.models.*;
import phss.testedevoperacional.sale.SaleManager;

import java.util.*;

public class Main {

	public static UserRepository userRepository;

	public static void main(String[] args) {
		Database database = new Database();
		userRepository = new UserRepository(database);

		execute(database.getUsers(), database.getCompanies(), database.getProducts(), database.getShoppingCart(), database.getSales());
	}

	public static void execute(List<User> users, List<Company> companies,
							   List<Product> products, List<Product> shoppingCart, List<Sale> sales) {
		Scanner scanner = new Scanner(System.in);

		if (!userRepository.isLogged()) {
			System.out.println("Entre com seu usuário e a senha:");
			System.out.print("Usuário: ");
			String username = scanner.next();
			System.out.print("Senha: ");
			String password = scanner.next();

			DataResult<User> loginResult = userRepository.login(username, password);
			if (!loginResult.isSuccess()) {
				System.out.println(loginResult.getError());
				execute(users, companies, products, shoppingCart, sales);
				return;
			}
		}

		User user = userRepository.getLoggedUser();

		if (user.isAdmin()) {
			executeForAdmin(scanner, user, users, companies, products, shoppingCart, sales);
			return;
		}

		System.out.println("Escolha uma opção para iniciar");
		if (user.isCompany()) executeForCompany(scanner, user, users, companies, products, shoppingCart, sales);
		else executeForUser(scanner, user, users, companies, products, shoppingCart, sales);
	}

	public static void executeForAdmin(Scanner scanner, User user,
									   List<User> users, List<Company> companies,
									   List<Product> products, List<Product> shoppingCart, List<Sale> sales) {
		System.out.println("1 - Visualizar como uma empresa");
		System.out.println("0 - Deslogar");

		switch (scanner.nextInt()) {
			case 1 -> {
				System.out.println("escolha a empresa para visualizar as informações: ");

				// listando empresas
				if (companies.isEmpty()) {
					System.out.println("Não existem empresas no nosso banco de dados.");
					execute(users, companies, products, shoppingCart, sales);
					return;
				} else companies.forEach(company -> System.out.println(company.getId() + " - " + company.getName()));

				int companyId;
				Optional<Company> foundCompany;

				do {
					companyId = scanner.nextInt();
					int finalCompanyId = companyId;
					foundCompany = companies.stream().filter(selectedCompany -> selectedCompany.getId().equals(finalCompanyId)).findFirst();

					if (foundCompany.isEmpty()) System.out.println("Empresa não encontrada, tente novamente");
				} while (foundCompany.isEmpty());

				System.out.println("Logado como empresa. Escolha uma opção para continuar: ");

				user.setCompany(foundCompany.get());
				executeForCompany(scanner, user, users, companies, products, shoppingCart, sales);
				return;
			}
			case 0 -> {
				userRepository.logout();
				execute(users, companies, products, shoppingCart, sales);
				return;
			}
		}

		executeForAdmin(scanner, user, users, companies, products, shoppingCart, sales);
	}

	public static void executeForCompany(Scanner scanner, User user,
								  List<User> users, List<Company> companies,
								  List<Product> products, List<Product> shoppingCart, List<Sale> sales) {
		System.out.println("1 - Listar vendas");
		System.out.println("2 - Ver produtos");
		System.out.println("0 - Deslogar");

		switch (scanner.nextInt()) {
			case 1 -> {
				System.out.println();
				System.out.println("************************************************************");
				System.out.println("VENDAS EFETUADAS");

				List<Sale> companySales = sales.stream().filter(sale -> sale.getCompany().getId().equals(user.getCompany().getId())).toList();
				if (companySales.isEmpty()) System.out.println("Essa empresa ainda não vendeu nenhum produto.");
				else {
					for (Sale sale : companySales) {
						// listando vendas
						System.out.println("************************************************************");
						System.out.println("Venda de código: " + sale.getCode() + " no CPF " + sale.getCustomer().getCpf() + ": ");
						sale.getItems().forEach(product -> System.out.println(product.getId() + " - " + product.getName() + "    R$" + product.getPrice()));

						// total
						System.out.println("Total: R$" + sale.getValue());
						System.out.println("Taxa a ser paga: R$" + sale.getCommission());
						System.out.println("Total Líquido  para empresa: R$" + (sale.getValue() - sale.getCommission()));
						System.out.println("************************************************************");
					}
				}

				// listando saldo
				System.out.println("Saldo da empresa: " + user.getCompany().getBalance());
				System.out.println("************************************************************");
			}
			case 2 -> {
				System.out.println();
				System.out.println("************************************************************");
				System.out.println("MEUS PRODUTOS");

				// listando produtos
				List<Product> companyProducts = products.stream().filter(product -> product.getCompany().getId().equals(user.getCompany().getId())).toList();
				if (companyProducts.isEmpty()) System.out.println("Essa empresa ainda não possui nenhum produto.");
				else {
					for (Product product : companyProducts) {
						// informações do produto
						System.out.println("************************************************************");
						System.out.println("Código: " + product.getId());
						System.out.println("Produto: " + product.getName());
						System.out.println("Quantidade em estoque: " + product.getQuantity());
						System.out.println("Valor: R$" + product.getPrice());
						System.out.println("************************************************************");
					}
				}

				System.out.println("Saldo da empresa: " + user.getCompany().getBalance());
				System.out.println("************************************************************");
			}
			case 0 -> {
				if (userRepository.getLoggedUser().isAdmin()) userRepository.getLoggedUser().setCompany(null);

				userRepository.logout();
				execute(users, companies, products, shoppingCart, sales);
				return;
			}
		}

		executeForCompany(scanner, user, users, companies, products, shoppingCart, sales);
	}

	public static void executeForUser(Scanner scanner, User user,
									  List<User> users, List<Company> companies,
									  List<Product> products, List<Product> shoppingCart, List<Sale> sales) {
		System.out.println("1 - Relizar Compras");
		System.out.println("2 - Ver Compras");
		System.out.println("0 - Deslogar");

		switch (scanner.nextInt()) {
			case 1 -> {
				System.out.println("Para realizar uma compra, escolha a empresa onde deseja comprar: ");

				// listando empresas
				if (companies.isEmpty()) {
					System.out.println("Não existem empresas no nosso banco de dados.");

					execute(users, companies, products, shoppingCart, sales);
					return;
				} else companies.forEach(company -> System.out.println(company.getId() + " - " + company.getName()));

				Integer companyId = scanner.nextInt();
				int productId;

				// escolhendo produtos
				do {
					System.out.println("Escolha os seus produtos: ");
					System.out.println("Para finalizar a compra digite 0");

					// listando produtos
					List<Product> companyProducts = products.stream().filter(product -> product.getCompany().getId().equals(companyId)).toList();
					if (companyProducts.isEmpty()) System.out.println("Essa empresa não possui nenhum produto.");
					else {
						for (Product product : companyProducts) {
							System.out.println(product.getId() + " - " + product.getName());
						}
					}

					// adicionando produto escolhido para o carrinho
					productId = scanner.nextInt();
					int finalProductId = productId;

					products.stream().filter(selectedProduct -> selectedProduct.getId().equals(finalProductId))
							.findFirst()
							.ifPresent(shoppingCart::add);
				} while (productId != 0);

				System.out.println("************************************************************");
				System.out.println("Resumo da compra: ");

				// listando produtos
				List<Product> shoppingProducts = shoppingCart.stream().filter(product -> product.getCompany().getId().equals(companyId)).toList();
				if (shoppingProducts.isEmpty()) System.out.println("Você não comprou nada");
				else {
					for (Product product : shoppingProducts) {
						System.out.println(product.getId() + " - " + product.getName() + "    R$" + product.getPrice());
					}
				}

				Optional<Company> foundCompany = companies.stream().filter(selectedCompany -> selectedCompany.getId().equals(companyId)).findFirst();
				if (foundCompany.isPresent()) {
					Sale sale = new SaleManager().createSale(foundCompany.get(), user.getCustomer(), sales, shoppingCart);
					System.out.println("Total: R$" + sale.getValue());
					System.out.println("************************************************************");
					shoppingCart.clear();
					sales.add(sale);
				} else {
					System.out.println("Empresa não encontrada, tente novamente");
				}
			}
			case 2 -> {
				System.out.println();
				System.out.println("************************************************************");
				System.out.println("COMPRAS EFETUADAS");

				// listando vendas
				List<Sale> userSales = sales.stream().filter(sale -> sale.getCustomer().getUsername().equals(user.getUsername())).toList();
				if (userSales.isEmpty()) System.out.println("Nenhuma compra foi efetuada até o momento.");
				else {
					for (Sale sale : userSales) {
						System.out.println("************************************************************");
						System.out.println("Compra de código: " + sale.getCode() + " na empresa " + sale.getCompany().getName() + ": ");

						// listando produtos
						sale.getItems().forEach(product -> System.out.println(product.getId() + " - " + product.getName() + "    R$" + product.getPrice()));

						System.out.println("Total: R$" + sale.getValue());
						System.out.println("************************************************************");
					}
				}
			}
			case 0 -> userRepository.logout();
		}

		execute(users, companies, products, shoppingCart, sales);
	}

}
