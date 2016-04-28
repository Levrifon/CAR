package car.tp4;

import java.util.List;

import javax.ejb.Local;

@Local
public interface LibraryItf {
	public List<Book> getAllBooks();
	public List<Author> getAllAuthors();
	public void addBook(Book book);
	public void init();
	public Book getBook(int idBook);

}
