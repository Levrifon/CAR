package main.java.session;

import javax.ejb.Local;


@Local
public interface BookItf {
	public boolean addBook(String author, String title);
	public boolean removeBook(long id);
	public boolean findAllBook(String author);

}