package beans;

/**
 * An ERS_USER_ROLE object. Every user must have a role, therefore every user
 * must have a Role object that represents their actual role and not a numerical value.
 * @author sgace
 *
 */
public class Role {
	private int roleID;
	private String role;
	
	public Role() {
		super();
	}
	
	public Role(int roleID, String role) {
		super();
		this.roleID = roleID;
		this.role = role;
	}
	
	public int getRoleID() {
		return roleID;
	}
	
	public void setRoleID(int roleID) {
		this.roleID = roleID;
	}
	
	public String getRole() {
		return role;
	}
	
	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "Role [roleID=" + roleID + ", role=" + role + "]";
	}
}
