package car.tp4;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class BookManager{
	@PersistenceContext
	private EntityManager em;
	
	public void init() {
		Query q  = em.createQuery("DELETE FROM Book");
		q.executeUpdate();
		em.flush();
		em.clear();
		Book b1 = new Book(("J.K Rowling"),"Harry Potter", "1998");
		Book b2 = new Book("Victor Hugo","Les Miserables","1800");
		Book b3 = new Book("Enterprise Java Beans","Lionel Seinturier","2145");
		
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
		Query q = em.createQuery("SELECT b FROM Book b");
		return q.getResultList();
	}
	
	public List<String> getAllAuthors() {
		//Query q = em.createQuery("SELECT name_author from Author");
		//return q.getResultList();
		
		Query q = em.createQuery("SELECT b FROM Book b");
		List<Book> listLivres = (List<Book>) q.getResultList();
		List<String> listAuteurs = new ArrayList<String>();
		for (Book l : listLivres) {
			if(l.getAuthor() != null) {
				listAuteurs.add(l.getAuthor().toString());
			}
		}
		return listAuteurs;
	}

}
