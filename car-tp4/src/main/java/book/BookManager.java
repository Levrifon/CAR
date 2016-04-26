package book;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


public class BookManager{
	@PersistenceContext
	private EntityManager em;
	
	public void init() {
		Book b1 = new Book(new Author("J.K Rowling"),"Harry Potter");
		Book b2 = new Book(new Author("Victor Hugo"),"Les Miserables");
		Book b3 = new Book(new Author("Enterprise Java Beans"),"Lionel Seinturier");
		
		/* ajoute les trois livres dans la table */
		em.persist(b1);
		em.persist(b2);
		em.persist(b3);
	}
	
	/**
	 * ajoute le book dans la base
	 * @param book to add
	 */
	public void addBook(Book book) {
		em.persist(book);
	}
	
	@SuppressWarnings("unchecked")
	public List<Book> getAllBooks() {
		Query q = em.createQuery("SELECT id,author,title FROM Book");
		return q.getResultList();
	}
	
	public List<Book> getAllAuthors() {
		Query q = em.createQuery("SELECT name_author from Author");
		return q.getResultList();
	}

}
