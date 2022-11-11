package be.Jadoulle.POJO;

import be.Jadoulle.DAO.AbstractDAOFactory;
import be.Jadoulle.DAO.DAO;

public class Administrator extends User {
	private static final long serialVersionUID = 2681927979423436184L;

	public Administrator(int id, String username, String password) {
		super(id, username, password);
	}
	
	//methods
	public boolean createVideoGame(VideoGame game) {
		AbstractDAOFactory adf = AbstractDAOFactory.getFactory(AbstractDAOFactory.DAO_FACTORY);
		DAO<VideoGame> gameDao = adf.getVideoGameDao();
		return gameDao.create(game);
	}
	
}
