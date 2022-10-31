package be.Jadoulle.DAO;

public abstract class AbstractDAOFactory {
	public static final int DAO_FACTORY = 0;
	
	public static AbstractDAOFactory getFactory(int type) {
		switch (type) {
		case DAO_FACTORY: return new DAOFactory();
		default: return null;
		}
	}
	
	//abstract methods
	
}
