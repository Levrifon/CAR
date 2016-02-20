package server;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Scanner;

public class Serveur {
	@SuppressWarnings("unused")
	private int port;
	private File directory;
	private ServerSocket serversocket;
	private HashMap<String,String> accounts;
	
	public Serveur(File f, int port) {
		this.port = port;
		this.directory = f;
		accounts = new HashMap<String,String>();
			try {
				serversocket = new ServerSocket(port);
			} catch (IOException e) {
				e.printStackTrace();
				System.err.println("Error during creation of socket");
			}
	}
	
	public void waitConnexion() {
		Socket sck;
		FtpRequest request;
		while(true) {
			try {
				sck = serversocket.accept();
				request = new FtpRequest(sck,this);
				request.start();
			} catch (IOException e) {
				System.err.println("Error during accept of socket");
				break;
			}
		}
	}
	
	public File getCurrentDirectory() {
		return this.directory;
	}
	
	public void addAccount(String user, String pwd) {
		accounts.put(user,pwd);
		File file = new File(directory.getAbsolutePath() + "/" + user);
		if(!file.exists()) {
			file.mkdir();
		}
	}
	
	public boolean isOnline() {
		return this.serversocket.isBound();
	}

	public static void main(String[] args) {
		File dir;
		int port;
		System.out.println("Please specify directory and port for the server :");
		System.out.println("Directory:");
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		dir = new File(scanner.nextLine());
		if(!dir.exists()) {
			dir.mkdir();
		}
		System.out.println("Port:");
		while((port = Integer.parseInt(scanner.nextLine())) < 1024) {
			System.err.println("Port number forbidden");
			port = Integer.parseInt(scanner.nextLine());
		}
		Serveur server = new Serveur(dir,port);
		server.addAccount("guest","guest");
		server.addAccount("admin","1234");
		server.addAccount("titi","password");
		server.waitConnexion();
	}

	public boolean connect(String user, String pwd) {
		if(!accounts.containsKey(user)) { return false;}
		return accounts.get(user).equals(pwd);
	}

	public boolean userExists(String user) {
		return accounts.containsKey(user);
	}
	
	public void closeServer() {
		try {
			serversocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
