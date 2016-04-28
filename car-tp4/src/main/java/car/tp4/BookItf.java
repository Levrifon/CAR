package car.tp4;
import javax.ejb.Local;

@Local
public interface BookItf {
	public boolean addBook(String author, String title,String year);
	public boolean removeBook(long id);
	public boolean findAllBook(String author);

}
