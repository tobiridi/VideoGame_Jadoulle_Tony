package be.Jadoulle.Components;

import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;

import be.Jadoulle.POJO.Loan;

public class LoanTableModel extends DefaultTableModel {
	private static final long serialVersionUID = 7472995574905763847L;
	
	private ArrayList<Loan> loans;

	public LoanTableModel (ArrayList<Loan> loans) {
		this.loans = loans;
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}

	public Loan getLoanAt(int row) {
		return this.loans.get(row);
	}

}
