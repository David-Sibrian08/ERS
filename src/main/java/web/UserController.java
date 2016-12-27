package web;

import java.io.IOException;
import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.AuthenticationException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.mindrot.jbcrypt.BCrypt;

import beans.Reimbursement;
import beans.User;
import middleTier.BusinessDelegate;

public class UserController {
	public void login(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String user = request.getParameter("username");
		String pass = request.getParameter("password");
		
		try {
			
			//get the user credentials and store them in a session
			//this will be used for managers as well
			User session = new BusinessDelegate().login(user,pass);
			
			boolean isManager = new BusinessDelegate().checkManagerialCredentials(user);
			
			if (isManager) {
				//after user passes verification, store his/her information in a session
				request.getSession().setAttribute("user", session);
				
				ArrayList<Reimbursement> managerReimbursementSession = new BusinessDelegate().returnReimbursementsByStatus();
				request.getSession().setAttribute("managerInfo", managerReimbursementSession);
				request.getRequestDispatcher("mHome.jsp").forward(request, response);
			} else {
				request.getSession().setAttribute("user", session);
				ArrayList<Reimbursement> reimbursementSession = new BusinessDelegate().returnReimbursementsByUserName(user);
				request.getSession().setAttribute("userInfo", reimbursementSession);
				request.getRequestDispatcher("home.jsp").forward(request, response);
			}
			
			//forward the session onward
			//request.getRequestDispatcher("home.jsp").forward(request, response);
			
		} catch (AuthenticationException ae) {
			request.setAttribute("WrongCredentials", "Retry Login");
			request.getRequestDispatcher("login.jsp").forward(request, response);
		} catch (SQLException e) {
			request.setAttribute("UserNotFound", "Retry Login");
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}
	}
	
	public void updateReimbursement (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//String user = request.getParameter("username");
		String resolver = request.getParameter("resolver");			//TODO
		String statusTo = request.getParameter("statusChange");
		String id = request.getParameter("rowId");
		
		//In order for the table to update, we need to fetch the new List of reimbursements
		ArrayList<Reimbursement> listForRefresh = new BusinessDelegate().updateReimbursement(id, resolver, statusTo);
		request.getSession().setAttribute("managerInfo", listForRefresh);
		
		request.getRequestDispatcher("mHome.jsp").forward(request, response);
		
	}

	public void logout(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession(false);
		
		if (session != null) {
			session.invalidate();
			try {
				request.getRequestDispatcher("login.jsp").forward(request, response);
			} catch (ServletException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}

	public void insertReimbursement(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String dollarAmount = request.getParameter("amount");
		String description = request.getParameter("description");
		String author = request.getParameter("author");
		String type = request.getParameter("reimbursementType");
		
		ArrayList<Reimbursement> insertedReimbursements = new BusinessDelegate().insertReimbursement(dollarAmount, description, author, type);
		request.getSession().setAttribute("userInfo", insertedReimbursements);
		request.getRequestDispatcher("home.jsp").forward(request, response);
	} 
}
