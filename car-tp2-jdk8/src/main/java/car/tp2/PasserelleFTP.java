package car.tp2;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Path("/ftp")
public class PasserelleFTP {
	
	@GET
	@Produces("text/html")
	public String sayHello() {
		return "<h1>Hello FTP</h1>";
	}

}
