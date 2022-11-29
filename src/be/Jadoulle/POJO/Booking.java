package be.Jadoulle.POJO;

import java.io.Serializable;
import java.time.LocalDate;

public class Booking implements Serializable {
	private static final long serialVersionUID = -5166184745766613819L;
	
	private int id;
	private LocalDate bookingDate;
	private VideoGame videoGame;
	private Player player;
	private int nbrWeeks;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public LocalDate getBookingDate() {
		return bookingDate;
	}
	public void setBookingDate(LocalDate bookingDate) {
		this.bookingDate = bookingDate;
	}

	public VideoGame getVideoGame() {
		return videoGame;
	}
	public void setVideoGame(VideoGame videoGame) {
		this.videoGame = videoGame;
	}

	public Player getPlayer() {
		return player;
	}
	public void setPlayer(Player player) {
		this.player = player;
	}
	
	public int getNbrWeeks() {
		return nbrWeeks;
	}
	public void setNbrWeeks(int nbrWeeks) {
		this.nbrWeeks = nbrWeeks;
	}

	public Booking(int id, LocalDate bookingDate, VideoGame videoGame, Player player, int nbrWeeks) {
		this.id = id;
		this.bookingDate = bookingDate;
		this.videoGame = videoGame;
		this.player = player;
		this.nbrWeeks = nbrWeeks;
	}
	
}
