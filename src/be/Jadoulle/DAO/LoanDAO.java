package be.Jadoulle.DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import be.Jadoulle.POJO.Copy;
import be.Jadoulle.POJO.Loan;
import be.Jadoulle.POJO.Player;

public class LoanDAO extends DAO<Loan> {

	public LoanDAO(Connection conn) {
		super(conn);
	}

	@Override
	public Loan find(int id) {
		Loan loan = null;
		AbstractDAOFactory adf = AbstractDAOFactory.getFactory(AbstractDAOFactory.DAO_FACTORY);
		DAO<Copy> copyDao = adf.getCopyDao();
		try {
			String query = "SELECT Loan.startDate, Loan.endDate, Loan.onGoing, Loan.lateDays, "
					+ "User.id AS user_id, User.username, User.pseudo, User.registration, User.dateOfBirth, User.credits, "
					+ "Video_game_copy.id AS Video_game_copy_id "
					+ "FROM ([User] INNER JOIN Loan ON User.id = Loan.idUserBorrower) INNER JOIN Video_game_copy ON (Video_game_copy.id = Loan.idVideoGameCopy) "
					+ "WHERE (((Loan.id) = ?))";
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

				LocalDate startDate = res.getDate("startDate").toLocalDate();
				LocalDate endDate = res.getDate("endDate").toLocalDate();
				boolean onGoing = res.getBoolean("onGoing");
				//TODO : complete loan if needs
				int lateDays = res.getInt("lateDays");

				Copy copy = copyDao.find(res.getInt("Video_game_copy_id"));

				loan = new Loan(id, startDate, endDate, onGoing, copy.getOwner(), player, copy, lateDays);
			}

			stmt.close();
			res.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return loan;
	}

	@Override
	public ArrayList<Loan> findAll() {
		ArrayList<Loan> loans = new ArrayList<>();
		try {
			String query = "SELECT id FROM Loan";

			ResultSet res = this.connection
					.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)
					.executeQuery(query);
			
			while(res.next()) {
				Loan loan = this.find(res.getInt("id"));
				loans.add(loan);
			}

			res.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return loans;
	}

	@Override
	public boolean create(Loan obj) {
		try {
			String query = "INSERT INTO Loan (startDate, endDate, onGoing, lateDays, idVideoGameCopy, idUserBorrower)"
					+ " VALUES (?,?,?,?,?,?)";
			PreparedStatement stmt = this.connection.prepareStatement(query);
			stmt.setDate(1, Date.valueOf(obj.getStartDate()));
			stmt.setDate(2, Date.valueOf(obj.getEndDate()));
			stmt.setBoolean(3, obj.isOnGoing());
			stmt.setInt(4, obj.getLateDays());
			stmt.setInt(5, obj.getCopy().getId());
			stmt.setInt(6, obj.getBorrower().getId());

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
	public boolean update(Loan obj) {
		try {
			String query = "UPDATE Loan SET startDate = ?, endDate = ?, onGoing = ?, lateDays = ? "
					+ "WHERE id = ?";
			PreparedStatement stmt = this.connection.prepareStatement(query);
			stmt.setDate(1, Date.valueOf(obj.getStartDate()));
			stmt.setDate(2, Date.valueOf(obj.getEndDate()));
			stmt.setBoolean(3, obj.isOnGoing());
			stmt.setInt(4, obj.getLateDays());
			stmt.setInt(5, obj.getId());

			int res = stmt.executeUpdate();
			stmt.close();
			if(res == 1)
				return true;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public boolean delete(Loan obj) {
		try {
			String query = "DELETE FROM Loan WHERE id = ?";
			PreparedStatement stmt = this.connection.prepareStatement(query);
			stmt.setInt(1, obj.getId());

			int res = stmt.executeUpdate();
			stmt.close();
			if(res == 1)
				return true;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

}
