package be.Jadoulle.DAO;

import be.Jadoulle.POJO.*;

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
}
