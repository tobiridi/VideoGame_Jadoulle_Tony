package be.Jadoulle.DAO;

import be.Jadoulle.POJO.Booking;
import be.Jadoulle.POJO.Copy;
import be.Jadoulle.POJO.Loan;
import be.Jadoulle.POJO.Player;
import be.Jadoulle.POJO.User;
import be.Jadoulle.POJO.VideoGame;

public abstract class AbstractDAOFactory {
	public static final int DAO_FACTORY = 0;

	public static AbstractDAOFactory getFactory(int type) {
		switch (type) {
		case DAO_FACTORY: return new DAOFactory();
		default: return null;
		}
	}

	//abstract methods
	public abstract DAO<User> getUserDao();
	public abstract DAO<Player> getPlayerDao();
	public abstract DAO<VideoGame> getVideoGameDao();
	public abstract DAO<Copy> getCopyDao();
	public abstract DAO<Loan> getLoanDao();
	public abstract DAO<Booking> getBookingDao();
}
