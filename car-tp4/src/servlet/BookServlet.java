package servlet;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import book.Author;
import book.Book;
import book.BookManager;


public class BookServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	@EJB(name="BooksManager")
	private BookManager bookBean = new BookManager();
	

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String formAuthor = request.getParameter("author");
		String formtitle = request.getParameter("title");
		Author aut = new Author(formAuthor);
		Book book = new Book(aut,formtitle, "1990");
		System.out.println("Success add book");
		request.setAttribute("livre", book);
		bookBean.addBook(book);
		 this.getServletContext().getRequestDispatcher( "/WEB-INF/index.jsp" ).forward( request, response );
	}

}
