package src;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class DBManager {
	final int ADD_POINT = 200;
	final int SUBTRACT_POINT = -100;

	private static Connection getConnection() {
		Connection con = null;
		try {
			con = DriverManager.getConnection("jdbc:mariadb://127.0.0.1:3306/subway", "swp1234", "park123");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DBManager a = new DBManager();
		Account account = new Account("swp1252", "asdaf");
		System.out.println(a.getPoint("swp1234"));
	}

	public Vector<String> logIn(Account account) {
		Connection con = getConnection();
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		String sql = "select favorite1, favorite2 from user where id = ? and pw = ?";
		Vector<String> favorites = new Vector<String>();
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, account.getID());
			pstmt.setString(2, account.getPassword());

			rs = pstmt.executeQuery();
			while (rs.next()) {
				favorites.add(rs.getString("favorite1"));
				favorites.add(rs.getString("favorite2"));
			}
			pstmt.close();
			con.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return favorites;
	}

	public boolean createNewAccount(Account account) {
		Connection con = getConnection();
		PreparedStatement pstmt = null;

		String sql = "Insert into user(id,pw,favorite1,favorite2) values(?,?,?,?)";
		boolean result = false;
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, account.getID());
			pstmt.setString(2, account.getPassword());
			pstmt.setString(3, account.getFavorites()[0]);
			pstmt.setString(4, account.getFavorites()[1]);
			pstmt.executeUpdate();

			result = (logIn(account) != null);
			pstmt.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public boolean checkIDDuplicates(String ID) {
		Connection con = getConnection();
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		boolean duplicateResult = false;
		String sql = "select * from user where id = '" + ID + "';";

		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				duplicateResult = true;
			} else {
				duplicateResult = false;
			}
			rs.close();
			pstmt.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return duplicateResult;
	}

	public void updatePoints(String ID, int addingPoint) {
		Connection con = getConnection();
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		String sql = "select point from user where id = '" + ID + "';";
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			String score = "";
			int newScore = 0;
			if (rs.next()) {
				score = rs.getString("point");
				newScore = Integer.parseInt(score) + addingPoint;
			}
			sql = "update user set point = " + newScore + " where id = '" + ID + "';";
			pstmt = con.prepareStatement(sql);
			pstmt.executeQuery();

			rs.close();
			pstmt.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void addPoints(String ID) {
		updatePoints(ID, ADD_POINT);
	}

	public void subtractPoints(String ID) {
		updatePoints(ID, SUBTRACT_POINT);
	}
	
	public int getPoint(String ID) {
		Connection con = getConnection();
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		String sql = "select point from user where id = '" + ID + "';";
		int point = 0;
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				String score = rs.getString("point");
				point = Integer.parseInt(score);
			}
			rs.close();
			pstmt.close();
			con.close();
		} catch (SQLException e) {
			// TODO: handle exception
		}
		return point;
	}

	public boolean checkPoints(String ID) {
		Connection con = getConnection();
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		String sql = "select point from user where id = '" + ID + "';";
		boolean isPointEnough = false;
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				String score = rs.getString("point");
				isPointEnough = (Integer.parseInt(score) >= Math.abs(SUBTRACT_POINT));
			}
			rs.close();
			pstmt.close();
			con.close();
		} catch (SQLException e) {
			// TODO: handle exception
		}
		return isPointEnough;
	}

	public void registerFavorites(String ID, String[] favorites) {
		Connection con = getConnection();
		PreparedStatement pstmt = null;
		String sql = "update user set favorite1 = ?, favorite2 = ? where id = ?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, favorites[0]);
			pstmt.setString(2, favorites[1]);
			pstmt.setString(3, ID);
			pstmt.executeQuery();

			pstmt.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
