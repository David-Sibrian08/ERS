package middleTier;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.AuthenticationException;

import org.mindrot.jbcrypt.BCrypt;

import DAO.Facade;
import beans.Reimbursement;
import beans.User;

public class UserService {

	public User authenticate(String username, String password) throws AuthenticationException, SQLException{
		Facade facade = new Facade();
		User user = facade.selectUserByUsername(username);
		String hashBasedPassword = facade.returnCurrentUserPassword(username);
		System.out.println(hashBasedPassword);
		if(user == null) throw new AuthenticationException();
		else if ((BCrypt.checkpw(password, hashBasedPassword))) {
			System.out.println("Password match" + hashBasedPassword);
			return user;
		} else {
			throw new AuthenticationException();
		}
	}
	
	public ArrayList<Reimbursement> ownReimbursements(String username) {
		Facade facade = new Facade();
		ArrayList<Reimbursement>reimbursementList = facade.getReimbursementsByUsername(username);
		return reimbursementList;
	}

	public ArrayList<Reimbursement> pendingReimbursements() {
		Facade facade =new Facade();
		ArrayList<Reimbursement> pendingList = facade.getPendingReimbursements();
		return pendingList;
	}
	
	public boolean sendManagerialStatus(String user) { 
		Facade facade = new Facade();
		return facade.isManager(user);
		
	}

	public ArrayList<Reimbursement> updateReimbursement(String statusTo, String resolver, int idOfRow) {
		Facade facade = new Facade();
		facade.updateReimbursement(statusTo, resolver, idOfRow);
		return facade.getPendingReimbursements();		//return the refreshed list of pending 
	}

	public ArrayList<Reimbursement> addReimb(double amount, String description, int numAuthor, int numType) {
		Facade facade = new Facade();
		//to add a reimbursement you need to MAKE a reimbursement
		Reimbursement reimbursementToInsert = facade.insertNewReimbursement(amount, description, numAuthor, numType);
		return facade.getReimbursementsByUsername(reimbursementToInsert.getAuthor().getUserName());
	}
}
