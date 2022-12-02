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
import be.Jadoulle.POJO.Loan;
import be.Jadoulle.POJO.Player;

public class PlayerDAO extends DAO<Player> {

	public PlayerDAO(Connection conn) {
		super(conn);
	}

	@Override
	public Player find(int id) {
		AbstractDAOFactory adf = AbstractDAOFactory.getFactory(AbstractDAOFactory.DAO_FACTORY);
		DAO<Booking> bookingDao = adf.getBookingDao();
		DAO<Copy> copyDao = adf.getCopyDao();
		DAO<Loan> loanDao = adf.getLoanDao();
		Player player = null;
		ArrayList<Integer> lastIdBooking = new ArrayList<>();
		ArrayList<Integer> lastIdLoan = new ArrayList<>();
		ArrayList<Integer> lastIdCopy = new ArrayList<>();

		try {
			String query = "SELECT User.username, User.pseudo, User.isAdmin, User.registration, User.dateOfBirth, User.credits, "
					+ "Loan.id AS Loan_id, Video_game_copy.id AS Video_game_copy_id, Booking.id AS booking_id "
					+ "FROM (([User] LEFT JOIN Booking ON User.id = Booking.idUser) LEFT JOIN Loan ON User.id = Loan.idUserBorrower) "
					+ "LEFT JOIN Video_game_copy ON User.id = Video_game_copy.idUserOwner "
					+ "WHERE (((User.id) = ?))";
			PreparedStatement stmt = this.connection.prepareStatement(query);
			stmt.setInt(1, id);

			ResultSet res = stmt.executeQuery();
			while(res.next()) {
				if(player == null) {
					boolean isAdmin = res.getBoolean("isAdmin");
					if(!isAdmin) {
						String username = res.getString("username");
						String pseudo = res.getString("pseudo");
						LocalDate registration = res.getDate("registration").toLocalDate();
						LocalDate birth =res.getDate("dateOfBirth").toLocalDate();
						int credits = res.getInt("credits");

						player = new Player(id, username, null, credits, pseudo, registration, birth);
						
						//add loans lender
						ArrayList<Loan> allLoans = adf.getLoanDao().findAll();
						for(Loan loan : allLoans) {
							if (loan.getCopy().getOwner().equals(player)) {
								player.addLenderLoan(loan);
							}
						}
					}
					else {
						return null;
					}
				}

				//add bookings
				if(res.getInt("booking_id") != 0) {
					Booking booking = bookingDao.find(res.getInt("booking_id"));
					if(lastIdBooking.size() == 0) {
						lastIdBooking.add(booking.getId());
						player.addBooking(booking);
					}
					else {
						boolean isFind = false;
						for(int idBooking : lastIdBooking) {
							if(idBooking == booking.getId()) {
								isFind = true;
								break;
							}
						}
						if(!isFind) {
							lastIdBooking.add(booking.getId());
							player.addBooking(booking);
						}
					}
				}

				//add copies
				if(res.getInt("Video_game_copy_id") != 0) {
					Copy copy = copyDao.find(res.getInt("Video_game_copy_id"));
					if(lastIdCopy.size() == 0) {
						lastIdCopy.add(copy.getId());
						player.addCopy(copy);
					}
					else {
						boolean isFind = false;
						for(int idCopy : lastIdCopy) {
							if(idCopy == copy.getId()) {
								isFind = true;
								break;
							}
						}
						if(!isFind) {
							lastIdCopy.add(copy.getId());
							player.addCopy(copy);
						}
					}
				}

				//add loans borrower
				if(res.getInt("Loan_id") != 0) {
					Loan loan = loanDao.find(res.getInt("Loan_id"));
					if(lastIdLoan.size() == 0) {
						lastIdLoan.add(loan.getId());
						player.addBorrowerLoan(loan);
					}
					else {
						boolean isFind = false;
						for(int idLoan : lastIdLoan) {
							if(idLoan == loan.getId()) {
								isFind = true;
								break;
							}
						}
						if(!isFind) {
							lastIdLoan.add(loan.getId());
							player.addBorrowerLoan(loan);
						}
					}
				}
			}

			stmt.close();
			res.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return player;
	}

	@Override
	public ArrayList<Player> findAll() {
		return null;
	}

	@Override
	public boolean create(Player obj) {
		try {
			String query = "INSERT INTO User (username, password, pseudo, registration, dateOfBirth, credits) VALUES (?,?,?,?,?,?)";
			PreparedStatement stmt = this.connection.prepareStatement(query);
			stmt.setString(1, obj.getUsername());
			stmt.setString(2, obj.getPassword());
			stmt.setString(3, obj.getPseudo());
			stmt.setDate(4, Date.valueOf(obj.getRegistrationDate()));
			stmt.setDate(5, Date.valueOf(obj.getDateOfBirth()));
			stmt.setInt(6, obj.getCredits());

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
	public boolean update(Player obj) {
		try {
			String query = "UPDATE User SET username = ?, pseudo = ?, credits = ? "
					+ "WHERE id = ?";
			PreparedStatement stmt = this.connection.prepareStatement(query);
			stmt.setString(1, obj.getUsername());
			stmt.setString(2, obj.getPseudo());
			stmt.setInt(3, obj.getCredits());
			stmt.setInt(4, obj.getId());

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
	public boolean delete(Player obj) {
		return false;
	}
}
