package assignment2;

public class Oversized_Vehicles extends Vehicle {

	private double weight;
	private String category;

	private final double CLEARENCE_HEIGHT;
	private final double LIGHT_VEHICLE_CHARGE = 10.0;
	private final double MEDIUM_VEHICLE_CHARGE = 20.0;
	private final double HEAVY_VEHICLE_CHARGE = 50.0;

	// Constructor
	public Oversized_Vehicles(String regNo, String make, int year, String description, double CLEARENCE_HEIGHT) {
		super(regNo, make, year, description);
		this.CLEARENCE_HEIGHT = CLEARENCE_HEIGHT;
		this.category = "N/A";
	}

	// Books in Oversized_Vehicles
	public double book(int numPassengers, DateTime bookingDate, double weight) throws VehicleException {
		this.weight = weight;
		// Throws an error if the vehicle height is greater than 3
		if (this.CLEARENCE_HEIGHT > 3) {
			throw new VehicleException("Ferry cannot book a vehicle with its height exceeding 3 metres.");
		}

		// Calculates initial cost using vehicle book method
		double initialCost = super.book(numPassengers, bookingDate);
		double additionalWeightCost = 0;

		// If weight is less than 3, no additional cost will be charged
		if (weight <= 3) {
			return initialCost;
		}

		recordWeight();
		additionalWeightCost = weight - 3;

		// Calculates additional cost based on category
		switch (category) {
		case "LIGHT":
			additionalWeightCost *= LIGHT_VEHICLE_CHARGE;
			break;
		case "MEDIUM":
			additionalWeightCost *= MEDIUM_VEHICLE_CHARGE;
			break;
		case "HEAVY":
			additionalWeightCost *= HEAVY_VEHICLE_CHARGE;
			break;
		}
		
		double totalCost = initialCost + additionalWeightCost;
		// Set oversized vehicle fee so can correctly display fee
		super.setFee(totalCost);
		return totalCost;
	}

	// Checks the weight and assigns a category depending on the value
	private void recordWeight() {
		if (weight > 3 && weight <= 4.5) {
			category = "LIGHT";
		} else if (weight > 4.5 && weight <= 8) {
			category = "MEDIUM";
		} else if (weight > 8) {
			category = "HEAVY";
		}
	}

	@Override
	// Overrides getDetails from Vehicle class to include more variables
	public String getDetails() {
		String vehicleDetails = "";
		vehicleDetails += super.getDetails();
		vehicleDetails += "\n";
		if (weight != 0) {
			vehicleDetails += String.format("%-20s %s\n", "Category:", category);
			vehicleDetails += String.format("%-20s %.2f\n", "Weight:", weight);
		}
		vehicleDetails += String.format("%-20s %.2f\n", "Height:", CLEARENCE_HEIGHT);
		return vehicleDetails;
	}

	@Override
	// Overrides getDetailsToTXT from Vehicle class to include more variables
	public String getDetailsToTXT() {
		String vehicleDetails = super.getDetailsToTXT();
		// Makes vehicle details equal from registration to num passengers. (Remove the two added 0s and delimiters)
		vehicleDetails = vehicleDetails.substring(0, vehicleDetails.length() - 3);
		vehicleDetails += CLEARENCE_HEIGHT + "#";
		vehicleDetails += weight + "#";
		return vehicleDetails;
	}

}