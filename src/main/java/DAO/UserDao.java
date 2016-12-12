package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import beans.Reimbursement;
import beans.User;

public class UserDao {
	private Connection conn = null;
	
	public UserDao(Connection conn) {
		super();
		this.conn = conn;
	}
	
	
	/**
	 * implementation of SELECT ALL from users table 
	 * @return The list of all the users
	 * Will probably not be used as there is really no need to grab all users at once
	 */
	public ArrayList<User> selectAll() {
		//create a list that will store all data from query
		ArrayList<User> selectAllQuery = new ArrayList<>();
		
		//construct the select all query
		String sql = "SELECT * FROM ers_users ";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			//take the result set and convert it into a list
			mapRows(rs, selectAllQuery);
		} catch (SQLException e) {
			System.out.println("QUERY COULD NOT BE PROCESSED");
			e.printStackTrace();
		}	
		return selectAllQuery;
	}
	
	/**
	 * 
	 * @param username The user whose information we are querying from the DB
	 * @return The user as a User object
	 */
	public User getByUsername(String username) {
		String sql = "SELECT * FROM ers_users WHERE ers_username = ?";
		//List<User> selectByQuery = new ArrayList<>();
		User selectedUser = null;
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ps.setString(1, username);
			
			ResultSet rs = ps.executeQuery();
			rs.next();
			int id = rs.getInt("ers_users_id");
			String user = rs.getString("ers_username");
			String password = rs.getString("ers_password");
			String firstname = rs.getString("user_first_name");
			String lastname = rs.getString("user_last_name");
			String email = rs.getString("user_email");
			int roleId = rs.getInt("user_role_id");
			
			selectedUser = new User(id, user, password, firstname, lastname, email, roleId);
			
			//place result in a list
			//mapRows(rs, selectByQuery);
					
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Query was not completed. User might not have been found.");
		}
		
		return selectedUser;
	}
	
	private ArrayList<User> mapRows(ResultSet rs, ArrayList<User> query) throws SQLException {
		while (rs.next()) {
			//get all the values from the row
			int id = rs.getInt("ers_users_id");
			String username = rs.getString("ers_username");
			String password = rs.getString("ers_password");
			String firstname = rs.getString("user_first_name");
			String lastname = rs.getString("user_last_name");
			String email = rs.getString("user_email");
			int roleId = rs.getInt("user_role_id");
			
			User selectUser = new User(id, username, password, firstname, lastname, email, roleId);
			
			query.add(selectUser);
		}

		printRows(query);
		return query;
	}
	
	private void printRows(List<User> results) {
		for (User u : results) {
			System.out.println(u);
		}
	}
}
