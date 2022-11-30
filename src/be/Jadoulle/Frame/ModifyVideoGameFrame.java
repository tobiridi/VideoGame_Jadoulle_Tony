package be.Jadoulle.Frame;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.time.format.DateTimeFormatter;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import be.Jadoulle.POJO.Administrator;
import be.Jadoulle.POJO.VideoGame;

public class ModifyVideoGameFrame extends JFrame {

	private JPanel contentPane;
	private JTextField tfName;
	private JTextField tfConsole;
	private JTextField tfCredit;
	private JTextField tfRelease;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					ModifyVideoGameFrame frame = new ModifyVideoGameFrame(null, null);
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
	public ModifyVideoGameFrame(Administrator admin, VideoGame game) {
		setTitle("Modification du jeu");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblTitle = new JLabel("<< " + game.getName() + " " + game.getConsole() + " >>");
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("Book Antiqua", Font.PLAIN, 18));
		lblTitle.setBounds(40, 10, 350, 20);
		contentPane.add(lblTitle);
		
		JLabel lblName = new JLabel("Nom");
		lblName.setHorizontalAlignment(SwingConstants.LEFT);
		lblName.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		lblName.setBounds(60, 50, 120, 20);
		contentPane.add(lblName);
		
		JLabel lblMinCharName = new JLabel("*min 3 caractères");
		lblMinCharName.setHorizontalAlignment(SwingConstants.LEFT);
		lblMinCharName.setFont(new Font("Book Antiqua", Font.PLAIN, 12));
		lblMinCharName.setBounds(70, 70, 130, 14);
		contentPane.add(lblMinCharName);
		
		tfName = new JTextField();
		tfName.setColumns(10);
		tfName.setBounds(200, 50, 200, 20);
		tfName.setText(game.getName());
		contentPane.add(tfName);
		
		JLabel lblConsole = new JLabel("Console");
		lblConsole.setHorizontalAlignment(SwingConstants.LEFT);
		lblConsole.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		lblConsole.setBounds(60, 130, 120, 20);
		contentPane.add(lblConsole);
		
		tfConsole = new JTextField();
		tfConsole.setColumns(10);
		tfConsole.setBounds(200, 130, 100, 20);
		tfConsole.setText(game.getConsole());
		contentPane.add(tfConsole);
		
		JLabel lblCreditcost = new JLabel("Coût en crédits");
		lblCreditcost.setHorizontalAlignment(SwingConstants.LEFT);
		lblCreditcost.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		lblCreditcost.setBounds(60, 90, 120, 20);
		contentPane.add(lblCreditcost);
		
		tfCredit = new JTextField();
		tfCredit.setColumns(10);
		tfCredit.setBounds(200, 90, 70, 20);
		tfCredit.setText("" + game.getCreditCost());
		contentPane.add(tfCredit);
		
		JLabel lblReleaseDate = new JLabel("Date de sortie");
		lblReleaseDate.setHorizontalAlignment(SwingConstants.LEFT);
		lblReleaseDate.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		lblReleaseDate.setBounds(60, 170, 120, 20);
		contentPane.add(lblReleaseDate);
		
		tfRelease = new JTextField();
		tfRelease.setColumns(10);
		tfRelease.setBounds(200, 170, 120, 20);
		tfRelease.setText(game.getReleaseDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		tfRelease.setEnabled(false);
		contentPane.add(tfRelease);
		
		
		JButton btnBack = new JButton("Retour");
		btnBack.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		btnBack.setBounds(70, 210, 110, 25);
		btnBack.addActionListener((ActionEvent e) -> {
			// back to edit video game frame
			EditVideoGameFrame frame = new EditVideoGameFrame(admin);
			frame.setVisible(true);
			dispose();
		});
		contentPane.add(btnBack);
		
		JButton btnConfirm = new JButton("Confirmer");
		btnConfirm.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		btnConfirm.setBounds(200, 210, 120, 25);
		btnConfirm.addActionListener((ActionEvent e) -> {
			if(isFormValid()) {
				String name = tfName.getText();
				String console = tfConsole.getText();
				int credit = Integer.parseInt(tfCredit.getText());
				
				if(admin.updateVideoGame(game, name, console, credit)) {
					JOptionPane.showMessageDialog(ModifyVideoGameFrame.this, "Jeu modifier avec succès", "Information", JOptionPane.INFORMATION_MESSAGE);
					EditVideoGameFrame editFrame = new EditVideoGameFrame(admin);
					editFrame.setVisible(true);
					dispose();
				}
				else {
					JOptionPane.showMessageDialog(ModifyVideoGameFrame.this, "Le jeu n'a pas pu être modifier", "Modifier jeu", JOptionPane.ERROR_MESSAGE);
				}
			}
			else {
				JOptionPane.showMessageDialog(ModifyVideoGameFrame.this, "Un ou plusieurs champ(s) ne sont pas valide", "Modifier jeu", JOptionPane.ERROR_MESSAGE);
			}
		});
		contentPane.add(btnConfirm);
	}
	
	//methods
	private boolean isFormValid() {
		try {
			int creditCost = Integer.parseInt(this.tfCredit.getText());

			// name, credit cost, console
			return this.tfName.getText().trim().length() >= 3 
					&& creditCost > 0 && !this.tfConsole.getText().isBlank();

		} catch (NumberFormatException e) {
			// credit cost is not a number
			return false;
		} catch (Exception e) {
			return false;
		}
	}
	
}
