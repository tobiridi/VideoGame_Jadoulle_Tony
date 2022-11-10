package be.Jadoulle.POJO;

import java.time.LocalDate;
import java.util.ArrayList;

import be.Jadoulle.DAO.AbstractDAOFactory;
import be.Jadoulle.DAO.DAO;

public class Player extends User {
	private static final long serialVersionUID = -5555278038998205449L;
	
	// TODO : not yet "Loan" classes
	//TODO : not all attributes
	private int credits;
	private String pseudo;
	private LocalDate registrationDate;
	private LocalDate dateOfBirth;
	private ArrayList<Booking> bookings;
	private ArrayList<Copy> copies;
	
	public int getCredits() {
		return credits;
	}
	public void setCredits(int credits) {
		this.credits = credits;
	}
	
	public String getPseudo() {
		return pseudo;
	}
	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	public LocalDate getRegistrationDate() {
		return registrationDate;
	}
	public void setRegistrationDate(LocalDate registrationDate) {
		this.registrationDate = registrationDate;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
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
	
	//constructor
	public Player(int id, String username, String password, int credits, String pseudo, LocalDate registrationDate,
			LocalDate dateOfBirth) {
		super(id, username, password);
		this.credits = credits;
		this.pseudo = pseudo;
		this.registrationDate = registrationDate;
		this.dateOfBirth = dateOfBirth;
		this.bookings = new ArrayList<>();
		this.copies = new ArrayList<>();
	}
	
	//methods
	public boolean signUp() {
		AbstractDAOFactory adf = AbstractDAOFactory.getFactory(AbstractDAOFactory.DAO_FACTORY);
		DAO<Player> playerDao = adf.getPlayerDao();
		return playerDao.create(this);
	}
	
	public void addBooking(Booking booking) {
		this.bookings.add(booking);
	}
	
	public void addCopy(Copy copy) {
		this.copies.add(copy);
	}
	
	public boolean createCopy(VideoGame game) {
		AbstractDAOFactory adf = AbstractDAOFactory.getFactory(AbstractDAOFactory.DAO_FACTORY);
		DAO<Copy> copyDao = adf.getCopyDao();
		Copy copy = new Copy(this, game);
		boolean isCreate = copyDao.create(copy);
		
		if(isCreate)
			this.addCopy(copy);
		
		return isCreate;
	}
}
