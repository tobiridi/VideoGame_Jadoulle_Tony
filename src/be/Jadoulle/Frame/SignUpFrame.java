package be.Jadoulle.Frame;

import java.awt.EventQueue;
import java.awt.Font;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import be.Jadoulle.POJO.Player;
import be.Jadoulle.POJO.User;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SignUpFrame extends JFrame {

	private JPanel contentPane;
	private JTextField tfUsername;
	private JTextField tfPassword;
	private JTextField tfPseudo;
	private JSpinner spinnerDateOfBirth;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SignUpFrame frame = new SignUpFrame();
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
	public SignUpFrame() {
		setTitle("Inscription");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblTitle = new JLabel("Inscription");
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("Book Antiqua", Font.PLAIN, 18));
		lblTitle.setBounds(146, 11, 150, 20);
		contentPane.add(lblTitle);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setHorizontalAlignment(SwingConstants.LEFT);
		lblUsername.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		lblUsername.setBounds(43, 65, 140, 20);
		contentPane.add(lblUsername);
		
		JLabel lblMinCharUsername = new JLabel("*min 3 caractères");
		lblMinCharUsername.setHorizontalAlignment(SwingConstants.LEFT);
		lblMinCharUsername.setFont(new Font("Book Antiqua", Font.PLAIN, 12));
		lblMinCharUsername.setBounds(53, 85, 130, 14);
		contentPane.add(lblMinCharUsername);
		
		JLabel lblPassword = new JLabel("Mot de passe");
		lblPassword.setHorizontalAlignment(SwingConstants.LEFT);
		lblPassword.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		lblPassword.setBounds(43, 105, 140, 20);
		contentPane.add(lblPassword);
		
		JLabel lblMinCharPassword = new JLabel("*min 3 caractères");
		lblMinCharPassword.setHorizontalAlignment(SwingConstants.LEFT);
		lblMinCharPassword.setFont(new Font("Book Antiqua", Font.PLAIN, 12));
		lblMinCharPassword.setBounds(53, 125, 130, 14);
		contentPane.add(lblMinCharPassword);
		
		JLabel lblPseudo = new JLabel("Pseudo (optionel)");
		lblPseudo.setHorizontalAlignment(SwingConstants.LEFT);
		lblPseudo.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		lblPseudo.setBounds(43, 145, 140, 20);
		contentPane.add(lblPseudo);
		
		JLabel lblDateOfBirth = new JLabel("Date de naissance");
		lblDateOfBirth.setHorizontalAlignment(SwingConstants.LEFT);
		lblDateOfBirth.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		lblDateOfBirth.setBounds(43, 185, 140, 20);
		contentPane.add(lblDateOfBirth);
		
		JLabel lblInfoDateOfBirth = new JLabel("*Age min 16 ans");
		lblInfoDateOfBirth.setHorizontalAlignment(SwingConstants.LEFT);
		lblInfoDateOfBirth.setFont(new Font("Book Antiqua", Font.PLAIN, 12));
		lblInfoDateOfBirth.setBounds(53, 205, 130, 14);
		contentPane.add(lblInfoDateOfBirth);
		
		tfUsername = new JTextField();
		tfUsername.setBounds(195, 65, 115, 20);
		contentPane.add(tfUsername);
		tfUsername.setColumns(10);
		
		tfPassword = new JTextField();
		tfPassword.setColumns(10);
		tfPassword.setBounds(195, 105, 115, 20);
		contentPane.add(tfPassword);
		
		tfPseudo = new JTextField();
		tfPseudo.setColumns(10);
		tfPseudo.setBounds(195, 145, 115, 20);
		contentPane.add(tfPseudo);
		
		spinnerDateOfBirth = new JSpinner();
		spinnerDateOfBirth.setModel(new SpinnerDateModel());
		spinnerDateOfBirth.setEditor(new JSpinner.DateEditor(spinnerDateOfBirth, "dd/MM/yyyy"));
		spinnerDateOfBirth.setBounds(195, 185, 115, 25);
		spinnerDateOfBirth.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		contentPane.add(spinnerDateOfBirth);
				
		JButton btnCancel = new JButton("Annuler");
		btnCancel.setBounds(80, 256, 100, 25);
		btnCancel.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		btnCancel.addActionListener((ActionEvent e) -> {
			//cancel inscription
			IndexFrame index = new IndexFrame();
			index.setVisible(true);
			dispose();
		});
		contentPane.add(btnCancel);
		
		JButton btnConfirm = new JButton("Confirmer");
		btnConfirm.setBounds(210, 256, 120, 25);
		btnConfirm.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		btnConfirm.addActionListener((ActionEvent e) -> {
			if(isValidInscription()) {
				String username = tfUsername.getText();
				String password = tfPassword.getText();
				int credits = 10;
				String pseudo = tfPseudo.getText();
				Date tempDate = (Date) spinnerDateOfBirth.getValue();
				LocalDate dateOfBirth = LocalDate.ofInstant(tempDate.toInstant(), ZoneId.systemDefault());
				
				User newPlayer = new Player(0, username, password, credits, pseudo, LocalDate.now(), dateOfBirth);
				if(newPlayer instanceof Player player) {
					if (player.signUp()) {
						JOptionPane.showMessageDialog(SignUpFrame.this, "Compte créer avec succès", "Information",JOptionPane.INFORMATION_MESSAGE);
						// back to login Frame
						IndexFrame frame = new IndexFrame();
						frame.setVisible(true);
						dispose();
					}
					else {
						JOptionPane.showMessageDialog(SignUpFrame.this, "Le compte n'a pas pu être créer", "Erreur", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
			else {
				JOptionPane.showMessageDialog(SignUpFrame.this, "Un ou plusieurs champ(s) ne sont pas valide", "Erreur", JOptionPane.ERROR_MESSAGE);
			}
		});
		contentPane.add(btnConfirm);
	}
	
	private boolean isValidInscription() {
		//get a "java.util.Date"
		Date date = (Date) spinnerDateOfBirth.getValue();
		LocalDate dateOfBirth = LocalDate.ofInstant(date.toInstant(), ZoneId.systemDefault());
		
		//minimum age 16 years old
		LocalDate currentDate = LocalDate.now(); 
		LocalDate minDate = currentDate.minusYears(16).plusDays(1);
		//maximum age 100 years old
		LocalDate maxDate = currentDate.minusYears(100).minusDays(1);
		
		//username, password, dateOfBirth
		return !tfUsername.getText().isBlank() && tfUsername.getText().trim().length() >= 3
				&& !tfPassword.getText().isBlank() && tfPassword.getText().length() >= 3
				&& dateOfBirth.isBefore(minDate) && dateOfBirth.isAfter(maxDate);
	}
}
