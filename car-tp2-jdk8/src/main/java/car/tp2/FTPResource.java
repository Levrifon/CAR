package car.tp2;

import java.io.File;
import java.io.IOException;
import java.net.SocketException;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.apache.commons.net.ftp.FTPClient;
@Path("/ftp")
public class FTPResource {
	FTPClient client;
	@GET
	@Produces("text/html")
	public String sayHello() {
		return "<h1>Hello FTP</h1>";
	}

	@GET
	@Produces("application/octet-stream")
	@Path("/file/{path}")
	public File get(@PathParam("path") String path) {
		File file = new File(path);
		if(file.exists()) {
			return file;
		}
		return null;
	}
	@POST
	@Produces("application/octet-stream")
	@Path("/post/{path}")
	public boolean post(@PathParam("name") String name) {
		File f = new File(name);
		if(f.exists()) {return false; }
		return true;
	}
	
	@GET
	@Produces("text/html")
	@Path("/connect/{user}/{pwd}")
	public String connect(@PathParam("user") String user ,@PathParam("pwd") String pwd){
		try {
			client = new FTPClient();
			client.connect("127.0.0.1",4444);
		} catch (SocketException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try {
			System.out.println("Resultat de la connexion : " + client.login(user, pwd));
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Termine");
		return "<h2><p>Bienvenue "  + user + " ! </p></h2>";
	}
}
