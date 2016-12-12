package web;

import java.io.IOException;

import javax.naming.AuthenticationException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.User;
import middleTier.BusinessDelegate;

public class UserController {
	public void login(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String user = request.getParameter("username");
		String pass = request.getParameter("password");
		
		try {
			User session = new BusinessDelegate().login(user,pass);
			request.getSession().setAttribute("userData", session);
			request.getRequestDispatcher("home.jsp").forward(request, response);
		} catch (AuthenticationException e) {
			request.setAttribute("Failed Authtnetication", "Log in again");
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}
	}
}
