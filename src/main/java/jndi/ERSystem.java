package jndi;

import java.sql.Connection;
import java.sql.SQLException;

import org.mindrot.jbcrypt.BCrypt;

import DAO.Facade;
import DAO.ServiceLocator; 

public class ERSystem {

	public static void main(String[] args) throws SQLException {
		
		//establish a connection to the DB
		Connection conn = ServiceLocator.getERSdb().getConnection();
		
		System.out.println();
		
		//reimbDAO.insertReimbursement(r);
		System.out.println();
		
		Facade f = new Facade();
/*		f.selectUserByUsername("aaaaa");
		System.out.println("aaaa");
		
		f.getReimbursementsByUsername("bbbbb");
		System.out.println("bbbb");
		
		
		f.deleteReimbursement(109);
		f.deleteReimbursement(110);
		f.deleteReimbursement(111);
		
		
		System.out.println();
		
		f.updateReimbursement("approved", "FMAN", 104);
		//reimbDAO.updateReimbursement2(1, 1, 104);
		f.selectAllReimbursements();
		
		System.out.println("Getting by username");
		f.getReimbursementsByUsername("ddddd");*/
		
		//f.updateReimbursement("approved", resolver, id);
		
		//f.getReimbursementsByUsername("bbbbb");
		//System.out.println(f.isManager("aaaaa", "aaaaa"));
		
		/*
		f.updateUserPasswords("aaaaa");
		f.updateUserPasswords("bbbbb");
		f.updateUserPasswords("ccccc");
		f.updateUserPasswords("ddddd");
		f.updateUserPasswords("FMAN");
		*/
		
		System.out.println(f.returnCurrentUserPassword("aaaaa"));
		//System.out.println(f.returnCurrentUserPassword("aaaaa"));
		
		//System.out.println(BCrypt.checkpw("bbbbb", f.returnCurrentUserPassword("bbbbb")));
		if (BCrypt.checkpw("aaaaa", f.returnCurrentUserPassword("aaaaa"))){
			System.out.println("matches");
		} else System.out.println("NO!");
		
	}
}
