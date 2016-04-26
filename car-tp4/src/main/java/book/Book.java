package book;
import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
/*@Table(name="Book") par défaut la table vaut ceci*/
public class Book implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	private Author author;
	private String title;
	
	public Book(Author aut, String title) {
		this.author = aut;
		this.title = title;
	}
	public Book(){}
	
	
	public long getId() {
		return this.id;
	}
	public Author getAuthor() {
		return this.author;
	}
	public void setAuthor(Author auth) {
		this.author = auth;
	}
	
	public String getTitle() {
		return this.title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
}
