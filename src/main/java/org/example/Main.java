package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.example.domain.Decoration;
import org.example.domain.Flower;
import org.example.domain.Product;
import org.example.domain.ProductforSale;
import org.example.domain.Ticket;
import org.example.domain.Tree;
import org.example.exception.GetMethodException;
import org.example.exception.SumMethodException;
import org.example.service.Serv;
import org.example.service.Service;

public class Main {

	// region variables
	private static Serv service;
	private static Scanner inputKey;
	private static String shopName;
	private static List<Product> products = new ArrayList<>();


	public static void main(String[] args) {

		wellcome();
	}

	public static void wellcome() {

		service = new Service();
		shopName = service.init();
		if (shopName == null) {
			shopName = captureString("Insert Flower Shop name");
			service.createFlowerShop(shopName);
		}

		System.out.println("\n" + "Bienvenido al sistema de gestion de Floristeria " + shopName + "\n");
		mainMenu();
	}

	public static void mainMenu() {

		System.out.println("\n" + "** Main menu **" + "\n" + "\n" +
			"1. Product"+ "\n" +
			"2. Invoice"+ "\n" +
			"3. Exit"+ "\n");

		int choice = captureNumber("Select task: " + "\n");

		if (choice > 3) {
			System.out.println("Option not supported");
			mainMenu();
		} else if (choice == 1) {
			productMenu();
		} else if (choice == 2) {
			invoiceMenu();
		} else if (choice == 3) {
			System.exit(0);
		}

		/*
		 * >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Stock menues  <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
		 */

	}

	public static void productMenu() {
		System.out.println("\n" + "** Product menu **" + "\n" + "\n" +
			"1. Create product" + "\n" +
			"2. List products" + "\n" +
			"3. Valuate stock" + "\n" +
			"4. Back");

		int choice = captureNumber("Select task: " + "\n");

		switch (choice) {

		case 1:
			productCreate();
			break;
		case 2:
			productList();
			break;
		case 3:
			productSum();
			break;
		case 4:
			wellcome();
			break;
		}
	}

	private static void productSum() {

		double totalStock;
		try {
			totalStock = service.sumStock();
			System.out.println("\n" + "** Stock ammount menu **" + "\n");
			System.out.println("The stock ammount is: " + totalStock + "\n");
		} catch (SumMethodException e) {
			System.err.println("\n" + "Error while adding stock" + "\n");
		}

		mainMenu();
	}

	private static void productList() {

		System.out.println("\n" + "** Stock list **" + "\n");
		try {
			products = service.getAllProducts();
		
		products.forEach(System.out::println);
		System.out.println("\n");
		} catch (GetMethodException e) {
			System.err.println("\n" + "Error creating stock list" + "\n");
		}
		
		mainMenu();
	}

	private static void productCreate() {

		System.out.println("\n" + "** Stock creation menu **" + "\n");
		System.out.println("1. Decoration");
		System.out.println("2. Flower");
		System.out.println("3. Tree");
		System.out.println("4. Back");

		int choice = captureNumber("Select task: ");

		switch (choice) {

		case 1:
			decorationCreate();
			break;
		case 2:
			flowerCreate();
			break;
		case 3:
			treeCreate();
			break;
		case 4:
			productMenu();
			break;
		}
	}

	private static void decorationCreate() {

		System.out.println("\n" + "Create decoration product " + "\n");
		String name = captureString("Name: ");
		String material = captureString("Material: ");
		double price = captureDouble("price: ");
		int quantity = captureNumber("quantity: ");
		Product decoration = new Decoration(name, price, quantity, material);

		boolean productExist = service.createProduct(decoration);

		if (productExist) {
			System.out.println("\n" + "The product has been created" + "\n");
		} else if (!productExist) {
			System.out.println("\n" + "The product already exists" + "\n");
		} else {
			System.out.println("\n" + "Hubo un problema al crear el producto. Por favor verifique el stock." + "\n");
		}
		mainMenu();
	}

	private static void flowerCreate() {

		System.out.println("\n" + "Create product flower" + "\n");
		String name = captureString("Name: ");
		String color = captureString("Color: ");
		double price = captureDouble("price: ");
		int quantity = captureNumber("quantity: ");

		Product flower = new Flower(name, price, quantity, color);
		boolean productExist = service.createProduct(flower);

		if (productExist) {
			System.out.println("\n" + "The product has been created" + "\n");
		} else if (!productExist) {
			System.out.println("\n" + "The product already exists" + "\n");
		} else {
			System.out.println("\n" + "Hubo un problema al crear el producto. Por favor verifique el stock." + "\n");

		}
		mainMenu();

	}

	private static void treeCreate() {

		System.out.println("\n" + "Create product tree " + "\n");
		String name = captureString("name: ");
		double high = captureDouble("heigth: ");
		double price = captureDouble("price: ");
		int quantity = captureNumber("quantity: ");

		Product tree = new Tree(name, price, quantity, high);
		boolean productExist = service.createProduct(tree);

		if (productExist) {
			System.out.println("\n" + "The product has been created" + "\n");
		} else if (!productExist) {
			System.out.println("\n" + "The product already exists" + "\n");
		} else {
			System.out.println("\n" + "Hubo un problema al crear el producto. Por favor verifique el stock." + "\n");

		}
		mainMenu();

	}

	/*
	 * >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Invoice menues  <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
	 */

	public static void invoiceMenu() {

		System.out.println("\n" + "** Invoice menu **" + "\n" + "\n" +
				"1. Create invoice"+ "\n" +
				"2. Total ammount of sales" + "\n" +
				"3. Back");

		int choice = captureNumber("Select task: " + "\n");

		switch (choice) {

		case 1:
			invoiceCreate();
			break;
		case 2:
			invoiceSum();
			break;
		case 3:
			wellcome();
			break;
		}
	}

	private static void invoiceCreate() {
		Integer selection = 0;
		int order = 0;
		double totalAmount = 0;

		try {
			products = service.getAllProducts();
		} catch (GetMethodException e) {
			System.err.println("\n" + "Unable to show product list" + "\n");
		} 

		Map<Integer, Product> productsToShow = new HashMap<>();
		List<ProductforSale> ticketDetail = new ArrayList<>();
		System.out.println("\n" + "** Create ticket **" + "\n");
		System.out.println("Products to include in ticket: " + "\n");

		// No agrega a la lista los prods sin stock
		for (Product product : products) {
			if(product.getQuantity() > 0) {
				order++;
				productsToShow.put(order, product);
				System.out.println("id: " + order + " name: " + product.getName() + " price: " + product.getPrice() + " stock: " + product.getQuantity());
			 }
			}

		do {
			selection = captureNumber("\n" + "Select product from list or 0 to finish: " + "\n");

			if (selection <= products.size() && selection > 0) {
				int productQuantity = captureNumber("Quantity: " + "\n");
				Product productSelected = productsToShow.get(selection);
				ProductforSale productForSale = new ProductforSale(productSelected, productQuantity);
				boolean enoughStock = service.checkStock(productForSale, productForSale.getProduct().getName());
				if(selection <= products.size() && selection > 0){
					if(enoughStock == false){
						do{
							System.out.println("Not enough stock. Please try again.");
							productQuantity = captureNumber("Quantity: " + "\n");
							productForSale.setQuantity(productQuantity);
							enoughStock = service.checkStock(productForSale, productForSale.getProduct().getName());
						}while (enoughStock == false);
					}
				}

				// controlar si el prod esta repetido
				
				boolean noRepeat = service.checkExistOnTicket(ticketDetail, productForSale.getProduct().getName());
					if(noRepeat == false) {
						ticketDetail.add(productForSale);
						totalAmount = totalAmount + productSelected.getPrice() * productForSale.getQuantity();					
					} else {
						System.out.println("The product already exists in the ticket.");
					}
					
					
				
				//
			} else if (selection > 0){

				System.out.println("Product not included in the list, try again");
			}
		} while (selection != 0);

		if (!ticketDetail.isEmpty()) {

			System.out.println("\n" + "** Ticket detail **" + "\n");

			for (ProductforSale productforSale : ticketDetail) {
				System.out.println("Product: " + productforSale.getProduct().getName() + " price: " + productforSale.getProduct().getPrice()  + " quantity: " + productforSale.getQuantity());
			}
			System.out.println("\n" + "Total amount: " + totalAmount + "\n");

			Ticket ticket = new Ticket(ticketDetail);
			boolean ticketOk = service.createTicket(ticket);
			if(!ticketOk) {
				System.err.println("Unable to create ticket");				
			}else {
				System.out.println("Ticket generated succefully");
			}
		}
			mainMenu();
		

	}

	private static void invoiceSum() {

		try {
		System.out.println("\n" + "** Total sales amount:  **" + "\n");
		System.out.println(service.sumAllTickets());

		System.out.println("\n");

		} catch (SumMethodException e) {
			System.err.println("\n" + "There has been an error getting total sales, please try again" + "\n");
		}
		
		mainMenu();

	}

	// Metodos auxiliares para el ingreso de datos numericos y alfanumericos por keyboard

	public static String captureString(String mensaje) {
		inputKey = new Scanner(System.in);
		System.out.println(mensaje);
		return inputKey.nextLine();
	}

	public static int captureNumber(String mensaje) {
		inputKey = new Scanner(System.in);
		Integer datoNumerico;

		do {
			System.out.println(mensaje);
			try {
				datoNumerico = Integer.parseInt(inputKey.nextLine());
			} catch (Exception e) {
				System.out.println("El valor ingresado no es un numero. Intente nuevamente ");
				datoNumerico = -1;
			}

		} while (datoNumerico == -1);
		return datoNumerico;
	}

	public static double captureDouble(String mensaje) {
		Double datoDouble;

		do {
			System.out.println(mensaje);
			try {
				datoDouble = Double.parseDouble(inputKey.nextLine());
			} catch (Exception e) {
				System.out.println("El valor ingresado no es un numero. Intente nuevamente ");
				datoDouble = -1.0;
			}

		} while (datoDouble == -1.0);
		return datoDouble;
	}
}