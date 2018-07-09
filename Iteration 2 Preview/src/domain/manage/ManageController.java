package domain.manage;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.search.DisplayAll;
import domain.search.Search;
import domain.book.*;

/**
* Servlet implementation class Login
*/
@WebServlet("/ManageController")
public class ManageController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public ManageController() {}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BookDao bookDao = new BookDaoImpl();
		Book b=new Book();
		ManageUsers mu = new ManageUsers();
		Search search = new Search();
		String submitType = request.getParameter("submit");
		String book_display;
		String user_display;
		
		if(submitType.equals("addbook")) {
			request.getRequestDispatcher("addbook.jsp").forward(request, response);
		}else if(submitType.equals("addbookComplete")) {
			b.setISBN(request.getParameter("ISBN"));
			b.setTitle(request.getParameter("title"));
			b.setAuthor(request.getParameter("author"));
			b.setCategory(request.getParameter("category"));
			b.setEdition(request.getParameter("edition"));
			int status = bookDao.addBook(b);
			if(status == 1) {
				request.setAttribute("secondMessage", "Add book success!");
			}else {
				request.setAttribute("secondMessage", "Book is already in the database!!");
			}
			
			b.setNull();
			book_display = search.displayBooks(search.search(b));
			request.setAttribute("displayTable", book_display);
			user_display = mu.displayUsers();
			request.setAttribute("displayTable2", user_display);
			request.getRequestDispatcher("welcome_admin.jsp").forward(request, response);
		}else if(submitType.equals("editbook")) {
			b.setNull();
			book_display = search.displayBooks(search.search(b));
			request.setAttribute("displayTable", book_display);
			request.getRequestDispatcher("editbook.jsp").forward(request, response);
		}else if(submitType.equals("editbookcomplete")) {
			EditBooks eb = new EditBooks();
			String ISBN = request.getParameter("TargetISBN");
			String CopyID = request.getParameter("TargetCopyID");
			b.setISBN(request.getParameter("ISBN"));
			b.setTitle(request.getParameter("title"));
			b.setAuthor(request.getParameter("author"));
			b.setCategory(request.getParameter("category"));
			b.setEdition(request.getParameter("edition"));
			b.setCopyID(request.getParameter("CopyID"));
			b.setRentedBy(request.getParameter("RentedBy"));
			b.setCheckOutDate(request.getParameter("CheckOutDate"));
			b.setReturnByDate(request.getParameter("ReturnByDate"));
			b.setStatus(request.getParameter("Status"));
			int status=0;
			if (CopyID.isEmpty()) {
				status = eb.editTitle(b, ISBN);
			} else {
				status = eb.editCopy(b, ISBN, CopyID);
			}
			
			if(status == 1) {
				request.setAttribute("secondMessage", "edit book success!");
			}else {
				request.setAttribute("secondMessage", "Book is not in the database yet!!");
			}
			
			//user_display = mu.displayUsers();
			b.setNull();
			book_display = search.displayBooks(search.search(b));
			request.setAttribute("displayTable", book_display);
			user_display = mu.displayUsers();
			request.setAttribute("displayTable2", user_display);
			request.getRequestDispatcher("welcome_admin.jsp").forward(request, response);
		}else if(submitType.equals("Repair Needed")) {
			ProcessReturn pr = new ProcessReturn();
			request.setAttribute("secondMessage", pr.processReturn(request.getParameter("ISBN"), 2));
			b.setNull();
			b.setStatus("2");
			List<Book> books = search.search(b);
			b.setStatus("3");
			books.addAll(search.search(b));
			book_display = search.displayBooks(books);
			request.setAttribute("displayTable", book_display);
			request.getRequestDispatcher("processreturn.jsp").forward(request, response);
		}else if(submitType.equals("Ruined")) {
			ProcessReturn pr = new ProcessReturn();
			request.setAttribute("secondMessage", pr.processReturn(request.getParameter("ISBN"), 3));
			b.setNull();
			b.setStatus("2");
			List<Book> books = search.search(b);
			b.setStatus("3");
			books.addAll(search.search(b));
			book_display = search.displayBooks(books);
			request.setAttribute("displayTable", book_display);
			request.getRequestDispatcher("processreturn.jsp").forward(request, response);
		}else if(submitType.equals("Presentable")) {
			/*String ISBN = request.getParameter("ISBN");
			int status=0;
			change call for book details; need to get copy you are talking about
			b.setNull();
			b.setISBN(ISBN);
			Search s = new Search();
			if ((bookDao.copyExists(ISBN, s.getCopyID(ISBN, "2")))==1) {
				status = bookDao.changeStatus(ISBN, s.getCopyID(ISBN, "2"), 1);
			} else if ((bookDao.copyExists(ISBN, s.getCopyID(ISBN, "3")))==1) {
				status = bookDao.changeStatus(ISBN, s.getCopyID(ISBN, "3"), 1);
			}
			
			if(status == 1) {
				request.setAttribute("secondMessage", "Book has been made available for Library patrons");
			}else {
				request.setAttribute("secondMessage", "Book has not been returned");
			} */
			ProcessReturn pr = new ProcessReturn();
			request.setAttribute("secondMessage", pr.processReturn(request.getParameter("ISBN"), 1));
			b.setNull();
			b.setStatus("2");
			List<Book> books = search.search(b);
			b.setStatus("3");
			books.addAll(search.search(b));
			book_display = search.displayBooks(books);
			request.setAttribute("displayTable", book_display);
			request.getRequestDispatcher("processreturn.jsp").forward(request, response);
		}else if(submitType.equals("process returned books")) {
			//
			//book_display = DisplayAll.displayCollection(); // should just display returned books
			b.setNull();
			b.setStatus("2");
			List<Book> books = search.search(b);
			b.setStatus("3");
			books.addAll(search.search(b));
			book_display = search.displayBooks(books);
			request.setAttribute("displayTable", book_display);
			request.getRequestDispatcher("processreturn.jsp").forward(request, response);
		}else if(submitType.equals("Search")) {
			request.getRequestDispatcher("search_admin.jsp").forward(request, response);
		}else if(submitType.equals("Return to Home page")) {
			//book_display = DisplayAll.displayCollection(); // 
			b.setNull();
			book_display = search.displayBooks(search.search(b));
			request.setAttribute("displayTable", book_display);
			user_display = mu.displayUsers();
			request.setAttribute("displayTable2", user_display);
			request.getRequestDispatcher("welcome_admin.jsp").forward(request, response);
		}
	}

}
