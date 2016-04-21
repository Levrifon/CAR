package main.java.session;

import javax.ejb.Local;

import main.java.book.Author;


@Local
public interface BookItf {
	public boolean addBook(Author author, String title);
	public boolean removeBook(long id);
	public boolean findAllBook(String author);

}
