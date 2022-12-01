package be.Jadoulle.Frame;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import be.Jadoulle.Components.LoanTableModel;
import be.Jadoulle.POJO.Loan;
import be.Jadoulle.POJO.Player;

public class ConsultLoanLenderFrame extends JFrame {

	private JPanel contentPane;
	private Player player;
	private JTable table;
	private Loan selectedLoan;
	private int selectedRow;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ConsultLoanLenderFrame frame = new ConsultLoanLenderFrame(null);
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
	public ConsultLoanLenderFrame(Player player) {
		this.player = player;
		
		setTitle("Vos jeux empruntés");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblTitle = new JLabel("Jeux emprunter");
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("Book Antiqua", Font.PLAIN, 18));
		lblTitle.setBounds(130, 10, 190, 20);
		contentPane.add(lblTitle);

		JScrollPane scrollPane = new JScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBounds(30, 39, 360, 150);
		contentPane.add(scrollPane);

		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				selectedRow = table.getSelectedRow();
				LoanTableModel model = (LoanTableModel) table.getModel();
				selectedLoan = model.getLoanAt(selectedRow);
				System.out.println("loan : " + selectedLoan.getId());
			}
		});
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		scrollPane.setViewportView(table);

		JButton btnBack = new JButton("Retour");
		btnBack.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		btnBack.setBounds(30, 200, 110, 25);
		btnBack.addActionListener((ActionEvent e) -> {
			// back to player main frame
			PlayerMainFrame mainFrame = new PlayerMainFrame(player);
			mainFrame.setVisible(true);
			dispose();
		});
		contentPane.add(btnBack);

		JButton btnEndLoan = new JButton("Confirmer la réception");
		btnEndLoan.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		btnEndLoan.setBounds(200, 200, 200, 25);
		btnEndLoan.addActionListener((ActionEvent e) -> {
			if(selectedLoan != null) {
				//display confirm message
				String confirmMessage = "Êtes-vous sur de vouloir mettre le jeu\n"
				+ selectedLoan.getCopy().getVideoGame().getName() + " " + selectedLoan.getCopy().getVideoGame().getConsole()
				+ "\ncomme rendu ?";
				
				int validation = JOptionPane.showConfirmDialog(ConsultLoanLenderFrame.this, confirmMessage, "Vos Emprunt", JOptionPane.YES_NO_OPTION);
				if(validation == 0) {
					if(player.getLoansLender().get(selectedRow).endLoan()) {
						JOptionPane.showMessageDialog(ConsultLoanLenderFrame.this, "L'emprunt vous a bien été rendu", "Vos Emprunt", JOptionPane.INFORMATION_MESSAGE);
						ConsultLoanLenderFrame refreshFrame = new ConsultLoanLenderFrame(player);
						refreshFrame.setVisible(true);
						dispose();
					}
					else {
						if(player.getLoansLender().get(selectedRow).getEndDate().isAfter(LocalDate.now())) {
							JOptionPane.showMessageDialog(ConsultLoanLenderFrame.this, "La date de fin n'est pas encore arrivée", "Erreur", JOptionPane.ERROR_MESSAGE);
						}
						else {
							JOptionPane.showMessageDialog(ConsultLoanLenderFrame.this, "L'emprunt n'a pas pu être modifier", "Erreur", JOptionPane.ERROR_MESSAGE);
						}
					}
				}				
			}
			else {
				JOptionPane.showMessageDialog(ConsultLoanLenderFrame.this, "Aucun emprunt n'a été sélectionner", "Erreur", JOptionPane.ERROR_MESSAGE);
			}
		});
		contentPane.add(btnEndLoan);
		
		//parameter the JTable model
		this.tableModelChange();
	}

	private void tableModelChange() {
		LoanTableModel customModel = new LoanTableModel(this.player.getLoansLender());

		//column title
		String[] identifiersCol = {"jeu emprunter", "console", "Date de début", "Date de fin", "emprunteur", "Status", "Jours de retard"};
		customModel.setColumnIdentifiers(identifiersCol);

		//data of each row
		for (Loan loan : this.player.getLoansLender()) {
			String startDate = loan.getStartDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
			String endDate = loan.getEndDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
			String status = loan.isOnGoing() ? "en prêt" : "rendu";
			
			String[] loanData = {
					loan.getCopy().getVideoGame().getName(), loan.getCopy().getVideoGame().getConsole(), 
					startDate, endDate, 
					loan.getBorrower().getUsername(), status,
					String.valueOf(loan.getLateDays()),
					};
			
			customModel.addRow(loanData);
		}

		table.setModel(customModel);
		//first column is the 1/3 of total column width
		int prefWidth = table.getColumnModel().getTotalColumnWidth() * 1 / 3;
		table.getColumnModel().getColumn(0).setPreferredWidth(prefWidth);
	}

}
