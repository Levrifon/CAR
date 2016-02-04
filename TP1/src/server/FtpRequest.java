package server;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;

import returncode.ReturnCode;

public class FtpRequest extends Thread {
	private Socket socket;
	private PrintWriter output;
	private BufferedReader input;
	private boolean isIdentified, isConnected;
	private Serveur serv;
	private String user;
	private File dir;

	public FtpRequest(Socket sck, Serveur serv) throws IOException {
		this.socket = sck;
		this.serv = serv;
		input = new BufferedReader(new InputStreamReader(
				socket.getInputStream()));
		output = new PrintWriter(socket.getOutputStream(), true);
		isIdentified = false;
		dir = serv.getCurrentDirectory();
	}

	@Override
	public void run() {
		sendServiceOK();
	}

	private void sendServiceOK() {
		output.println(ReturnCode.serviceOK());
		while (!isConnected) {
			processRequest();
		}
		while (isConnected) {
			processRequest();
		}
	}

	private void processRequest() {
		String arg = "";
		String message = null, cmd;
		String[] received = null;
		try {
			message = input.readLine();
			System.out.println("Message : " + message);

		} catch (IOException e) {
			System.err.println("Error during reading");
		}
		if (message.isEmpty()) {
			output.println(ReturnCode.wrongSequence());
			return;
		}
		received = message.split(" ", 2);
		if (received.length < 2) {
			if (!(received[0].equals("LIST") || received[0].equals("QUIT") || received[0]
					.equals("PWD"))) {
				output.println(ReturnCode.wrongSequence());
				return;
			}
		}
		cmd = received[0];
		switch (cmd) {
		case "USER":
			arg = received[1];
			processUSER(arg);
			break;
		case "PASS":
			arg = received[1];
			processPASS(arg);
			break;
		case "LIST":
			processLIST();
			break;
		case "STOR":
			arg = received[1];
			processSTOR(arg);
			break;
		case "PWD":
			processPWD();
			break;
		case "CWD":
			arg = received[1];
			processCWD(arg);
		case "QUIT":
			processQUIT();
		default:
			output.println(ReturnCode.wrongSequence());
			break;
		}
	}

	private void processQUIT() {
		this.isIdentified = false;
		this.isConnected = false;
		interrupt();

	}

	private void processSTOR(String arg) {
		if (!isConnected) {
			output.println(ReturnCode.nonAuth());
			return;
		}
		File f = new File(arg);
		if (!f.exists()) {
			output.println(ReturnCode.fileNotFound());
			return;
		}
		try {
			System.out.println("Coucou");
			Files.copy(Paths.get(f.getPath()),
					Paths.get(this.dir.getPath() + f.getName()),
					REPLACE_EXISTING);
		} catch (IOException e) {
			System.err.println("Error during copy of file");
		}
	}

	private void processLIST() {
		if (!isConnected) {
			output.println(ReturnCode.nonAuth());
			return;
		}
		File[] listFiles = dir.listFiles();
		if (listFiles.length == 0) {
			System.out.println("Empty dir");
			return;
		}

		for (File f : dir.listFiles()) {
			output.println(f.getName());
		}
	}

	private void processPASS(String pwd) {
		if (isConnected) {
			output.println(ReturnCode.nonAuth());
			return;
		}
		if (isIdentified) {
			if (serv.connect(user, pwd)) {
				output.println(ReturnCode.authSucc());
				isConnected = true;
				System.out.println("Successful Connect!");
			} else {
				output.println(ReturnCode.authFail());
				return;
			}
		} else {
			output.println(ReturnCode.wrongSequence());
			return;
		}
	}

	private void processPWD() {
		if (!isConnected) {
			output.println(ReturnCode.nonAuth());
			return;
		}
		output.println(dir.getAbsolutePath());

	}
	
	private void processCWD(String directory) {
		File f = new File(directory);
		if(!f.exists() || !(f.isDirectory())) {
			output.println(ReturnCode.fileNotFound());
			return;
		}
		this.dir = f;
		
	}

	private void processUSER(String user) {
		if (isIdentified) {
			output.println(ReturnCode.wrongSequence());
		}
		if (serv.userExists(user)) {
			this.user = user;
			output.println(ReturnCode.waitPwd());
			this.isIdentified = true;
		} else {
			output.println(ReturnCode.authFail());
		}
	}
}
