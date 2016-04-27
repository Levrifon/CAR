package book;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Author {

	/*@Id
	private long id;*/
	private String name;
	private Collection<Book> books;

	public Author() {
		books = new ArrayList<Book>();
	}

	public Author(String name) {
		this.name = name;
	}
/* exception java si je l'active
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}*/

	@OneToMany
	public Collection<Book> getBooks() {
		return books;
	}

	public void setBooks(Collection<Book> books) {
		this.books = books;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	@Override
	public String toString() {
		return name;
	}
}
