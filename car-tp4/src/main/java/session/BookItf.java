package session;

import javax.ejb.Local;

import book.Author;



@Local
public interface BookItf {
	public boolean addBook(Author author, String title);
	public boolean removeBook(long id);
	public boolean findAllBook(String author);

}
