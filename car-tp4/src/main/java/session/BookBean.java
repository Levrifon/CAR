package session;

import java.awt.print.Book;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
@Stateless(name="myBook")
public class BookBean implements BookItf {
	@PersistenceContext
	private EntityManager em;
	@Override
	public boolean addBook(String author, String title) {
		book.Book b = new book.Book(author, title);
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

}
