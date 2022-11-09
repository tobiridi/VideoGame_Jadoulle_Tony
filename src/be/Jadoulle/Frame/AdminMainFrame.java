package be.Jadoulle.Frame;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import be.Jadoulle.POJO.Administrator;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.SwingConstants;

public class AdminMainFrame extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminMainFrame frame = new AdminMainFrame(null);
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
	public AdminMainFrame(Administrator admin) {
		setTitle("Admin Accueil");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
		
		JButton btnAddVideoGame = new JButton("<html>Encoder un jeu vidéo</html>");
		btnAddVideoGame.setVerticalAlignment(SwingConstants.TOP);
		btnAddVideoGame.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		btnAddVideoGame.setBounds(150, 50, 120, 50);
		btnAddVideoGame.addActionListener((ActionEvent e) -> {
			AddVideoGameFrame frame = new AddVideoGameFrame(admin);
			frame.setVisible(true);
			dispose();
		});
		contentPane.add(btnAddVideoGame);
		
		JButton btnEditVideoGames = new JButton("<html>Modifier les crédits des jeux</html>");
		btnEditVideoGames.setVerticalAlignment(SwingConstants.TOP);
		btnEditVideoGames.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		btnEditVideoGames.setBounds(130, 120, 160, 50);
		contentPane.add(btnEditVideoGames);
		
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
