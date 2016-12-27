package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.ReimbursementType;

public class ReimbursementTypeDao implements AutoCloseable {
	private Connection conn = null;
	
	public ReimbursementTypeDao(Connection conn) {
		super();
		this.conn = conn;
	}
	
	public ArrayList<ReimbursementType> selectReimbursementsByType(int typeID) {
		ArrayList<ReimbursementType> selectByTypeQuery = new ArrayList<>();
		String sql = "SELECT * FROM ers_reimbursement WHERE reimb_type_id = ?";
		
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, typeID);
			
			ResultSet rs = ps.executeQuery();
			mapRows(rs, selectByTypeQuery);
		} catch (SQLException e) {
			System.out.println("Select By Type could not be completed");
			e.printStackTrace();
		}
		return selectByTypeQuery;
	}

	/**
	 * To access all field in our Beans from the jsp page and return user friendly results,
	 * it is necessary to have a ReimbursementType object in the Reimbursement bean so the 
	 * actual status and not just the ID can be accessed.
	 * @param typeID
	 * @return The appropriate ReimbursementType object 
	 * @throws SQLException to the CallingDao
	 */
	public ReimbursementType createNewType(int typeID) throws SQLException {
		String sql = "SELECT reimb_type from ers_reimbursement_type WHERE reimb_type_id = ?";
		
		PreparedStatement ps = conn.prepareStatement(sql);
		
		ps.setInt(1, typeID);
		ResultSet rs = ps.executeQuery();
		
		//point to the actual first row of the set
		rs.next();
		String reimbType = rs.getString("reimb_type");
		ReimbursementType reimbursementTypeObject = new ReimbursementType(typeID, reimbType);
		return reimbursementTypeObject;
	}
	
	private ArrayList<ReimbursementType> mapRows(ResultSet rs, ArrayList<ReimbursementType> query) throws SQLException {
		while(rs.next()){
			int typeID = rs.getInt("reimb_type_id");
			String type = rs.getString("reimb_type");
			ReimbursementType obj = new ReimbursementType(typeID,
								  						  type);			  
			query.add(obj);
		}
		printRows(query);
		
		return query;
	}
	
	private void printRows(List<ReimbursementType> query) {
		for (ReimbursementType r : query) {
			System.out.println(r);
		}
	}

	@Override
	public void close() throws Exception {

		conn.close();
	}
}
