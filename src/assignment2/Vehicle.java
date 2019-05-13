package assignment2;

public class Vehicle {

	private String regNo, make, description, bookingID;
	private int year, numPassengers;
	private DateTime bookingDate;
	private final int BOOKING_FEE = 100;
	private final int PASSENGER_SURCHARGE = 20;
	private double fee;

	// Constructor
	public Vehicle(String regNo, String make, int year, String description) {
		this.regNo = regNo;
		this.make = make;
		this.year = year;
		this.description = description;
		bookingID = "N/A";
	}

	// Accessor for registration
	public String getRegNo() {
		return regNo;
	}

	// Record the passenger number
	private boolean setNumPassengers(int numPassengers) {
		// Passenger count must be between 1 and 6
		if (numPassengers < 1 || numPassengers > 6) {
			return false;
		} else {
			this.numPassengers = numPassengers;
			return true;
		}
	}

	// Book in vehicles
	public double book(int numPassengers, DateTime bookingDate) throws VehicleException {
		DateTime currentTime = new DateTime();
		// Throws an error if passenger count is not between 1 and 6
		if (this.setNumPassengers(numPassengers) == false) {
			throw new VehicleException("Error - Passenger input must be between 1 and 6.");
		// Throws an error if booking date is in the past
		} else if (DateTime.diffDays(bookingDate, currentTime) < 0) {
			throw new VehicleException("Error - Booking date cannot be set in the past.");
		} else {
			this.bookingDate = bookingDate;
			bookingID = regNo + bookingDate.getEightDigitDate();
			fee = BOOKING_FEE + (PASSENGER_SURCHARGE * numPassengers);
			return fee;
		}
	}

	// Returns the vehicle details
	public String getDetails() {
		String vehicleDetails = "";
		vehicleDetails += String.format("%-20s %s\n", "Reg Num:", regNo);
		vehicleDetails += String.format("%-20s %s\n", "Make:", make);
		vehicleDetails += String.format("%-20s %s\n", "Year:", year);
		vehicleDetails += String.format("%-20s %s\n", "Description:", description);
		vehicleDetails += String.format("%-20s %s\n", "Booking Ref:", bookingID);
		if (!bookingID.equals("N/A")) {
			vehicleDetails += String.format("%-20s %s\n", "Booking Date:", bookingDate.getFormattedDate());
			vehicleDetails += String.format("%-20s %s\n", "Num Passengers:", numPassengers);
			vehicleDetails += String.format("%-20s %s%.2f\n", "Fee: ", "$", fee);
		}
		return vehicleDetails;
	}

	// Converts vehicle details to txt format. BookingID and fee not included. 0 is assigned to height and 
	// weight as they do not exist for standard vehicles. # is used as the delimiter
	public String getDetailsToTXT() {
		String vehicleDetails = "";
		vehicleDetails += regNo + "#";
		vehicleDetails += make + "#";
		vehicleDetails += year + "#";
		vehicleDetails += description + "#";
		vehicleDetails += bookingDate + "#";
		vehicleDetails += numPassengers + "#";
		vehicleDetails += "0#";
		vehicleDetails += "0#";

		return vehicleDetails;
	}

	// Allows the Oversized_Vehicles to call this method to correctly display
	// the fee
	public void setFee(double fee) {
		this.fee = fee;
	}

}