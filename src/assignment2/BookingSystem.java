package assignment2;

import java.util.*;

import java.io.*;

public class BookingSystem {
	static Scanner scan = new Scanner(System.in);
	private static String response;
	private static ArrayList<Vehicle> vehicles = new ArrayList<Vehicle>();
	private static String filename = "Vehicles.txt";

	// Display system menu
	public static void systemMenu() {
		System.out.println("--- Vehicle Booking System ---");
		System.out.printf("%-25s %s\n", "Seed Data", "A");
		System.out.printf("%-25s %s\n", "Add Vehicle", "B");
		System.out.printf("%-25s %s\n", "Display Vehicles", "C");
		System.out.printf("%-25s %s\n", "Book Passage", "D");
		System.out.printf("%-25s %s\n", "Exit Program", "X");
		System.out.print("Enter Selection: ");
		response = scan.nextLine();

	}

	// What method to run depending on user response
	public static void menuChoice(String response) {
		switch (response.toUpperCase()) {
		case "A":
			System.out.println("-------------------------------");
			seedData();
			break;
		case "B":
			System.out.println("-------------------------------");
			addVehicle();
			break;
		case "C":
			System.out.println("-------------------------------");
			displayVehicles();
			break;
		case "D":
			System.out.println("-------------------------------");
			bookPassage();
			break;
		case "X":
			System.out.println("-------------------------------");
			exitMenu();
			break;
		default:
			System.out.println("-------------------------------");
			System.out.println("Invalid Response.");
			break;
		}
	}

	// Seeds fixed and hard coded vehicle data
	public static void seedData() {
		DateTime dates[] = new DateTime[5];
		dates[0] = new DateTime(17, 12, 2017);
		dates[1] = new DateTime(2, 12, 2017);
		dates[2] = new DateTime(13, 1, 2018);
		dates[3] = new DateTime(28, 11, 2017);
		dates[4] = new DateTime(30, 2, 2018);

		// Standard Vehicles
		Vehicle standardVehicles[] = new Vehicle[4];
		standardVehicles[0] = new Vehicle("AAA000", "Holden", 2004, "White Sedan"); // Book
		standardVehicles[1] = new Vehicle("BBB111", "Toyota", 2008, "Red Sedan"); // Book
		standardVehicles[2] = new Vehicle("CCC222", "Tesla", 2012, "Blue Sedan"); // Not booked
		standardVehicles[3] = new Vehicle("DDD333", "BMW", 2000, "Black Sedan"); // Not booked

		// Oversized Vehicles
		Oversized_Vehicles oversizedVehicles[] = new Oversized_Vehicles[6];
		oversizedVehicles[0] = new Oversized_Vehicles("EEE444", "Minivan", 2008, "White Van", 2.8); // Book
		oversizedVehicles[1] = new Oversized_Vehicles("FFF555", "Truck", 2010, "Black Truck", 2.2); // Book
		oversizedVehicles[2] = new Oversized_Vehicles("JJJ999", "Cargo Truck", 2014, "White Truck", 2.8); // Book
		oversizedVehicles[3] = new Oversized_Vehicles("HHH777", "Dump Truck", 2005, "Blue Truck", 2.4); // Not booked
		oversizedVehicles[4] = new Oversized_Vehicles("III888", "Fire Truck", 2000, "Red Truck", 2.5); // Not booked
		oversizedVehicles[5] = new Oversized_Vehicles("GGG666", "Van", 2004, "Gray Van", 2); // Not booked

		// Book vehicles
		try {
			standardVehicles[0].book(4, dates[0]);
			standardVehicles[1].book(6, dates[1]);

			oversizedVehicles[0].book(2, dates[2], 4.0);
			oversizedVehicles[1].book(2, dates[3], 6.5);
			oversizedVehicles[2].book(6, dates[4], 8.5);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Add standard vehicles
		for (int i = 0; i < standardVehicles.length; i++) {
			vehicles.add(standardVehicles[i]);
		}
		
		// Add oversized vehicles
		for (int i = 0; i < oversizedVehicles.length; i++) {
			vehicles.add(oversizedVehicles[i]);
		}

		System.out.println("Data Seeded.\n");
	}

	// Add Vehicle to arraylist
	public static void addVehicle() {

		System.out.printf("%-40s", "Enter Vehicle Registration:");
		String registration = scan.nextLine().toUpperCase();

		// Check if registration exists
		if (checkRegistration(registration)) {
			System.out.println("Error - Registration " + registration + " already exists in the system.\n");
			return;
		}

		System.out.printf("%-40s", "Enter Vehicle Make:");
		String make = scan.nextLine();
		System.out.printf("%-40s", "Enter Vehicle Year:");
		int year = scan.nextInt();
		scan.nextLine();
		System.out.printf("%-40s", "Enter Vehicle Description:");
		String description = scan.nextLine();
		System.out.printf("%-40s", "Enter Vehicle Height:");
		double height = scan.nextDouble();
		scan.nextLine();
		// If height is less than or equal to 1, adds to standard vehicles. Otherwise, adds to oversized vehicles
		if (height <= 1) { 
			System.out.println("New standard vehicle added successfully for registration " + registration + "\n");
			vehicles.add(new Vehicle(registration, make, year, description));
		} else { 
			System.out.println("New oversized vehicle added successfully for registration " + registration + "\n");
			vehicles.add(new Oversized_Vehicles(registration, make, year, description, height));
		}
	}

	// Displays all vehicles
	public static void displayVehicles() {
		for (int i = 0; i < vehicles.size(); i++) {
			System.out.println(vehicles.get(i).getDetails());
		}
	}

	// Books Passage
	public static void bookPassage() {
		Vehicle vehicle;
		double cost = 0;

		System.out.printf("%-40s", "Enter Registration Number:");
		String registration = scan.nextLine().toUpperCase();

		// Check if registration exists. If doesn't, returns to menu
		if (!checkRegistration(registration)) {
			System.out.println("Error - Registration Number not found.\n");
			return;
		} else {
			vehicle = getVehicle(registration);
		}

		System.out.printf("%-40s", "Enter Day:");
		int day = scan.nextInt();
		System.out.printf("%-40s", "Enter Month:");
		int month = scan.nextInt();
		System.out.printf("%-40s", "Enter Year:");
		int year = scan.nextInt();
		System.out.printf("%-40s", "Enter Number of Passengers:");
		int passengers = scan.nextInt();
		scan.nextLine();

		DateTime bookDate = new DateTime(day, month, year);

		// If vehicle is oversized, asks for weight and displays cost. If not, displays cost
		try {
			if (vehicle instanceof Oversized_Vehicles) {
				System.out.printf("%-40s", "Enter Weight:");
				double weight = scan.nextDouble();
				scan.nextLine();
				cost = ((Oversized_Vehicles) getVehicle(registration)).book(passengers, bookDate, weight);
			} else {
				cost = getVehicle(registration).book(passengers, bookDate);
			}
		} catch (VehicleException e) {
			System.out.println("Booking for " + registration + " was unsuccessful.");
			System.out.println(e.getMessage() + "\n");
			return;
		}
		System.out.println("Booking for " + registration + " on " + bookDate.getFormattedDate() + " was successful.");
		System.out.println("The total cost of the booking is: $" + cost + "\n");

	}

	// Exits the menu and program. Saves data
	public static void exitMenu() {
		save();
		System.out.println("Exiting...");
		System.exit(0);
	}

	// Checks if the registration number exists
	public static boolean checkRegistration(String registration) {
		for (int i = 0; i < vehicles.size(); i++) {
			if (vehicles.get(i).getRegNo().equals(registration)) {
				return true;
			}
		}
		return false;
	}

	// Returns the vehicle with same registration number
	public static Vehicle getVehicle(String registration) {
		for (int i = 0; i < vehicles.size(); i++) {
			if (vehicles.get(i).getRegNo().equals(registration)) {
				return vehicles.get(i);
			}
		}
		return null;
	}

	// Writes the vehicle data onto a txt file
	public static void save() {
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new File(filename));
			for (int i = 0; i < vehicles.size(); i++) {
				pw.write(vehicles.get(i).getDetailsToTXT());
				pw.println();
			}
			System.out.println("Data has successfully been saved to " + filename);
		} catch (IOException e) {
			System.err.println("Data has failed to save. " + e);
		}
		pw.close();
	}
	
	// Reads the vehicle data from a txt file
	public static void load() {
		Scanner fileScan = null;
		// Searches for file name. If does not exist, creates file. Otherwise, reads from file.
		try {
			fileScan = new Scanner(new File(filename));
			fileScan.useDelimiter("#");
			System.out.println(filename + " has been loaded successfully.");
		} catch (Exception e) {
			System.out.println(filename + " could not be found.");
			System.out.println("Creating new " + filename + " file.");
			return;
		}
		// If file has a next line, continues to read
		while (fileScan.hasNextLine()) {
			String registration = fileScan.next();
			String make = fileScan.next();
			int year = fileScan.nextInt();
			String description = fileScan.next();
			String stringDate = fileScan.next();
			int passengers = fileScan.nextInt();
			double height = fileScan.nextDouble();
			double weight = fileScan.nextDouble();
			fileScan.nextLine();
			
			DateTime date;
			Vehicle savedVehicles;
			
			String stringDateDMY[] = stringDate.split("-");

			// If height is 0, assigns it to standard Vehicle. Otherwise, assigns it to Oversized
			if (height == 0) {
				savedVehicles = new Vehicle(registration, make, year, description);
			} else {
				savedVehicles = new Oversized_Vehicles(registration, make, year, description, height);
			}

			// Books vehicle if there is a date in the file
			if (!stringDate.equals("null")) {
				date = new DateTime(Integer.parseInt(stringDateDMY[2]), 
						Integer.parseInt(stringDateDMY[1]), 
						Integer.parseInt(stringDateDMY[0]));
				
				try {
					if (savedVehicles instanceof Oversized_Vehicles) {
						((Oversized_Vehicles) savedVehicles).book(passengers, date, weight);
					} 
					else {
						savedVehicles.book(passengers, date);
					}
				} catch (VehicleException e) {
					e.printStackTrace();
				}
			}
			vehicles.add(savedVehicles);
		}
		fileScan.close();
	}

	public static void main(String[] args) {
		// Try to load file. If error exists, prints to console.
		try {
			load();
		} catch (Exception e) {
			e.printStackTrace();
		}

		do {
			systemMenu();
			menuChoice(response);
		} while (response != "X");
			
		
	}

}