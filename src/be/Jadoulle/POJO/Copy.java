package be.Jadoulle.POJO;

import java.io.Serializable;

public class Copy implements Serializable {
	private static final long serialVersionUID = -2171358160857519019L;
	
	private Player owner;
	private VideoGame videoGame;
	
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

	public Copy(Player owner, VideoGame videoGame) {
		this.owner = owner;
		this.videoGame = videoGame;
	}
	
}
