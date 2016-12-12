package beans;

public class ReimbursementType {
	private int typeID;
	private String reimbType;
	
	public ReimbursementType() {
		super();
	}

	public ReimbursementType(int typeID, String reimbType) {
		super();
		this.typeID = typeID;
		this.reimbType = reimbType;
	}

	public int getTypeID() {
		return typeID;
	}

	public void setTypeID(int typeID) {
		this.typeID = typeID;
	}

	public String getReimbType() {
		return reimbType;
	}

	public void setReimbType(String reimbType) {
		this.reimbType = reimbType;
	}

	@Override
	public String toString() {
		return "ReimbursementType [typeID=" + typeID + ", reimbType=" + reimbType + "]";
	}
	
	
}
