package DAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import beans.Reimbursement;
import DAO.ReimbursementDao;
import beans.ReimbursementStatus;
import DAO.ReimbursementStatusDao;
import beans.ReimbursementType;
import beans.User;

public class Facade {
	public Connection getConnection() throws SQLException {
		Connection conn = ServiceLocator.getERSdb().getConnection();
		return conn;
	}
	
	//Call the reimbursement DAO functions
	/**
	 * @return an ArrayList of Reimbursement objects
	 * @throws SQLException
	 * Alternate signature: public void
	 */
	public ArrayList<Reimbursement> selectAllReimbursements() {
		ReimbursementDao rDAO = makeReimbDaoAndGetConnection();
		System.out.println("opened a connection");
		
		ArrayList<Reimbursement> allReimbursements = null;
		try {
			allReimbursements = rDAO.selectAll();
		} catch (SQLException e) {
			System.out.println("Could not select all the reimbursements");
			e.printStackTrace();
		}
		try {
			System.out.println("closed a connection");
			rDAO.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return allReimbursements;
	}
	
	/**
	 * @param id the id of the reimbursement that will be fetched
	 * @return the list of the reimbursements that are tied to that ID
	 */
	public ArrayList<Reimbursement> selectReimbursementByID(int id) {
		ReimbursementDao rDAO = makeReimbDaoAndGetConnection();
		System.out.println("opened a connection");
		
		//rDAO.selectByID(id);
		ArrayList<Reimbursement> selectedByID = null;
		try {
			selectedByID = rDAO.selectByID(id);
		} catch (SQLException e) {
			System.out.println("Could not get the reimbursements by their ID");
			e.printStackTrace();
		}
		try {
			System.out.println("closed a connection");
			rDAO.close();  // conn.close()
		} catch (Exception e) {	
			e.printStackTrace();
		}
		return selectedByID;
	}
	
	/**
	 * @param status The id of the status being fetched.
	 * @return the list of reimbursement statuses fetched via their ID, 
	 * not their actual status.
	 */
	public ArrayList<Reimbursement> selectReimbursementByStatus(int status) {
		ReimbursementDao rDAO = makeReimbDaoAndGetConnection();
		System.out.println("Opened a connection");
		
		//rDAO.selectByStatus(status);
		ArrayList<Reimbursement> selectedByStatus = null;
		try {
			selectedByStatus = rDAO.selectByStatus(status);
		} catch (SQLException e) {
			System.out.println("Could not get the reimbursements by status");
			e.printStackTrace();
		}
		try {
			rDAO.close();  // conn.close()
			System.out.println("closed a connection");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return selectedByStatus;
	}
	
	/**
	 * Insert the Reimbursement object into the DB. 
	 * @param r The reimbursement object to insert. 
	 * @deprecated use {@link #insertNewReimbursement(double, String, int, int)} instead
	 */
	public void insertReimbursement(Reimbursement r) {
		ReimbursementDao rDAO = makeReimbDaoAndGetConnection();
		System.out.println("opened a connection");
		
		try {
			rDAO.insertReimbursement(r);
		} catch (SQLException e) {
			System.out.println("Could not insert the reimbursement");
			e.printStackTrace();
		}
		
		try {
			System.out.println("closed a connection");
			rDAO.close();  //conn.close()
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * The submit reimbursement button will take these parameters in.
	 * No other parameters are necessary as they will be added automatically.
	 * @param amount The amount of the reimbursement 
	 * @param description The description of the reimbursement (optional)
	 * @param numAuthor The numerical id of the author
	 * @param numType The numerical id of the type 
	 * @return The reimbursement to insert 
	 */
	public Reimbursement insertNewReimbursement(double amount, String description, int numAuthor, int numType) {
		ReimbursementDao rDAO = makeReimbDaoAndGetConnection();
		ReimbursementStatusDao rsDAO = makeReimbStatusDaoAndGetConnection();
		
		User rAuthor = null;
		ReimbursementType rType = makeNewType(numType);
		
		ReimbursementStatus rStatus = null;
		Reimbursement reimbursementToInsert = null;
		
		try {
			rAuthor = makeNewUser(numAuthor);
			rStatus = rsDAO.makeNewStatus(2);
			
			reimbursementToInsert = 
					new Reimbursement(rDAO.getTheNextID(), amount, ReimbursementDao.getCurrentTimeStamp(), ReimbursementDao.getCurrentTimeStamp(), description, rAuthor, null, rStatus, rType); 
			rDAO.insertReimbursement(reimbursementToInsert);	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			System.out.println("closed a connection");
			rDAO.close();  // conn.close()
			rsDAO.close();
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return reimbursementToInsert;
	}
	
	/**
	 * @deprecated
	 */
	public void deleteReimbursement(int id) throws SQLException {
		ReimbursementDao rDAO = makeReimbDaoAndGetConnection();
		System.out.println("opened a connection");
		
		rDAO.deleteReimbursement(id);
		
		try {
			System.out.println("closed a connection");
			rDAO.close();  // conn.close()
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param status Either 'approve' or 'deny'
	 * @param resolver The manager who was logged in when the reimbursement was resolved
	 * @param id The id of the reimbursement that was resolved
	 */
	public void updateReimbursement(String status, String resolver, int id) {
		ReimbursementDao rDAO = makeReimbDaoAndGetConnection();
		System.out.println("opened a connection");
		
		try {
			rDAO.updateReimbursement(status, resolver, id);
		} catch (SQLException e) {
			System.out.println("Reimbursement could not be updated");
			e.printStackTrace();
		}
		try {
			System.out.println("closed a connection");
			rDAO.close();  // conn.close()
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * @param author 
	 * @return The reimbursements for the specified author
	 * @throws SQLException
	 */
	public ArrayList<Reimbursement> getReimbursementsByUsername(String username) {
		ReimbursementDao rDAO = makeReimbDaoAndGetConnection();
		System.out.println("opened a connection");
		
		//rDAO.selectReimbursementsByAuthor(author);
		
		ArrayList<Reimbursement> userReimbursements = null;
		try {
			userReimbursements = rDAO.selectReimbursementsByUsername(username);
		} catch (SQLException e) {
			System.out.println("Could not get the reimbursements related to this user");
			e.printStackTrace();
		}
		try {
			System.out.println("closed a connection");
			rDAO.close();  // conn.close()
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return userReimbursements;
	}
	
	/**
	 * Managerial function. Meant to display the reimbursements that have 
	 * yet to be resolved. 
	 * @return A list of reimbursements that are pending
	 */
	public ArrayList<Reimbursement> getPendingReimbursements() {
		ReimbursementStatusDao rsDAO = makeReimbStatusDaoAndGetConnection();
		System.out.println("opened a connection");
		
		ArrayList<Reimbursement> pendingReimbursements = null;
		try {
			pendingReimbursements = rsDAO.selectPendingReimbursements();
		} catch (SQLException e) {
			System.out.println("Could not get the pending reimbursements");
			e.printStackTrace();
		}
		try {
			System.out.println("opened a connection");
			rsDAO.close();  // conn.close()
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return pendingReimbursements;
	}
		
	/**
	 * Determines whether the logged in user is a manager. 
	 * Redirects to the appropriate screen after check is made in the front end.
	 * @param user The user logging
	 * @param pass the password to check against
	 * @return whether the user is a manager based on the user log in information
	 */
	public boolean isManager(String user) {
		UserDao uDAO = makeUserDaoAndGetConnection();
		System.out.println("opened a connection");
		
		boolean manager = false;
		try {
			manager = uDAO.userIsManager(user); 
		} catch (SQLException e) {
			System.out.println("Could not verify managerial credentials");
			e.printStackTrace();
		}
		try {
			System.out.println("closed a connection");
			uDAO.close();  // conn.close()
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return manager;
	}
	
	//call the user DAO functions
	/**
	 * @param user Take in a username and return the information of said user
	 * @throws SQLException
	 */
	public User selectUserByUsername(String user) {
		UserDao uDAO = null;
		try {
			uDAO = new UserDao(getConnection());
			System.out.println("opened a connection");
		} catch (SQLException e) {
			System.out.println("Could not select user by username");
			e.printStackTrace();
		}
		// get what I need from DAOs
		User userObj = uDAO.getByUsername(user);
		
		// close connection
		try {
			System.out.println("closed a connection");
			uDAO.close();
		} catch (Exception e) {e.printStackTrace();} // conn.close()
		//uDAO.getByUsername(user);
	
		return userObj;
	}
	
	public void updateUserPasswords(String username) {
		UserDao uDAO = makeUserDaoAndGetConnection();
		try {
			uDAO.returnAndUpdateHashedPassword(username);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Passwords could not be updated");
		} try {
			System.out.println("closed a connection");
			uDAO.close();  // conn.close()
		} catch (Exception e) {
			e.printStackTrace();
		}
	}    
	
	public String returnCurrentUserPassword(String username) {
		UserDao uDAO = makeUserDaoAndGetConnection();
		String password = null;
		try {
			password = uDAO.getUserPassword(username);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Password could not be fetched");
		} try {
			System.out.println("closed a connection");
			uDAO.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return password;
	}

//////////////////////MAKE OBJECTS THAT WILL BE INSIDE OTHER OBJECTS////////////////////////////////
	/**
	 * Because a reimbursement has a resolver, who is also a user, a new User object must be 
	 * created and used as a field within the Reimbursement object so that the resolver's 
	 * traits may be accessed in the easiest way possible.
	 * @param id The id of the person resolving the reimbursement. This will
	 * be found in the id field of the new User object
	 * @return The reimbursement object with all the fields filled in
	 */
	public User makeNewUser(int id) {
		UserDao uDAO = makeUserDaoAndGetConnection();
		User rUser = null;
		
		try {
			rUser = UserDao.makeNewUser(id);
		} catch (SQLException e){
			System.out.println("New User not created");
			e.printStackTrace();
		} try {
			System.out.println("closed a connection");
			uDAO.close();  // conn.close()
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rUser;
	}
	
	public ReimbursementType makeNewType(int typeID) {
		ReimbursementTypeDao rtDAO = makeReimbTypeDaoAndGetConnection();
		ReimbursementType rType = null;
		
		try {
			rType = rtDAO.createNewType(typeID);
		} catch (SQLException e){
			System.out.println("New type not created");
			e.printStackTrace();
		} try {
			System.out.println("closed a connection");
			rtDAO.close();  // conn.close()
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rType;
	}
//////////////////////////////DAO CREATION OBJECTS///////////////////////////////////////////	
	/**
	 * 
	 * @return a UserDao object  with an established connection to the caller
	 * @throws SQLException
	 */
	public UserDao makeUserDaoAndGetConnection() {
		UserDao uDAO = null;
		try {
			uDAO = new UserDao(getConnection());
		} catch (SQLException e) {
			System.out.println("UserDAO creation failed");
			e.printStackTrace();
		}
		return uDAO;
	}
	
	/**
	 * 
	 * @return a ReimbursementDao object with an established connection to the caller
	 * @throws SQLException
	 */
	public ReimbursementDao makeReimbDaoAndGetConnection() {
		ReimbursementDao rDAO = null;
		try {
			rDAO = new ReimbursementDao(getConnection());
		} catch (SQLException e) {
			System.out.println("ReimbursementDAO creation failed");
			e.printStackTrace();
		}
		
		return rDAO;
	}
	
	/**
	 * 
	 * @return A ReimbursementTypeDao object with an established connection to the caller
	 * @throws SQLException
	 */
	public ReimbursementTypeDao makeReimbTypeDaoAndGetConnection() {
		ReimbursementTypeDao rtDAO = null;
		try {
			rtDAO = new ReimbursementTypeDao(getConnection());
		} catch (SQLException e) {
			System.out.println("ReimbursementTypeDAO creation failed");
			e.printStackTrace();
		}
		return rtDAO;
	}
	
	/**
	 * @return A ReimbursementStatus object with an established connection to the caller
	 * @throws SQLException
	 */
	public ReimbursementStatusDao makeReimbStatusDaoAndGetConnection() {
		ReimbursementStatusDao rsDAO = null;
		try {
			rsDAO = new ReimbursementStatusDao(getConnection());
		} catch (SQLException e) {
			System.out.println("ReimbursementStatusDAO creation failed");
			e.printStackTrace();
		}
		return rsDAO;
	}
}

