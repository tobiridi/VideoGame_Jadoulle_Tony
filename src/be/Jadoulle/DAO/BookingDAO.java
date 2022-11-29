package be.Jadoulle.DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import be.Jadoulle.POJO.Booking;
import be.Jadoulle.POJO.Player;
import be.Jadoulle.POJO.VideoGame;

public class BookingDAO extends DAO<Booking> {

	public BookingDAO(Connection conn) {
		super(conn);
	}

	@Override
	public Booking find(int id) {
		Booking booking = null;
		try {
			String query = "SELECT Booking.bookingDate, Booking.nbrWeeks, "
					+ "User.id AS user_id, User.username, User.pseudo, User.registration, User.dateOfBirth, User.credits, "
					+ "Video_game.Number, Video_game.videoGameName, Video_game.creditCost, Video_game.console, Video_game.releaseDate "
					+ "FROM [User] INNER JOIN (Video_game INNER JOIN Booking ON Video_game.Number = Booking.numberVideoGame) ON User.id = Booking.idUser "
					+ "WHERE (((Booking.id) = ?))";
			PreparedStatement stmt = this.connection.prepareStatement(query);
			stmt.setInt(1, id);
			
			ResultSet res = stmt.executeQuery();
			while(res.next()) {
				int idPlayer = res.getInt("user_id");
				String username = res.getString("username");
				String pseudo = res.getString("pseudo");
				LocalDate registration = res.getDate("registration").toLocalDate();
				LocalDate birth =res.getDate("dateOfBirth").toLocalDate();
				int credits = res.getInt("credits");
				
				Player player = new Player(idPlayer, username, null, credits, pseudo, registration, birth);

				int numberVideoGame = res.getInt("number");
				String name = res.getString("videoGameName");
				int cost = res.getInt("creditCost");
				String console = res.getString("console");
				LocalDate release = res.getDate("releaseDate").toLocalDate();
				
				VideoGame game = new VideoGame(numberVideoGame, name, cost, console, release);
				
				LocalDate bookingDate = res.getDate("bookingDate").toLocalDate();
				//TODO : complete booking if needs
				int weeks = res.getInt("nbrWeeks");
				
				booking = new Booking(id, bookingDate, game, player, weeks);
			}
			
			stmt.close();
			res.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return booking;
	}

	@Override
	public ArrayList<Booking> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean create(Booking obj) {
		try {
			String query = "INSERT INTO Booking (bookingDate, nbrWeeks, numberVideoGame, idUser)"
					+ " VALUES (?,?,?,?)";
			PreparedStatement stmt = this.connection.prepareStatement(query);
			stmt.setDate(1, Date.valueOf(obj.getBookingDate()));
			stmt.setInt(2, obj.getNbrWeeks());
			stmt.setInt(3, obj.getVideoGame().getNumber());
			stmt.setInt(4, obj.getPlayer().getId());
			
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
	public boolean update(Booking obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Booking obj) {
		// TODO Auto-generated method stub
		return false;
	}

}
