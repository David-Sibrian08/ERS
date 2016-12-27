package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import beans.Reimbursement;
import beans.ReimbursementStatus;
import beans.ReimbursementType;
import beans.User;

import java.sql.Timestamp;

public class ReimbursementDao implements AutoCloseable{
	private Connection conn = null;
	
	public ReimbursementDao(Connection conn) {
		super();
		this.conn = conn;
	}
	
	/**
	 * implementation of SELECT ALL from reimbursement table
	 * It will return a list of Reimbursement objects 
	 * This will be a MANAGER function
	 * @throws SQLException 
	 */
	public ArrayList<Reimbursement> selectAll() throws SQLException {
		//create a list that will store all data from query
		ArrayList<Reimbursement> selectAllQuery = new ArrayList<>();
		
		//construct the select all query
		String sql = "SELECT * FROM ers_reimbursement";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			//take the result set and convert it into a list
			mapRows(rs, selectAllQuery);
			System.out.println("Printed all");
			
			return selectAllQuery;
	}
	
	/**
	 * implementation of SELECT BY ID from reimbursement table 
	 * @param idToSelect Will return a list of all reimbursement that belong to that username 
	 * This will be an EMPLOYEE function 
	 * @throws SQLException 
	 */
	public ArrayList<Reimbursement> selectByID(int idToSelect) throws SQLException {
		ArrayList<Reimbursement> selectByQuery = new ArrayList<>();
		
		String sql = "SELECT * FROM ers_reimbursement WHERE reimb_id = ?";

			PreparedStatement ps = conn.prepareStatement(sql);
			
			//pass in the parameter
			ps.setInt(1, idToSelect);
			
			//your results are in the result set
			ResultSet rs = ps.executeQuery();
			
			//place result in a list
			mapRows(rs, selectByQuery);
			
			return selectByQuery;
	}
	
	/**
	 * 
	 * @param typeToSelect Specifies which type of reimbursement to show
	 * 1 represents lodging
	 * 2 travel
	 * 3 food
	 * 4 other
	 * The selection will be passed in via radio button (???)
	 * 
	 * This function might not be used
	 */
/*		MOVED TO REIMBURSEMENT TYPE DAO!!

	public ArrayList<Reimbursement> selectByType(int typeToSelect) {
		ArrayList<Reimbursement> selectTypeQuery = new ArrayList<>();
		String sql = "SELECT * FROM ers_reimbursement where REIMB_TYPE_ID = ?";
		
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ps.setInt(1, typeToSelect);
			
			ResultSet rs = ps.executeQuery();
			
			mapRows(rs, selectTypeQuery);
		} catch (SQLException e) {
			System.out.println("Select By Type could not be completed");
			e.printStackTrace();
		}
		return selectTypeQuery;
	}
*/	
	/**
	 * 
	 * @param statusToSelect specified reimbursements to show by their status
	 * 1 represents approved
	 * 2 pending
	 * 3 declined
	 * @return 
	 * @throws SQLException 
	 */
	public ArrayList<Reimbursement> selectByStatus(int statusToSelect) throws SQLException {
		ArrayList<Reimbursement> selectStatusQuery = new ArrayList<>();
		String sql = "SELECT * FROM ers_reimbursement where REIMB_STATUS_ID = ?";
		
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ps.setInt(1, statusToSelect);
			
			ResultSet rs = ps.executeQuery();
			
			mapRows(rs, selectStatusQuery);

			return selectStatusQuery;
	}
	/**
	 * 
	 * @param reimb A reimbursement object with all fields that will be inserted into the DB
	 * No return type. Should refresh the jsp ideally
	 * @throws SQLException 
	 */
	public void insertReimbursement(Reimbursement reimb) throws SQLException {
		
		//1. User should not be making ID's, find largest ID in table and +1 on every insert
		int nextID;
		
			nextID = getTheNextID();
			
			//2. Once that ID is found, use it (but increase it by 1, done in method)
			
			//3. execute your statement to insert
			Timestamp time = getCurrentTimeStamp();
			
			String insertSQL = "INSERT INTO ers_reimbursement VALUES (?,?,?,?,?,?,?,?,?)";
			PreparedStatement ps = conn.prepareStatement(insertSQL);
			ps.setInt(1, nextID);
			ps.setDouble(2, reimb.getAmount());
			ps.setTimestamp(3, time);		//submitted day
			ps.setNull(4, java.sql.Types.TIMESTAMP);							//resolved date
			ps.setString(5,reimb.getDescription());
			ps.setInt(6, reimb.getAuthor().getUserID());
			ps.setNull(7, java.sql.Types.INTEGER);			//resolver
			ps.setInt(8, 2);						//1 approved, 2 pending, 3 denied. Automatically pending
			ps.setInt(9, reimb.getType().getTypeID());
			
			ps.executeUpdate();
			conn.commit();
			
			//selectAll();
			
			System.out.println("Reimbursement inserted");	
	}
	
	/**
	 * 
	 * @return the next valid ID within the DB. 
	 * @throws SQLException
	 */
	public int getTheNextID() throws SQLException {
				String sql = "SELECT MAX(reimb_id) AS MaxID FROM ers_reimbursement";
				PreparedStatement ps = conn.prepareStatement(sql);
				ResultSet rs = ps.executeQuery();
				rs.next();
				
				int currentID = rs.getInt("MaxID") + 1;
				
				return currentID;
	}
	
	/**
	 * Update the null fields within the reimbursement object
	 * @param status
	 * @param resolver
	 * @param id
	 * This will be a MANAGER function. Will update when the manager changes a status from pending to
	 * denied or approved
	 * @throws SQLException 
	 */
	public void updateReimbursement(String status, String resolver, int id) throws SQLException {
		String sql = "UPDATE ers_reimbursement "
				+ "SET reimb_resolved = ?, "
				+ "reimb_status_id = (SELECT reimb_status_id FROM ers_reimbursement_status WHERE reimb_status = ?), "
				+ "reimb_resolver = (SELECT ers_users_id FROM ers_users WHERE ers_username = ?) "
				+ "WHERE reimb_id = ?"; 
		
			Timestamp time = getCurrentTimeStamp();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setTimestamp(1, time);
			ps.setString(2, status);
			ps.setString(3, resolver);
			ps.setInt(4, id);

			ps.executeUpdate();
			conn.commit();
			
			//selectAll();	
	}
	
	/**
	 * Delete a reimbursement based on its ID
	 * @param id The ID of the reimbursement to be deleted
	 * Will probably not be used
	 * @throws SQLException 
	 */
	public void deleteReimbursement(int id) throws SQLException {
		String sql = "DELETE FROM ers_reimbursement WHERE reimb_id = ?";
		
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ps.executeUpdate();
			
			conn.commit();
			
			//selectAll();
	}
	
	/**
	 * 
	 * @param author The author of the reimbursements. 
	 * Will probably not be used as the reimbursements will be fielded by username not author 
	 * @return 
	 * @throws SQLException 
	 */
	public ArrayList<Reimbursement> selectReimbursementsByUsername(String username) throws SQLException {
		ArrayList<Reimbursement> selectByAuthorQuery = new ArrayList<>();
		String sql = "SELECT * FROM ers_reimbursement "
				+ "WHERE reimb_author = (SELECT ers_users_id from ers_users WHERE ers_username = ?)";
		
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ps.setString(1, username);
			
			ResultSet rs = ps.executeQuery();
			System.out.println("before adding to map");
			mapRows(rs, selectByAuthorQuery);
			System.out.println("Mapped!");

		return selectByAuthorQuery;
	}
	
	/**
	 * A helper function to help send timestamp objects into the DB
	 * @return the current system time in SQL format
	 */
	public static java.sql.Timestamp getCurrentTimeStamp() {

		java.util.Date today = new java.util.Date();
		return new java.sql.Timestamp(today.getTime());
	}

	/**
	 * 
	 * @param rs The ResultSet from the selectAll
	 * @param query
	 * @return The list containing all the reimbursements that have been added 
	 * @throws SQLException
	 */
	private ArrayList<Reimbursement> mapRows(ResultSet rs, ArrayList<Reimbursement> query) throws SQLException {
		while (rs.next()) {
			//get all the values from the row
			int id = rs.getInt("reimb_id");
			double amount = rs.getDouble("reimb_amount");
			Timestamp submitDate = rs.getTimestamp("reimb_submitted");
			Timestamp resolveDate = rs.getTimestamp("reimb_resolved");
			System.out.println("resolved:" + resolveDate);

			String description = rs.getString("reimb_description");
			
			int authorID = rs.getInt("reimb_author");
			UserDao authorDAO = new UserDao(conn);
			User newAuthor = authorDAO.createNewUser(authorID);
			
			int resolver = rs.getInt("reimb_resolver");
			User newResolver = null;
			if(resolver !=0) {
				UserDao resolverDAO = new UserDao(conn);
				newResolver = resolverDAO.createNewUser(resolver);
			}
			
			System.out.println(resolver);
			
			int statusID = rs.getInt("reimb_status_id");
			ReimbursementStatusDao statusDAO = new ReimbursementStatusDao(conn);
			ReimbursementStatus newStatus = statusDAO.createNewStatus(statusID);
			
			int typeID = rs.getInt("reimb_type_id");
			ReimbursementTypeDao typeDAO = new ReimbursementTypeDao(conn);
			ReimbursementType newType = typeDAO.createNewType(typeID);
			
			Reimbursement selectReimb = new Reimbursement(id, amount, submitDate, resolveDate, description, newAuthor, newResolver, newStatus, newType);
			
			query.add(selectReimb);
		}
		printRows(query);
		
		return query;
	}	
	
	/**
	 * A helper function to print the reimbursements from the list
	 * @param results The list sent from mapRows(: _:) containing Reimbursement objects
	 */
	private void printRows(List<Reimbursement> results) {
		for (Reimbursement r : results) {
			System.out.println(r);
		}
	}
	
	/**
	 * Close the open connection. 
	 * This method exists in the Facade so this one might not be used
	 * @param conn The connection to close
	 */
	public void closeConnection(Connection conn) {
		try {
			conn.close();
			System.out.println("Connection closed.");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	@Override
	public void close() throws Exception {
		conn.close();
	}
}
