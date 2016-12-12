package jndi;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import DAO.Facade;
import DAO.ReimbursementDao;
import DAO.ServiceLocator;
import beans.Reimbursement;

public class ERSystem {

	public static void main(String[] args) throws SQLException {
		
		//establish a connection to the DB
		Connection conn = ServiceLocator.getERSdb().getConnection();
		
		Authenticator auth = new Authenticator(conn);
		
		if (auth.autheticate("eeeee", "bbbbb")) {
			System.out.println("Authentication passed");
		} else {
			System.out.println("Authentication failed");
		}
		
		//Reimbursement r = new Reimbursement(201, 199.99, reimbDAO.getCurrentTimeStamp(), reimbDAO.getCurrentTimeStamp(), "Hotel room", 2, 1, 2, 2);
		System.out.println();
		
		//reimbDAO.insertReimbursement(r);
		System.out.println();
		
		Facade f = new Facade();
		f.selectUserByUsername("aaaaa");
		System.out.println("aaaa");
		
		f.getReimbursementsByUsername("bbbbb");
		System.out.println("bbbb");
		
		/*
		f.deleteReimbursement(109);
		f.deleteReimbursement(110);
		f.deleteReimbursement(111);
		*/
		
		System.out.println("Printing all reimbursements");
		System.out.println();
		
		f.updateReimbursement("approved", "FMAN", 104);
		//reimbDAO.updateReimbursement2(1, 1, 104);
		f.selectAllReimbursements();
	}
}
