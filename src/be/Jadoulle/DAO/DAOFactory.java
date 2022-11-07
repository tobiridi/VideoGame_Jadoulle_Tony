package be.Jadoulle.DAO;

import java.sql.Connection;

import be.Jadoulle.POJO.Player;
import be.Jadoulle.POJO.User;

public class DAOFactory extends AbstractDAOFactory {

	public static final Connection connection = DatabaseConnection.getInstance();

	@Override
	public DAO<User> getUserDao() {
		return new UserDAO(connection);
	}

	@Override
	public DAO<Player> getPlayerDao() {
		return new PlayerDAO(connection);
	}
	
}
