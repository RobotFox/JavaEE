package esempio;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.dbutils.DbUtils;

public class ConnessionePrepared {

	public static void main(String[] args) throws SQLException {

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try (Connection conn = DriverManager.getConnection("jdbc:mysql://192.168.1.170/sakila", "root", "root");)

		{
			pstmt = conn.prepareStatement("SELECT * FROM film WHERE title LIKE ? and length >= ?");
			pstmt.setString(1, "%LOVE%");
			pstmt.setInt(2, 90);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("film_id");
				String title = rs.getString("title");
				int lenght = rs.getInt("length");
				System.out.println(id + " " + title + " " + lenght);
			}

		} catch (SQLException e) {
			DbUtils.printStackTrace(e);
		} finally {
			DbUtils.close(rs);
			DbUtils.closeQuietly(pstmt);
		}

	}

}
