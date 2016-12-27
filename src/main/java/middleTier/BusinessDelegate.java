package middleTier;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.AuthenticationException;

import beans.Reimbursement;
import beans.User;

public class BusinessDelegate {

	public User login(String user, String pass) throws AuthenticationException, SQLException {
		
		return new UserService().authenticate(user, pass);
	}

	public ArrayList<Reimbursement> returnReimbursementsByUserName(String user) {
		ArrayList<Reimbursement> userReimbursements = new UserService().ownReimbursements(user);
		return userReimbursements;
	}

	/**
	 * Currently only returns pending statuses
	 * @return the list of pending statuses so the manager can see them
	 */
	public ArrayList<Reimbursement> returnReimbursementsByStatus() {
		ArrayList<Reimbursement> pendingReimbursements = new UserService().pendingReimbursements();
		return pendingReimbursements;
	}
	
	//TODO
	public boolean checkManagerialCredentials(String user) { 
		boolean isManager = new UserService().sendManagerialStatus(user);
		
		return isManager;
	}
	
	

	public ArrayList<Reimbursement> updateReimbursement(String id, String resolver, String statusTo) {
		int idOfRow = Integer.parseInt(id);
		return new UserService().updateReimbursement(statusTo, resolver, idOfRow);
	}

	public ArrayList<Reimbursement> insertReimbursement(String dollarAmount, String description, String author,
			String type) {
		double amount = Double.parseDouble(dollarAmount);
		int numAuthor = Integer.parseInt(author);
		int numType = Integer.parseInt(type);
		
		return new UserService().addReimb(amount, description, numAuthor, numType);
	} 
}
