package bookservlet;

import java.util.List;

import javax.ejb.Local;

import book.Author;
import book.Book;

@Local
public interface LibraryItf {
	public List<Book> getAllBooks();
	public List<Author> getAllAuthors();
	public void addBook(Book book);
	public void init();
	public Book getBook(int idBook);

}
