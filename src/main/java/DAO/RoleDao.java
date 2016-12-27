package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import beans.Role;

public class RoleDao implements AutoCloseable{
	private Connection conn = null;
	
	public RoleDao(Connection conn) {
		super();
		this.conn = conn;
	}

	public Role createNewRole(int roleID) throws SQLException {
		String sql = "SELECT user_role FROM ers_user_roles WHERE ers_user_role_id = ?";

		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, roleID);

		ResultSet rs = ps.executeQuery();
		rs.next();

		String role = rs.getString("user_role");
		Role newRole = new Role(roleID, role);

		return newRole;
	}

	@Override
	public void close() throws Exception {

		conn.close();
	}
	
/*	public boolean isManager() {}*/
}
