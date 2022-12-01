package be.Jadoulle.Frame;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import be.Jadoulle.Components.VideoGameTableModel;
import be.Jadoulle.POJO.Player;
import be.Jadoulle.POJO.VideoGame;

public class AddCopyVideoGameFrame extends JFrame {
	private JPanel contentPane;
	private JTable table;
	private JButton btnCancel;
	private JButton btnConfirm;
	private JScrollPane scrollPane;
	private VideoGame selectedGame;
	private ArrayList<VideoGame> videoGames;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					AddCopyVideoGameFrame frame = new AddCopyVideoGameFrame(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	@Override
	protected void frameInit() {
		super.frameInit();
		this.videoGames = VideoGame.getAll();
	}
	
	/**
	 * Create the frame.
	 */
	public AddCopyVideoGameFrame(Player player) {
		setTitle("Prêter un nouveau jeu");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblTitle = new JLabel("Jeu à mettre en location");
		lblTitle.setHorizontalAlignment(SwingConstants.LEFT);
		lblTitle.setFont(new Font("Book Antiqua", Font.PLAIN, 18));
		lblTitle.setBounds(110, 10, 210, 20);
		contentPane.add(lblTitle);

		scrollPane = new JScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBounds(30, 70, 375, 130);
		contentPane.add(scrollPane);

		table = new JTable();
		//parameter the JTable model
		this.tableModelChange();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//save the video game object based the row in the JTable
				int row = table.getSelectedRow();
				VideoGameTableModel model = (VideoGameTableModel) table.getModel();
				selectedGame = model.getVideoGameAt(row);
				System.out.println(selectedGame.getNumber() + " " + selectedGame.getName() + " " + selectedGame.getConsole());
			}
		});
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		scrollPane.setViewportView(table);

		btnCancel = new JButton("Annuler");
		btnCancel.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		btnCancel.setBounds(80, 211, 100, 25);
		btnCancel.addActionListener((ActionEvent e) -> {
			//back to player main frame
			PlayerMainFrame frame = new PlayerMainFrame(player);
			frame.setVisible(true);
			dispose();
		});
		contentPane.add(btnCancel);

		btnConfirm = new JButton("Confirmer");
		btnConfirm.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		btnConfirm.setBounds(210, 211, 120, 25);
		btnConfirm.addActionListener((ActionEvent e) -> {
			if(selectedGame != null) {
				//display confirm message
				String confirmMessage = "Êtes-vous sur de vouloir ajouter\n" + selectedGame.getName() + " " + selectedGame.getConsole();
				int validation = JOptionPane.showConfirmDialog(AddCopyVideoGameFrame.this, confirmMessage, "Confirmation jeu", JOptionPane.YES_NO_OPTION);
				if(validation == 0) {
					if(player.createCopy(selectedGame)) {
						//display success message
						String successMessage = "Une copie pour " + selectedGame.getName() + " " + selectedGame.getConsole() + " a été enregistrer";
						JOptionPane.showMessageDialog(AddCopyVideoGameFrame.this, successMessage, "Information",JOptionPane.INFORMATION_MESSAGE);
					}
					else {
						JOptionPane.showMessageDialog(AddCopyVideoGameFrame.this, "Le jeu n'a pas pu être enregistrer", "Erreur", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
			else {
				JOptionPane.showMessageDialog(AddCopyVideoGameFrame.this, "Aucun jeu n'a été sélectionner", "Erreur", JOptionPane.ERROR_MESSAGE);
			}

		});
		contentPane.add(btnConfirm);

		JLabel lblTableInfo = new JLabel("<html>*Sélectionner dans le tableau le jeu pour lequel vous voudriez mettre une copie en prêt.</html>");
		lblTableInfo.setBounds(80, 35, 300, 30);
		lblTableInfo.setFont(new Font("Book Antiqua", Font.PLAIN, 12));
		contentPane.add(lblTableInfo);
	}

	private void tableModelChange() {
		VideoGameTableModel customModel = new VideoGameTableModel(this.videoGames);

		//column title
		String[] identifiersCol = {"Nom du jeu", "Console", "Gain crédits", "Date de Sortie"};
		customModel.setColumnIdentifiers(identifiersCol);

		//data of each row
		for (VideoGame game : this.videoGames) {
			String[] gameData = {game.getName(), game.getConsole(), "" + game.getCreditCost(), game.getReleaseDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))};
			customModel.addRow(gameData);
		}

		table.setModel(customModel);
		//first column is the 2/3 of total column width
		int prefWidth = table.getColumnModel().getTotalColumnWidth() * 2 / 3;
		table.getColumnModel().getColumn(0).setPreferredWidth(prefWidth);
	}
}
