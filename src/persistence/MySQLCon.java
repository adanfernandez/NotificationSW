package persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import conf.Config;

public class MySQLCon {

	private Connection con;
	private Config config = Config.getInstance();

	public Connection getConnection() {

		if (con == null) {
			try {
				Class.forName(config.get("DRIVER"));
				this.con = DriverManager.getConnection(config.get("JDBC") + config.get("HOST") + "/"
						+ config.get("DATABASE") + "?user=" + config.get("USERNAME") + "&password="
						+ config.get("PASSWORD") + "&allowPublicKeyRetrieval=true&useSSL=false");
//				this.con = DriverManager.getConnection(config.get("JDBC") + System.getenv("MYSQL_SW_PORT_3306_TCP_ADDR") //HACER IGUAL CON APP, NECESARIO LINKEAR A MYSQL
//				+ ":" + System.getenv("MYSQL_SW_PORT_3306_TCP_PORT") + "/" + config.get("DATABASE") + "?user="
//				+ config.get("USERNAME") + "&password=" + config.get("PASSWORD")
//				+ "&allowPublicKeyRetrieval=true&useSSL=false");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return con;
	}

	public void closeConnection() {
		try {
			if (con != null) {
				this.con.close();
				this.con = null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
