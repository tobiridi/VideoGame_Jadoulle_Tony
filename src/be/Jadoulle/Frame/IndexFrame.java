package be.Jadoulle.Frame;

import java.awt.EventQueue;
import java.time.LocalDate;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import be.Jadoulle.POJO.Administrator;
import be.Jadoulle.POJO.Player;
import be.Jadoulle.POJO.User;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Frame;

import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class IndexFrame extends JFrame {

	private JPanel contentPane;
	private JTextField tfUsername;
	private JPasswordField pfPassword;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					IndexFrame frame = new IndexFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * 
	 */
	public IndexFrame() {
		setTitle("Connexion");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblTitle = new JLabel("Video Game");
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("Book Antiqua", Font.PLAIN, 18));
		lblTitle.setBounds(150, 11, 150, 20);
		contentPane.add(lblTitle);
		
		tfUsername = new JTextField();
		tfUsername.setBounds(150, 80, 150, 20);
		contentPane.add(tfUsername);
		tfUsername.setColumns(10);
		
		pfPassword = new JPasswordField();
		pfPassword.setBounds(150, 140, 150, 20);
		contentPane.add(pfPassword);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setHorizontalAlignment(SwingConstants.CENTER);
		lblUsername.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		lblUsername.setBounds(150, 50, 150, 20);
		contentPane.add(lblUsername);
		
		JLabel lblPassword = new JLabel("Mot de passe");
		lblPassword.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		lblPassword.setHorizontalAlignment(SwingConstants.CENTER);
		lblPassword.setBounds(150, 110, 150, 20);
		contentPane.add(lblPassword);
		
		JButton btnConnection = new JButton("Se connecter");
		btnConnection.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		btnConnection.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// attempt to connect and go to Main frame of the user type
				String username = tfUsername.getText();
				String password = String.valueOf(pfPassword.getPassword());
				User connectedUser = User.login(username, password);
				if(connectedUser != null) {
					JOptionPane.showMessageDialog(IndexFrame.this, "Bienvenue " + connectedUser.getUsername(), "Information",JOptionPane.INFORMATION_MESSAGE);
					// go to main frame
					Frame mainFrame = null;
					if (connectedUser instanceof Player player) {
						mainFrame = new PlayerMainFrame(player);
						
					} else if (connectedUser instanceof Administrator admin) {
						mainFrame = new AdminMainFrame(admin);
					}
					
					mainFrame.setVisible(true);
					dispose();
				}
				else {
					JOptionPane.showMessageDialog(IndexFrame.this, "Username et/ou mot de passe incorrect", "Erreur",JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnConnection.setBounds(160, 170, 130, 20);
		contentPane.add(btnConnection);
		
		JButton btnSignUp = new JButton("S'inscrire");
		btnSignUp.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		btnSignUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//go to sign up Frame
				SignUpFrame frame = new SignUpFrame();
				frame.setVisible(true);
				dispose();
			}
		});
		btnSignUp.setBounds(165, 200, 120, 20);
		contentPane.add(btnSignUp);
	}
}
