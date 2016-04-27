package session;


import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import book.Author;
import book.Book;
@Stateless(name="myBook")
public class BookBean implements BookItf {
	@PersistenceContext
	private EntityManager em;
	
	@Override
	public boolean addBook(Author author, String title,String year) {
		Book b = new Book(author, title, year);
		em.persist(b);
		return true;
	}

	@Override
	public boolean removeBook(long id) {
		if(em.find(Book.class,id) != null) {
			em.remove(em.find(Book.class,id));
			return true;
		}
		return false;
	}

	@Override
	public boolean findAllBook(String author) {
		em.find(Book.class,author);
		return false;
	}
}
