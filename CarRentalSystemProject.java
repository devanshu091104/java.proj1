import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

class Car {
	private String carId;
	private String brand;
	private String model;
	private double basePricePerDay;
	private boolean isAvailable;

	public Car(String carId, String brand, String model, double basePricePerDay) {
		this.carId = carId;
		this.brand = brand;
		this.model = model;
		this.basePricePerDay = basePricePerDay;
		this.isAvailable = true;
	}

	public String getCarId() {
		return carId;
	}

	public String getBrand() {
		return brand;
	}

	public String getModel() {
		return model;
	}

	public double calculatePrice(int rentalDays) {
		return basePricePerDay * rentalDays;
	}

	public boolean isAvailable() {
		return isAvailable;
	}

	public void rent() {
		isAvailable = false;
	}

	public void returnCar() {
		isAvailable = true;
	}

	@Override
	public String toString() {
		return "\tCar(" + this.carId + ") : " 
				+ this.brand + " " + this.model + " " 
				+ this.basePricePerDay + " " 
				+ (this.isAvailable?" Available":" Not Available");
	}
}

class Customer {
	private String customerId;
	private String name;

	public Customer(String customerId, String name) {
		this.customerId = customerId;
		this.name = name;
	}

	public String getCustomerId() {
		return customerId;
	}

	public String getName() {
		return name;
	}
}

class Rental {
	private Car car;
	private Customer customer;
	private int days;

	public Rental(Car car, Customer customer, int days) {
		this.car = car;
		this.customer = customer;
		this.days = days;
	}

	public Car getCar() {
		return car;
	}

	public Customer getCustomer() {
		return customer;
	}

	public int getDays() {
		return days;
	}
}

class CarRentalSystem {
	private List<Car> cars;
	private List<Customer> customers;
	private List<Rental> rentals;

	public CarRentalSystem() {
		cars = new ArrayList<>();
		customers = new ArrayList<>();
		rentals = new ArrayList<>();
	}

	public void addCar(Car car) {
		cars.add(car);
	}

	public void addCar(List<Car> cars) {
		for(Car car : cars){
			this.addCar(car);
		}
	}

	public void addCustomer(Customer customer) {
		customers.add(customer);
	}

	public void rentCar(Car car, Customer customer, int days) {
		if (car.isAvailable()) {
			car.rent();
			rentals.add(new Rental(car, customer, days));

		} else {
			System.out.println("Car is not available for rent.");
		}
	}

	public void returnCar(Car car) {
		car.returnCar();
		Rental rentalToRemove = null;
		for (Rental rental : rentals) {
			if (rental.getCar() == car) {
				rentalToRemove = rental;
				break;
			}
		}
		if (rentalToRemove != null) {
			rentals.remove(rentalToRemove);

		} else {
			System.out.println("Car was not rented.");
		}
	}

	public void addInitialCars() {
		List<Car> carsToAdd = Arrays.asList(
			new Car("C001", "Toyota", "Camry", 60.0),
			new Car("C002", "Honda", "Accord", 70.0),
			new Car("C003", "Mahindra", "Thar", 150.0),
			new Car("C004", "Mahindra", "Bolero", 70.0),
			new Car("C005", "Maruti", "Swift", 50.0),
			new Car("C006", "Tata", "Nexon", 60.0),
			new Car("C007", "Nexa", "Baleno", 60.0),
			new Car("C008", "Mahindra", "Scorpio", 60.0),
			new Car("C009", "Hyundai", "Creta", 60.0),
			new Car("C010", "Honda", "City", 60.0)
		);
		this.addCar(carsToAdd);
		
		System.out.println("Cars added successfully.");

	}

	public void addNewCar() {
		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter car ID: ");
		String Id = scanner.nextLine();

		boolean carIdExists = false;
		for (Car car : cars) {
			if (car.getCarId().equals(Id)) {
				carIdExists = true;
				break;
			}
		}

		if (carIdExists) {
			System.out.println("This car ID is already available.");
		} else {
			System.out.print("Enter brand: ");
			String brand = scanner.nextLine();

			System.out.print("Enter model: ");
			String model = scanner.nextLine();

			System.out.print("Enter base price per day: ");
			double basePricePerDay = scanner.nextDouble();
			scanner.nextLine();

			Car car = new Car(Id, brand, model, basePricePerDay);
			addCar(car);
			System.out.println("Car added successfully: " + car);
		}

	}

	public void showcars() {
		for(Car car : this.cars){
			System.out.println(car);
		}
	}

	public void menu() {
		Scanner scanner = new Scanner(System.in);
		while (true) {
			System.out.println("===== Welcome to Car Rental System =====");
			System.out.println("1. Rent a Car");
			System.out.println("2. Return a Car");
			System.out.println("3. Add New Car");
			System.out.println("4. Show Cars");
			System.out.println("5. Exit");

			System.out.print("Enter your choice: ");

			int choice = scanner.nextInt();
			scanner.nextLine();

			if (choice == 1) {
				System.out.println("\n== Rent a Car ==\n");
				System.out.print("Enter your name: ");
				String customerName = scanner.nextLine();

				System.out.println("\nAvailable Cars:");
				showcars();

				System.out.print("\nEnter the car ID you want to rent: ");
				String carId = scanner.nextLine();

				System.out.print("Enter the number of days for rental: ");
				int rentalDays = scanner.nextInt();
				scanner.nextLine();

				Customer newCustomer = new Customer("CUS" + (customers.size() + 1), customerName);
				addCustomer(newCustomer);

				Car selectedCar = null;
				for (Car car : cars) {
					if (car.getCarId().equals(carId) && car.isAvailable()) {
						selectedCar = car;
						break;
					}
				}

				if (selectedCar != null) {
					double totalPrice = selectedCar.calculatePrice(rentalDays);
					System.out.println("\n== Rental Information ==\n");
					System.out.println("Customer ID: " + newCustomer.getCustomerId());
					System.out.println("Customer Name: " + newCustomer.getName());
					System.out.println("Car: " + selectedCar.getBrand() + " " + selectedCar.getModel());
					System.out.println("Rental Days: " + rentalDays);
					System.out.printf("Total Price: $%.2f%n", totalPrice);

					System.out.print("\nConfirm rental (Y/N): ");
					String confirm = scanner.nextLine();

					if (confirm.equalsIgnoreCase("Y")) {
						rentCar(selectedCar, newCustomer, rentalDays);
						System.out.println("\nCar rented successfully.");
					} else {
						System.out.println("\nRental canceled.");
					}
				} else {
					System.out.println("\nInvalid car selection or car not available for rent.");
				}
			} else if (choice == 2) {
				System.out.println("\n== Return a Car ==\n");
				System.out.print("Enter the car ID you want to return: ");
				String carId = scanner.nextLine();

				Car carToReturn = null;
				for (Car car : cars) {
					if (car.getCarId().equals(carId) && !car.isAvailable()) {
						carToReturn = car;
						break;
					}
				}

				if (carToReturn != null) {
					Customer customer = null;
					for (Rental rental : rentals) {
						if (rental.getCar() == carToReturn) {
							customer = rental.getCustomer();
							break;
						}
					}

					if (customer != null) {
						returnCar(carToReturn);
						System.out.println("Car returned successfully by " + customer.getName());
					} else {
						System.out.println("Car was not rented or rental information is missing.");
					}
				} else {
					System.out.println("Invalid car ID or car is not rented.");
				}
			} else if (choice == 3) {
				System.out.println("Adding New car....");
				addNewCar();
			} else if (choice == 4) {
				System.out.println("The following cars are available");
				showcars();
			} else if (choice == 5) {
				break;
			} else {
				System.out.println("Invalid choice. Please enter a valid option.");
			}
			System.out.println("Press Enter to Continue...");
			scanner.nextLine();
		}
		System.out.println("\nThank you for using the Car Rental System!");
		scanner.close();
	}
}

public class CarRentalSystemProject {
	public static void main(String[] args) {
		CarRentalSystem rentalSystem = new CarRentalSystem();
		rentalSystem.addInitialCars();
		rentalSystem.menu();
	}
}
