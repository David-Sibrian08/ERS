package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import beans.Reimbursement;
import beans.ReimbursementStatus;
import beans.ReimbursementType;
import beans.User;

class ReimbursementStatusDao implements AutoCloseable{
	private Connection conn = null;

	public ReimbursementStatusDao(Connection conn) {
		super();
		this.conn = conn;
	}
	
	public ReimbursementStatus makeNewStatus(int statusId) throws SQLException {
		String sql = "SELECT reimb_status FROM ers_reimbursement_status " + 
				 	"WHERE reimb_status_id = ?";	
		
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, statusId);
		
		ResultSet rs = ps.executeQuery();
		
		rs.next();
		String reimbStatus = rs.getString("reimb_status");
		
		ReimbursementStatus rStatus = new ReimbursementStatus(statusId, reimbStatus);
		
		return rStatus;
	}
	
	public String getStatusByID(int statusID) throws SQLException {
		String rStatus = null;
		
		String sql = "SELECT reimb_status FROM ers_reimbursement_status WHERE reimb_status_id = ?";
		
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, statusID);
			ResultSet rs = ps.executeQuery();
			rs.next();
			
			String status = rs.getString("reimb_status_id");
			if (status == "pending") {
				rStatus = "pending";
				return rStatus;
			} else if (status == "approved") {
				rStatus = "approved";
				return rStatus;
			} else {
				rStatus = "denied";
				return rStatus;
			}
	}
	
	/**
	 * 
	 * @return A list of all reimbursements that are currently pending. WIll be expanded as needed
	 * @throws SQLException
	 */
	public ArrayList<Reimbursement> selectPendingReimbursements() throws SQLException {
		ArrayList<Reimbursement> pendingReimbursements = new ArrayList<>();
		String sql = "SELECT * FROM ers_reimbursement "
				+ "WHERE reimb_status_id = (SELECT reimb_status_id FROM ers_reimbursement_status WHERE reimb_status = 'pending')";
		
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ResultSet rs = ps.executeQuery();
			mapRows(rs, pendingReimbursements);

		return pendingReimbursements;
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
	 * 
	 * @param statusID 
	 * @return The ReimbursementStatus object that we will use in our bean
	 * @throws SQLException to the calling Dao
	 */
	public ReimbursementStatus createNewStatus(int statusID) throws SQLException {
		String sql = "SELECT reimb_status FROM ers_reimbursement_status WHERE reimb_status_id = ?";

			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, statusID);
			
			ResultSet rs = ps.executeQuery();
			rs.next();
			
			String status = rs.getString("reimb_status");
			ReimbursementStatus newStatus = new ReimbursementStatus(statusID, status);
		
		return newStatus;
	}

	@Override
	public void close() throws Exception {
		conn.close();
	}
}
