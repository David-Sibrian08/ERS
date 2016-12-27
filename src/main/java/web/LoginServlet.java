package web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.Facade;
import middleTier.BusinessDelegate;
import weblogic.ejbgen.ResourceRef.Auth;

/**
 * Servlet implementation class LoginServlet
 */
/*@WebServlet("/LoginServlet")*/
@WebServlet(urlPatterns="*")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//this.doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String requestURI = request.getRequestURI();
		UserController uControl  = new UserController();

		//SWITCH 
		switch(requestURI){
			case "/ERS/login.do":{
				uControl.login(request, response);
				break;
			}

			case "/ERS/update.do":{
				uControl.updateReimbursement(request, response);
				break;
			}
			
			case "/ERS/insert.do":{
				uControl.insertReimbursement(request, response);
				break;
			}
			
			case "/ERS/logout.do":{
				uControl.logout(request, response);
				break;
			}
			default:{
				response.setStatus(404);
				response.sendRedirect("toDO.html");
			}

		}
		
	}

}
