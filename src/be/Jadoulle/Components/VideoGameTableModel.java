package be.Jadoulle.Components;

import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;

import be.Jadoulle.POJO.VideoGame;

public class VideoGameTableModel extends DefaultTableModel {
	private static final long serialVersionUID = -7665755577487132566L;

	private ArrayList<VideoGame> videoGames;

	public VideoGameTableModel (ArrayList<VideoGame> games) {
		this.videoGames = games;
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}

	public VideoGame getVideoGameAt(int row) {
		return this.videoGames.get(row);
	}

}
