package be.Jadoulle.POJO;

import java.io.Serializable;
import java.time.LocalDate;

public class Booking implements Serializable {
	private static final long serialVersionUID = -5166184745766613819L;
	
	private LocalDate bookingDate;
	private VideoGame videoGame;
	private Player player;
	
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

	public Booking(LocalDate bookingDate, VideoGame videoGame, Player player) {
		this.bookingDate = bookingDate;
		this.videoGame = videoGame;
		this.player = player;
	}
	
}
