package be.Jadoulle.POJO;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

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
	
}
