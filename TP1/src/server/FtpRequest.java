package server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import returncode.ReturnCode;

public class FtpRequest extends Thread {
	private Socket socket;
	private Socket socketData;
	private PrintWriter output;
	private OutputStreamWriter outputData;
	private BufferedReader inputData;
	private BufferedReader input;
	private boolean isIdentified, isConnected;
	private Serveur serv;
	private String user;
	private File dir;
	private File userDir;

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
					.equals("PWD")) || received[0].equals("SYST")) {
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
		case "SYST":
			processSYST();
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
			break;
		case "QUIT":
			processQUIT();
			break;
		case "PORT":
			arg = received[1];
			processPORT(arg);
			break;
		case "RETR":
			arg = received[1];
			processRETR(arg);
			break;
		case "GET":
			/*TODO*/
			break;
		default:
			output.println(ReturnCode.wrongSequence());
			break;
		}
	}

	private void processRETR(String arg){
		InputStream flux;
		InputStreamReader reader;
		BufferedReader buff = null;
		String currLine ="",line ="";
		String pathSrc = this.dir.getAbsolutePath() +"/" + arg;
		File file = new File(pathSrc);
		try {
			flux = new FileInputStream(file);
			reader = new InputStreamReader(flux);
			buff = new BufferedReader(reader);

			/*
			 * si le fichier n'existe pas on renvoit au client le return code
			 * correspondant
			 */
			if (!file.exists()) {
				output.println(ReturnCode.fileNotFound());
				output.println(ReturnCode.finishTransfert());
				flux.close();
				reader.close();
				buff.close();
				return;
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		output.println(ReturnCode.fileStatusOk());
		try {
			while((currLine = buff.readLine())!=null) {
				line += currLine + '\n';
			}
			buff.close();
			outputData.write(line);
			outputData.flush();
			outputData.close();
		} catch (FileNotFoundException e) {
			output.println(ReturnCode.fileNotFound());
		} catch (IOException e) {
			e.printStackTrace();
		}

		output.println(ReturnCode.finishTransfert());
	}

	private void processSYST() {
		output.println(ReturnCode.systType());
	}

	private void processPORT(String arg) {
		String[] argaddr = arg.split(",", 6);
		String myaddr = "";
		for (int i = 0; i < 4; i++) {
			if (i < 3) {
				myaddr += argaddr[i] + ".";
			} else {
				myaddr += argaddr[i];
			}
		}
		Integer argport = Integer.parseInt(argaddr[4]) * 256
				+ Integer.parseInt(argaddr[5]);
		InetAddress addr;
		try {
			addr = InetAddress.getByName(myaddr);
			socketData = new Socket(addr, argport);
			outputData = new OutputStreamWriter(socketData.getOutputStream());
			inputData = new BufferedReader(new InputStreamReader(socketData.getInputStream()));
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		output.println(ReturnCode.serviceOK());
	}

	private void processQUIT() {
		this.isIdentified = false;
		this.isConnected = false;
		output.write(ReturnCode.quit());
		output.write('\n');
		output.flush();
	}

	private void processSTOR(String arg) {
		String line = "";
		String path = userDir.getAbsolutePath() + "/" + arg;
		String currLine;
		FileOutputStream outputFile = null;
		BufferedReader buff = null;
		if (!isConnected) {
			output.println(ReturnCode.nonAuth());
			return;
		}
		File f = new File(path);
		if (!f.exists()) {
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			outputFile = new FileOutputStream(f);
			buff = new BufferedReader(inputData);
			output.println(ReturnCode.startTransfert());
			while ((currLine = buff.readLine()) != null) {
				line += currLine + '\n';
			}
			buff.close();
			outputFile.write(line.getBytes());
			outputData.flush();
			outputData.close();
			output.println(ReturnCode.finishTransfert());
		} catch (IOException e) {
			System.err.println("Error during copy of file");
		}
	}

	private void processLIST() {
		System.out.println("Current dir : " + this.userDir.getName());
		if (!isConnected) {
			output.println(ReturnCode.nonAuth());
			return;
		}
		File[] listFiles = userDir.listFiles();
		if (listFiles.length == 0) {
			output.println(ReturnCode.actionNotTaken());
			try {
				outputData.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}
		try {
			output.println(ReturnCode.startTransfert());
			for (File f : listFiles) {
				outputData.write(f.getName());
				outputData.write('\n');
			}
			output.println(ReturnCode.finishTransfert());
			outputData.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void processPASS(String pwd) {
		if (isConnected) {
			output.println("Already connected");
			return;
		}
		if (isIdentified) {
			if (serv.connect(user, pwd)) {
				output.println(ReturnCode.authSucc());
				isConnected = true;
				this.userDir = new File(dir.getAbsolutePath() + "/" + user);
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
		output.write(userDir.getAbsolutePath());
		output.write('\n');
		output.flush();
	}

	private void processCWD(String directory) {
		/* verifie si le dossier dans lequel on veut naviguer correspond déjà au dossier de l'utilisateur */
		if(sameDirectory(directory)) {
			output.println(ReturnCode.fileNotFound());
			return;
		}
		/* si on veut retourner dans le dossier précédent, le cwd devient le parent */
		if (directory.equals("..")) {
			if(userDir.getParent().equals(dir)) {
				output.println(ReturnCode.notAllowed());
				return;
			}
			userDir = new File(userDir.getParent());
		} else {
			File f = new File(userDir.getAbsolutePath() + "/" + directory);
			if (!f.exists() || !(f.isDirectory())) {
				output.println(ReturnCode.fileNotFound());
				return;
			}
			this.userDir = f;
		}
		output.println(ReturnCode.fileActionOkay());
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
	
	private boolean sameDirectory(String directory) {
		return (dir.getAbsolutePath() + "/" + directory).equals(userDir.getAbsolutePath());
	}
}
