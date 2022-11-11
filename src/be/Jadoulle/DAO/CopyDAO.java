package be.Jadoulle.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import be.Jadoulle.POJO.Copy;

public class CopyDAO extends DAO<Copy> {

	public CopyDAO(Connection conn) {
		super(conn);
	}

	@Override
	public Copy find(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Copy> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean create(Copy obj) {
		try {
			String query = "INSERT INTO Video_game_copy (idUserOwner, numberVideoGame) VALUES (?,?)";
			PreparedStatement stmt = this.connection.prepareStatement(query);
			stmt.setInt(1, obj.getOwner().getId());
			stmt.setInt(2, obj.getVideoGame().getNumber());
			
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
	public boolean update(Copy obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Copy obj) {
		// TODO Auto-generated method stub
		return false;
	}

}
