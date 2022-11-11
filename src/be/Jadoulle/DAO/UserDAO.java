package be.Jadoulle.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import be.Jadoulle.POJO.Administrator;
import be.Jadoulle.POJO.Player;
import be.Jadoulle.POJO.User;

public class UserDAO extends DAO<User> {

	public UserDAO(Connection conn) {
		super(conn);
	}

	@Override
	public User find(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean create(User obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(User obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(User obj) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public static User authenticate(String username, String password) {
		User authenticateUser = null;
		Connection connection = DatabaseConnection.getInstance();
		String query = "SELECT * FROM User WHERE username = ? AND password = ?";
		
		try {
			PreparedStatement stmt = connection.prepareStatement(query);
			stmt.setString(1, username);
			stmt.setString(2, password);
			ResultSet res = stmt.executeQuery();
			
			if(res.next()) {
				int id = res.getInt("id");
				if(res.getBoolean("isAdmin")) {
					authenticateUser = new Administrator(id, username, password);
				}
				else {
					//TODO : maybe call another DAO to get all data from user (booking, loan, copy, ...)
					int credits = res.getInt("credits");
					String pseudo = res.getString("pseudo");
					LocalDate registration = res.getDate("registration").toLocalDate();
					LocalDate dateOfBirth = res.getDate("dateOfBirth").toLocalDate();
					authenticateUser = new Player(id, username, password, credits, pseudo, registration, dateOfBirth);
				}
			}
			res.close();
			stmt.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return authenticateUser;
	}

	@Override
	public ArrayList<User> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
}
