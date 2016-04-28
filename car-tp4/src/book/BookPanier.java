package book;

import java.util.HashMap;

import javax.ejb.EJB;

import bookservlet.LibraryItf;

public class BookPanier {
	@EJB(name="library")
	private LibraryItf library;
	/*la hashmap represente le livre et sa quantité*/
	private HashMap<Book,Integer> mycart = new HashMap<Book,Integer>();
	
	public void addToCart(int idBook, int quantity) {
		Book book = library.getBook(idBook);
		if(book != null) {
			if(mycart.containsKey(book)) {
				mycart.put(book,mycart.get(book) +quantity);
			} else {
				mycart.put(book,quantity);
			}
		}
	}
}
