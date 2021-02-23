package persistence.notificable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Notificable;
import persistence.MySQLCon;

public class NotificableDAO implements NotificableDataService {
	
	private MySQLCon connection = null;

	@Override
	public MySQLCon getDbConnection() {
		if (connection == null) {
			connection = new MySQLCon();
		}
		return connection;
	}

	@Override
	public List<Notificable> getNotificableList() throws SQLException {
		String query = "SELECT t.id, t.title, t.expirationDate, p.id, p.name, u.id, u.phone FROM "
				+ "task t, state s, panel p, user u WHERE t.state_id = s.id AND "
				+ "s.panel_id = p.id AND p.user_id = u.id AND TIMESTAMPDIFF(MINUTE, t.expirationDate, NOW()) <= u.notification;";
		List<Notificable> notificables = new ArrayList<Notificable>();
		try {
			PreparedStatement ps = getDbConnection().getConnection().prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				notificables.add(this.getNotificable(rs));
			}
			
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			getDbConnection().closeConnection();
		}
		return notificables;

	
	}

	private Notificable getNotificable(ResultSet rs) throws SQLException {
		Notificable notificable = new Notificable();
		notificable.setTaskId(rs.getLong(1));
		notificable.setTaskTitle(rs.getString(2));
		notificable.setTaskExpirationDate(rs.getDate(3));
		notificable.setPanelId(rs.getLong(4));
		notificable.setPanelName(rs.getString(5));
		notificable.setUserId(rs.getLong(6));
		notificable.setPhone(rs.getString(7));
		return notificable;
	}
	
}
