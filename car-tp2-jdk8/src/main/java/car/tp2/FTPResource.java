package car.tp2;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.SocketException;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.StreamingOutput;

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
	@Path("/file/{path: .*}")
	public StreamingOutput get(@PathParam("path") String path) {
		
		StreamingOutput outputstream = null;
		StreamingOutput streamingOutput;
		try {
			final InputStream inputstream = client.retrieveFileStream(path);
			outputstream = new StreamingOutput() {
				@Override
				public void write(OutputStream os) throws IOException,
						WebApplicationException {
					int lect;
					while((lect = inputstream.read()) != -1) {
						os.write(lect);
					}
					
				}
			};
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return outputstream;
		
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
		boolean connection;
		try {
			client = new FTPClient();
			client.connect("localhost",4444);
			connection = client.login(user, pwd);
		} catch (SocketException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		System.out.println("Termine");
		return "<h2><p>Bienvenue "  + user + " ! </p></h2>";
	}
	@GET
	@Produces("text/html")
	@Path("/list/")
	public String listFiles() {
		StringBuilder builderhtml = new StringBuilder();
		String[] filelist = null;
		try {
			filelist = client.listNames();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(filelist == null || filelist.length == 0) { 
			builderhtml.append("<h2><p> Repertoire Vide </p></h2>");
		}else {
			builderhtml.append("<table>");
			for (String file : filelist) {
				builderhtml.append("<tr><td><p>");
				builderhtml.append(file);
				builderhtml.append("</p></td></tr>");
			}
			builderhtml.append("</table>");
		}
		System.out.println(client.getReplyString());
		return builderhtml.toString();
	}
}
