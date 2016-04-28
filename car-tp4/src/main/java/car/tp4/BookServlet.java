package car.tp4;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name="Auteurs", urlPatterns = "/auteurs")
public class BookServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	@EJB
	private BookManager bookBean;
	

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		//String formAuthor = request.getParameter("author");
		//String formtitle = request.getParameter("title");
		//Author aut = new Author(formAuthor);
		//Book book = new Book(aut,formtitle, "1990");
		//System.out.println("Success add book");
		//request.setAttribute("livre", book);
		//bookBean.addBook(book);
		List<String> l=  bookBean.getAllAuthors();
		request.setAttribute("bookAuthor", l);
		 this.getServletContext().getRequestDispatcher( "/author.jsp" ).forward( request, response );
	}

}
