package beans;

import java.sql.Timestamp;
import java.util.Date;

/**
 * 
 * @author sgace
 * The reimbursement class representing a Reimbursement object.
 * The reimbursement table is the main table that the ERS system is 
 * going to use 
 *
 */
public class Reimbursement {
	private int id;				
	private double amount;
	private Timestamp submitDate;
	private Timestamp resolveDate;
	private String description;
	private User author;
	private User resolver;
	private ReimbursementStatus status;
	private ReimbursementType type;
	
	public Reimbursement() {
		super();
	}
		
	public Reimbursement(int id, double amount, Timestamp submitDate, Timestamp resolveDate, String description, User author,
			User resolver, ReimbursementStatus status, ReimbursementType type) {
		super();
		this.id = id;
		this.amount = amount;
		this.submitDate = submitDate;
		this.resolveDate = resolveDate;
		this.description = description;
		this.author = author;
		this.resolver = resolver;
		this.status = status;
		this.type = type;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Timestamp getSubmitDate() {
		return submitDate;
	}

	public void setSubmitDate(Timestamp submitDate) {
		this.submitDate = submitDate;
	}

	public Timestamp getResolveDate() {
		return resolveDate;
	}

	public void setResolveDate(Timestamp resolveDate) {
		this.resolveDate = resolveDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public User getResolver() {
		return resolver;
	}

	public void setResolver(User resolver) {
		this.resolver = resolver;
	}

	public ReimbursementStatus getStatus() {
		return status;
	}

	public void setStatus(ReimbursementStatus status) {
		this.status = status;
	}

	public ReimbursementType getType() {
		return type;
	}

	public void setType(ReimbursementType type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Reimbursement id: " + id + " for amount= $" + amount + " submitted on: " + submitDate + " and resolved on: "
				+ resolveDate + ". Description: " + description + " by author " + author.getFullName() + " and resolved by " + resolver
				+ ". Current status= " + status.getStatus() + " for reibursement of type " + type.getReimbType() + ".";
	}
		
}
