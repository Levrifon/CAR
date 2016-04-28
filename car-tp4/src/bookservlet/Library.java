package bookservlet;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;

import book.Author;
import book.Book;

public class Library implements LibraryItf {
	@PersistenceContext(unitName = "booklibrary", type = PersistenceContextType.EXTENDED)
	private EntityManager em;

	@Override
	public List<Book> getAllBooks() {
		Query q = em.createQuery("SELECT id,author,title FROM Book");
		return q.getResultList();
	}

	@Override
	public List<Author> getAllAuthors() {
		Query q = em.createQuery("SELECT name_author from Author");
		return q.getResultList();
	}

	@Override
	public void addBook(Book book) {
		/* retourne l'auteur qui a créé ce livre */
		Author a = em.find(Author.class, book.getAuthor().toString());
		if (a == null) {
			/* si il n'existe pas on l'ajoute dans l'em */
			em.persist(book.getAuthor());
			em.persist(book);
			return;
		} else {
			em.persist(book);
		}
	}

	@Override
	public void init() {
		Book b1 = new Book(new Author("J.K Rowling"), "Harry Potter", "1998");
		Book b2 = new Book(new Author("Victor Hugo"), "Les Miserables", "1800");
		Book b3 = new Book(new Author("Enterprise Java Beans"),
				"Lionel Seinturier", "2145");

		/* ajoute les trois livres dans la table */
		em.persist(b1);
		em.persist(b2);
		em.persist(b3);

	}

	@Override
	public Book getBook(int idBook) {
		Query q = em.createQuery("SELECT author,title from Book WHERE id="
				+ idBook);
		return (Book) q.getResultList().get(0);
	}

}
