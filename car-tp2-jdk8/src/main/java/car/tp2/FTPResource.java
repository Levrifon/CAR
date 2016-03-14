package car.tp2;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.StreamingOutput;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.cxf.jaxrs.ext.multipart.Multipart;

@Path("/ftp")
public class FTPResource {
	FTPClient client;
	final String url = "localhost:8080/rest/tp2/ftp";
	final String accessForbidden = "<h1> You are not connected, no access is possible </h1>";
	FTPClientConfig conf;
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
		try {
			final InputStream inputstream = client.retrieveFileStream(path);
			outputstream = new StreamingOutput() {
				@Override
				public void write(OutputStream os) throws IOException,
						WebApplicationException {
					int lect;
					while ((lect = inputstream.read()) != -1) {
						os.write(lect);
					}
				}
			};
		} catch (IOException e) {
			e.printStackTrace();
		}
		return outputstream;

	}
	
	@DELETE
	@Produces("application/octet-stream")
	@Path("/deleted/{path: .*}")
	public String delete(@PathParam("path") String path) {
		if (client == null || !client.isConnected()) {
			return accessForbidden;
		}
		try {
			client.deleteFile(path);
		} catch (IOException e) {
			return "<h2><p> Impossible de supprimer " + path + " </p></h2>";
		}
		return "<h2><p> Suppression réussie + " + path + " </p></h2>";
	}

	@GET
	@Produces("text/html")
	@Path("/connect/{user}/{pwd}")
	public String connect(@PathParam("user") String user,@PathParam("pwd") String pwd) {
		boolean connection = false;
		/* si l'utilisateur est déjà connecte on affiche un message le précisant */
		if (client != null) {
			if (client.isConnected()) {
				return "<h2><p> Vous etes deja connecte + " + user
						+ " </p></h2>";
			}
		}
		try {
			client = new FTPClient();
			conf = new FTPClientConfig(FTPClientConfig.SYST_L8);
			conf.setUnparseableEntries(true);
			client.connect("localhost", 4444);
			client.configure(conf);
			connection = client.login(user, pwd);
		} catch (SocketException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		/* si la connexion a reussi (bon login et mot de passe */
		if (connection) {
			return "<h2><p>Bienvenue " + user + " ! </p></h2>";
		}
		return "<h2><p> Mauvais identifiant et/ou mot de passe </p></h2>";
	}

	@GET
	@Produces("text/html")
	@Path("/list/")
	public String listFiles() {
		StringBuilder builderhtml = new StringBuilder();
		FTPFile[] filelist = null;
		try {
			filelist = client.listFiles();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (filelist == null || filelist.length == 0) {
			builderhtml.append("<h2><p> Repertoire Vide </p></h2>");
		} else {
			builderhtml.append("<table>");
			for (FTPFile file : filelist) {
				/* si ce n'est pas un dossier on peut cliquer pour télécharger le fichier directement */
				if(!file.isDirectory()) {
					builderhtml.append("<tr><td><p><a href=" + this.url + "/file/"+ file + ">");
					/* si c'est un dossier on réappelle cette methode sur le dossier choisi*/
				}else {
					builderhtml.append("<tr><td><p><a href=" + this.url + "/list/"+ file + "/>");
				}
				builderhtml.append(file);
				builderhtml.append("</a></p></td></tr>");
			}
			builderhtml.append("</table>");
		}
		System.out.println(client.getReplyString());
		return builderhtml.toString();
	}

	@GET
	@Produces("text/html")
	@Path("/pwd/")
	public String pwd() {
		try {
			return "<h1>" + client.printWorkingDirectory() + "</h1>";
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "<h1> Problem during access of Working Directory </h1>";
	}

	@GET
	@Produces("text/html")
	@Path("/cwd/{directory}")
	public String cwd(@PathParam("directory") String directory) {
		if (!client.isConnected()) {
			return accessForbidden;
		}
		try {
			client.cwd(directory);
		} catch (IOException e) {
			return "<h1> Erreur lors du changement de directory </h1>";
		}
		try {
			return "<h1> Current directory is : "
					+ client.printWorkingDirectory() + "</h1>";
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "<h1> Erreur lors du changement de directory </h1>";
	}

	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces("text/html; charset=UTF-8")
	@Path("/post/{name}")
	public String postFile(@Multipart("name") String file) {
		InputStream input;
		if (!client.isConnected()) {
			return accessForbidden;
		}
		boolean termine = false;
		try {
			input = new FileInputStream(file);
			termine = client.storeFile(file, input);
			input.close();
		} catch (IOException e) {
			return "<h1>" + client.getReplyString() + "</h1>";
		}
		if (termine) {
			return "<h1> Transfert terminé </h1>";
		}
		return "<h1> Transfert interrompu </h1>";
	}
}
