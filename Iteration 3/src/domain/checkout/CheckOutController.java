package domain.checkout;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.book.Book;
import domain.search.Search;
import domain.search.SearchTitles;
import domain.user.*;
import domain.waitList.WaitListDao;
import domain.waitList.WaitListDaoImpl;

/**
* Servlet implementation class Login
*/
@WebServlet("/CheckOutController")
public class CheckOutController extends HttpServlet {
	private static final long serialVersionUID = 1L;
      

   public CheckOutController() {}
   
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String submitType = request.getParameter("submit");
		User u = (User)request.getSession().getAttribute("user"); //get user details again
		Book b = new Book(request.getParameter("ISBN"),"ISBN");
		WaitListDao waitlistdao = new WaitListDaoImpl();
		String user_display;
		String OverDue_display;
		
		if(submitType.equals("Check Out")){
			if(u.CheckOverdueLimit() == 0) {
				// checks if over overdue limit
				String text = "You already have 4 overdue books, you can not check out more!!!";
				request.setAttribute("secondMessage", text);
				OverDue_display = u.displayOverdue(); // displays overdue books
				request.setAttribute("displayTable1", OverDue_display);
				request.setAttribute("overdueMessage", u.getTotalOverdueFees(1));
				user_display = u.displayCheckedOutBooks(); //displays all checked out books
				request.setAttribute("displayTable", user_display);
				request.getRequestDispatcher("welcome_user.jsp").forward(request, response);
				return;
			}
			
			int Status = waitlistdao.checkUser(b, u); // checks if title has already been rented by this user
			if(Status == 1) {
				b = u.getLoanedBook(request.getParameter("ISBN"));
				String text = "You already rented book " +b.getTitle()+" !!! Check out failed";
				request.setAttribute("secondMessage", text);
			}else if (Status == 2) {   /// book is not available and user is not in waiting list, add to wait list
				waitlistdao.add(b, u);
				String text = "You have been successfully added in to wait list of "+ b.getISBN() +" book";
				request.setAttribute("secondMessage", text);
			}else if (Status == 3){// book is not available and user is already in waiting list, can not add to wait list
				String text = "You are already in the wait list of "+ request.getParameter("ISBN") +" book";
				request.setAttribute("secondMessage", text);
			}else if (Status == 4){// book is not available and user is on 0 position, delete user in wait list, and check out book
				waitlistdao.deleteUser(b, u);
				String text = u.checkOutBook(request.getParameter("ISBN"));
				request.setAttribute("secondMessage", text);
			}else {// book is available, then check out book
				String text = u.checkOutBook(request.getParameter("ISBN"));
				request.setAttribute("secondMessage", text);	
			}
			u.setRentedBooks();
			OverDue_display = u.displayOverdue();
			request.setAttribute("displayTable1", OverDue_display);
			request.setAttribute("overdueMessage", u.getTotalOverdueFees(1));
			user_display = u.displayCheckedOutBooks();
			request.setAttribute("displayTable", user_display);
			request.setAttribute("displayTable2", u.getWaitlistPositions());
			
			request.getRequestDispatcher("welcome_user.jsp").forward(request, response);
		}else if(submitType.equals("Search and Check Out")){
			Search s = new SearchTitles("","","","","");
			String book_table = s.find();
			request.setAttribute("displayTable", book_table);
			request.setAttribute("displayTable2", u.getWaitlistPositions());
			request.getRequestDispatcher("search_user.jsp").forward(request, response);
		}else if (submitType.equals("Return to Home page")){
			OverDue_display = u.displayOverdue();
			request.setAttribute("displayTable1", OverDue_display);
			request.setAttribute("overdueMessage", u.getTotalOverdueFees(1));
			user_display = u.displayCheckedOutBooks();
			request.setAttribute("displayTable", user_display);
			request.setAttribute("displayTable2", u.getWaitlistPositions());
			request.getRequestDispatcher("welcome_user.jsp").forward(request, response);
		}
	}
}

