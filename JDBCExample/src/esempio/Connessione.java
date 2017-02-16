package esempio;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Connessione {

	public static void main(String[] args) throws SQLException {

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost/sakila", "root", "root");
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT film_id, title, length FROM film");

			while (rs.next()) {
				int id = rs.getInt("film_id");
				String title = rs.getString("title");
				int length = rs.getInt("length");
				System.out.println(id + " " + title + " " + length);
			}

		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
				if (conn!=null) {
					conn.close();
				}
		}

	}

}
