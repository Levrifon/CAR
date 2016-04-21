package main.java.book;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


public class BookManager{
	@PersistenceContext
	private EntityManager em;
	
	public void init() {
		Book b1 = new Book("J.K Rowling","Harry Potter");
		Book b2 = new Book("Victor Hugo","Les Miserables");
		Book b3 = new Book("Enterprise Java Beans","Lionel Seinturier");
		
		/* ajoute les trois livres dans la table */
		em.persist(b1);
		em.persist(b2);
		em.persist(b3);
	}

}
