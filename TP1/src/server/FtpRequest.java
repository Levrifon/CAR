package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import returncode.ReturnCode;

public class FtpRequest extends Thread {
	private Socket socket;
	private PrintWriter output;
	private BufferedReader input;
	private boolean isIdentified,isConnected;
	private Serveur serv;

	public FtpRequest(Socket sck, Serveur serv) throws IOException {
		this.socket = sck;
		this.serv = serv;
		input = new BufferedReader(new InputStreamReader(
				socket.getInputStream()));
		output = new PrintWriter(socket.getOutputStream(), true);
		isIdentified = false;
	}

	@Override
	public void run() {
		sendServiceOK();
	}

	private void sendServiceOK() {
		output.println(ReturnCode.SERVICE_OK + " Service OK");
		while (!isIdentified) {
			waitUserConnection();
		}
		while(isConnected) {
			waitUserCommand();
		}
	}

	private void waitUserCommand() {
		// TODO Auto-generated method stub
		
	}

	private void waitUserConnection() {
		String user = "", pwd = "";
		String message = null, cmd;
		String[] received = null;
		try {
			message = input.readLine();

		} catch (IOException e) {
			System.err.println("Error during reading");
		}
		received = message.split(" ",2);
		if (received.length < 2) {
			output.println(ReturnCode.ERR_CMD + " Wrong command sequence");
			return;
		}
		cmd = received[0];
		if (!cmd.equals("USER")) {
			output.println(ReturnCode.ERR_CMD + " Wrong command sequence");
			return;
		}
		user = received[1];
		output.println();
		if (serv.userExists(user)) {
			output.println(ReturnCode.WAIT_MDP + " Waiting password");
			try {
				message = input.readLine();
			} catch (IOException e) {
				System.err.println("Error during reading");
			}
			received = message.split(" ",2);
			cmd = received[0];
			if (!cmd.equals("PASS")) {
				output.println(ReturnCode.ERR_CMD + " Wrong command sequence");
				return;
			}
			pwd = received[1];
			output.println();
			if (serv.connect(user, pwd)) {
				output.println(ReturnCode.AUTH_SUCC
						+ " Authentification succeed");
				isIdentified = true;
			} else {
				output.println(ReturnCode.AUTH_FAIL + " Wrong password");
			}
		} else {
			output.println(ReturnCode.AUTH_FAIL + " Wrong user name");
		}
	}
}
