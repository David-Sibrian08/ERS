package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.naming.spi.DirStateFactory.Result;
import javax.swing.JButton;

import org.mindrot.jbcrypt.BCrypt;
import beans.Reimbursement;
import beans.Role;
import beans.User;

public class UserDao implements AutoCloseable{
	private static Connection conn = null;
	
	public UserDao(Connection conn) {
		super();
		this.conn = conn;
	}
	
	public static User makeNewUser(int id) throws SQLException {
		String sql = "SELECT ers_username, ers_password, user_first_name, user_last_name, user_email, user_role_id FROM ers_users WHERE ers_users_id = ?";
	
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, id);
		ResultSet rs = ps.executeQuery();
		
		rs.next();
		String userName = rs.getString("ers_username");
		String password = rs.getString("ers_password");
		String fName = rs.getString("user_first_name");
		String lName = rs.getString("user_last_name");
		String email = rs.getString("user_email");
		int roleID = rs.getInt("user_role_id");
		
		RoleDao rDAO = new RoleDao(conn);
		Role userRole = rDAO.createNewRole(roleID);
		
		User newUser = new User(id, userName, password, fName, lName, email, userRole);
		return newUser;
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
			
			if(ps.executeUpdate() == 0) return null;
			ResultSet rs = ps.executeQuery();
			rs.next();
			int id = rs.getInt("ers_users_id");
			String user = rs.getString("ers_username");
			String password = rs.getString("ers_password");
			String firstname = rs.getString("user_first_name");
			String lastname = rs.getString("user_last_name");
			String email = rs.getString("user_email");
			
			int roleID = rs.getInt("user_role_id");
			
			RoleDao rDAO = new RoleDao(conn);
			Role newRole = rDAO.createNewRole(roleID);
			
			selectedUser = new User(id, user, password, firstname, lastname, email, newRole);
			
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
			int roleID = rs.getInt("user_role_id");
			
			RoleDao rDAO = new RoleDao(conn);
			Role newRole = rDAO.createNewRole(roleID);
			
			User selectUser = new User(id, username, password, firstname, lastname, email, newRole);
			
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
	
	public boolean userIsManager(String user) throws SQLException {
		String sql = "SELECT * FROM ers_users WHERE user_role_id = 1 AND ers_username = ?";
		PreparedStatement ps = conn.prepareStatement(sql);
		
		ps.setString(1, user);
		
		ResultSet rs = ps.executeQuery();
		
		if (rs.next()) {
			return true;
		}
		else 
			return false;	
	}

	/**
	 * @param authorID The ID of the user we will create a User object for
	 * @return The user object
	 * @throws SQLException to the calling Dao
	 */
	public User createNewUser(int authorID) throws SQLException {
			String sql = "SELECT ers_username, ers_password, user_first_name, user_last_name, user_email, user_role_id FROM ers_users " +
						 "WHERE ers_users_id = ?";
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, authorID);
			ResultSet rs = stmt.executeQuery();
			rs.next();
			String username = rs.getString("ers_username");
			String password = rs.getString("ers_password");
			String firstName = rs.getString("user_first_name");
			String lastName = rs.getString("user_last_name");
			String email = rs.getString("user_email");
			int roleID = rs.getInt("user_role_id");
			
			RoleDao rDAO = new RoleDao(conn);
			Role newRole = rDAO.createNewRole(roleID);
			
			User newUser = new User(authorID, username, password, firstName, lastName, email, newRole);
			return newUser;
	}
	

	@Override
	public void close() throws Exception {
		conn.close();
	}

	//IMPLEMENT JBCRYPT
	public String hashPassword(String plainText) {
		return BCrypt.hashpw(plainText, BCrypt.gensalt());
	}
	
	public void returnAndUpdateHashedPassword(String username) throws SQLException {
		String sql = "SELECT ers_password FROM ers_users WHERE ers_username = ?";
		String user = username;
		
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1, username);
		ResultSet rs = ps.executeQuery();
		rs.next();
		
		String plainTextPassword = rs.getString("ers_password");
		String hashedPassword = hashPassword(plainTextPassword);
		
		updateToHashedPassword(user, hashedPassword);
	}
	
	public void updateToHashedPassword(String username, String hashedPassword) throws SQLException {
		String sql = "UPDATE ers_users "
				    + "SET ers_password = ?"
				    + "WHERE ers_username = ?";
		
		PreparedStatement ps = conn.prepareStatement(sql);
		
		ps.setString(1, hashedPassword);
		ps.setString(2, username);
		
		ps.executeUpdate();
		conn.commit();
	}
	
	
	public String getUserPassword(String username) throws SQLException {
		String sql = "SELECT ers_password FROM ers_users WHERE ers_username = ?";
		
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1, username);
		
		if(ps.executeUpdate() == 0) return null;
		
		ResultSet rs = ps.executeQuery();
		rs.next();
		String password = rs.getString("ers_password");
		
		return password;
	}
	
	
}
