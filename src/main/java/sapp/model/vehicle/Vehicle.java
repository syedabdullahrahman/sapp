package sapp.model.vehicle;

public class Vehicle {
	private String vehicleNo;
	private String color;
	private int wheel;
	private int seat;
	
	public Vehicle() {

	}

	public Vehicle(String vehicleNo, String color, int wheel, int seat) {
		super();
		this.vehicleNo = vehicleNo;
		this.color = color;
		this.wheel = wheel;
		this.seat = seat;
	}

	/**
	 * @return the vehicleNo
	 */
	public String getVehicleNo() {
		return vehicleNo;
	}

	/**
	 * @param vehicleNo the vehicleNo to set
	 */
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	/**
	 * @return the color
	 */
	public String getColor() {
		return color;
	}

	/**
	 * @param color the color to set
	 */
	public void setColor(String color) {
		this.color = color;
	}

	/**
	 * @return the wheel
	 */
	public int getWheel() {
		return wheel;
	}

	/**
	 * @param wheel the wheel to set
	 */
	public void setWheel(int wheel) {
		this.wheel = wheel;
	}

	/**
	 * @return the seat
	 */
	public int getSeat() {
		return seat;
	}

	/**
	 * @param seat the seat to set
	 */
	public void setSeat(int seat) {
		this.seat = seat;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Vehicle [vehicleNo=" + vehicleNo + ", color=" + color + ", wheel=" + wheel + ", seat=" + seat + "]";
	}
	
	
	
}
