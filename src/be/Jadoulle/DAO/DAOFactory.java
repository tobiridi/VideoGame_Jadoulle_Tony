package be.Jadoulle.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DAOFactory extends AbstractDAOFactory {

	public static final Connection connection = DatabaseConnection.getInstance();
	
	//override methods
	
}
