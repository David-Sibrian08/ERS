package jndi;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import DAO.UserDao;
import beans.Role;

public class Authenticator {
	
	private int roleID;
	private Connection conn = null;
	
	public Authenticator(Connection conn) {
		super();
		this.conn = conn;
	}

	public boolean autheticate(String user, String pass) {
		boolean success;
		
		try {
			UserDao dao = new UserDao(conn);
			//dao.createUserForAuthentication(user, pass);
			
			
			String sql = "SELECT user_first_name, user_last_name, user_role_id FROM ers_users "
					+ "WHERE ers_username = ? AND ers_password = ?";
			
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ps.setString(1, user);
			ps.setString(2, pass);
			
			ResultSet rs = ps.executeQuery();
			
			success = rs.next();
			
			if (success) {
				Role r = new Role();
				r.setRoleID(rs.getInt("user_role_id"));
				return true;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public void checkRole(Role r) {
		if (r.getRoleID() == 1) {
			r.setRole("manager");
			System.out.println("manager");
		} else {
			r.setRole("employee");
			System.out.println("employee");
		}
	}
}
