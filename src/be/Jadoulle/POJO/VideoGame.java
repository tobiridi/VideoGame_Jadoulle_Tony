package be.Jadoulle.POJO;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;

import be.Jadoulle.DAO.AbstractDAOFactory;
import be.Jadoulle.DAO.DAO;

public class VideoGame implements Serializable {
	private static final long serialVersionUID = 6497780619918639099L;

	private int number;
	private String name;
	private int creditCost;
	private String console;
	private LocalDate releaseDate;
	private ArrayList<Booking> bookings;
	private ArrayList<Copy> copies;

	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public int getCreditCost() {
		return creditCost;
	}
	public void setCreditCost(int creditCost) {
		this.creditCost = creditCost;
	}

	public String getConsole() {
		return console;
	}
	public void setConsole(String console) {
		this.console = console;
	}

	public LocalDate getReleaseDate() {
		return releaseDate;
	}
	public void setReleaseDate(LocalDate releaseDate) {
		this.releaseDate = releaseDate;
	}

	public ArrayList<Booking> getBookings() {
		return bookings;
	}
	public void setBookings(ArrayList<Booking> bookings) {
		this.bookings = bookings;
	}

	public ArrayList<Copy> getCopies() {
		return copies;
	}
	public void setCopies(ArrayList<Copy> copies) {
		this.copies = copies;
	}

	public VideoGame(int number, String name, int creditCost, String console, LocalDate releaseDate) {
		this.number = number;
		this.name = name;
		this.creditCost = creditCost;
		this.console = console;
		this.releaseDate = releaseDate;
		this.bookings = new ArrayList<>();
		this.copies = new ArrayList<>();
	}

	//methods
	public void addBooking(Booking booking) {
		this.bookings.add(booking);
	}

	public void addCopy(Copy copy) {
		this.copies.add(copy);
	}

	public static ArrayList<VideoGame> getAll(){
		AbstractDAOFactory adf = AbstractDAOFactory.getFactory(AbstractDAOFactory.DAO_FACTORY);
		DAO<VideoGame> gameDao = adf.getVideoGameDao();
		return gameDao.findAll();
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(number, name, creditCost, console, releaseDate);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if ((obj == null) || (this.getClass() != obj.getClass()))
			return false;
		VideoGame other = (VideoGame) obj;
		return this.number == other.number;
	}

	/**
	 * Search if a copy is available, if {@code player} argument is not null<br>
	 * exclude the copy of this player if exists.
	 * @author tony
	 * @param player  the player who wants to borrow a copy.
	 * @return {@link Copy}
	 */
	public Copy copyAvailable(Player player) {
		for(Copy c : this.copies) {
			if(player != null) {
				//exclude player's copies
				if(c.getOwner().equals(player)) {
					continue;
				}
			}

			if(c.isAvailable()) {
				//return first copy available
				return c;
			}
		}
		return null;
	}
	
	public void selectBooking() {
		AbstractDAOFactory adf = AbstractDAOFactory.getFactory(AbstractDAOFactory.DAO_FACTORY);
		ArrayList<Booking> bookings = adf.getBookingDao().findAll();
		
		List<Booking> gameBookings = bookings.stream()
		.filter(booking -> booking.getVideoGame().equals(this))
		.collect(Collectors.toList());
		
		//no booking found
		if(gameBookings.size() == 0)
			return;
		
		//1. Le plus d’unités sur son compte
		int maxCredit = 0;
		for (Booking b : gameBookings) {
			if(b.getPlayer().getCredits() > maxCredit) {
				maxCredit = b.getPlayer().getCredits();
			}
		}
		
		List<Booking> maxCreditBookings = new ArrayList<>();
		for (Booking b : gameBookings) {
			if(b.getPlayer().getCredits() == maxCredit) {
				maxCreditBookings.add(b);
			}
		}
		
		//2. Réservation la plus ancienne
		LocalDate oldBooking = maxCreditBookings.get(0).getBookingDate();
		for (Booking b : maxCreditBookings) {
			if(b.getBookingDate().isBefore(oldBooking)) {
				oldBooking = b.getBookingDate();
			}
		}
		
		List<Booking> oldBookings = new ArrayList<>();
		for (Booking b : maxCreditBookings) {
			if(b.getBookingDate().equals(oldBooking)) {
				oldBookings.add(b);
			}
		}
		
		//3. Abonné inscrit depuis le plus longtemps
		LocalDate oldPlayer = oldBookings.get(0).getPlayer().getRegistrationDate();
		for (Booking b : oldBookings) {
			if(b.getPlayer().getRegistrationDate().isBefore(oldPlayer)) {
				oldPlayer = b.getPlayer().getRegistrationDate();
			}
		}
		
		List<Booking> oldPlayerBookings = new ArrayList<>();
		for (Booking b : oldBookings) {
			if(b.getPlayer().getRegistrationDate().equals(oldPlayer)) {
				oldPlayerBookings.add(b);
			}
		}
		
		//4. Abonné le plus âgé
		LocalDate oldPlayerAge = oldPlayerBookings.get(0).getPlayer().getDateOfBirth();
		for (Booking b : oldPlayerBookings) {
			if(b.getPlayer().getDateOfBirth().isBefore(oldPlayerAge)) {
				oldPlayerAge = b.getPlayer().getDateOfBirth();
			}
		}
		
		List<Booking> oldPlayerAgeBookings = new ArrayList<>();
		for (Booking b : oldPlayerBookings) {
			if(b.getPlayer().getDateOfBirth().equals(oldPlayerAge)) {
				oldPlayerAgeBookings.add(b);
			}
		}
		
		//5. Aléatoire
		Random random = new Random();
		int randomBooking = random.nextInt(oldPlayerAgeBookings.size());	
		
		Booking finalBooking = oldPlayerAgeBookings.get(randomBooking);
		
		//transform booking to loan
		VideoGame game = adf.getVideoGameDao().find(this.number);
		Copy c = game.copyAvailable(finalBooking.getPlayer());
		if(c != null) {
			//create loan
			LocalDate endDate = LocalDate.now().plusWeeks(finalBooking.getNbrWeeks());
			Loan newLoan = new Loan(0, LocalDate.now(), endDate, true, c.getOwner(), finalBooking.getPlayer(), c, 0);
			c.setCopyLoan(newLoan);
			if(c.borrow()) {
				finalBooking.delete();
			}
		}
	}
}
