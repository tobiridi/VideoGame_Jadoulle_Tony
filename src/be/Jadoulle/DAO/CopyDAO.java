package be.Jadoulle.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import be.Jadoulle.POJO.Copy;
import be.Jadoulle.POJO.Player;
import be.Jadoulle.POJO.VideoGame;

public class CopyDAO extends DAO<Copy> {

	public CopyDAO(Connection conn) {
		super(conn);
	}

	@Override
	public Copy find(int id) {
//		AbstractDAOFactory adf = AbstractDAOFactory.getFactory(AbstractDAOFactory.DAO_FACTORY);
//		DAO<Loan> loanDao = adf.getLoanDao();
		Copy copy = null;
		try {
			String query = "SELECT User.id AS user_id, User.username, User.pseudo, User.registration, User.dateOfBirth, User.credits, "
					+ "Video_game.Number, Video_game.videoGameName, Video_game.creditCost, Video_game.console, Video_game.releaseDate "
					+ "FROM Video_game INNER JOIN (([User] INNER JOIN Video_game_copy ON User.id = Video_game_copy.idUserOwner) LEFT JOIN Loan ON Video_game_copy.id = Loan.idVideoGameCopy) ON Video_game.Number = Video_game_copy.numberVideoGame "
					+ "WHERE (((Video_game_copy.id) = ?))";
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

				copy = new Copy(id, player, game);

//				int idLoan = res.getInt("loan_id");
//				if(idLoan != 0) {
//					Loan loan = loanDao.find(idLoan);
//					loan.setCopy(copy);
//					loan.setLender(copy.getOwner());
//					copy.setCopyLoan(loan);
//				}
			}

			stmt.close();
			res.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return copy;
	}

	@Override
	public ArrayList<Copy> findAll() {
		ArrayList<Copy> copies = new ArrayList<>();
		try {
			String query = "SELECT id FROM Video_game_copy";

			ResultSet res = this.connection
					.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)
					.executeQuery(query);
			while(res.next()) {
				Copy copy = this.find(res.getInt("id"));
				copies.add(copy);
			}

			res.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return copies;
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
		return false;
	}

	@Override
	public boolean delete(Copy obj) {
		return false;
	}

}
