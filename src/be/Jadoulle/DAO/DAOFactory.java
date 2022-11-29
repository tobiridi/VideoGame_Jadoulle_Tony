package be.Jadoulle.DAO;

import java.sql.Connection;

import be.Jadoulle.POJO.Booking;
import be.Jadoulle.POJO.Copy;
import be.Jadoulle.POJO.Loan;
import be.Jadoulle.POJO.Player;
import be.Jadoulle.POJO.User;
import be.Jadoulle.POJO.VideoGame;

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

	@Override
	public DAO<VideoGame> getVideoGameDao() {
		return new VideoGameDAO(connection);
	}

	@Override
	public DAO<Copy> getCopyDao() {
		return new CopyDAO(connection);
	}

	@Override
	public DAO<Loan> getLoanDao() {
		return new LoanDAO(connection);
	}

	@Override
	public DAO<Booking> getBookingDao() {
		return new BookingDAO(connection);
	}
	
}
