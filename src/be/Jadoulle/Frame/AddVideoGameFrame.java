package be.Jadoulle.Frame;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import be.Jadoulle.POJO.Administrator;
import be.Jadoulle.POJO.VideoGame;

import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.JSpinner;
import javax.swing.JButton;

public class AddVideoGameFrame extends JFrame {
	private Administrator admin;
	private JPanel contentPane;
	private JTextField tfName;
	private JTextField tfCreditCost;
	private JTextField tfConsole;
	private JSpinner spinnerReleaseDate;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddVideoGameFrame frame = new AddVideoGameFrame(null);
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
	public AddVideoGameFrame(Administrator admin) {
		this.admin = admin;
		setTitle("Ajouter un jeu vidéo");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblTitle = new JLabel("Ajouter un jeu");
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("Book Antiqua", Font.PLAIN, 18));
		lblTitle.setBounds(130, 10, 150, 20);
		contentPane.add(lblTitle);
		
		JLabel lblName = new JLabel("Nom");
		lblName.setHorizontalAlignment(SwingConstants.LEFT);
		lblName.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		lblName.setBounds(59, 51, 140, 20);
		contentPane.add(lblName);
		
		tfName = new JTextField();
		tfName.setColumns(10);
		tfName.setBounds(211, 51, 115, 20);
		contentPane.add(tfName);
		
		JLabel lblCreditcost = new JLabel("Coût en crédits");
		lblCreditcost.setHorizontalAlignment(SwingConstants.LEFT);
		lblCreditcost.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		lblCreditcost.setBounds(59, 88, 140, 20);
		contentPane.add(lblCreditcost);
		
		tfCreditCost = new JTextField();
		tfCreditCost.setColumns(10);
		tfCreditCost.setBounds(211, 88, 115, 20);
		contentPane.add(tfCreditCost);
		
		JLabel lblConsole = new JLabel("Console");
		lblConsole.setHorizontalAlignment(SwingConstants.LEFT);
		lblConsole.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		lblConsole.setBounds(59, 118, 140, 20);
		contentPane.add(lblConsole);
		
		tfConsole = new JTextField();
		tfConsole.setColumns(10);
		tfConsole.setBounds(211, 118, 115, 20);
		contentPane.add(tfConsole);
		
		JLabel lblReleaseDate = new JLabel("Date de sortie");
		lblReleaseDate.setHorizontalAlignment(SwingConstants.LEFT);
		lblReleaseDate.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		lblReleaseDate.setBounds(59, 149, 140, 20);
		contentPane.add(lblReleaseDate);
		
		spinnerReleaseDate = new JSpinner();
		spinnerReleaseDate.setModel(new SpinnerDateModel());
		spinnerReleaseDate.setEditor(new JSpinner.DateEditor(spinnerReleaseDate, "dd/MM/yyyy"));
		spinnerReleaseDate.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		spinnerReleaseDate.setBounds(211, 149, 115, 25);
		contentPane.add(spinnerReleaseDate);
		
		JButton btnCancel = new JButton("Annuler");
		btnCancel.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		btnCancel.setBounds(69, 185, 100, 25);
		btnCancel.addActionListener((ActionEvent e) -> {
			// back to admin main frame
			AdminMainFrame frame = new AdminMainFrame(this.admin);
			frame.setVisible(true);
			dispose();
		});
		contentPane.add(btnCancel);
		
		JButton btnConfirm = new JButton("Confirmer");
		btnConfirm.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		btnConfirm.setBounds(199, 185, 120, 25);
		btnConfirm.addActionListener((ActionEvent e) -> {
			if(isFormValid()) {
				//create the video game
				String name = tfName.getText();
				int creditCost = Integer.parseInt(tfCreditCost.getText());
				String console = tfConsole.getText();
				Date release = (Date) spinnerReleaseDate.getValue();
				LocalDate releaseDate = LocalDate.ofInstant(release.toInstant(), ZoneId.systemDefault());
				
				VideoGame game = new VideoGame(0, name, creditCost, console, releaseDate);
				if(this.admin.createVideoGame(game)) {
					JOptionPane.showMessageDialog(AddVideoGameFrame.this, "Jeu créer avec succès", "Information",JOptionPane.INFORMATION_MESSAGE);
					// back to admin main frame
					AdminMainFrame frame = new AdminMainFrame(this.admin);
					frame.setVisible(true);
					dispose();
				}
				else {
					JOptionPane.showMessageDialog(AddVideoGameFrame.this, "Le jeu n'a pas pu être créer", "Erreur", JOptionPane.ERROR_MESSAGE);
				}
			}
			else {
				JOptionPane.showMessageDialog(AddVideoGameFrame.this, "Un ou plusieurs champ(s) ne sont pas valide", "Erreur", JOptionPane.ERROR_MESSAGE);
			}
		});
		contentPane.add(btnConfirm);
	}
	
	private boolean isFormValid() {
		try {
			int creditCost = Integer.parseInt(tfCreditCost.getText());
			Date d = (Date) spinnerReleaseDate.getValue();
			LocalDate release = LocalDate.ofInstant(d.toInstant(), ZoneId.systemDefault());
			
			//name, credit cost, release date, console
			return tfName.getText().trim().length() >= 3 && creditCost > 0
					&& release.isBefore(LocalDate.now().plusDays(1))
					&& !tfConsole.getText().isBlank();
			
		} catch (NumberFormatException e) {
			//credit cost is not a number
			return false;
		} catch (Exception e) {
			return false;
		}
	}
}
