package be.Jadoulle.Frame;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import be.Jadoulle.POJO.Player;

public class PlayerMainFrame extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					PlayerMainFrame frame = new PlayerMainFrame(null);
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
	public PlayerMainFrame(Player player) {
		setTitle("Joueur Accueil");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblTitle = new JLabel("Menu principal");
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("Book Antiqua", Font.PLAIN, 18));
		lblTitle.setBounds(130, 10, 150, 20);
		contentPane.add(lblTitle);

		JLabel lblUserCredits = new JLabel("Vous avez : " + player.getCredits() + " crédits");
		lblUserCredits.setHorizontalAlignment(SwingConstants.RIGHT);
		//change foreground depend of player credits
		if(player.getCredits() <= 0) {
			lblUserCredits.setForeground(new Color(215, 31, 21));
		}
		else {
			lblUserCredits.setForeground(new Color(37, 158, 47));
		}
		lblUserCredits.setFont(new Font("Book Antiqua", Font.BOLD, 16));
		lblUserCredits.setBounds(250, 45, 170, 20);
		contentPane.add(lblUserCredits);

		JButton btnConsultGames = new JButton("<html>Consulter les jeux vidéos</html>");
		btnConsultGames.setVerticalAlignment(SwingConstants.TOP);
		btnConsultGames.setBounds(38, 75, 150, 50);
		btnConsultGames.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		btnConsultGames.addActionListener((ActionEvent e) -> {
			//display message before consult video game frame
			if(!player.hasCredits()) {
				JOptionPane.showMessageDialog(PlayerMainFrame.this, "Vous n'avez pas assez de crédits pour louer un jeu", "Copie", JOptionPane.WARNING_MESSAGE);
			}
			ConsultVideoGamesFrame frame = new ConsultVideoGamesFrame(player);
			frame.setVisible(true);
			dispose();
		});
		contentPane.add(btnConsultGames);

		JButton btnConsultLoans = new JButton("<html>Consulter vos jeux empruntés</html>");
		btnConsultLoans.setVerticalAlignment(SwingConstants.TOP);
		btnConsultLoans.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		btnConsultLoans.setBounds(38, 135, 150, 50);
		btnConsultLoans.addActionListener((ActionEvent e) -> {
			ConsultLoanLenderFrame frame = new ConsultLoanLenderFrame(player);
			frame.setVisible(true);
			dispose();
		});
		contentPane.add(btnConsultLoans);

		JButton btnAddCopy = new JButton("<html>Prêter un jeu vidéo</html>");
		btnAddCopy.addActionListener((ActionEvent e) -> {
			AddCopyVideoGameFrame frame = new AddCopyVideoGameFrame(player);
			frame.setVisible(true);
			dispose();
		});
		btnAddCopy.setVerticalAlignment(SwingConstants.TOP);
		btnAddCopy.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		btnAddCopy.setBounds(38, 195, 150, 50);
		contentPane.add(btnAddCopy);

		JButton btnDisconnect = new JButton("Déconnexion");
		btnDisconnect.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		btnDisconnect.setBounds(290, 10, 130, 20);
		btnDisconnect.addActionListener((ActionEvent e) -> {
			IndexFrame frame = new IndexFrame();
			frame.setVisible(true);
			dispose();
		});
		contentPane.add(btnDisconnect);
	}
}
