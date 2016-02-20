package test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import org.junit.Before;
import org.junit.Test;

import server.FtpRequest;
import server.Serveur;

public class TestFtpRequest {
	private Serveur s;
	private PrintWriter testOutput;
	private BufferedReader testInput;

	@Before
	public void init() {
		s = new Serveur(new File("toto"),8080);
	}
	
	@Test
	public void testServerisOnline() {
		assertTrue(s.isOnline());
		s.closeServer();
	}
	
	@Test
	public void testServiceOK() throws IOException {
		Socket sc = null;
		FtpRequest req = null;
		try {
			sc = new Socket("127.0.0.1", 8080);
			req = new FtpRequest(sc, s);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			testOutput = new PrintWriter(sc.getOutputStream(),true);
			testInput = new BufferedReader(new InputStreamReader(sc.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		req.start();
		assertTrue(req.isAlive());
		s.closeServer();
	}
	
	@Test
	public void testCloseServer() {
		assertTrue(s.isOnline());
		s.closeServer();
		assertFalse(s.isOnline());
	}

}
