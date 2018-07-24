package domain.useLoan;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Waitlist.*;
import domain.book.Book;
import domain.loan.*;
import domain.user.*;

/**
* Servlet implementation class Login
*/
@WebServlet("/LoanController")
public class LoanController extends HttpServlet {
	private static final long serialVersionUID = 1L;
      

   public LoanController() {}
   
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String submitType = request.getParameter("submit");
		if (submitType==null) {submitType = "";}
		String submitType1 = request.getParameter("return");
		if (submitType1==null) {submitType1 = "";}
		//Book b = new Book(request.getParameter("ISBN"), "ISBN");
		User u = (User)request.getSession().getAttribute("user"); //get user details again
		Book b = new Book(request.getParameter("ISBN"));
		LoanDao loanDao = new LoanDaoImpl();
		WaitListDao waitlistdao = new WaitListDaoImpl();
		//User u = new User();
		//u.setUsername((String) request.getSession().getAttribute("username"));
		String user_display;
		String OverDue_display;
		//copy col = new copy(ISBN, (String)request.getSession().getAttribute("username"));
		if(submitType.equals("Check Out")){
			//String text = checkOut.checkout(b, u);
			//String text = lib.checkOut(b, u);			
			if(loanDao.CheckOverdueLimit(u) == 0) {
				String text = "You already have checked out 4 books, you can not check out more!!!";
				request.setAttribute("secondMessage", text);
				OverDue_display = u.displayOverdue();
				request.setAttribute("displayTable1", OverDue_display);
				user_display = u.displayBooks();
				request.setAttribute("displayTable", user_display);
				request.getRequestDispatcher("welcome_user.jsp").forward(request, response);
				return;
			}
			
			int Status = waitlistdao.checkUser(b, u);
			if(Status == 1) {
				String text = "You already rented book " +b.getTitle()+" !!! Check out failed";
				request.setAttribute("secondMessage", text);
			}else if (Status == 2) {   /// book is not available and user is not in waiting list, add to wait list
				waitlistdao.add(b, u);
				String text = "You have been successfully added in to wait list of "+ b.getISBN() +" book";
				request.setAttribute("secondMessage", text);
			}else if (Status == 3){// book is not available and user is already in waiting list, can not add to wait list
				String text = "You are already in the wait list of "+ b.getISBN() +" book";
				request.setAttribute("secondMessage", text);
			}else if (Status == 4){// book is not available and user is on 0 position, delete user in wait list, and check out book
				waitlistdao.deleteUser(b, u);
				String text = u.checkOutBook(request.getParameter("ISBN"));
				request.setAttribute("secondMessage", text);
			}else {// book is available, then check out book
				String text = u.checkOutBook(request.getParameter("ISBN"));
				request.setAttribute("secondMessage", text);	
			}
			
			OverDue_display = u.displayOverdue();
			request.setAttribute("displayTable1", OverDue_display);
			user_display = u.displayBooks();
			request.setAttribute("displayTable", user_display);
			
			
			/*
			// *** CODE THAT NEEDS CHANGE START ***
			int status = checkOut.checkout(b, u);
			//check out function using collection class
			// *** CODE THAT NEEDS CHANGE END ***
			if(status == 1) {
				String text = "Book "+ b.getISBN() + " Check out success!";
				request.setAttribute("secondMessage", text);
			}else if(status == 2) {
				String text = "Book "+ b.getISBN() + " Not found in database";
				request.setAttribute("secondMessage", text);
			}else if(status == 0) {
				request.setAttribute("secondMessage", "Book is not available now! Please select another one!");
			}else if(status == 3) {
				String text = "Book "+ b.getISBN() + " Check out failed!!";
				request.setAttribute("secondMessage", text);
			}else if(status == 4 ) {
				String text = "You already rent Book " +b.getISBN()+" !!! Check out failed";
				request.setAttribute("secondMessage", text);
			}
			*/
			//Search sb = new SearchCheckedout(u.getUsername());
			//user_display = sb.find();
			
			request.getRequestDispatcher("welcome_user.jsp").forward(request, response);
		}else if(submitType1.equals("Return")){
			//String text = returnBook.returnBook(b, u);
			//String text = lib.returnBook(b, u);
			String text = u.returnBook(request.getParameter("ISBN"));
			request.setAttribute("secondMessage", text);
			/*
			// *** CODE THAT NEEDS CHANGE START ***
			int status = returnBook.returnBook(b, u);
			// *** CODE THAT NEEDS CHANGE END ***
			if(status == 1) {
				String text = "Book "+ b.getISBN() + " Return success!";
				request.setAttribute("secondMessage", text);
			}else if(status == 2) {
				String text = "Book "+ b.getISBN() + " Not found in database";
				request.setAttribute("secondMessage", text);
			}else if(status == 0) {
				request.setAttribute("secondMessage", "You did not rent book "+b.getISBN()+" !!! Return Failed!!!");
			}else if(status == 3) {
				String text = "Book "+ b.getISBN() + " Check out failed!!";
				request.setAttribute("secondMessage", text);
			}
			*/
			//String username = request.getSession().getAttribute("username").toString();
			//Search sb = new SearchCheckedout(username);
			//user_display = sb.find();
			OverDue_display = u.displayOverdue();
			request.setAttribute("displayTable1", OverDue_display);
			user_display = u.displayBooks();
			request.setAttribute("displayTable", user_display);
			request.getRequestDispatcher("welcome_user.jsp").forward(request, response);
		}else if(submitType.equals("Search and Check Out")){
			request.getRequestDispatcher("search_user.jsp").forward(request, response);
		}

	}

}
