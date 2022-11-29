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
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import be.Jadoulle.Components.VideoGameTableModel;
import be.Jadoulle.POJO.Administrator;
import be.Jadoulle.POJO.User;
import be.Jadoulle.POJO.VideoGame;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;

public class EditVideoGameFrame extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private VideoGame selectedGame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EditVideoGameFrame frame = new EditVideoGameFrame(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public EditVideoGameFrame(Administrator admin) {
		setTitle("Edit jeux");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblTitle = new JLabel("Modification des jeux");
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("Book Antiqua", Font.PLAIN, 18));
		lblTitle.setBounds(130, 10, 190, 20);
		contentPane.add(lblTitle);
		
		JScrollPane scrollPane = new JScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBounds(30, 39, 360, 150);
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
		
		JButton btnBack = new JButton("Retour");
		btnBack.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		btnBack.setBounds(30, 200, 110, 25);
		btnBack.addActionListener((ActionEvent e) -> {
			// back to admin main frame
			AdminMainFrame frame = new AdminMainFrame(admin);
			frame.setVisible(true);
			dispose();
		});
		contentPane.add(btnBack);
		
		JButton btnModify = new JButton("Modifier");
		btnModify.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		btnModify.setBounds(150, 200, 110, 25);
		btnModify.addActionListener((ActionEvent e) -> {
			//TODO : not implemented
//			if(selectedGame != null) {
//				System.out.println(selectedGame.getName() + " " + selectedGame.getConsole());
//			}
		});
		contentPane.add(btnModify);
		
		JButton btnDelete = new JButton("Supprimer");
		btnDelete.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		btnDelete.setBounds(270, 200, 120, 25);
		btnDelete.addActionListener((ActionEvent e) -> {
			if(selectedGame != null) {
				int res = JOptionPane.showConfirmDialog(EditVideoGameFrame.this, "Voulez-vous vraiment supprimer le jeu\n"
						  + selectedGame.getName() + " " + selectedGame.getConsole() + " ?", "Edit jeux", JOptionPane.YES_NO_OPTION);
				
				if(res == 0) {
					if(admin.deleteVideoGame(selectedGame)) {
						JOptionPane.showMessageDialog(EditVideoGameFrame.this, "le jeu a bien été supprimer", "Edit jeux", JOptionPane.INFORMATION_MESSAGE);
						EditVideoGameFrame refreshFrame = new EditVideoGameFrame(admin);
						refreshFrame.setVisible(true);
						dispose();
					}
					else {
						JOptionPane.showMessageDialog(EditVideoGameFrame.this, "Erreur lors de la suppression", "Edit jeux", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
			else {
				JOptionPane.showMessageDialog(EditVideoGameFrame.this, "Aucun jeu sélectionner", "Edit jeux", JOptionPane.ERROR_MESSAGE);
			}
		});
		contentPane.add(btnDelete);
		
	}
	
	private void tableModelChange() {
		ArrayList<VideoGame> videoGames = VideoGame.getAll();
		VideoGameTableModel customModel = new VideoGameTableModel(videoGames);
		
		//column title
		String[] identifiersCol = {"Nom du jeu", "Console", "Coût en crédits", "Date de Sortie"};
		customModel.setColumnIdentifiers(identifiersCol);
		
		//data of each row
		for (VideoGame game : videoGames) {
			String[] gameData = {game.getName(), game.getConsole(), "" + game.getCreditCost(), game.getReleaseDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))};
			customModel.addRow(gameData);
		}
		
		table.setModel(customModel);
		//first column is the 2/3 of total column width
		int prefWidth = table.getColumnModel().getTotalColumnWidth() * 2 / 3;
		table.getColumnModel().getColumn(0).setPreferredWidth(prefWidth);
	}
}
