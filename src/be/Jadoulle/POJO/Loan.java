package be.Jadoulle.POJO;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDate;

import be.Jadoulle.DAO.AbstractDAOFactory;

public class Loan implements Serializable {
	private static final long serialVersionUID = -2955997554746322446L;

	private int id;
	private LocalDate startDate;
	private LocalDate endDate;
	private boolean onGoing;
	private Player lender;
	private Player borrower;
	private Copy copy;
	private int lateDays;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public LocalDate getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public boolean isOnGoing() {
		return onGoing;
	}
	public void setOnGoing(boolean onGoing) {
		this.onGoing = onGoing;
	}

	public Player getLender() {
		return lender;
	}
	public void setLender(Player lender) {
		this.lender = lender;
	}

	public Player getBorrower() {
		return borrower;
	}
	public void setBorrower(Player borrower) {
		this.borrower = borrower;
	}

	public Copy getCopy() {
		return copy;
	}
	public void setCopy(Copy copy) {
		this.copy = copy;
	}

	public int getLateDays() {
		return lateDays;
	}
	public void setLateDays(int lateDays) {
		this.lateDays = lateDays;
	}

	//constructor
	public Loan(int id, LocalDate startDate, LocalDate endDate, boolean onGoing, Player lender, Player borrower, Copy copy, int lateDays) {
		this.id = id;
		this.startDate = startDate;
		this.endDate = endDate;
		this.onGoing = onGoing;
		this.lender = lender;
		this.borrower = borrower;
		this.copy = copy;
		this.lateDays = lateDays;
	}

	//methods
	public void calculateBalance() {
		AbstractDAOFactory adf = AbstractDAOFactory.getFactory(AbstractDAOFactory.DAO_FACTORY);
		
		this.lateDays = (int) Duration.between(this.endDate.atStartOfDay(), LocalDate.now().atStartOfDay()).toDays();
		int lateDaysMalus = this.lateDays * 5;
		int creditBorrower = this.borrower.getCredits();
		int creditLender = this.lender.getCredits();;
		
		creditBorrower -= lateDaysMalus;
		creditBorrower -= this.copy.getVideoGame().getCreditCost();
		
		creditLender += this.copy.getVideoGame().getCreditCost();
		creditLender += lateDaysMalus;
		
		this.borrower.setCredits(creditBorrower);
		this.lender.setCredits(creditLender);
		
		adf.getLoanDao().update(this);
		adf.getPlayerDao().update(this.borrower);
		adf.getPlayerDao().update(this.lender);
	}
	
	public boolean endLoan() {
		boolean success = false;
		
		if((LocalDate.now().isAfter(this.endDate) || LocalDate.now().equals(this.endDate))
			&& this.onGoing) {
			
			this.onGoing = false;
			this.copy.setCopyLoan(this);
			success = this.copy.releaseCopy();
			
			if(success) {
				this.calculateBalance();
			}
		}
		return success;
	}

}
