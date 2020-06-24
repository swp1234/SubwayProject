package src;

public class Seat {
	private int seatNo;
	private int seatLoc;
	private int destination;
	private int roomNumber;
	String registeredId;
	
	public int GetRoomNumber() {
		return roomNumber;
	}

	public void SetRoomNumber(int roomNumber) {
		this.roomNumber = roomNumber;
	}
	public Seat(int seatLoc, int seatNo, int destination,int roomNumber, String registeredID) {
		super();
		this.seatNo = seatNo;
		this.seatLoc = seatLoc;
		this.destination = destination;
		this.roomNumber = roomNumber;
		this.registeredId = registeredID;
	}
	
	public int GetSeatNo() {
		return seatNo;
	}
	public void SetSeatNo(int seatNo) {
		this.seatNo = seatNo;
	}
	public int GetDestination() {
		return destination;
	}
	public void SetDestination(int destination) {
		this.destination = destination;
	}
	
	public int GetSeatLoc() {
		return seatLoc;
	}
	
	public void SetSeatLocation(int seatLoc) {
		this.seatLoc = seatLoc;
	}
	
}
