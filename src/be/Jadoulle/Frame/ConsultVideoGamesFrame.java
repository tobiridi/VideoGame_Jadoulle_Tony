package be.Jadoulle.Frame;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
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
import be.Jadoulle.POJO.Booking;
import be.Jadoulle.POJO.Copy;
import be.Jadoulle.POJO.Loan;
import be.Jadoulle.POJO.Player;
import be.Jadoulle.POJO.VideoGame;

public class ConsultVideoGamesFrame extends JFrame {

	private JPanel contentPane;
	private JTable table;
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
					ConsultVideoGamesFrame frame = new ConsultVideoGamesFrame(null);
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
	public ConsultVideoGamesFrame(Player player) {
		setTitle("Liste des jeux disponible");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblTitle = new JLabel("Liste des jeux");
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("Book Antiqua", Font.PLAIN, 18));
		lblTitle.setBounds(130, 10, 150, 20);
		contentPane.add(lblTitle);

		JScrollPane scrollPane = new JScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBounds(45, 50, 345, 150);
		contentPane.add(scrollPane);

		table = new JTable();
		//parameter the JTable model
		this.tableModelChange();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = table.getSelectedRow();
				VideoGameTableModel model = (VideoGameTableModel) table.getModel();
				selectedGame = model.getVideoGameAt(row);
			}
		});
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		scrollPane.setViewportView(table);

		JButton btnLoan = new JButton("Louer");
		btnLoan.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(selectedGame == null) {
					JOptionPane.showMessageDialog(ConsultVideoGamesFrame.this, "Aucun jeu séléctionner", "Copie", JOptionPane.ERROR_MESSAGE);
					return;
				}
				//attempt to get a copy of the selectedGame
				Copy gameCopy = selectedGame.copyAvailable(player);
				if(gameCopy != null) {
					loanCopy(player, gameCopy);
				}
				else {
					//ask to make a booking because not copies are available or exists
					int res = JOptionPane.showConfirmDialog(ConsultVideoGamesFrame.this, "Aucune copie disponible\n Voulez-vous faire une réservation ?", "Copie", JOptionPane.YES_NO_OPTION);
					if(res == 0) {
						bookingVideoGame(player);
					}
				}
			}
		});
		btnLoan.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		btnLoan.setBounds(226, 211, 120, 25);
		//disable if player doesn't have more than 0 credits
		if(!player.hasCredits()) {
			btnLoan.setEnabled(false);
		}
		contentPane.add(btnLoan);

		JButton btnBack = new JButton("Retour");
		btnBack.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PlayerMainFrame frame = new PlayerMainFrame(player);
				frame.setVisible(true);
				dispose();
			}
		});
		btnBack.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		btnBack.setBounds(96, 211, 100, 25);
		contentPane.add(btnBack);
	}

	private void tableModelChange() {
		VideoGameTableModel customModel = new VideoGameTableModel(this.videoGames);

		//column title
		String[] identifiersCol = {"Nom du jeu", "Console", "Coût en crédits", "Date de Sortie"};
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

	private void loanCopy(Player player, Copy gameCopy) {
		try {
			if(player.loanAllowed(gameCopy)) {
				String userInput = JOptionPane.showInputDialog(this, "Entrer le nombre de semaines pour l'emprunt", "Emprunt", JOptionPane.QUESTION_MESSAGE);
				if(userInput == null)
					//user cancel
					return;

				int weeks = Integer.parseInt(userInput);
				//LocalDate endDate = LocalDate.now().plusDays((weeks * 7) - 1); // start date + 6 days
				LocalDate endDate = LocalDate.now().plusWeeks(weeks); // not start date + 7 days
				Loan newLoan = new Loan(0, LocalDate.now(), endDate, true, gameCopy.getOwner(), player, gameCopy, 0);
				gameCopy.setCopyLoan(newLoan);

				if(gameCopy.borrow()) {
					JOptionPane.showMessageDialog(ConsultVideoGamesFrame.this, "Emprunt effectuée", "Emprunt", JOptionPane.INFORMATION_MESSAGE);
				}
				else {
					JOptionPane.showMessageDialog(ConsultVideoGamesFrame.this, "Erreur lors de l'emprunt", "Emprunt", JOptionPane.ERROR_MESSAGE);
				}
			}
			else {
				JOptionPane.showMessageDialog(ConsultVideoGamesFrame.this, "Vous n'avez pas assez de crédits pour le jeu\n\"" + selectedGame.getName() + "\"", "Emprunt", JOptionPane.ERROR_MESSAGE);
			}

		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "Veuillez entrer un nombre valide", "Emprunt", JOptionPane.ERROR_MESSAGE);
			return;
		}
		catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Impossible d'effectuer l'emprunt", "Emprunt", JOptionPane.ERROR_MESSAGE);
			return;
		}
	}

	private void bookingVideoGame(Player player) {
		try {
			String userInput = JOptionPane.showInputDialog(this, "Entrer le nombre de semaines pour le futur emprunt", "reservation", JOptionPane.QUESTION_MESSAGE);
			if(userInput == null)
				//user cancel
				return;

			int weeks = Integer.parseInt(userInput);
			Booking newBooking = new Booking(0, LocalDate.now(), selectedGame, player, weeks);
			if(player.createBooking(newBooking)) {
				JOptionPane.showMessageDialog(ConsultVideoGamesFrame.this, "Réservation effectuée", "Emprunt", JOptionPane.INFORMATION_MESSAGE);
			}
			else {
				JOptionPane.showMessageDialog(ConsultVideoGamesFrame.this, "Erreur lors de la réservation", "Emprunt", JOptionPane.ERROR_MESSAGE);
			}

		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "Veuillez entrer un nombre valide", "reservation", JOptionPane.ERROR_MESSAGE);
			return;
		}
		catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Impossible d'effectuer la réservation", "reservation", JOptionPane.ERROR_MESSAGE);
			return;
		}
	}

}
