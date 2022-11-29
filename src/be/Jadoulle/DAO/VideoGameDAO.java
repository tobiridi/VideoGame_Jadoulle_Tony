package be.Jadoulle.DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import be.Jadoulle.POJO.Booking;
import be.Jadoulle.POJO.Copy;
import be.Jadoulle.POJO.VideoGame;

public class VideoGameDAO extends DAO<VideoGame> {

	public VideoGameDAO(Connection conn) {
		super(conn);
	}

	@Override
	public VideoGame find(int number) {
		VideoGame game = null;
		AbstractDAOFactory adf = AbstractDAOFactory.getFactory(AbstractDAOFactory.DAO_FACTORY);
		DAO<Booking> bookingDao = adf.getBookingDao();
		DAO<Copy> copyDao = adf.getCopyDao();
		int lastIdBooking = 0;
		int lastIdCopy = 0;
		try {
			String query = "SELECT Video_game.videoGameName, Video_game.creditCost, Video_game.console, Video_game.releaseDate, "
					+ "Booking.id AS booking_id, "
					+ "Video_game_copy.id AS video_game_copy_id "
					+ "FROM (Video_game LEFT JOIN Video_game_copy ON Video_game.Number = Video_game_copy.numberVideoGame) LEFT JOIN Booking ON Video_game.Number = Booking.numberVideoGame "
					+ "WHERE (((Video_game.Number) = ?))";
			PreparedStatement stmt = this.connection.prepareStatement(query);
			stmt.setInt(1, number);
			
			ResultSet res = stmt.executeQuery();
			while(res.next()) {
				if(game == null) {
					String name = res.getString("videoGameName");
					int cost = res.getInt("creditCost");
					String console = res.getString("console");
					LocalDate release = res.getDate("releaseDate").toLocalDate();
					
					game = new VideoGame(number, name, cost, console, release);
				}
				
				//add bookings
				if(res.getInt("booking_id") != 0) {
					Booking booking = bookingDao.find(res.getInt("booking_id"));
					if(booking.getId() != lastIdBooking) {
						lastIdBooking = booking.getId();
						game.addBooking(booking);
					}
				}
				
				//add copies
				if(res.getInt("Video_game_copy_id") != 0) {
					Copy copy = copyDao.find(res.getInt("Video_game_copy_id"));
					if(copy.getId() != lastIdCopy) {
						lastIdCopy = copy.getId();
						game.addCopy(copy);
					}
				}
			}
			
			stmt.close();
			res.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return game;
	}

	@Override
	public ArrayList<VideoGame> findAll() {
		ArrayList<VideoGame> allGames = new ArrayList<>();
		try {
			String query = "SELECT number FROM Video_game ORDER BY videoGameName ASC";
			ResultSet res = this.connection.
					createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)
					.executeQuery(query);
			
			while(res.next()) {
				VideoGame game = this.find(res.getInt("number"));
				allGames.add(game);
			}
			
			res.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return allGames;
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

}
