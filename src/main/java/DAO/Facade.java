package DAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import beans.Reimbursement;
import beans.User;

public class Facade {
	public Connection getConnection() throws SQLException {
		Connection conn = ServiceLocator.getERSdb().getConnection();
		return conn;
	}
	
	public void closeConnection(Connection conn) {
		try {
			conn.close();
		} catch(SQLException e) {
			System.out.println("Connection could not be closed");
			e.printStackTrace();
		}
	}
	
	//Call the reimbursement DAO functions
	/**
	 * 
	 * @return an ArrayList of Reimbursement objects
	 * @throws SQLException
	 * Alternate signature: public void
	 */
	public ArrayList<Reimbursement> selectAllReimbursements() throws SQLException {
		ReimbursementDao rDAO = makeReimbDaoAndGetConnection();
		
		//rDAO.selectAll();
		
		ArrayList<Reimbursement> allReimbursements = rDAO.selectAll();

		return allReimbursements;
		
	}
	
	public ArrayList<Reimbursement> selectReimbursementByID(int id) throws SQLException {
		ReimbursementDao rDAO = makeReimbDaoAndGetConnection();
		
		//rDAO.selectByID(id);
		ArrayList<Reimbursement> selectedByID = rDAO.selectByID(id);
		return selectedByID;
	}
	
	public ArrayList<Reimbursement> selectReimbursementByStatus(int status) throws SQLException {
		ReimbursementDao rDAO = makeReimbDaoAndGetConnection();
		
		//rDAO.selectByStatus(status);
		ArrayList<Reimbursement> selectedByStatus = rDAO.selectByStatus(status);
		return selectedByStatus;
	}
	
	public void insertReimbursement(Reimbursement r) throws SQLException {
		ReimbursementDao rDAO = makeReimbDaoAndGetConnection();
		
		rDAO.insertReimbursement(r);
	}
	
	public void deleteReimbursement(int id) throws SQLException {
		ReimbursementDao rDAO = makeReimbDaoAndGetConnection();
		
		rDAO.deleteReimbursement(id);
	}
	
	public void updateReimbursement(String status, String resolver, int id) throws SQLException {
		ReimbursementDao rDAO = makeReimbDaoAndGetConnection();
		
		rDAO.updateReimbursement(status, resolver, id);
	}
	
	/**
	 * 
	 * @param author 
	 * @return The reimbursements for the specified author
	 * @throws SQLException
	 */
	public ArrayList<Reimbursement> getReimbursementsByUsername(String username) throws SQLException {
		ReimbursementDao rDAO = makeReimbDaoAndGetConnection();
		
		//rDAO.selectReimbursementsByAuthor(author);
		
		ArrayList<Reimbursement> userReimbursements = rDAO.selectReimbursementsByUsername(username);
		return userReimbursements;
	}
	

	
	//call the user DAO functions
	/**
	 * 
	 * @param user Take in a username and return the information of said user
	 * @throws SQLException
	 * Alternate signature 'public void'
	 */
	public User selectUserByUsername(String user) throws SQLException {
		UserDao uDAO = new UserDao(getConnection());
		
		//uDAO.getByUsername(user);
		
		return uDAO.getByUsername(user);
	}	
	
	/**
	 * 
	 * @return a UserDao object
	 * Sends a UserDao object with an established connection to the caller
	 * @throws SQLException
	 */
	public UserDao makeUserDaoAndGetConnection() throws SQLException {
		UserDao uDAO = new UserDao(getConnection());
		
		return uDAO;
	}
	
	/**
	 * 
	 * @return a ReimbursementDao object with an established connection to the caller
	 * @throws SQLException
	 */
	public ReimbursementDao makeReimbDaoAndGetConnection() throws SQLException {
		ReimbursementDao rDAO = new ReimbursementDao(getConnection());
		
		return rDAO;
	}
}

