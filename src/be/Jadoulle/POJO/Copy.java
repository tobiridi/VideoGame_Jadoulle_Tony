package be.Jadoulle.POJO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

import be.Jadoulle.DAO.AbstractDAOFactory;

public class Copy implements Serializable {
	private static final long serialVersionUID = -2171358160857519019L;
	
	private int id;
	private Player owner;
	private VideoGame videoGame;
	private Loan copyLoan;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public Player getOwner() {
		return owner;
	}
	public void setOwner(Player owner) {
		this.owner = owner;
	}

	public VideoGame getVideoGame() {
		return videoGame;
	}
	public void setVideoGame(VideoGame videoGame) {
		this.videoGame = videoGame;
	}
	
	public Loan getCopyLoan() {
		return copyLoan;
	}
	public void setCopyLoan(Loan copyLoan) {
		this.copyLoan = copyLoan;
	}

	public Copy(int id, Player owner, VideoGame videoGame) {
		this.id = id;
		this.owner = owner;
		this.videoGame = videoGame;
	}
	
	public Copy(int id, Player owner, VideoGame videoGame, Loan loan) {
		this(id, owner, videoGame);
		this.copyLoan = loan;
	}
	
	public boolean isAvailable() {
		AbstractDAOFactory adf = AbstractDAOFactory.getFactory(AbstractDAOFactory.DAO_FACTORY);
		ArrayList<Loan> loans = adf.getLoanDao().findAll();
		boolean isAvailable = true;
		
		for(Loan loan : loans) {
			if(loan.getCopy().equals(this)) {
				//copy loaned
				isAvailable = false;
				break;
			}
		}
		return isAvailable;
	}
	
	public boolean borrow() {
		AbstractDAOFactory adf = AbstractDAOFactory.getFactory(AbstractDAOFactory.DAO_FACTORY);
		return adf.getLoanDao().create(this.copyLoan);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id, owner);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (this.getClass() != obj.getClass())
			return false;
		Copy other = (Copy) obj;
		return this.id == other.id;
	}
	
	
}
