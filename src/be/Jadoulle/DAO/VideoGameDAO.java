package be.Jadoulle.DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import be.Jadoulle.POJO.VideoGame;

public class VideoGameDAO extends DAO<VideoGame> {

	public VideoGameDAO(Connection conn) {
		super(conn);
	}

	@Override
	public VideoGame find(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean create(VideoGame obj) {
		try {
			String query = "INSERT INTO Video_game (videoGameName, creditCost, console, releaseDate) VALUES (?,?,?,?)";
			PreparedStatement stmt = this.connection.prepareStatement(query);
			stmt.setString(1, obj.getName());
			stmt.setInt(2, obj.getCreditCost());
			stmt.setString(3, obj.getConsole());
			stmt.setDate(4, Date.valueOf(obj.getReleaseDate()));
			
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
	public boolean update(VideoGame obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(VideoGame obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ArrayList<VideoGame> findAll() {
		ArrayList<VideoGame> allGames = new ArrayList<>();
		String query = "SELECT number, videoGameName, creditCost, console, releaseDate FROM Video_game ORDER BY videoGameName ASC";
		try {
			ResultSet res = this.connection.
					createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)
					.executeQuery(query);
			
			while(res.next()) {
				int number = res.getInt("number");
				String name = res.getString("videoGameName");
				int creditCost = res.getInt("creditCost");
				String console = res.getString("console");
				LocalDate release = res.getDate("releaseDate").toLocalDate();
				
				allGames.add(new VideoGame(number, name, creditCost, console, release));
			}
			
			res.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return allGames;
	}

}
