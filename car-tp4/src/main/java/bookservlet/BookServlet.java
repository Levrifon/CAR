package bookservlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import book.Author;
import book.Book;
public class BookServlet extends HttpServlet {
	private static final long serialVersionUID = 473375328599205467L;


	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String getAuteur = request.getParameter("author");
		String getTitle = request.getParameter("title");

		Author auteur = new Author(getAuteur);
		Book livre = new Book(auteur, getTitle);
		System.out.println("Added book : " + livre);
		LibraryItf bibliotheque = (LibraryItf) this.getServletContext()
				.getAttribute("bibli");
		bibliotheque.addBook(livre);
		request.setAttribute("livre", livre);

		this.getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
	}
}