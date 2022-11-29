package be.Jadoulle.POJO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

import be.Jadoulle.DAO.AbstractDAOFactory;
import be.Jadoulle.DAO.DAO;

public class Player extends User {
	private static final long serialVersionUID = -5555278038998205449L;
	
	private int credits;
	private String pseudo;
	private LocalDate registrationDate;
	private LocalDate dateOfBirth;
	private ArrayList<Booking> bookings;
	private ArrayList<Copy> copies;
	private ArrayList<Loan> loansLender;
	private ArrayList<Loan> loansBorrower;
	
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
	
	public ArrayList<Loan> getLoansLender() {
		return loansLender;
	}
	public void setLoansLender(ArrayList<Loan> loansLender) {
		this.loansLender = loansLender;
	}
	
	public ArrayList<Loan> getLoansBorrower() {
		return loansBorrower;
	}
	public void setLoansBorrower(ArrayList<Loan> loansBorrower) {
		this.loansBorrower = loansBorrower;
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
		this.loansLender = new ArrayList<>();
		this.loansBorrower = new ArrayList<>();
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
		Copy copy = new Copy(0, this, game);
		boolean isCreate = copyDao.create(copy);
		
		if(isCreate)
			this.addCopy(copy);
		
		return isCreate;
	}
	
	public boolean createBooking(Booking booking) {
		AbstractDAOFactory adf = AbstractDAOFactory.getFactory(AbstractDAOFactory.DAO_FACTORY);
		DAO<Booking> bookingDao = adf.getBookingDao();
		boolean isCreate = bookingDao.create(booking);
		
		if(isCreate)
			this.addBooking(booking);
		
		return isCreate;
	}
	
	public boolean loanAllowed(Copy copy) {
		return this.credits >= copy.getVideoGame().getCreditCost();
	}
	
	public boolean hasCredits() {
		return this.credits > 0;
	}
	
	public void addLenderLoan(Loan loan) {
		this.loansLender.add(loan);
	}
	
	public void addBorrowerLoan(Loan loan) {
		this.loansBorrower.add(loan);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id, username, password, bookings, copies, credits, dateOfBirth, 
				loansBorrower, loansLender, pseudo, registrationDate);
	}
	
	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}
	
}
