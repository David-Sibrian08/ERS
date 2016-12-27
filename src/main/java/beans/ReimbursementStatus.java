package beans;

/**
 * The ERS_REIMBURSEMENT_STATUS object acts like the TYPE and ROLE
 * objects in that it allows easy access to the actual status of the 
 * reimbursement object without needing to perform logic on the numerical
 * representation of the status.
 * @author sgace
 *
 */
public class ReimbursementStatus {
	private int statusID;
	private String status;
	
	public ReimbursementStatus() {
		super();
	}
	
	public ReimbursementStatus(int statusID, String status) {
		super();
		this.statusID = statusID;
		this.status = status;
	}

	public int getStatusID() {
		return statusID;
	}

	public void setStatusID(int statusID) {
		this.statusID = statusID;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "ReimbursementStatus [statusID=" + statusID + ", status=" + status + "]";
	}	
}
