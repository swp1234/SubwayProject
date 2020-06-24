package com.example.project1;

public class Account {
	private String ID;
	private String password;
	private int point;
	private String[] favorites = new String[2] ;
	
	public Account(String ID, String pw) {
		this.ID = ID;
		this.password = pw;
		this.point = 200;
	}
	
	public Account(String ID, String pw, String[] favorites ) {
		this(ID, pw);
		this.favorites = favorites;
	}
	
	public Account(String ID, String pw, String favorite1, String favorite2 ) {
		this(ID, pw);
		favorites[0] = favorite1;
		favorites[1] = favorite2;
	}

	public String getID() {
		return ID;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}

	public String[] getFavorites() {
		return favorites;
	}

	public void setFavorites(String[] favorites) {
		this.favorites = favorites;
	}
	
	
}
