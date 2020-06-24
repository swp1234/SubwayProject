package src;

import java.util.Vector;

public class Room {
	 Vector<Integer> coloredSeats = new Vector<Integer>();
	
	
	public void AddColoredSeat(int color) {
		coloredSeats.add(color);
	}
}
