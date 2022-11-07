package be.Jadoulle.DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import be.Jadoulle.POJO.Player;

public class PlayerDAO extends DAO<Player> {

	public PlayerDAO(Connection conn) {
		super(conn);
	}

	@Override
	public Player find(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean create(Player obj) {
		try {
			String query = "INSERT INTO User (username, password, pseudo, registration, dateOfBirth, credits) VALUES (?,?,?,?,?,?)";
			PreparedStatement stmt = this.connection.prepareStatement(query);
			stmt.setString(1, obj.getUsername());
			stmt.setString(2, obj.getPassword());
			stmt.setString(3, obj.getPseudo());
			stmt.setDate(4, Date.valueOf(obj.getRegistrationDate()));
			stmt.setDate(5, Date.valueOf(obj.getDateOfBirth()));
			stmt.setInt(6, obj.getCredits());
			
			int res = stmt.executeUpdate();
			stmt.close();
			if(res != 0)
				return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public boolean update(Player obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Player obj) {
		// TODO Auto-generated method stub
		return false;
	}

}
